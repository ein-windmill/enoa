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
package io.enoa.example.yosart.anost;

import io.enoa.example.yosart.anost.ctrl.IndexCtrl;
import io.enoa.example.yosart.anost.hook.GlobalHook;
import io.enoa.example.yosart.anost.thr.AnostCatch;
import io.enoa.log.provider.slf4j.Slf4JLogProvider;
import io.enoa.repeater.provider.nanohttpd.server.NanoHTTPDProvider;
import io.enoa.yosart.Yosart;
import io.enoa.yosart.ext.anost.AnostExt;
import io.enoa.yosart.ext.anost.AnostHookMgr;

public class YosartAnostBoot {

  private AnostExt anost() {
    AnostExt ext = new AnostExt();
    AnostHookMgr mgr = ext.hookMgr();
    mgr.load(new GlobalHook());
    return ext;
  }

  private void start() {
    Yosart.createServer()
      .provider(new NanoHTTPDProvider())
      .log(new Slf4JLogProvider())
      .ext(this.anost())
      .ext(new AnostCatch())
      .context("/")
      .handle("/", IndexCtrl.class)
      .listen();
  }

  public static void main(String[] args) {
    YosartAnostBoot boot = new YosartAnostBoot();
    boot.start();
  }

}
