/*
 * Copyright (c) 2018, enoa (fewensa@enoa.io)
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
import io.enoa.eml.EoEmlSession;
import io.enoa.toolkit.text.TextKit;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class EnoaEmlSession implements EoEmlSession {

  private EmlConfig config;
  private Session sess;

  public EnoaEmlSession(EmlConfig config) {
    this.config = config;
  }

  @Override
  public Session sess(EmlProtocol protocol) {
    if (this.sess != null)
      return this.sess;

    Properties prop = new Properties();
    prop.put("mail.transport.protocol", protocol.val());
    prop.put(this.confName(protocol, "ssl.enable"), this.config.ssl());
    prop.put(this.confName(protocol, "host"), this.config.host());
    if (this.config.port() > 0)
      prop.put(this.confName(protocol, "port"), this.config.port());
    prop.put(this.confName(protocol, "auth"), this.config.auth());

    this.fillOther(protocol, prop);

    if (this.config.auth()) {
      this.sess = Session.getDefaultInstance(prop, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(config.user(), config.passwd());
        }
      });
    } else {
      this.sess = Session.getDefaultInstance(prop);
    }
    if (this.config.debug())
      this.sess.setDebug(Boolean.TRUE);

    return this.sess;
  }

  @Override
  public EmlConfig config() {
    return this.config;
  }

  private String confName(EmlProtocol protocol, String name) {
    return TextKit.union("mail.", protocol.val(), ".", name);
  }

  private void fillOther(EmlProtocol protocol, Properties prop) {
    if (this.config.prop() == null)
      return;
    this.config.prop().forEach(prop::put);
//    this.config.prop().forEach((k, v) -> {
//      if (Is.not().truthy(k))
//        return;
//      if (!k.startsWith(TextKit.union("mail.", protocol.val())))
//        return;
//      prop.put(k, v);
//    });
  }

}
