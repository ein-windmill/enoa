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
package io.enoa.example.email;

import io.enoa.eml.*;
import io.enoa.eml.provider.enoa.EnoaEmlSession;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.digest.UUIDKit;
import io.enoa.toolkit.path.PathKit;
import io.enoa.toolkit.text.TextKit;

public class EmlExample {

  private String email = "a****b@****.com";
  private String passwd = "********";

  private EoEmlSession sesssender() {
    EmlConfig config = new EmlConfig.Builder()
//      .skipError()
//      .debug()
      .ssl()
      .auth()
      .user(this.email)
      .passwd(this.passwd)
      .host("smtp.****.com")
      .port(465)
      .build();
    return new EnoaEmlSession(config);
  }

  private EoEmlSession sessreceiver() {
    EmlConfig config = new EmlConfig.Builder()
//      .skipError()
//      .debug()
      .ssl()
      .auth()
      .user(this.email)
      .passwd(this.passwd)
      .host("pop.****.com")
      .port(995)
      .other("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
      .other("mail.pop3.socketFactory.fallback", "false")
      .other("mail.pop3.socketFactory.port", "995")
      .build();
    return new EnoaEmlSession(config);
  }

  private void send(boolean async) {
    EmlSender sender = Eml.with(this.sesssender())
      .sender()
      .charset(EoConst.CHARSET)
      .richtext()
      .to(this.email)
      .cc(this.email)
      .bcc(this.email)
      .subject(TextKit.union("SUBJECT -> ", UUIDKit.next()))
      .body("<h1>BODY</h1>")
      .attachment(PathKit.debugPath().resolve("file/attachment.txt"))
      .handler(stream -> System.out.println("FROM -> " + stream))
      .handler(stream -> System.out.println("TO -> " + stream.tos()))
      .handler(stream -> System.out.println("REQ BODY -> " + stream.req().string()))
      .handler(stream -> System.out.println("EML -> " + stream.eml().string()))
      .handler(stream -> System.out.println("SUBJECT -> " + stream.subject()));
    if (!async) {
      sender.emit();
      return;
    }

    sender
      .enqueue()
      .done(() -> System.out.println("Done..."))
      .done(() -> {
        throw new RuntimeException("Some exception...");
      })
      .capture(System.err::println)
      .always(() -> System.out.println("Always echo.."));
  }

  private void receive() {
    Eml.with(this.sessreceiver())
      .receiver(EmlProtocol.POP3)
      .inbox()
      .handle(stream -> {
        System.out.println(stream.folder());
      });
  }


  public static void main(String[] args) {
    try {
      EmlExample example = new EmlExample();
      example.send(false);
      example.send(true);
//      example.receive();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
