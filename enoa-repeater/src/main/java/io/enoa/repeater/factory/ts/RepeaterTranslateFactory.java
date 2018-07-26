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
package io.enoa.repeater.factory.ts;

import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.name.EoxNameRuleFactory;
import io.enoa.repeater.http.Request;
import io.enoa.repeater.http.Response;

/**
 * 翻译器, 将不同的 server 翻译成 Repeater
 *
 * @param <REQ>
 * @param <RESP>
 */
public interface RepeaterTranslateFactory<REQ, RESP> {

  /**
   * 将原始 request 翻译成 repeater request
   *
   * @param config 配置
   * @param rule   临时文件命名规则
   * @param oreq   原始 request
   * @return Request
   */
  Request request(EoxConfig config, EoxNameRuleFactory rule, REQ oreq);

  /**
   * @param config 配置
   * @param oresp  原始 response
   * @param resp   repeater response
   * @return RESP 某些 server 需要返回 response 某部分不需要, 两种都兼容方案
   */
  RESP response(EoxConfig config, RESP oresp, Response resp);

}
