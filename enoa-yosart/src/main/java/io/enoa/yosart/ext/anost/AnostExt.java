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

import io.enoa.log.Log;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.sys.ThrowableKit;
import io.enoa.toolkit.thr.EoException;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.YoConfig;
import io.enoa.yosart.ext.anost.hook.HookException;
import io.enoa.yosart.ext.anost.serialize.AnostObjectSerializer;
import io.enoa.yosart.kernel.ext.YmRouterExt;
import io.enoa.yosart.kernel.http.PathVariable;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.kernel.resources.OyActionResource;
import io.enoa.yosart.kernel.resources.OyControlResource;
import io.enoa.yosart.kernel.resources.OyResource;
import io.enoa.yosart.kit.variable.PathVariableKit;
import io.enoa.yosart.kit.yo.OyResourceKit;
import io.enoa.yosart.resp.Resp;

public class AnostExt implements YmRouterExt {

  private AnostActionHandler actionHandler;
  private AnostControlHandler controlHandler;
  private AnostHookMgr hookMgr;
  private AnostHookRunner hookRunner;

  public AnostExt() {
    this(new AnostObjectSerializer());
  }

  public AnostExt(AnostSerializer serializer) {
    this.hookMgr = new AnostHookMgr();
    this.hookRunner = new AnostHookRunner();
    this.actionHandler = new AnostActionHandler(this.hookMgr, this.hookRunner);
    this.controlHandler = new AnostControlHandler(this.hookMgr, this.hookRunner, serializer);
  }

  public AnostHookMgr hookMgr() {
    return hookMgr;
  }

  @Override
  public String name() {
    return "AnostExt";
  }

  @Override
  public String version() {
    return "1";
  }

  @Override
  public double weight() {
    return 2D;
  }

  @Override
  public Response handle(String action, Request request) {

    YoConfig config = Oysart.config();

    // no resources return 404
    if (Oysart.resources() == null)
      return this.renderError(request, HttpStatus.NOT_FOUND);

    // find handle resource
    OyResource resource = OyResourceKit.choose(request.method(), action);
    PathVariable variable = null;

    // if not resource handle try restful uri handle
    if (resource == null) {

      variable = PathVariableKit.parse(action, Oysart.resources().keySet());
      // no handle return 404
      if (variable == null) {
        return this.renderError(request, HttpStatus.NOT_FOUND);
      }
      resource = OyResourceKit.choose(request.method(), variable.uri());
    }

    if (resource == null) {
      return this.renderError(request, HttpStatus.NOT_FOUND);
    }

    Response response;
    YoRequest req = (request instanceof YoRequest) ? (YoRequest) request : YoRequest.create(request, variable);
    Resp resp = Resp.with(request);

    boolean force = false;
    try {
      switch (resource.type()) {
        case ACTION:
          response = this.actionHandler.handle(config, (OyActionResource) resource, action, req, resp);
          break;
        case CONTROL:
          response = this.controlHandler.handle(config, (OyControlResource) resource, action, req, resp);
          break;
        default:
          throw new EoException(EnoaTipKit.message("eo.tip.yosart.unknow_resource"));
      }

      if (response == null)
        throw new EoException(EnoaTipKit.message("eo.tip.yosart.acton_handle_no_result"), action);
      return response;
    } catch (Exception e) {
      force = true;
      Throwable accurate = ThrowableKit.accurate(e);
      Log.error(e.getMessage(), accurate);
      return this.renderError(req, resp, HttpStatus.INTERNAL_ERROR, config.debug() ? accurate : null);
    } finally {
      Boolean reqclear = request.attr("_eo.request.clear");
      if (force || (reqclear != null && reqclear))
        req.clear();
    }
  }

  private Response renderError(Request request, HttpStatus stat) {
    return this.renderError(request, null, stat, null);
  }

  private Response renderError(Request request, Resp resp, HttpStatus stat, Throwable t) {
    YoRequest req = request instanceof YoRequest ? (YoRequest) request : YoRequest.create(request);
    if (resp == null)
      resp = Resp.with(req);
    try {
      this.hookRunner.call(req, resp, this.hookMgr, null, true);
      return resp.renderError(stat, t)
        .end();
    } catch (HookException e) {
      return resp.renderError(stat, t).end();
    }
  }

}
