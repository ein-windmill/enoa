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

import java.nio.file.Path;

public interface Yosart extends Yasart, Yesart, YoRepeater {

  static Yosart createServer() {
    return new YosartImpl();
  }

  Yosart name(String name);

  Yosart context(String context);

  Yosart config(YoConfig config);

  Yosart assets(String uri, Path path);

  Yosart assets(String uri, Path path, boolean showTree);

  Yosart before(YoBefore before);

  void listen(String hostname, int port);

  void listen(int port);

  void listen();

}
