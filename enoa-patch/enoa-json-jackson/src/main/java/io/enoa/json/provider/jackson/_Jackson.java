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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.enoa.json.EnoaJson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * enoa - io.enoa.json.provider.jackson
 */
class _Jackson extends EnoaJson {

//  // Jackson 生成 json 的默认行为是生成 null value，可设置此值全局改变默认行为
//  private static boolean defaultGenerateNullValue = true;
//
//  // generateNullValue 通过设置此值，可临时改变默认生成 null value 的行为
//  private Boolean generateNullValue = null;

//  private ObjectMapper objectMapper;

  private Map<String, ObjectMapper> CACHE = new HashMap<>();

  private static class Holder {
    private static final EnoaJson INSTANCE = new _Jackson();
  }

  static EnoaJson instance() {
    return Holder.INSTANCE;
  }

  private _Jackson() {

  }

  private ObjectMapper mapper(String datePattern) {
    ObjectMapper _om = CACHE.get(datePattern == null ? "def" : datePattern);
    if (_om != null)
      return _om;

    _om = new ObjectMapper();
    if (datePattern == null) {
      CACHE.put("def", _om);
      return _om;
    }

    _om.setDateFormat(new SimpleDateFormat(datePattern));
    _om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    CACHE.put(datePattern, _om);
    return _om;
  }

  private ObjectMapper mapper() {
    return this.mapper(null);
  }

  @Override
  public String toJson(Object object) {
    try {
      return this.mapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toJson(Object object, String datePattern) {
    try {
      return this.mapper(datePattern).writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public <T> T parse(String json, Class<T> type) {
    try {
      return this.mapper().readValue(json, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public <T> T parse(String json, Type type) {
    try {
      return this.mapper().readValue(json, this.mapper().getTypeFactory().constructType(type));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public <T> List<T> parseArray(String json, Class<T> type) {
    try {
      return this.mapper().readValue(json, this.mapper().getTypeFactory().constructArrayType(type));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
