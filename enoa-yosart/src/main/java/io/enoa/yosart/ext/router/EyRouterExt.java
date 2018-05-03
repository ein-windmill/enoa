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
package io.enoa.yosart.ext.router;

import io.enoa.log.kit.LogKit;
import io.enoa.repeater.http.*;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.sys.ThrowableKit;
import io.enoa.toolkit.sys.ReflectKit;
import io.enoa.toolkit.thr.EoException;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.YoConfig;
import io.enoa.yosart.YoExt;
import io.enoa.yosart.kernel.ext.OyBeaRet;
import io.enoa.yosart.kernel.ext.YmBeforeActionExt;
import io.enoa.yosart.kernel.ext.YmExceptionExt;
import io.enoa.yosart.kernel.ext.YmRouterExt;
import io.enoa.yosart.kernel.http.PathVariable;
import io.enoa.yosart.kernel.http.Session;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.kernel.resources.*;
import io.enoa.yosart.kit.tip.OysartTip;
import io.enoa.yosart.kit.variable.PathVariableKit;
import io.enoa.yosart.kit.yo.OyResourceKit;
import io.enoa.yosart.kit.yo.ResponseKit;
import io.enoa.yosart.resp.Renderer;
import io.enoa.yosart.resp.Resp;
import io.enoa.yosart.thr.OyBeaException;
import io.enoa.yosart.thr.OyExtException;

import java.lang.reflect.Parameter;
import java.util.stream.Stream;

public class EyRouterExt implements YmRouterExt {

  private static YmBeforeActionExt[] EXTS;
  private static YmExceptionExt exceptionExt;


  @Override
  public String name() {
    return "EyRouterExt";
  }

  @Override
  public String version() {
    return "1";
  }

  @Override
  public double weight() {
    return 0;
  }

  @Override
  public Response handle(String action, Request request) {

    YoConfig config = Oysart.config();
    boolean force = false;
    try {

      // find handle resource
      OyResource resource = OyResourceKit.choose(request.method(), action);
      PathVariable variable = null;

      // if not resource handle try restful uri handle
      if (resource == null) {
        variable = PathVariableKit.parse(action, Oysart.resources().keySet());

        // no handle return 404
        if (variable == null)
          return Resp.with(request)
            .renderError(HttpStatus.NOT_FOUND)
            .end();

        resource = OyResourceKit.choose(request.method(), variable.uri());
      }

      if (resource == null) {
        return Resp.with(request)
          .renderError(HttpStatus.NOT_FOUND)
          .end();
      }

      Response resp;
      YoRequest req = (request instanceof YoRequest) ? (YoRequest) request :
        YoRequest.create(request, variable);

      switch (resource.type()) {
        case ACTION:
          resp = this.handleAction(config, (OyActionResource) resource, action, req);
          break;
        case CONTROL:
          resp = this.handleControl(config, (OyControlResource) resource, action, req);
          break;
        default:
          throw new EoException(EnoaTipKit.message("eo.tip.yosart.unknow_resource"));
      }
      if (resp == null)
        throw new EoException(EnoaTipKit.message("eo.tip.yosart.acton_handle_no_result"), action);
      return resp;
    } catch (Exception e) {
      force = true;
      Throwable accurate = ThrowableKit.accurate(e);
      LogKit.error(e.getMessage(), accurate);
      return Renderer.with(request).renderError(HttpStatus.INTERNAL_ERROR, config.debug() ? accurate : null).end();
    } finally {
      if (force) {
        request.clear();
      } else {
        if (request.attr("_eo.request.clear") == null) {
          request.clear();
        } else {
          if (request.attr("_eo.request.clear"))
            request.clear();
        }
      }
    }

  }

