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
package io.enoa.yosart.kernel.http;

import io.enoa.repeater.http.Request;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.value.EnoaValue;
import io.enoa.yosart.thr.OyExtException;

public interface YoRequest extends Request {

  static YoRequest create(Request request) {
    return create(request, null);
  }

  static YoRequest create(Request request, PathVariable variable) {
    return new OyRequestWrapper(request, variable);
  }

  EnoaValue cookieValue(String name);

  EnoaValue paraValue(String name);

  EnoaValue headerValue(String name);

  EnoaValue variableValue(String name);

  default <T> T bean(Class<T> clazz) {
    return this.bean(clazz, null);
  }

  default <T> T bean(Class<T> clazz, boolean skipConvertError) {
    return this.bean(clazz, null, skipConvertError);
  }

  <T> T bean(Class<T> clazz, String name);

  <T> T bean(Class<T> clazz, String name, boolean skipConvertError);

  String[] variableNames();

  default String variable(String name) {
    return this.variable(name, null);
  }

  String variable(String name, String def);

  default Integer variableToInt(String name) {
    return this.variableToInt(name, null);
  }

  Integer variableToInt(String name, Integer def);

  default Boolean variableToBoolean(String name) {
    return this.variableToBoolean(name, null);
  }

  Boolean variableToBoolean(String name, Boolean def);

  Session session() throws OyExtException;

  /**
   * 大多數模板中獲取信息都是使用 getter setter 方式獲取, 但是 repeater & yosart 中 request 均不提供 getter setter 方法,
   * 如果模板中有需要使用 request 信息, 可調用此方法, 將 request 數據放置與 Kv (Map) 中
   * <p>
   * xfixme 可以考慮添加一個擴展, 支持自定義 request
   * -- 暫不支持, 若提供自定義 request 現有 control 則無法直接指定 request 需要再次進行轉換, 若需要自定義 request 可直接實現 accessor 或 router 實現
   *
   * @return Kv
   */
  Kv data();
}
