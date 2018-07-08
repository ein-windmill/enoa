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
package io.enoa.yosart.ext.anost;

import io.enoa.log.Log;
import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Method;
import io.enoa.repeater.http.Response;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.reflect.ReflectKit;
import io.enoa.toolkit.thr.EoException;
import io.enoa.yosart.YoConfig;
import io.enoa.yosart.ext.anost.hook.HookException;
import io.enoa.yosart.kernel.http.Session;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.kernel.resources.OyControlResource;
import io.enoa.yosart.kernel.resources.OyControlSwap;
import io.enoa.yosart.kernel.resources.YaControl;
import io.enoa.yosart.kit.yo.ResponseKit;
import io.enoa.yosart.resp.Renderer;
import io.enoa.yosart.resp.Resp;
import io.enoa.yosart.thr.OyExtException;

import java.lang.reflect.Parameter;

class AnostControlHandler extends AbstractAnostHandler {


  private AnostParasBuilder parasBuilder;
  private AnostHookMgr hookMgr;
  private AnostHookRunner hookRunner;
  private FunctionReturnBuilder returnBuilder;
  private AnostSerializer serializer;

  AnostControlHandler(AnostHookMgr hookMgr, AnostHookRunner hookRunner, AnostSerializer serializer) {
    this.parasBuilder = new AnostParasBuilder();
    this.returnBuilder = new FunctionReturnBuilder();
    this.hookMgr = hookMgr;
    this.hookRunner = hookRunner;
    this.serializer = serializer;
  }

  Response handle(YoConfig config, OyControlResource resource, String action, YoRequest request, Resp resp) {
    super.printLog(config, resource);
    Class<? extends YaControl> control = resource.control();

    /*
    new instance
     */
    YaControl ctl;
    try {
      ctl = ReflectKit.newInstance(control).build();
    } catch (Exception e) {
      Log.error(e.getMessage(), e);
      Renderer renderer = Renderer.with(request);
      if (config.debug())
        renderer.attr("msg", EnoaTipKit.message("eo.tip.yosart.cant_not_create_control_instance"));
      return renderer.renderError(HttpStatus.INTERNAL_ERROR, config.debug() ? e : null).end();
    }


//    /*
//    Control response builder
//     */
//    Resp resp = Resp.with(request);


    try {
      this.hookRunner.call(request, resp, this.hookMgr, resource);
    } catch (HookException e) {
      return super.handleException(e, request, resource, resp);
    }

    // skip options request
    if (request.method() == Method.OPTIONS) {
      return resp.render("").end();
    }

    /*
    build control request
     */
    OyControlSwap.init(ctl, request, resp);

    /*
     call action method
     */
    java.lang.reflect.Method func = resource.func();
    Object funcRet;
    try {
      Parameter[] paras = resource.paras();
      if (CollectionKit.isEmpty(paras)) {
        funcRet = func.invoke(ctl);
      } else {
        Object[] funcArgs = this.parasBuilder.build(request, resp, resource);
        funcRet = func.invoke(ctl, funcArgs);
      }
    } catch (Exception e) {
      return super.handleException(e, request, resource, resp);
    }
    Response ret = this.returnBuilder.build(this.serializer, request, func.getReturnType(), funcRet);


    /*
    swap control response
     */
    try {
      Session session = request.session();
      Cookie sessVal = session._value();
      String originSessVal = request.cookie(session._name());
      if ((originSessVal == null && sessVal != null) || (sessVal != null && !originSessVal.equals(sessVal.value()))) {
        resp.cookie(sessVal);
      }
    } catch (OyExtException ignored) {
    }

    Response response = resp.end();
    response = ResponseKit.merge(ret, response);

    if (response != null && response.body() != null)
      return response;

    // debug model return error
    if (config.debug())
      throw new EoException(EnoaTipKit.message("eo.tip.yosart.action_no_content"));

    return Renderer.with(request).renderHtml("").end();
  }


}
