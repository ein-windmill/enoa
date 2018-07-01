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
package io.enoa.repeater.provider.tio.server;

import io.enoa.log.Log;
import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.provider.EoxProviderFactory;
import io.enoa.repeater.factory.server.RepeaterServer;
import io.enoa.repeater.provider.tio.ts.TioTranslateProvider;
import org.tio.http.common.HttpConfig;
import org.tio.http.server.HttpServerStarter;

import java.io.IOException;

class _Tio implements RepeaterServer {
  private EoxAccessor accessor;

  _Tio(EoxAccessor accessor) {
    this.accessor = accessor;
  }

  @Override
  public void listen(String hostname, int port, boolean ssl, EoxConfig config, EoxProviderFactory factory) {
    TioTranslateProvider tiots = (TioTranslateProvider) factory.ts();
    HttpConfig tiocfg = new HttpConfig(port, (long) config.soTimeout(), "/", "");
    HttpServerStarter starter = new HttpServerStarter(tiocfg,
      new _TioHandler(this.accessor, tiots, factory.rule(), factory.errorRender(), config, tiocfg));
    try {
      starter.start();
    } catch (IOException e) {
      Log.error(e.getMessage(), e);
    }
  }
}
