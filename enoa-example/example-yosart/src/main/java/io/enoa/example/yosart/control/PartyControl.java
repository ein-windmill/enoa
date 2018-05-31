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

import io.enoa.example.yosart.hook.IndexHook;
import io.enoa.json.Json;
import io.enoa.log.Log;
import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.Method;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.ret.EoResp;
import io.enoa.toolkit.ret.Ret;
import io.enoa.yosart.ext.anost.hook.Hook;
import io.enoa.yosart.ext.anost.para.Para;
import io.enoa.yosart.ext.anost.para.Paras;
import io.enoa.yosart.kernel.anno.Action;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.kernel.resources.YoControl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PartyControl extends YoControl<PartyControl> {

  public void index() {
    String context = super.request().context();
    Log.debug(context);
    Log.debug(Json.toJson(super.paraNames()));
    for (String s : super.paraNames()) {
      Log.debug("{}={}", s, super.para(s));
    }
    Log.debug(Json.toJson(super.headerNames()));
    Log.debug(Json.toJson(super.variableNames()));
    Log.debug(Json.toJson(super.attrNames()));
  }

  @Action(method = {Method.GET, Method.POST}, value = "go")
  @Hook(IndexHook.class)
  @Paras({
    @Para(index = 3, value = "ts", format = "yyyy-MM-dd"),
    @Para(index = 1, value = "where", def = "street #1"),
    @Para(value = "zone", def = "9")
  })
  public Ret go(YoRequest request, String where, int zone, Timestamp ts) {
    String context = super.request().context();
    Log.debug(context);

    Log.debug(Json.toJson(super.headerNames()));
    Log.debug(Json.toJson(super.variableNames()));
    Log.debug(Json.toJson(super.attrNames()));

    super.header("from", "CoC.");
    super.cookie(new Cookie.Builder().name("k").value("v").build());
    super.cookie(new Cookie.Builder().name("z").value("y").build());

//    super.renderText("Log.debug(Json.toJson(super.headerNames()));");
    return EoResp.ok("OK");
  }

  @Action("/finish/:id/where/:where/:times")
  public void finish(Date time) {
    Log.debug(Json.toJson(super.variableNames()));
    Log.debug(Json.toJson(super.paraNames()));
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
    Log.debug(Json.toJson(super.variableNames()));
    Log.debug(Json.toJson(super.paraNames()));
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
    Log.debug(Json.toJson(super.variableNames()));
    Log.debug(Json.toJson(super.paraNames()));
  }

  @Action(method = Method.GET, value = "/rest/:id")
  public void restGet() {
    Log.debug(super.variable("id"));
    super.renderText("get");
  }

  @Action(method = Method.PUT, value = "/rest/:id")
  public void restPut() {
    Log.debug(super.variable("id"));
    super.renderText("put");
  }

  @Action(method = {Method.DELETE, Method.PATCH}, value = "/rest/:id")
  public void restOther() {
    Log.debug(super.variable("id"));
    super.renderText("delete patch");
  }

  @Para("pas")
  public Ret arr0(String[] pas) {
    Log.debug(Json.toJson(pas));
    return EoResp.ok();
  }

  @Para("pas")
  public Ret arr1(List<Integer> pas) {
    Log.debug(Json.toJson(pas));
    return EoResp.ok();
  }

  @Para("pas")
  public Ret arr2(Set<Integer> pas) {
    Log.debug(Json.toJson(pas));
    return EoResp.ok();
  }

  @Para("pas")
  public Ret arr3(TreeSet<Double> pas) {
    Log.debug(Json.toJson(pas));
    return EoResp.ok();
  }

  @Para("pas")
  public Ret arr4(short... pas) {
    Log.debug(Json.toJson(pas));
    return EoResp.ok();
  }

}
