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
package io.enoa.yosart;

import io.enoa.log.EoLogFactory;
import io.enoa.log.provider.jdk.JdkLogProvider;
import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.factory.error.EoxErrorRenderFactory;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.factory.server.RepeaterServerFactory;
import io.enoa.repeater.name.OriginNameRule;
import io.enoa.yosart.ext.assets.EyAssetsExt;
import io.enoa.yosart.ext.render.binary.BinaryRenderExt;
import io.enoa.yosart.ext.render.common.CommonRenderExt;
import io.enoa.yosart.ext.render.error.ErrorRenderExt;
import io.enoa.yosart.ext.render.file.FileRenderExt;
import io.enoa.yosart.ext.render.html.HtmlRenderExt;
import io.enoa.yosart.ext.render.redirect.RedirectRenderExt;
import io.enoa.yosart.ext.render.text.TextRenderExt;
import io.enoa.yosart.ext.router.EyRouterExt;
import io.enoa.yosart.repeater.OysartAccessor;
import io.enoa.yosart.repeater.OysartRepeaterErrorRender;

import java.util.ArrayList;
import java.util.List;

abstract class YasartImpl implements Yosart {

  private List<YoPlugin> plugins;
  private List<YoExt> exts;

  boolean ssl;
  RepeaterServerFactory provider;

  EoxAccessor accessor;
  EoLogFactory log;
  EoxNameRuleFactory rule;
  EoxErrorRenderFactory capture;


  YasartImpl() {
    this.ssl = false;
    this.plugins = new ArrayList<>();
    this.exts = new ArrayList<>();
    this.initDefProvider();
    this.initDefExt();

    this.accessor = new OysartAccessor();
    this.log = new JdkLogProvider();
    this.rule = new OriginNameRule();
    this.capture = new OysartRepeaterErrorRender();

  }

  private void initDefProvider() {
    try {
      Class<?> nano = Class.forName("io.enoa.repeater.provider.nanohttpd.server.NanoHTTPDProvider");
      this.provider = (RepeaterServerFactory) nano.newInstance();
    } catch (Exception e) {
      // skip
    }
  }

  private void initDefExt() {
    // register router ext
    YoExtd.yo().reg(new EyRouterExt(), true);

    // register render ext
    YoExtd.yo().reg(new CommonRenderExt(), true);
    YoExtd.yo().reg(new TextRenderExt(), true);
    YoExtd.yo().reg(new HtmlRenderExt(), true);
    YoExtd.yo().reg(new FileRenderExt(), true);
    YoExtd.yo().reg(new RedirectRenderExt(), true);
    YoExtd.yo().reg(new ErrorRenderExt("text/html"), true);
    YoExtd.yo().reg(new BinaryRenderExt(), true);

    // register assets ext
    YoExtd.yo().reg(new EyAssetsExt(), true);
  }

  void initExt() {
    this.exts.forEach(ext -> YoExtd.yo().reg(ext));
  }

  YoPlugin[] initPlugin() {
    for (YoPlugin plugin : this.plugins) {
      YoPlugind.yo().start(plugin);
    }
    return this.plugins.toArray(new YoPlugin[this.plugins.size()]);
  }

  @Override
  public Yosart plugin(YoPlugin plugin) {
    this.plugins.add(plugin);
    return this;
  }

  @Override
  public Yosart ext(YoExt ext) {
    this.exts.add(ext);
    return this;
  }

  @Override
  public Yosart ssl(boolean ssl) {
    this.ssl = ssl;
    return this;
  }

  @Override
  public Yosart provider(RepeaterServerFactory provider) {
    this.provider = provider;
    return this;
  }

  @Override
  public Yosart log(EoLogFactory log) {
    this.log = log;
    return this;
  }

  @Override
  public Yosart accessor(EoxAccessor accessor) {
    this.accessor = accessor;
    return this;
  }

  @Override
  public Yosart rule(EoxNameRuleFactory rule) {
    this.rule = rule;
    return this;
  }

  @Override
  public Yosart capture(EoxErrorRenderFactory capture) {
    this.capture = capture;
    return this;
  }
}
