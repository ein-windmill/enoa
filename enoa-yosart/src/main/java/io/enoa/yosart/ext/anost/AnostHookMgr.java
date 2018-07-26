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

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.yosart.ext.anost.hook.IHook;

import java.util.ArrayList;
import java.util.List;

public class AnostHookMgr {

  private List<IHook> GLOBALS_LOAD;
  private List<Class<? extends IHook>> GLOBALS_UNLOAD;
  private List<HookLoad> LOADS;
  private List<HookUnload> UNLOADS;

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

  public AnostHookMgr load(String uri, IHook hook, Mode mode) {
    if (this.LOADS == null)
      this.LOADS = new ArrayList<>();
    this.LOADS.add(new HookLoad(uri, hook, mode));
    return this;
  }

  public AnostHookMgr load(String uri, IHook hook) {
    return this.load(uri, hook, Mode.FULL);
  }

  public AnostHookMgr load(IHook hook) {
    if (this.GLOBALS_LOAD == null)
      this.GLOBALS_LOAD = new ArrayList<>();
    this.GLOBALS_LOAD.add(hook);
    return this;
  }

  public AnostHookMgr unload(Class<? extends IHook> unload) {
    if (this.GLOBALS_UNLOAD == null)
      this.GLOBALS_UNLOAD = new ArrayList<>();
    this.GLOBALS_UNLOAD.add(unload);
    return this;
  }

  public AnostHookMgr unload(Class<? extends IHook> unload, Mode mode, String uri) {
    if (this.UNLOADS == null)
      this.UNLOADS = new ArrayList<>();
    this.UNLOADS.add(new HookUnload(uri, unload, mode));
    return this;
  }

  public AnostHookMgr unload(Class<? extends IHook> unload, String uri) {
    return this.unload(unload, Mode.FULL, uri);
  }

  /**
   * 取消加载 Hook, 需要注意与 unload(Class unload) 的区别, 这里并非添加全局
   * 取消 Hook 如果没有传递 uri 则会放弃添加
   *
   * @param unload Hook class
   * @param uris   uris
   * @return AnostHookMgr
   */
  public AnostHookMgr unload(Class<? extends IHook> unload, Mode mode, String... uris) {
    if (CollectionKit.isEmpty(uris))
      return this;
    for (String uri : uris) {
      this.unload(unload, mode, uri);
    }
    return this;
  }

  public AnostHookMgr unload(Class<? extends IHook> unload, String... uris) {
    return this.unload(unload, Mode.FULL, uris);
  }

  List<IHook> globalLoads() {
    return this.GLOBALS_LOAD;
  }

  List<Class<? extends IHook>> globalUnloads() {
    return this.GLOBALS_UNLOAD;
  }

  List<HookLoad> loads() {
    return this.LOADS;
  }

  List<HookUnload> unloads() {
    return this.UNLOADS;
  }

}
