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
package io.enoa.yosart;

import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.Repeater;
import io.enoa.repeater.http.Method;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.http.UriKit;
import io.enoa.toolkit.sys.EnvKit;
import io.enoa.toolkit.thr.EoException;
import io.enoa.yosart.kernel.data.YdAssets;
import io.enoa.yosart.kernel.data.YdRouterInfo;
import io.enoa.yosart.kernel.ext.YmBootHookExt;
import io.enoa.yosart.kernel.ext.YmRouterExt;
import io.enoa.yosart.kernel.resources.OyResourced;
import io.enoa.yosart.kernel.resources.YaControl;
import io.enoa.yosart.kernel.resources.YoAction;
import io.enoa.yosart.kernel.resources.YoRouter;
import io.enoa.yosart.kit.tip.OysartTip;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class YosartImpl extends YasartImpl {

  private String name;
  private String context;
  private YdAssets assets;
  private YoConfig config;
  private List<YdRouterInfo> routerRegisters;
  private List<YoCut> befores;
  private List<YoCut> afters;

  YosartImpl() {
    this.name = "Yosart";
    this.context = "/";
    this.config = YoConfig.def();
    this.routerRegisters = new ArrayList<>();
  }

  private void init() {

    if (this.befores != null) {
      this.befores.forEach(YoCut::execute);
    }

    /*
    init plugin
     */
    YoPlugin[] plugins = super.initPlugin();

    /*
    init extension
     */
    super.initExt();

    // 率先初始化配置信息, 供其他地方调用 config
    // start oysartd data
    Oysartd.Builder builder = new Oysartd.Builder()
      .name(this.name)
      .version(EnoaTipKit.message("yosart.version"))
      .config(this.config);
    if (this.assets != null)
      builder.assets(this.assets);

    // init ext to oysartd
    builder.exts(YoExtd.yo().exts());

    // fill plugin to oysartd
    builder.plugins(plugins);

    Oysartd.yo(builder);

    YoExt[] bootHooks = Oysart.exts(YoExt.Type.BOOT_HOOK);
    for (YoExt hook : bootHooks) {
      if (!(hook instanceof YmBootHookExt))
        throw new EoException(EnoaTipKit.message("eo.tip.yosart.ext_not_boot_hook", hook.name(), hook.getClass().getName()));
      ((YmBootHookExt) hook).hook(this);
    }

    /*
    check router extension
     */
    YoExt ext = Oysart.ext(YoExt.Type.ROUTER);
    if (ext == null)
      throw new EoException(EnoaTipKit.message("eo.tip.yosart.ext_404_router"));
    if (!(ext instanceof YmRouterExt))
      throw new EoException(EnoaTipKit.message("eo.tip.yosart.ext_not_router", ext.name(), ext.getClass().getName()));

    // register routers
    if (CollectionKit.notEmpty(this.routerRegisters)) {
      builder.resources(OyResourced.yo().reg(this.routerRegisters));
    }

    Oysartd.yo(builder);


    if (this.afters != null)
      this.afters.forEach(YoCut::execute);

  }

  @Override
  public Yosart config(YoConfig config) {
    this.config = config;
    return this;
  }

  @Override
  public Yosart assets(String uri, Path path) {
    return this.assets(uri, path, this.config.debug());
  }

  @Override
  public Yosart assets(String uri, Path path, boolean showTree) {
    this.assets = new YdAssets.Builder().uri(UriKit.correct(uri)).path(path).showTree(showTree).build();
    return this;
  }

  @Override
  public Yosart before(YoCut before) {
    if (this.befores == null)
      this.befores = new ArrayList<>();
    this.befores.add(before);
    return this;
  }

  @Override
  public Yosart after(YoCut after) {
    if (this.afters == null)
      this.afters = new ArrayList<>();
    this.afters.add(after);
    return this;
  }

  @Override
  public Yosart name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public Yosart context(String context) {
    this.context = UriKit.correct(context);
    return this;
  }

  @Override
  public Yosart handle(Method method, String uri, YoAction action) {
    this.routerRegisters.add(new YdRouterInfo.Builder().method(method).uri(uri).action(action).build());
    return this;
  }

  @Override
  public Yosart handle(String uri, YoAction action) {
    this.routerRegisters.add(new YdRouterInfo.Builder().uri(uri).action(action).build());
    return this;
  }

  @Override
  public Yosart handle(String uri, Class<? extends YaControl> control) {
    this.routerRegisters.add(new YdRouterInfo.Builder().uri(uri).control(control).build());
    return this;
  }

  @Override
  public Yosart handle(String uri, YoRouter router) {
    this.routerRegisters.add(new YdRouterInfo.Builder().uri(uri).router(router).build());
    return this;
  }

  @Override
  public Yosart handle(Class<? extends YaControl> control) {
    this.routerRegisters.add(new YdRouterInfo.Builder().control(control).build());
    return this;
  }

  @Override
  public Yosart handle(YoRouter router) {
    this.routerRegisters.add(new YdRouterInfo.Builder().router(router).build());
    return this;
  }

  @Override
  public void listen(String hostname, int port) {
    if (this.provider == null)
      throw new EoException(EnoaTipKit.message("eo.tip.yosart.provider_null"));

    this.init();

    OysartTip.message("{0} start on {1}://{2}:{3}{4}", this.name, this.ssl ? "https" : "http",
      hostname == null ? "localhost" : hostname, port, this.context);
    Repeater.createServer(super.provider)
      .ssl(super.ssl)
//      .factory(this.repeater)
      .accessor(super.accessor)
      .log(super.log)
      .rule(super.rule)
      .capture(super.capture)
      .config(
        new EoxConfig.Builder()
          .info(false)
          .infoUseLog(false)
          .context(this.context)
          .charset(this.config.charset())
          .debug(this.config.debug())
          .other(this.config.other())
          .ssl(this.config.ssl())
          .maxUploadSize(this.config.maxUploadSize())
          .tmp(this.config.tmp())
          .build()
      )
      .listen(hostname, port);
  }

  @Override
  public void listen(int port) {
    this.listen(null, port);
  }

  @Override
  public void listen() {
    this.listen(null, EnvKit.integer("yosart.port", 9102));
  }

}
