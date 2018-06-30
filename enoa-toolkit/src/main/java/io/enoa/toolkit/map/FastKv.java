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

import io.enoa.toolkit.value.EnoaValue;

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

  default Boolean bool(String key, Boolean def) {
    return this.value(key).bool(def);
  }

  default Boolean bool(String key) {
    return this.value(key).bool();
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
