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
package io.enoa.ext.bea.beaction;

import io.enoa.repeater.http.Method;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.http.UriKit;
import io.enoa.yosart.kernel.ext.OyBeaRet;
import io.enoa.yosart.kernel.ext.YmBeforeActionExt;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.kernel.resources.OyResource;
import io.enoa.yosart.resp.Resp;
import io.enoa.yosart.thr.OyBeaException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeaExt implements YmBeforeActionExt {

  private static Map<String, Interceptor[]> IORCACHE = new HashMap<>();
  private static List<BeaReg> REGIOR = new ArrayList<>();
  private static List<Interceptor> GLOBAL = new ArrayList<>();
  private static Interceptor[] CACHE_OPTIONS;

  public enum Mode {
    /**
     * uri 全文匹配
     */
    FULL,
    /**
     * uri 前缀匹配
     */
    PREFIX,
    /**
     * 正则匹配
     */
    REGEX
  }

  @Override
  public String name() {
    return "BeaExt";
  }

  @Override
  public String version() {
    return "2";
  }

  @Override
  public OyBeaRet handle(OyResource resource, String uri, YoRequest request, Resp resp) throws OyBeaException {
    Interceptor[] iors = request.method() == Method.OPTIONS ? this.iorsOptions(request) : this.iors(resource, request);
    if (CollectionKit.isEmpty(iors) && CollectionKit.isEmpty(GLOBAL))
      return OyBeaRet.ok();
    return this.doIntercept(iors, request, resp);
  }

  /**
   * 注册全局拦击器
   *
   * @param interceptor 拦截器
   * @return BeaExt
   */
  public BeaExt load(Interceptor interceptor) {
    GLOBAL.add(interceptor);
    return this;
  }

  /**
   * 装载自定义拦截器
   *
   * @param uri         拦截 uri
   * @param interceptor 拦截器
   * @param mode        匹配模式
   * @return BeaExt
   */
  public BeaExt load(String uri, Interceptor interceptor, Mode mode) {
    REGIOR.add(new BeaReg(mode, UriKit.correct(uri), interceptor));
    return this;
  }

  public BeaExt load(String uri, Interceptor interceptor) {
    return this.load(uri, interceptor, Mode.FULL);
  }

  /**
   * 注册自定义清除拦截器
   *
   * @param uri    uri
   * @param clears 清除的拦截器
   * @param mode   匹配模式
   * @return BeaExt
   */
  public BeaExt unload(String uri, Class<? extends Interceptor> clears, Mode mode) {
    REGIOR.add(new BeaReg(mode, UriKit.correct(uri), clears));
    return this;
  }

  public BeaExt unload(String uri, Class<? extends Interceptor> clears) {
    return this.unload(uri, clears, Mode.FULL);
  }

  /**
   * 提取注册的拦截信息
   *
   * @param type    类型
   * @param request request
   * @param <T>     return type
   * @return List
   */
  private <T> List<T> extraReg(BeaReg.Type type, YoRequest request) {
    String uri = UriKit.rmcontext(request.context(), request.uri());
    List<BeaReg> brs = REGIOR.stream().filter(b -> b.type() == type).collect(Collectors.toList());
    List<T> rets = new ArrayList<>();
    for (BeaReg br : brs) {
      switch (br.mode()) {
        case FULL:
          if (uri.equals(br.uri()))
            rets.add(type == BeaReg.Type.LOAD ? (T) br.interceptor() : (T) br.clears());
          break;
        case PREFIX:
          if (uri.startsWith(br.uri()))
            rets.add(type == BeaReg.Type.LOAD ? (T) br.interceptor() : (T) br.clears());
          break;
        case REGEX:
          if (uri.matches(br.uri()))
            rets.add(type == BeaReg.Type.LOAD ? (T) br.interceptor() : (T) br.clears());
          break;
      }
    }
    return rets;
  }


  /**
   * options 請求攔截器
   *
   * @param request request
   * @return Interceptor[]
   */
  private Interceptor[] iorsOptions(YoRequest request) {
    if (CACHE_OPTIONS != null)
      return CACHE_OPTIONS;
    List<Interceptor> rets = new ArrayList<>(GLOBAL);
    rets.addAll(this.extraReg(BeaReg.Type.LOAD, request));
    CACHE_OPTIONS = this.clearInterceptor(request, null, rets);
    return CACHE_OPTIONS;
  }

  /**
   * 提取 action 以及 control method 中 Before 註解
   *
   * @param resource OyResource
   * @return Interceptor
   */
  private Interceptor[] iors(OyResource resource, YoRequest request) {
    String name = resource.hashName();
    /*
    缓存提取此方法所有拦截器
     */
    if (IORCACHE.get(name) != null)
      return IORCACHE.get(name);

    /*
    填充全局攔截器
     */
    List<Interceptor> rets = new ArrayList<>(GLOBAL);

    /*
    填充自定义规则拦截器
     */
    rets.addAll(this.extraReg(BeaReg.Type.LOAD, request));

    Before before = resource.func().getAnnotation(Before.class);

    /*
    填充注解提供拦截器
     */
    if (before != null) {
      Class<? extends Interceptor>[] biors = before.value();
      if (CollectionKit.notEmpty(biors)) {
        for (Class<? extends Interceptor> bior : biors) {
          try {
            Interceptor interceptor = bior.newInstance();
            rets.add(interceptor);
          } catch (Exception e) {
            throw new OyBeaException(e.getMessage(), e);
          }
        }
      }
    }


    /*
    清除设定的拦截器
     */
    Interceptor[] iors = this.clearInterceptor(request, resource, rets);

    /*
    将此方法的所有拦截器缓存
     */
    IORCACHE.put(name, iors);
    return iors;
  }

  private Interceptor[] clearInterceptor(YoRequest request, OyResource resource, List<Interceptor> iors) {
    /*
    首先加载自定义清除拦截器
     */
    List<Class<? extends Interceptor>> clears = new ArrayList<>(this.extraReg(BeaReg.Type.UNLOAD, request));

    if (resource != null) {
      Clear clear = resource.func().getAnnotation(Clear.class);
    /*
    方法签名提供清除加入到自定义清除中
     */
      if (clear != null) {
        clears.addAll(Stream.of(clear.value()).collect(Collectors.toSet()));
      }
    }

    if (CollectionKit.isEmpty(clears))
      return iors.toArray(new Interceptor[iors.size()]);

    /*
    清除拦截器
     */
    for (int i = iors.size(); i-- > 0; ) {
      for (Class<? extends Interceptor> cior : clears) {
        if (iors.get(i).getClass().getName().equals(cior.getName()))
          iors.remove(i);
      }
    }
    return iors.toArray(new Interceptor[iors.size()]);
  }

  private OyBeaRet doIntercept(Interceptor[] iors, YoRequest req, Resp resp) {
    for (Interceptor ior : iors) {
      Invocation inv = new Invocation(req, resp);
      ior.intercept(inv);
      if (inv.nextIor())
        continue;
      return OyBeaRet.fail(ior.getClass());
    }
    return OyBeaRet.ok();
  }
}
