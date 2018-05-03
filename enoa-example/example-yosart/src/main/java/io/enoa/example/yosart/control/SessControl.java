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
package io.enoa.example.yosart.control;

import io.enoa.example.yosart.entity.Gender;
import io.enoa.example.yosart.entity.User;
import io.enoa.json.kit.JsonKit;
import io.enoa.repeater.http.Cookie;
import io.enoa.toolkit.map.Kv;
import io.enoa.yosart.kernel.http.Session;
import io.enoa.yosart.kernel.resources.YoControl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SessControl extends YoControl<SessControl> {

  /**
   * @api {get} /sess/example Test session
   * @apiVersion 0.1.0
   * @apiName Session test
   * @apiGroup sess
   */
  public void example() {
    Session session = super.session();
    User user = new User();
    user.setId(1);
    user.setNo(1345789L);
    user.setGender(Gender.FMALE);
    user.setName(super.para("name", "Kin"));
    user.setEmail(super.para("email", "test@example.com"));
    session.set("test", user);

    List<String> cks = Stream.of(super.cookies()).map(Cookie::toString).collect(Collectors.toList());
    cks.add("<br><br><br>");
    cks.add(session._value() == null ? null : session._value().toString());

    Kv data = Kv.create();
    String[] names = session.names();
    for (String name : names) {
      data.set(name, session.get(name));
    }
    cks.add("<br><br><br>");
    cks.add(JsonKit.toJson(data));
    super.renderHtml(String.join("<br>", cks));
  }

  public void cookie() {
    Cookie cookie = new Cookie.Builder()
      .name("ckt")
      .value("vkc")
      .build();
    super.cookie(cookie);
    List<String> cks = Stream.of(super.cookies()).map(Cookie::toString).collect(Collectors.toList());
    cks.add("<br><br><br>");
    cks.add(cookie.toString());
    super.renderHtml(String.join("<br>", cks));
  }

}
