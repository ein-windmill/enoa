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
package io.enoa.example.email;

import io.enoa.eml.Eml;
import io.enoa.eml.EmlConfig;
import io.enoa.eml.EmlProtocol;
import io.enoa.eml.EmlSender;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.digest.UUIDKit;
import io.enoa.toolkit.path.PathKit;
import io.enoa.toolkit.text.TextKit;

public class EmlExample {

  private String email = "a****b@****.com";
  private String passwd = "********";

  private void ibe() {
    EmlConfig sconf = new EmlConfig.Builder()
//      .skipError()
//      .debug()
      .ssl()
      .auth()
      .user(this.email)
      .passwd(this.passwd)
      .host("smtp.****.com")
      .port(465)
      .build();
    Eml.epm().install("sender", sconf);



    EmlConfig rconf = new EmlConfig.Builder()
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
    Eml.epm().install("receiver", rconf);
  }

//  private EoEmlSession sesssender() {
//    return new EnoaEmlSession(config);
//  }

//  private EoEmlSession sessreceiver() {
//    return new EnoaEmlSession(config);
//  }

  private void send(boolean async) {
    EmlSender sender = Eml.use("sender")
      .sender()
      .charset(EoConst.CHARSET)
      .richtext()
      .to(this.email)
      .cc(this.email)
      .bcc(this.email)
      .subject(TextKit.union("SUBJECT -> ", UUIDKit.next()))
      .body("<h1>BODY</h1>")
      .attachment(PathKit.debugPath().resolve("file/attachment.txt"))
      .reporter(stream -> System.out.println("FROM -> " + stream.from()))
      .reporter(stream -> System.out.println("TO -> " + stream.tos()))
      .reporter(stream -> System.out.println("REQ BODY -> " + stream.req().string()))
      .reporter(stream -> System.out.println("EML -> " + stream.eml().string()))
      .reporter(stream -> System.out.println("SUBJECT -> " + stream.subject()));
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

  private void receiver() {
    Eml.use("receiver")
      .receiver(EmlProtocol.POP3)
      .inbox()
      .handle(stream -> {
        System.out.println(stream.folder());
      });
  }


  public static void main(String[] args) {
    try {
      EmlExample example = new EmlExample();
      example.ibe();
      example.send(false);
      example.send(true);
//      example.receiver();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
