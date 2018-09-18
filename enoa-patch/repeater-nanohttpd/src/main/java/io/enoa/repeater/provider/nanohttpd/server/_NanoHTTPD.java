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
package io.enoa.repeater.provider.nanohttpd.server;

import io.enoa.log.Log;
import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.provider.EoxProviderFactory;
import io.enoa.repeater.factory.server.RepeaterServer;
import io.enoa.repeater.provider.nanohttpd.server.plus._RepeaterNanoTempFileManagerFactory;
import io.enoa.toolkit.thr.EoException;

import java.io.IOException;

class _NanoHTTPD implements RepeaterServer {

  private EoxAccessor accessor;

  _NanoHTTPD(EoxAccessor accessor) {
    this.accessor = accessor;
  }

  @Override
  public void listen(String hostname, int port, boolean ssl, EoxConfig config, EoxProviderFactory factory) {
    _NanoApp server = new _NanoApp(hostname, port, this.accessor, factory.ts(), factory.rule(), factory.errorRender(), config);
    server.setTempFileManagerFactory(new _RepeaterNanoTempFileManagerFactory(config));
    try {
      server.start(config.soTimeout(), false);
    } catch (IOException e) {
      Log.error("Cannot start NanoHTTPD server: {}", e.getMessage(), e);
      throw new EoException(e);
    }
  }

}
