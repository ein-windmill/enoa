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
package io.enoa.toolkit.map;

import io.enoa.toolkit.convert.IConverter;
import io.enoa.toolkit.value.EnoaValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public interface FastKv<S extends Map> extends Map<String, Object> {

  default S set(String key, Object value) {
    this.put(key, value);
    return (S) this;
  }

  default S set(Map<String, ?> map) {
    this.putAll(map);
    return (S) this;
  }

//  default S set(FastKv kv) {
//    this.putAll(kv);
//    return (S) this;
//  }

  default S delete(String key) {
    this.remove(key);
    return (S) this;
  }

  default Object origin(String key) {
    return this.get(key);
  }

  default EnoaValue value(String key) {
    return EnoaValue.with(this.origin(key));
  }

  default <T> T as(String key, T def) {
    return this.value(key).as(def);
  }

  default <T> T as(String key) {
    return this.value(key).as();
  }

  default <T> T to(String key, IConverter<T, EnoaValue> converter) {
    return this.value(key).to(converter);
  }

  default <T> T to(String key, Class<T> clazz) {
    return this.value(key).to(clazz);
  }

  default String string(String key, String def) {
    return this.value(key).string(def);
  }

  default String string(String key) {
    return this.value(key).string();
  }

  default Integer integer(String key, Integer def) {
    return this.value(key).integer(def);
  }

  default Integer integer(String key) {
    return this.value(key).integer();
  }

  default Long longer(String key, Long def) {
    return this.value(key).longer(def);
  }

  default Long longer(String key) {
    return this.value(key).longer();
  }

  default Number number(String key, Number def) {
    return this.value(key).number(def);
  }

  default Number number(String key) {
    return this.value(key).number();
  }

  default Short shorter(String key) {
    return this.value(key).shorter();
  }

  default Short shorter(String key, Short def) {
    return this.value(key).shorter(def);
  }

  default BigInteger bigint(String key) {
    return this.value(key).bigint();
  }

  default BigInteger bigint(String key, BigInteger def) {
    return this.value(key).bigint(def);
  }

  default BigDecimal bigdecimal(String key) {
    return this.value(key).bigdecimal();
  }

  default BigDecimal bigdecimal(String key, BigDecimal def) {
    return this.value(key).bigdecimal(def);
  }

  default Double doubler(String key) {
    return this.value(key).doubler();
  }

  default Double doubler(String key, Double def) {
    return this.value(key).doubler(def);
  }

  default Float floater(String key) {
    return this.value(key).floater();
  }

  default Float floater(String key, Float def) {
    return this.value(key).floater(def);
  }

  default Boolean bool(String key, Boolean def) {
    return this.value(key).bool(def);
  }

  default Boolean bool(String key) {
    return this.value(key).bool();
  }

  default Date date(String key, String format, Date def) {
    return this.value(key).date(format, def);
  }

  default Date date(String key, String format) {
    return this.value(key).date(format);
  }

  default Date date(String key) {
    return this.value(key).date();
  }

  default Timestamp timestamp(String key, String format, Timestamp def) {
    return this.value(key).timestamp(format, def);
  }

  default Timestamp timestamp(String key, String format) {
    return this.value(key).timestamp(format);
  }

  default Timestamp timestamp(String key) {
    return this.value(key).timestamp();
  }

  /**
   * 是否存在当前 key
   */
  default boolean exists(String key) {
    return this.containsKey(key);
  }

  /**
   * 值是否为 null
   *
   * @param key 键
   * @return boolean
   */
  default boolean isNull(String key) {
    return this.get(key) == null;
  }

  /**
   * 值是否不为 null
   *
   * @param key 键
   * @return boolean
   */
  default boolean notNull(String key) {
    return !this.isNull(key);
  }
}
