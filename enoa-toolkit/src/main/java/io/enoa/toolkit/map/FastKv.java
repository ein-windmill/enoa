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
package io.enoa.toolkit.map;

import io.enoa.toolkit.value.EnoaValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

public interface FastKv {

  Object origin(String key);

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

  default Double doubler(String key, Double def) {
    return this.value(key).doubler(def);
  }

  default Double doubler(String key) {
    return this.value(key).doubler();
  }

  default Float floater(String key, Float def) {
    return this.value(key).floater(def);
  }

  default Float floater(String key) {
    return this.value(key).floater();
  }

  default Short shorter(String key, Short def) {
    return this.value(key).shorter(def);
  }

  default Short shorter(String key) {
    return this.value(key).shorter();
  }

  default BigDecimal bigdecimal(String key, BigDecimal def) {
    return this.value(key).bigdecimal(def);
  }

  default BigDecimal bigdecimal(String key) {
    return this.value(key).bigdecimal();
  }

  default BigInteger bigint(String key, BigInteger def) {
    return this.value(key).bigint(def);
  }

  default BigInteger bigint(String key) {
    return this.value(key).bigint();
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

  boolean exists(String key);


}
