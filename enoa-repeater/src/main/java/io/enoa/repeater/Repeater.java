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
package io.enoa.repeater;

import io.enoa.log.EoLogFactory;
import io.enoa.repeater.factory.error.EoxErrorRenderFactory;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.factory.server.RepeaterServerFactory;

public interface Repeater {

  static Repeater createServer(RepeaterServerFactory server) {
    return new EnoaRepeaterImpl(server);
  }

  static EoxConfig config() {
    return EoxConfig.current();
  }

  Repeater config(EoxConfig config);

  default Repeater ssl() {
    return this.ssl(true);
  }

  Repeater ssl(boolean open);

  Repeater accessor(EoxAccessor accessor);

  Repeater log(EoLogFactory log);

  Repeater rule(EoxNameRuleFactory rule);

  Repeater capture(EoxErrorRenderFactory capture);

  void listen(String hostname, int port);

  void listen(int port);

}
