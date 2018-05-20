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

import io.enoa.log.kit.LogKit;
import io.enoa.repeater.http.HttpStatus;
import io.enoa.repeater.http.Response;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.sys.ThrowableKit;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.YoConfig;
import io.enoa.yosart.YoExt;
import io.enoa.yosart.kernel.ext.YmExceptionExt;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.kernel.resources.OyActionResource;
import io.enoa.yosart.kernel.resources.OyControlResource;
import io.enoa.yosart.kernel.resources.OyResource;
import io.enoa.yosart.kit.tip.OysartTip;
import io.enoa.yosart.resp.Renderer;
import io.enoa.yosart.resp.Resp;

abstract class AbstractAnostHandler {

  private static YmExceptionExt _exceptionExt;

  private YmExceptionExt exceptionExt() {
    if (_exceptionExt != null)
      return _exceptionExt;
    _exceptionExt = (YmExceptionExt) Oysart.ext(YoExt.Type.EXCEPTION);
    return _exceptionExt;
  }

  void printLog(YoConfig config, OyResource resource) {
    if (!config.info())
      return;
    switch (resource.type()) {
      case CONTROL:
        OyControlResource ocr = (OyControlResource) resource;
        OysartTip.message("\nHint `{0}` => {1}\n", resource.route(), ocr.identityFuncName());
        break;
      case ACTION:
        OysartTip.message("\nHint `{0}` => {1}\n", resource.route(), ((OyActionResource) resource).action().getClass().getName());
        break;
      default:
        OysartTip.message("\nHint null\n");
    }
  }


  Response handleException(Throwable e, YoRequest request, OyResource resource, Resp resp) {
    YmExceptionExt eext = this.exceptionExt();
    try {
      if (eext != null)
        return eext.handle(request, resp, e);
    } catch (Exception ex) {
      return Renderer.with(request)
        .renderError(HttpStatus.INTERNAL_ERROR, e)
        .end();
    }

    Throwable throwable = ThrowableKit.accurate(e);
    String message = EnoaTipKit.message("eo.tip.yosart.router.call_fail",
      resource.funcName(), resource.identityFuncName());
    LogKit.error("{} -> {}", throwable.getMessage(), message, throwable);
    return Renderer.with(request)
      .attr("msg", message)
      .renderError(HttpStatus.INTERNAL_ERROR, throwable)
      .end();
  }

}
