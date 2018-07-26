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
package io.enoa.toolkit.bean.tomap;

import io.enoa.toolkit.bean.Bonfig;

import java.util.Map;

public class _BeanToMap {


  public static Map<String, Object> map(Object bean, int depth, Bonfig config) {
    if (bean == null)
      return null;

    if (depth-- < 0)
      return null;

    if (bean instanceof Map) {
      return MapConvert.instance().convert((Map) bean, depth, config);
    }

    return BeanConvert.instance().convert(bean, depth, config);
  }

}
