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
package io.enoa.json.provider.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.enoa.json.EnoaJson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * enoa - io.enoa.json.provider.fastjson
 */
class _Fastjson extends EnoaJson {
  @Override
  public String toJson(Object object) {
    String dp = datePattern != null ? datePattern : defaultDatePattern();
    if (dp == null)
      return JSON.toJSONString(object);

    return JSON.toJSONStringWithDateFormat(object, dp, SerializerFeature.WriteDateUseDateFormat);
  }

  @Override
  public <T> T parse(String json, Class<T> type) {
    return JSON.parseObject(json, type);
  }

  @Override
  public <T> T parse(String json, Type type) {
    return JSON.parseObject(json, type);
  }

  @Override
  public <T> List<T> parseArray(String json, Class<T> type) {
    return JSON.parseArray(json, type);
  }

}
