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
package io.enoa.repeater.factory.server;

import io.enoa.repeater.EoxAccessor;
import io.enoa.repeater.factory.ts.RepeaterTranslateFactory;

public interface RepeaterServerFactory {

  /**
   * 服务名称
   *
   * @return String
   */
  String name();

  /**
   * 服务版本
   *
   * @return String
   */
  String version();

  /**
   * 服务实现
   *
   * @param accessor accessor
   * @return RepeaterServer
   */
  RepeaterServer server(EoxAccessor accessor);

  /**
   * 翻译器
   *
   * @return RepeaterTranslateFactory
   */
  RepeaterTranslateFactory ts();

}
