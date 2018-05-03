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
package io.enoa.json.provider.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.enoa.json.EnoaJson;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * enoa - io.enoa.json.provider.jackson
 */
class _Jackson extends EnoaJson {

  // Jackson 生成 json 的默认行为是生成 null value，可设置此值全局改变默认行为
  private static boolean defaultGenerateNullValue = true;

  // generateNullValue 通过设置此值，可临时改变默认生成 null value 的行为
  private Boolean generateNullValue = null;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String toJson(Object object) {
    try {
      String dp = datePattern != null ? datePattern : defaultDatePattern();
      if (dp != null)
        objectMapper.setDateFormat(new SimpleDateFormat(dp));

      // 优先使用对象属性 generateNullValue，决定转换 json时是否生成 null value
      Boolean pnv = generateNullValue != null ? generateNullValue : defaultGenerateNullValue;
      if (!pnv)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

      return objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }
  }

  @Override
  public <T> T parse(String json, Class<T> type) {
    try {
      return objectMapper.readValue(json, type);
    } catch (Exception e) {
      throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }
  }

  @Override
  public <T> T parse(String json, Type type) {
    try {
      return objectMapper.readValue(json, objectMapper.getTypeFactory().constructType(type));
    } catch (Exception e) {
      throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }
  }

  @Override
  public <T> List<T> parseArray(String json, Class<T> type) {
    try {
      return objectMapper.readValue(json, objectMapper.getTypeFactory().constructArrayType(type));
    } catch (Exception e) {
      throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }
  }

}
