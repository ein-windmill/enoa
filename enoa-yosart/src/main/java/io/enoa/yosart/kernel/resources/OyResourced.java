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
package io.enoa.yosart.kernel.resources;

import io.enoa.repeater.http.Method;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.http.UriKit;
import io.enoa.toolkit.text.PadKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.kernel.anno.Action;
import io.enoa.yosart.kernel.data.YdRouterInfo;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.kit.tip.OysartTip;
import io.enoa.yosart.thr.OyExtException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OyResourced {

  private static final OyResourced yo = new OyResourced();

  public static OyResourced yo() {
    return yo;
  }


  private Map<String, List<OyResource>> resources = new ConcurrentHashMap<>();


  /**
   * reg router
   *
   * @param regs router register
   * @return Map
   */
  public Map<String, OyResource[]> reg(List<YdRouterInfo> regs) {
    OysartTip.message("START ROUTER REGISTER.");
    for (YdRouterInfo reg : regs) {
      if (reg.uri() == null && reg.method() == null && reg.action() == null &&
        reg.control() == null && reg.router() == null)
        throw new OyExtException(EnoaTipKit.message("eo.tip.yosart.router_reg_cant_null"));
      this.reg(reg);
    }
    CollectionKit.clear(regs);
    OysartTip.message("ROUTE TOTAL: {0}", this.resources.size());
    OysartTip.message("END ROUTER REGISTER.");
    this.resources = new TreeMap<>(this.resources);
    final Map<String, OyResource[]> rets = new TreeMap<>();
    this.resources.forEach((k, v) -> rets.put(UriKit.correct(k), v.toArray(new OyResource[v.size()])));
    CollectionKit.clear(this.resources);
    return rets;
  }


  private void reg(YdRouterInfo reg) {
    this.reg(null, reg);
  }

  private void reg(String uri, YdRouterInfo reg) {
    uri = uri == null ? reg.uri() : this.mergeUri(uri, reg.uri());
    // action mapping
    if (reg.action() != null) {
      if (uri == null)
        throw new OyExtException(EnoaTipKit.message("eo.tip.yosart.resource_action_uri_must"));

      if (reg.method() == null) {
        this.add(uri, reg.action());
      } else {
        this.add(reg.method(), uri, reg.action());
      }
      return;
    }

    if (reg.control() != null) {
      if (uri == null) {
        this.add(reg.control());
      } else {
        this.add(uri, reg.control());
      }
      return;
    }

    if (reg.router() != null) {
      if (uri == null) {
        this.add(reg.router());
      } else {
        this.add(uri, reg.router());
      }
      return;
    }

    throw new OyExtException(EnoaTipKit.message("eo.tip.yosart.resource_unrecognized"));
  }


  private void log(OyResource resource) {
    if (!Oysart.config().info())
      return;
    Method[] methods = resource.methods();

    String type = resource instanceof OyActionResource ? "[ACTION ]" : "[CONTROL]";
    String message = String.format("-> %s `%s`", type, resource.route());
    if (message.length() < 70)
      message = PadKit.rpad(message, " ", 70 - message.length());

    message = message.concat(String.format(" %s", CollectionKit.isEmpty(methods) ?
      "[ALL]" : "[".concat(String.join(", ", Stream.of(methods).map(Enum::name).collect(Collectors.toSet()))).concat("]")));

    if (message.length() < 95)
      message = PadKit.rpad(message, " ", 95 - message.length());

    message = message.concat(String.format("=> %s", resource.identityFuncName()));
    OysartTip.message(message);
  }

  private static final String RESTFUL_PLACEHOLDER = "~VAR";

  private String[] restfulVars(String uri) {
    if (!uri.contains(":"))
      return CollectionKit.emptyArray(String.class);

    String[] uris = uri.split("/");
    Set<String> vars = new HashSet<>();
    int total = 0;
    for (String ru : uris) {
      if (!ru.contains(EoConst.RESTFUL_RECOGNIZE))
        continue;
      String var = ru.substring(1, ru.length());
      if (var.contains(EoConst.RESTFUL_RECOGNIZE))
        // 如果進入這裏, 表明定義 uri 時存在了類似 `/api/:id:id` 這樣的寫法, 這是不被允許的寫法
        throw new EoException(EnoaTipKit.message("eo.tip.yosart.resource_reg_restful_notsupport", uri));
      total += 1;
      vars.add(var);
    }
    if (vars.size() != total)
      throw new EoException(EnoaTipKit.message("eo.tip.yosart.resource_reg_var_same", uri));
    return vars.toArray(new String[vars.size()]);
  }


  private String formatRestful(String uri) {
    String[] vars = this.restfulVars(uri);
    // 替換變量名爲統一佔位符
    for (String var : vars) {
      uri = uri.replace(TextKit.union(EoConst.RESTFUL_RECOGNIZE, var), RESTFUL_PLACEHOLDER);
    }
    CollectionKit.clear(vars);
    return uri;
  }

  private boolean exists(String uri, Method[] methods) {
    Set<String> uris = this.resources.keySet();
    // 如果非 restful uri (不包含 :), 則判斷爲已存在
    if (!uri.contains(EoConst.RESTFUL_RECOGNIZE)) {
      return uris.stream().anyMatch(k -> k.equals(uri));
    }

    // 如果是 restful uri, 繼續判斷
    // 格式化 restful uri
    String restfulRegUri = this.formatRestful(uri);
    // 取出所有 restful uri
    Set<String> restfulUris = uris.stream().filter(u -> u.contains(EoConst.RESTFUL_RECOGNIZE)).collect(Collectors.toSet());
    for (String restUri : restfulUris) {
      // 格式化 restful uri
      String regrfu = this.formatRestful(restUri);
      // 當前註冊的 uri 和已註冊的不同, 進入下一匹配
      if (!regrfu.equals(restfulRegUri))
        continue;
      // 如果相同, 校驗 method 是否也相同

      List<OyResource> resources = this.resources.get(restUri);
      for (OyResource resource : resources) {
        Method[] regMethods = resource.methods();
        // 如果當前註冊的 uri 以及 已註冊的 uri 都不存在 method, 則判定重複
        if (CollectionKit.isEmpty(methods) || CollectionKit.isEmpty(regMethods))
          return true;

        // 如果當前註冊的 uri method 存在與 已註冊的相同 uri 的 method 則判斷重複
        for (Method regMethod : regMethods) {
          for (Method method : methods) {
            if (regMethod == method)
              return true;
          }
        }
      }

    }

    return false;
  }

  private String mergeUri(String uri0, String uri1) {
    if (TextKit.isBlank(uri0) || "/".equals(uri0))
      return uri1;
    if (TextKit.isBlank(uri1) || "/".equals(uri1))
      return uri0;

    uri0 = UriKit.correct(uri0);
    uri1 = UriKit.correct(uri1);
    if (TextKit.isBlank(uri1))
      return uri0;
    return uri0.concat(uri1);
  }

  private void add(String uri, YoAction action) {
    this.add(null, uri, action);
  }

  private void add(Method method, String uri, YoAction action) {
    uri = UriKit.correct(uri);
    Method[] methods = method == null ? CollectionKit.emptyArray(Method.class) : new Method[]{method};
    if (this.exists(uri, methods))
      throw new EoException(EnoaTipKit.message("eo.tip.yosart.resource_reg_uri_already_reg", uri,
        CollectionKit.isEmpty(methods) ? "ALL" : String.join(", ", Stream.of(methods).map(Enum::name).collect(Collectors.toSet())),
        action.getClass().getName()));

    java.lang.reflect.Method func;
    try {
      func = action.getClass().getMethod("handle", YoRequest.class);
    } catch (NoSuchMethodException e) {
      throw new EoException(e);
    }
    OyActionResource oar = new OyActionResource(methods, uri, action, func, func.getName(), this.restfulVars(uri));

    List<OyResource> resources = this.resources.get(uri);
    resources = resources == null ? new ArrayList<>() : resources;
    resources.add(oar);
    this.resources.put(uri, resources);
    this.log(oar);
  }

  private void add(String uri, Class<? extends YaControl> control) {
    uri = UriKit.correct(uri);
//    Class<?> superClazz = control.getSuperclass();
//    boolean isSon = (superClazz == YaControl.class || superClazz == YoControl.class || superClazz == YeControl.class);
//    java.lang.reflect.Method[] methods = (isSon ? control.getDeclaredMethods() : control.getMethods());
    java.lang.reflect.Method[] methods = control.getDeclaredMethods();
    for (java.lang.reflect.Method method : methods) {
      StringBuilder newUri = new StringBuilder();
      if (!"/".equals(uri))
        newUri.append(uri);

      List<Method> httpMethods = new ArrayList<>();
      String methodName = method.getName();
      String methodUri;
      Action action = method.getAnnotation(Action.class);
      if (action == null) {
        methodUri = methodName;
      } else {
        if (CollectionKit.notEmpty(action.method()))
          httpMethods = Arrays.stream(action.method()).collect(Collectors.toList());
        methodUri = TextKit.isBlank(action.uri()) && TextKit.isBlank(action.value()) ? null :
          (TextKit.isBlank(action.uri()) ? action.value() : action.uri());
        if (methodUri == null)
          methodUri = methodName;
      }
      if (TextKit.notBlank(methodUri)) {
        methodUri = UriKit.correct(methodUri);
        methodUri = methodUri.endsWith("/index") ? methodUri.substring(0, methodUri.lastIndexOf("/index")) : methodUri;
        newUri.append(methodUri);
      }
      if (this.exists(newUri.toString(), httpMethods.toArray(new Method[httpMethods.size()])))
        throw new EoException(EnoaTipKit.message("eo.tip.yosart.resource_reg_uri_already_reg", newUri.toString(),
          CollectionKit.isEmpty(httpMethods) ? "ALL" : String.join(", ", httpMethods.stream().map(Enum::name).collect(Collectors.toSet())),
          control.getName()));


      OyControlResource ycr = new OyControlResource(
        control, newUri.toString(), methodName,
        httpMethods.toArray(new Method[httpMethods.size()]),
        method,
        method.getAnnotations(),
        method.getParameters(),
        this.restfulVars(newUri.toString())
      );

      List<OyResource> resources = this.resources.get(newUri.toString());
      resources = resources == null ? new ArrayList<>() : resources;
      resources.add(ycr);
      this.resources.put(newUri.toString(), resources);
      this.log(ycr);
    }
  }

  private void add(Class<? extends YaControl> control) {
    this.add("/", control);
  }

  private void add(String uri, YoRouter router) {
    uri = UriKit.correct(uri);
    String context = router.context();
    uri = this.mergeUri(uri, context);

    YdRouterInfo[] routes = router.routes();
    for (YdRouterInfo route : routes) {
      this.reg(uri, route);
    }
  }

  private void add(YoRouter router) {
    this.add("/", router);
  }

}
