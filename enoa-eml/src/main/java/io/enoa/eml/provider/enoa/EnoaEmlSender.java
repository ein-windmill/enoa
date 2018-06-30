/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.enoa.eml.provider.enoa;

import io.enoa.eml.EmlConfig;
import io.enoa.eml.EmlProtocol;
import io.enoa.eml.EmlSender;
import io.enoa.eml.EoEmlSession;
import io.enoa.eml.api.ISenderReporter;
import io.enoa.eml.entity.Attachment;
import io.enoa.eml.entity.MailPerson;
import io.enoa.eml.thr.EoEmailException;
import io.enoa.promise.DonePromise;
import io.enoa.promise.arg.PromiseCapture;
import io.enoa.promise.arg.PromiseVoid;
import io.enoa.promise.builder.EEnodPromiseBuilder;
import io.enoa.promise.builder.PromiseBuilder;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.stream.StreamKit;
import io.enoa.toolkit.text.TextKit;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ExecutorService;

class EnoaEmlSender implements EmlSender {


  private static ExecutorService EXEC_REPORTS = PromiseBuilder.executor().enqueue("EnoaEmail Report Dispatcher");
  private static ExecutorService EXEC_ENQUEUE = PromiseBuilder.executor().enqueue("EnoaEmail Enqueue Dispatcher");

  private Charset charset;
  private MailPerson from;
  private Set<MailPerson> tos;
  private Set<MailPerson> ccs;
  private Set<MailPerson> bccs;
  private String subject;
  private boolean richtext;
  private String body;
  private List<Attachment> attachments;
  private List<ISenderReporter> reporters;

  private EoEmlSession sess;

  EnoaEmlSender(EoEmlSession sess) {
    this.charset = EoConst.CHARSET;
    this.sess = sess;
    this.richtext = false;
  }

  @Override
  public EmlSender charset(Charset charset) {
    this.charset = charset;
    return this;
  }

  @Override
  public EmlSender from(MailPerson from) {
    this.from = from;
    return this;
  }

  @Override
  public EmlSender to(MailPerson person) {
    if (this.tos == null)
      this.tos = new HashSet<>();
    this.tos.add(person);
    return this;
  }

  @Override
  public EmlSender cc(MailPerson person) {
    if (this.ccs == null)
      this.ccs = new HashSet<>();
    this.ccs.add(person);
    return this;
  }

  @Override
  public EmlSender bcc(MailPerson person) {
    if (this.bccs == null)
      this.bccs = new HashSet<>();
    this.bccs.add(person);
    return this;
  }

  @Override
  public EmlSender subject(String subject) {
    this.subject = subject;
    return this;
  }

  @Override
  public EmlSender richtext(boolean richtext) {
    this.richtext = richtext;
    return this;
  }

  @Override
  public EmlSender body(String body) {
    this.body = body;
    return this;
  }

