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
package io.enoa.example.repeater;

import io.enoa.log.EoLogFactory;
import io.enoa.log.provider.slf4j.Slf4JLogProvider;
import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.EoxConfig;
import io.enoa.toolkit.path.PathKit;

abstract class AbstractRepeaterExample {

  static final int PORT = 9001;

  EoxAccessor accessor() {
    return new ExampleRepeaterAccessorImpl();
  }

  EoLogFactory log() {
    return new Slf4JLogProvider();
  }

  EoxConfig config() {
    return new EoxConfig.Builder()
      .debug(true)
      .info(true)
      .infoUseLog(false)
      .context("/example")
//      .tmp(Paths.get(PathKit.path(), "tmp"))
      .tmp(PathKit.debugPath().resolve("tmp"))
      .build();
  }


  protected abstract void start();

}
