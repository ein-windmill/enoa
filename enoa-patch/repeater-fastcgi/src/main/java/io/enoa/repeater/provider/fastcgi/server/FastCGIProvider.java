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
package io.enoa.repeater.provider.fastcgi.server;

import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.factory.server.RepeaterServer;
import io.enoa.repeater.factory.server.RepeaterServerFactory;
import io.enoa.repeater.factory.ts.RepeaterTranslateFactory;
import io.enoa.repeater.provider.fastcgi.ts.FastCGITranslateProvider;

public class FastCGIProvider implements RepeaterServerFactory {
  @Override
  public String name() {
    return "FastCGI";
  }

  @Override
  public String version() {
    return "1.0";
  }

  @Override
  public RepeaterServer server(EoxAccessor accessor) {
    return new _FastCGI(accessor);
  }

  @Override
  public RepeaterTranslateFactory ts() {
    return new FastCGITranslateProvider();
  }
}
