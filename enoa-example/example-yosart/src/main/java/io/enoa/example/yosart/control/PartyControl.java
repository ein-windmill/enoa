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

import io.enoa.example.yosart.entity.Party;
import io.enoa.example.yosart.handler.PartyGoHandler;
import io.enoa.example.yosart.interceptor.GoPartyInterceptor;
import io.enoa.example.yosart.valid.PartyGoValid;
import io.enoa.ext.bea.beaction.Before;
import io.enoa.ext.bea.beaction.Clear;
import io.enoa.json.kit.JsonKit;
import io.enoa.log.kit.LogKit;
import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.Method;
import io.enoa.toolkit.map.Kv;
import io.enoa.yosart.kernel.anno.Action;
import io.enoa.yosart.kernel.resources.YoControl;

import java.util.Date;

public class PartyControl extends YoControl<PartyControl> {

  public void index() {
    String context = super.request().context();
    LogKit.debug(context);
    LogKit.debug(JsonKit.toJson(super.paraNames()));
    for (String s : super.paraNames()) {
      LogKit.debug("{}={}", s, super.para(s));
    }
    LogKit.debug(JsonKit.toJson(super.headerNames()));
    LogKit.debug(JsonKit.toJson(super.variableNames()));
    LogKit.debug(JsonKit.toJson(super.attrNames()));
  }

  @Action(method = {Method.GET, Method.POST}, uri = "/go")
  @Before({GoPartyInterceptor.class, PartyGoValid.class, PartyGoHandler.class})
  @Clear(GoPartyInterceptor.class)
  public Object go(Party party, int stat, long id, byte b, char c, String name, String where, Date ctime) {
    String context = super.request().context();
    LogKit.debug(context);

    LogKit.debug(JsonKit.toJson(super.headerNames()));
    LogKit.debug(JsonKit.toJson(super.variableNames()));
    LogKit.debug(JsonKit.toJson(super.attrNames()));

    super.header("from", "CoC.");
    super.cookie(new Cookie.Builder().name("k").value("v").build());
    super.cookie(new Cookie.Builder().name("z").value("y").build());

    super.renderText("LogKit.debug(JsonKit.toJson(super.headerNames()));");
    return null;
  }

  @Action("/finish/:id/where/:where/:times")
  public void finish(Date time) {
    LogKit.debug(JsonKit.toJson(super.variableNames()));
    LogKit.debug(JsonKit.toJson(super.paraNames()));
    super.forward("/party/again/aaa/where/JNS/2/go");
  }

  @Action("/again/:id/where/:where/:times")
  public void again0(String where) {
    Kv vars = Kv.create();
    for (String name : super.variableNames()) {
      vars.set(name, super.variable(name));
    }
    Kv paras = Kv.create();
    for (String name : super.paraNames()) {
      paras.set(name, super.para(name));
    }
    Kv rets = Kv.create().set("vars", vars).set("paras", paras);
    super.renderJson(rets);
  }

  @Action("/again/:id1/where/:where1/:times1/go")
  public void again1() {
    LogKit.debug(JsonKit.toJson(super.variableNames()));
    LogKit.debug(JsonKit.toJson(super.paraNames()));
  }

  /**
   * @title Party again
   * @summary party again get api
   * @method get
   * @contentType form
   * @para name=id2 arch=restful type=long def=null
   * @para name=where2 arch=restful type=string def=null
   * @para name=times2 arch=restful type=long def=null
   */
  @Action("/again/:id2/:where2/:times2")
  public void again2() {
    LogKit.debug(JsonKit.toJson(super.variableNames()));
    LogKit.debug(JsonKit.toJson(super.paraNames()));
  }

  @Action(method = Method.GET, value = "/rest/:id")
  public void restGet() {
    LogKit.debug(super.variable("id"));
    super.renderText("get");
  }

  @Action(method = Method.PUT, value = "/rest/:id")
  public void restPut() {
    LogKit.debug(super.variable("id"));
    super.renderText("put");
  }

  @Action(method = {Method.DELETE, Method.PATCH}, value = "/rest/:id")
  public void restOther() {
    LogKit.debug(super.variable("id"));
    super.renderText("delete patch");
  }
}
