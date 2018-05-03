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

import io.enoa.yosart.ext.anost.hook.IHook;

import java.util.ArrayList;
import java.util.List;

public class AnostHookMgr {

  private static List<IHook> GLOBALS;
  private static List<HookLoad> LOADS;
  private static List<HookUnload> UNLOADS;

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
    if (LOADS == null)
      LOADS = new ArrayList<>();
    LOADS.add(new HookLoad(uri, hook, mode));
    return this;
  }

  public AnostHookMgr load(String uri, IHook hook) {
    return this.load(uri, hook, Mode.FULL);
  }

  public AnostHookMgr load(IHook hook) {
    if (GLOBALS == null)
      GLOBALS = new ArrayList<>();
    GLOBALS.add(hook);
    return this;
  }

  public AnostHookMgr unload(String uri, Class<? extends IHook> unloads, Mode mode) {
    if (UNLOADS == null)
      UNLOADS = new ArrayList<>();
    UNLOADS.add(new HookUnload(uri, unloads, mode));
    return this;
  }

  public AnostHookMgr unload(String uri, Class<? extends IHook> unloads) {
    return this.unload(uri, unloads, Mode.FULL);
  }

  List<IHook> globals() {
    return GLOBALS;
  }

  List<HookLoad> loads() {
    return LOADS;
  }

  List<HookUnload> unloads() {
    return UNLOADS;
  }

}