  @Override
  public EmlSender attachment(Attachment attachment) {
    if (attachment == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.attachments_null"));
    if (this.attachments == null)
      this.attachments = new ArrayList<>();
    this.attachments.add(attachment);
    return this;
  }

  @Override
  public EmlSender reporter(ISenderReporter reporter) {
    if (reporter == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.email.send_handler_null"));
    if (this.reporters == null)
      this.reporters = new ArrayList<>();
    this.reporters.add(reporter);
    return this;
  }

  @Override
  public void emit() {
    if (CollectionKit.isEmpty(this.tos))
      throw new EoEmailException(EnoaTipKit.message("eo.tip.email.send_emit_target_null"));

    EmlConfig config = this.sess.config();
    Session session = this.sess.sess(EmlProtocol.SMTP);

    try {

      MimeMessage message = new MimeMessage(session);

      // 发信方
      message.setFrom(this.from == null ?
        new InternetAddress(config.user())
        : this.addr(this.from, this.charset));

      /*
      收信方
       */

      // 收信人
      for (MailPerson to : this.tos) {
        message.addRecipient(Message.RecipientType.TO, this.addr(to, this.charset));
      }
      // 抄送
      if (CollectionKit.notEmpty(this.ccs)) {
        for (MailPerson cc : this.ccs) {
          message.addRecipient(Message.RecipientType.CC, this.addr(cc, this.charset));
        }
      }
      // 密送
      if (CollectionKit.notEmpty(this.bccs)) {
        for (MailPerson bcc : this.bccs) {
          message.addRecipient(Message.RecipientType.BCC, this.addr(bcc, this.charset));
        }
      }

      // 主题
      message.setSubject(this.subject, this.charset.name());

      /*
      邮件内容
       */

      // 最终发送邮件内容
      MimeMultipart finpart = new MimeMultipart();

      // 正文内容
      MimeBodyPart part0 = new MimeBodyPart();
      if (this.richtext) {
        part0.setContent(this.body, "text/html;charset=" + this.charset.name());
      } else {
        part0.setText(this.body, this.charset.name());
      }
      finpart.addBodyPart(part0);

      if (this.attachments != null) {
        // 附件
        for (Attachment attachment : this.attachments) {
          MimeBodyPart part1 = new MimeBodyPart();
          DataHandler dh0 = new DataHandler(new ByteArrayDataSource(attachment.binary(), attachment.mime()));
          part1.setDataHandler(dh0);
          if (attachment.name() == null) {
            part1.setFileName(MimeUtility.encodeText(dh0.getName()));
          } else {
            part1.setFileName(MimeUtility.encodeText(attachment.name(), this.charset.name(), null));
          }
          finpart.addBodyPart(part1);
        }
      }

      finpart.setSubType("mixed");
      message.setContent(finpart);
      message.setSentDate(new Date());
      message.saveChanges();

      this.execHandler(message, config);

      Transport transport = session.getTransport();
      if (config.auth()) {
        transport.connect(this.from == null ? config.user() : this.from.email(), config.passwd());
      } else {
        transport.connect();
      }
      transport.sendMessage(message, message.getAllRecipients());
      transport.close();

    } catch (Exception e) {
      if (!config.skipError())
        throw new EoEmailException(e.getMessage(), e);
      else
        e.printStackTrace();
    }
  }

  @Override
  public DonePromise enqueue() {
    EEnodPromiseBuilder builder = PromiseBuilder.done();
    EXEC_ENQUEUE.execute(() -> {
      String oldName = Thread.currentThread().getName();
      Thread.currentThread().setName(TextKit.union("ENOA-EMAIL-SENDER-ENQUEUE-", oldName));
      try {
        this.emit();

        if (CollectionKit.notEmpty(builder.dones())) {
          for (PromiseVoid done : builder.dones()) {
            done.execute();
          }
        }
      } catch (Exception e) {
        if (CollectionKit.notEmpty(builder.captures())) {
          for (PromiseCapture capture : builder.captures()) {
            capture.execute(e);
          }
        }
      } finally {
        if (builder.always() != null) {
          builder.always().execute();
        }
        Thread.currentThread().setName(oldName);
      }
    });
    return builder.build();
  }

  private InternetAddress addr(MailPerson personal, Charset charset) throws AddressException, UnsupportedEncodingException {
    if (TextKit.isBlank(personal.name()))
      return new InternetAddress(personal.email());
    return new InternetAddress(personal.email(), personal.name(), charset.name());
  }

  private void execHandler(MimeMessage message, EmlConfig config) {
    if (this.reporters == null)
      return;

    EXEC_REPORTS.execute(() -> {
      String oldName = Thread.currentThread().getName();
      Thread.currentThread().setName(TextKit.union("ENOA-EMAIL-SENDER-HANDLER-", oldName));
      try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

        message.writeTo(baos);

        _EnoaMailSenderData ss = new _EnoaMailSenderData();
        ss.charset = this.charset;
        ss.from = this.from == null ? new MailPerson(config.user()) : this.from;
        ss.tos = this.tos;
        ss.ccs = this.ccs == null ? Collections.emptySet() : this.ccs;
        ss.bccs = this.bccs == null ? Collections.emptySet() : this.bccs;
        ss.subject = this.subject;
        ss.richtext = this.richtext;
        ss.body = this.body;
        ss.attachments = this.attachments == null ? Collections.emptyList() : this.attachments;
        ss.eml = EnoaBinary.create(baos.toByteArray(), this.charset);
        try {
          ss.req = StreamKit.binary(message.getInputStream(), this.charset);
        } catch (Exception e) {
          e.printStackTrace();
        }

        for (ISenderReporter reporter : this.reporters) {
          reporter.report(ss);
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        Thread.currentThread().setName(oldName);
      }
    });
  }


}
