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

import io.enoa.repeater.http.Method;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.http.UriKit;
import io.enoa.toolkit.sys.ReflectKit;
import io.enoa.yosart.ext.anost.hook.Hook;
import io.enoa.yosart.ext.anost.hook.HookException;
import io.enoa.yosart.ext.anost.hook.IHook;
import io.enoa.yosart.kernel.http.YoRequest;
import io.enoa.yosart.kernel.resources.OyResource;
import io.enoa.yosart.resp.Resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AnostHookRunner {

  private static List<IHook> CACHE_OPTIONS;
  private static Map<String, List<IHook>> CACHE = new HashMap<>();

  void call(YoRequest request, Resp resp, AnostHookMgr hookMgr, OyResource resource) throws HookException {
    this.call(request, resp, hookMgr, resource, false);
  }

  void call(YoRequest request, Resp resp, AnostHookMgr hookMgr, OyResource resource, boolean forceOptions) throws HookException {
    List<IHook> hooks = (request.method() == Method.OPTIONS || forceOptions) ?
      this.hooksOptions(request, hookMgr) :
      this.hooks(request, hookMgr, resource);
    for (IHook hook : hooks) {
      hook.hook(request, resp);
    }
  }

  /**
   * OPTIONS 請求執行 hook
   *
   * @param request request
   * @param hookMgr hookMgr
   * @return List
   */
  private List<IHook> hooksOptions(YoRequest request, AnostHookMgr hookMgr) {
    if (CACHE_OPTIONS != null)
      return CACHE_OPTIONS;

    /*
    優先添加全局 hook
     */
    CACHE_OPTIONS = CollectionKit.isEmpty(hookMgr.globalLoads()) ? new ArrayList<>() : new ArrayList<>(hookMgr.globalLoads());

    /*
    优先加入规则配置 Hook
     */
    this.loadHooks(request, hookMgr, CACHE_OPTIONS);

    /*
    先清除全局  Hook
     */
    this.unloadGlobalHooks(hookMgr, CACHE_OPTIONS);

    /*
    根据规则清除无需执行的 Hook
     */
    this.unloadHooks(request, hookMgr, CACHE_OPTIONS);

    return CACHE_OPTIONS;
  }

  /**
   * 正常請求執行 hook
   *
   * @param request  request
   * @param hookMgr  hookMgr
   * @param resource 請求資源
   * @return List
   */
  private List<IHook> hooks(YoRequest request, AnostHookMgr hookMgr, OyResource resource) {
    List<IHook> ihooks = CACHE.get(resource.hashName());
    if (ihooks != null)
      return ihooks;
    ihooks = new ArrayList<>();

    /*
    优先执行全局 Hook
     */
    if (CollectionKit.notEmpty(hookMgr.globalLoads())) {
      ihooks.addAll(hookMgr.globalLoads());
    }

    /*
    优先加入规则配置 Hook
     */
    this.loadHooks(request, hookMgr, ihooks);

    /*
    最后加入 function 添加 Hook
     */
    Hook hook = resource.func().getAnnotation(Hook.class);
    if (hook != null) {
      Class<? extends IHook>[] ihookClazz = hook.value();
      if (CollectionKit.notEmpty(ihookClazz)) {
        for (Class<? extends IHook> clazz : ihookClazz) {
          IHook annohook = ReflectKit.newInstance(clazz);
          ihooks.add(annohook);
        }
      }
    }

    /*
    先清除全局  Hook
     */
    this.unloadGlobalHooks(hookMgr, ihooks);

    /*
    根据规则清除无需执行的 Hook
     */
    this.unloadHooks(request, hookMgr, ihooks);

    CACHE.put(resource.hashName(), ihooks);
    return ihooks;
  }

  private void loadHooks(YoRequest request, AnostHookMgr hookMgr, List<IHook> rets) {
    String uri = UriKit.rmcontext(request.context(), request.uri());
    List<HookLoad> loads = hookMgr.loads();
    if (CollectionKit.isEmpty(loads))
      return;

    loads.forEach(load -> {
      switch (load.mode()) {
        case FULL:
          if (uri.equals(load.uri()))
            rets.add(load.hook());
          return;
        case PREFIX:
          if (uri.startsWith(load.uri()))
            rets.add(load.hook());
          return;
        case REGEX:
          if (uri.matches(load.uri()))
            rets.add(load.hook());
          return;
        default:
          return;
      }
    });
  }

  private void unloadGlobalHooks(AnostHookMgr hookMgr, List<IHook> rets) {
    List<Class<? extends IHook>> hks = hookMgr.globalUnloads();
    if (CollectionKit.isEmpty(hks))
      return;
    hks.forEach(hk -> this.clearHook(hk, rets));
  }

  private void unloadHooks(YoRequest request, AnostHookMgr hookMgr, List<IHook> rets) {
    if (CollectionKit.isEmpty(rets))
      return;
    List<HookUnload> unloads = hookMgr.unloads();
    if (CollectionKit.isEmpty(unloads))
      return;
    String uri = UriKit.rmcontext(request.context(), request.uri());

    unloads.forEach(un -> {
      switch (un.mode()) {
        case FULL:
          if (uri.equals(un.uri()))
            this.clearHook(un.unload(), rets);
          break;
        case PREFIX:
          if (uri.startsWith(un.uri()))
            this.clearHook(un.unload(), rets);
          break;
        case REGEX:
          if (uri.matches(un.uri()))
            this.clearHook(un.unload(), rets);
          break;
        default:
          break;
      }
    });
  }

  private void clearHook(Class<? extends IHook> hclz, List<IHook> rets) {
    for (int i = rets.size(); i-- > 0; ) {
      IHook ret = rets.get(i);
      if (!hclz.getName().equals(ret.getClass().getName()))
        continue;
      rets.remove(i);
      i--;
    }
  }

}
