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
package io.enoa.log.provider.log4j;

import io.enoa.log.EnoaLog;
import io.enoa.log.EoLogFactory;

/**
 * Created by ein on 2017/8/13.
 */
public class Log4JProvider implements EoLogFactory {
  @Override
  public EnoaLog logger(Class<?> clazz) {
    return new _Log4jLog(clazz);
  }

  @Override
  public EnoaLog logger(String name) {
    return new _Log4jLog(name);
  }
}