  private void printLog(YoConfig config, OyResource resource) {
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

  private YmBeforeActionExt[] exts() {
    if (EXTS != null)
      return EXTS;
    YoExt[] exts = Oysart.exts(Type.BEFORE_ACTION);
    if (CollectionKit.isEmpty(exts))
      return CollectionKit.emptyArray(YmBeforeActionExt.class);
    EXTS = Stream.of(exts).map(e -> (YmBeforeActionExt) e).toArray(YmBeforeActionExt[]::new);
    return EXTS;
  }

  private YmExceptionExt exceptionExt() {
    if (exceptionExt != null)
      return exceptionExt;
    exceptionExt = (YmExceptionExt) Oysart.ext(Type.EXCEPTION);
    return exceptionExt;
  }

  /**
   * before action extension handle
   *
   * @param resource resource
   * @param action   action
   * @param request  request
   * @param resp     Response
   */
  private void beforeAction(OyResource resource, String action, YoRequest request, Resp resp) {
    for (YmBeforeActionExt ext : this.exts()) {
      if (ext == null)
        throw new EoException(EnoaTipKit.message("eo.tip.yosart.ext_404_before_action"));
      OyBeaRet ret = ext.handle(resource, action, request, resp);

      if (ret == null)
        throw new EoException(EnoaTipKit.message("eo.tip.yosart.ext_before_action_no_result", ext.getClass().getName()));

      if (!ret.stat())
        throw new OyBeaException("Before action execute fail. => {0}", ret.errorClazz().getName());
    }
  }

  /**
   * 路由異常統一處理
   *
   * @param e        throwable
   * @param request  request
   * @param resource resource
   * @return Response
   */
  private Response handleException(Throwable e, YoRequest request, Resp resp, OyResource resource) {
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

  /**
   * handle action request
   *
   * @param config   config
   * @param resource resource
   * @param action   action uri
   * @param request  request
   * @return Response
   */
  private Response handleAction(YoConfig config, OyActionResource resource, String action, YoRequest request) {
    this.printLog(config, resource);

    Resp resp = Resp.with(request);
    try {
      this.beforeAction(resource, action, request, resp);
    } catch (Exception e) {
      LogKit.error(e.getMessage(), e);
      if (e instanceof OyBeaException)
        return resp.end();
      return Renderer.with(request).renderError(HttpStatus.INTERNAL_ERROR, e).end();
    }

    // skip options request
    if (request.method() == Method.OPTIONS) {
      return resp.render("").end();
    }

    Response handleResp = resp.end();
    if (handleResp == null) {
      // fixme session support
      return resource.action().handle(request);
    }

    if (handleResp.status() != HttpStatus.OK)
      return handleResp;

    // 優先考慮 before action 返回值
    if (handleResp.body() != null)
      return handleResp;

    Response actionResp;
    try {
      actionResp = resource.action().handle(request);
    } catch (Exception e) {
      return this.handleException(e, request, resp, resource);
    }
    if (actionResp == null) {
      // debug model return error
      if (config.debug())
        throw new EoException(EnoaTipKit.message("eo.tip.yosart.action_no_content"));

      return Renderer.with(request).renderHtml("").end();
    }

    Response ret = ResponseKit.merge(actionResp, handleResp);

    try {
      Session session = request.session();
      Cookie sessVal = session._value();
      String originSessVal = request.cookie(session._name());
      if ((originSessVal == null && sessVal != null) || (sessVal != null && !originSessVal.equals(sessVal.value()))) {
        Response.Builder builder = ret.newBuilder();
        builder.cookie(sessVal);
        ret = builder.build();
      }
      return ret;
    } catch (OyExtException ignored) {
    }
    return ret;
  }

  /**
   * handle control request
   *
   * @param config   config
   * @param resource resource
   * @param action   action uri
   * @param request  request
   * @return Response
   */
  private Response handleControl(YoConfig config, OyControlResource resource, String action, YoRequest request) {
    this.printLog(config, resource);

    Class<? extends YaControl> control = resource.control();
    /*
    new instance
     */
    YaControl ctl;
    try {
      ctl = ReflectKit.newInstance(control);
    } catch (Exception e) {
      LogKit.error(e.getMessage(), e);
      Renderer renderer = Renderer.with(request);
      if (config.debug())
        renderer.attr("msg", EnoaTipKit.message("eo.tip.yosart.cant_not_create_control_instance"));
      return renderer.renderError(HttpStatus.INTERNAL_ERROR, config.debug() ? e : null).end();
    }

    /*
    Control response builder
     */
    Resp resp = Resp.with(request);

    try {
      this.beforeAction(resource, action, request, resp);
    } catch (Exception e) {
      LogKit.error(e.getMessage(), e);
      if (e instanceof OyBeaException)
        return resp.end();
      return Renderer.with(request).renderError(HttpStatus.INTERNAL_ERROR, e).end();
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
    try {
      Parameter[] paras = resource.paras();
      if (CollectionKit.isEmpty(paras)) {
        func.invoke(ctl);
      } else {
        func.invoke(ctl, this.methodParas(paras));
      }
    } catch (Exception e) {
      return this.handleException(e, request, resp, resource);
    }
    if (func.getReturnType() != void.class)
      LogKit.warn(EnoaTipKit.message("eo.tip.yosart.ext.ey_router_not_support_returned"));

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

    if (response != null && response.body() != null)
      return response;

    // debug model return error
    if (config.debug())
      throw new EoException(EnoaTipKit.message("eo.tip.yosart.action_no_content"));

    return Renderer.with(request).renderHtml("").end();
  }

  private Object[] methodParas(Parameter[] paras) {
    LogKit.warn(EnoaTipKit.message("eo.tip.yosart.ext.ey_router_not_support_para_parse"));
    return Stream.of(paras).map(m -> {
      Class<?> type = m.getType();
      if (type == int.class)
        return 0;
      if (type == long.class)
        return 0L;
      if (type == double.class)
        return 0D;
      if (type == float.class)
        return 0F;
      if (type == short.class)
        return (short) 0;
      if (type == byte.class)
        return (byte) 0;
      if (type == char.class)
        return (char) 0;
      if (type == byte.class)
        return (byte) 0;
      return null;
    }).toArray(Object[]::new);
  }

}
