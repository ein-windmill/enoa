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
package io.enoa.repeater;

import io.enoa.log.EMgrLog;
import io.enoa.log.EoLogFactory;
import io.enoa.log.provider.jdk.JdkLogProvider;
import io.enoa.repeater.factory.error.EoxErrorRenderFactory;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.factory.provider.EoxProviderFactory;
import io.enoa.repeater.factory.server.RepeaterServerFactory;
import io.enoa.repeater.factory.ts.RepeaterTranslateFactory;
import io.enoa.repeater.kit.tip.RepeaterTip;
import io.enoa.repeater.name.OriginNameRule;
import io.enoa.toolkit.http.UriKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.text.TextKit;

class EnoaRepeaterImpl implements Repeater {

  private EoxConfig config;
  private RepeaterServerFactory server;
  private boolean ssl;

  private EoxAccessor accessor;
  private EoLogFactory log;
  private EoxNameRuleFactory rule;
  private EoxErrorRenderFactory capture;


  EnoaRepeaterImpl(RepeaterServerFactory server) {
    this.config = EoxConfig.def();
    this.server = server;
    this.ssl = false;
    this.accessor = new EnoaRepeaterAccessorImpl();
    this.log = new JdkLogProvider();
    this.capture = EoxErrorRenderFactory.def();
    this.rule = new OriginNameRule();
  }

  @Override
  public Repeater config(EoxConfig config) {
    if (config == null)
      return this;
    EoxConfig.Builder builder = config.newBuilder();
    builder.context(UriKit.correct(config.context()));
    this.config = builder.build();
    EoxConfig.current(this.config);
    return this;
  }

  @Override
  public Repeater accessor(EoxAccessor accessor) {
    this.accessor = accessor;
    return this;
  }

  @Override
  public Repeater log(EoLogFactory log) {
    this.log = log;
    return this;
  }

  @Override
  public Repeater rule(EoxNameRuleFactory rule) {
    this.rule = rule;
    return this;
  }

  @Override
  public Repeater capture(EoxErrorRenderFactory capture) {
    this.capture = capture;
    return this;
  }

  @Override
  public Repeater ssl(boolean open) {
    this.ssl = open;
    return this;
  }

  @Override
  public void listen(String hostname, int port) {

    Kv other = this.config.other();
    if (other == null || other.isEmpty()) {
      other = Kv.create();
    }
    other.set("_EOX_SERVER", this.server.name())
      .set("_EOX_SERVER_VERSION", this.server.version());

    // 配置过滤
    EoxConfig.Builder builder = this.config.newBuilder();

    builder.other(other);
    String context = this.config.context();
    if (!context.startsWith("/"))
      context = "/".concat(context);
    builder.context(context);

    this.config = builder.build();
    EMgrLog.defLogFactory(this.log);
    EoxConfig.current(this.config);

    this.server.server(this.accessor)
      .listen(hostname, port, this.ssl, this.config,
        new EoxProviderFactory() {
          @Override
          public RepeaterTranslateFactory ts() {
            return server.ts();
          }

          @Override
          public EoxNameRuleFactory rule() {
            return rule;
          }

          @Override
          public EoxErrorRenderFactory errorRender() {
            return capture;
          }
        }
      );

    RepeaterTip.message("Repeater start on {0}://{1}:{2}/{3}",
      this.ssl ? "https" : "http",
      hostname == null ? "localhost" : hostname,
      port,
      TextKit.isBlank(this.config.context()) ?
        ("/".equals(this.config.context()) ? "" : this.config.context()) :
        (this.config.context().startsWith("/") ? this.config.context().substring(1, this.config.context().length()) :
          this.config.context())
    );
  }

  @Override
  public void listen(int port) {
    this.listen(null, port);
  }
}
