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
package io.enoa.example.repeater;

import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.Repeater;
import io.enoa.repeater.provider.tomcat.server.TomcatProvider;
import io.enoa.toolkit.map.Kv;

public class TomcatRepeater extends AbstractRepeaterExample {

  public static void main(String[] args) {
    TomcatRepeater repeater = new TomcatRepeater();
    repeater.start();
  }

  @Override
  protected void start() {
    EoxConfig config = super.config();
    config = config.newBuilder()
      .other(Kv.by("provider.tomcat.upload.vendor", "cos"))
      .build();
    Repeater.createServer(new TomcatProvider())
      .accessor(super.accessor())
      .log(super.log())
      .config(config)
      .listen(PORT);
  }
}
