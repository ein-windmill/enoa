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
package io.enoa.yosart.ext.anost;

import io.enoa.repeater.http.Cookie;
import io.enoa.repeater.http.Method;
import io.enoa.repeater.http.Response;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.thr.EoException;
import io.enoa.yosart.YoConfig;
import io.enoa.yosart.ext.anost.hook.HookException;
import io.enoa.yosart.kernel.http.Session;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.kernel.resources.OyActionResource;
import io.enoa.yosart.kit.yo.ResponseKit;
import io.enoa.yosart.resp.Renderer;
import io.enoa.yosart.resp.Resp;
import io.enoa.yosart.thr.OyExtException;

class AnostActionHandler extends AbstractAnostHandler {

  private AnostHookRunner hookRunner;
  private AnostHookMgr hookMgr;

  AnostActionHandler(AnostHookMgr hookMgr, AnostHookRunner hookRunner) {
    this.hookMgr = hookMgr;
    this.hookRunner = hookRunner;
  }

  Response handle(YoConfig config, OyActionResource resource, String action, YoRequest request, Resp resp) {
    super.printLog(config, resource);


    try {
      this.hookRunner.call(request, resp, this.hookMgr, resource);
    } catch (HookException e) {
      return super.handleException(e, request, resource, resp);
    }

    // skip options request
    if (request.method() == Method.OPTIONS) {
      return resp.render("").end();
    }

    Response actionResp;
    try {
      actionResp = resource.action().handle(request);
    } catch (Exception e) {
      return super.handleException(e, request, resource, resp);
    }
    if (actionResp == null) {
      // debug model return error
      if (config.debug())
        throw new EoException(EnoaTipKit.message("eo.tip.yosart.action_no_content"));

      return Renderer.with(request).renderHtml("").end();
    }

    try {
      Session session = request.session();
      Cookie sessVal = session._value();
      String originSessVal = request.cookie(session._name());
      if ((originSessVal == null && sessVal != null) || (sessVal != null && !originSessVal.equals(sessVal.value()))) {
        Response.Builder builder = actionResp.newBuilder();
        builder.cookie(sessVal);
        actionResp = builder.build();
      }
    } catch (OyExtException ignored) {
    }

    return ResponseKit.merge(actionResp, resp.end());
  }

}
