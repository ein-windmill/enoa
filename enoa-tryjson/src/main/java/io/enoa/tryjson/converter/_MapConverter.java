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
package io.enoa.tryjson.converter;

import io.enoa.tryjson.Tryjson;

import java.util.Map;

class _MapConverter implements EsonConverter<Map> {
  private static class Holder {
    private static final _MapConverter INSTANCE = new _MapConverter();
  }

  static _MapConverter instance() {
    return Holder.INSTANCE;
  }

  @Override
  public String json(Map map, int depth, ConvConf conf) {
    if (map == null)
      return null;
    StringBuilder _json = new StringBuilder();
    _json.append('{');
    map.forEach((key, value) -> {
      _json.append('"').append(String.valueOf(key)).append('"').append(':');
      _json.append(Tryjson.json(value, depth, conf));
      _json.append(',');
    });
    int lastIx = _json.length() - 1;
    if (_json.charAt(lastIx) == ',')
      _json.deleteCharAt(lastIx);

    _json.append('}');
    return _json.toString();
  }
}
