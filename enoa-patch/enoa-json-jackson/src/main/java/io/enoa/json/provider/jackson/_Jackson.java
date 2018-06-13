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

  private ObjectMapper objectMapper;

  private static class Holder {
    private static final EnoaJson INSTANCE = new _Jackson();
  }

  static EnoaJson instance() {
    return Holder.INSTANCE;
  }

  private _Jackson() {

  }

  private ObjectMapper mapper() {
    if (this.objectMapper != null)
      return this.objectMapper;

    this.objectMapper = new ObjectMapper();
    String dp = datePattern != null ? datePattern : defaultDatePattern();
    if (dp != null)
      this.objectMapper.setDateFormat(new SimpleDateFormat(dp));
    this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return this.objectMapper;
  }

//  @Override
//  public Object origin() {
//    return this.mapper();
//  }

  @Override
  public String toJson(Object object) {
    try {
      return this.mapper().writeValueAsString(object);
    } catch (Exception e) {
      throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }
  }

  @Override
  public <T> T parse(String json, Class<T> type) {
    try {
      return this.mapper().readValue(json, type);
    } catch (Exception e) {
      throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }
  }

  @Override
  public <T> T parse(String json, Type type) {
    try {
      return this.mapper().readValue(json, this.mapper().getTypeFactory().constructType(type));
    } catch (Exception e) {
      throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }
  }

  @Override
  public <T> List<T> parseArray(String json, Class<T> type) {
    try {
      return this.mapper().readValue(json, this.mapper().getTypeFactory().constructArrayType(type));
    } catch (Exception e) {
      throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }
  }

}
