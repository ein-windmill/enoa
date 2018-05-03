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

import io.enoa.eml.api.stream.MailSenderStream;
import io.enoa.eml.entity.Attachment;
import io.enoa.eml.entity.MailPerson;
import io.enoa.toolkit.binary.EnoaBinary;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

class _EnoaMailSenderData implements MailSenderStream {

  Charset charset;
  MailPerson from;
  Set<MailPerson> tos;
  Set<MailPerson> ccs;
  Set<MailPerson> bccs;
  String subject;
  boolean richtext;
  String body;
  List<Attachment> attachments;
  EnoaBinary req;
  EnoaBinary eml;

  @Override
  public Charset charset() {
    return this.charset;
  }

  @Override
  public MailPerson from() {
    return this.from;
  }

  @Override
  public Set<MailPerson> tos() {
    return this.tos;
  }

  @Override
  public Set<MailPerson> ccs() {
    return this.ccs;
  }

  @Override
  public Set<MailPerson> bccs() {
    return this.bccs;
  }

  @Override
  public String subject() {
    return this.subject;
  }

  @Override
  public boolean richtext() {
    return this.richtext;
  }

  @Override
  public String body() {
    return this.body;
  }

  @Override
  public List<Attachment> attachments() {
    return this.attachments;
  }

  @Override
  public EnoaBinary req() {
    return this.req;
  }

  @Override
  public EnoaBinary eml() {
    return this.eml;
  }

  @Override
  public String toString() {
    return "_EnoaMailSenderData{" +
      "charset=" + charset +
      ", from=" + from +
      ", tos=" + tos +
      ", ccs=" + ccs +
      ", bccs=" + bccs +
      ", subject='" + subject + '\'' +
      ", richtext=" + richtext +
      ", body='" + body + '\'' +
      ", attachments=" + attachments +
      ", req=" + req +
      ", eml=" + eml +
      '}';
  }
}
