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
package io.enoa.toolkit.value;

import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.convert.IConverter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

public class EnoaValue implements Serializable {

  private Object value;

  public static final EnoaValue NULL = EnoaValue.with(null);

  private EnoaValue(Object value) {
    this.value = value;
  }

  public static EnoaValue with(Object value) {
    return new EnoaValue(value);
  }

  public Object origin() {
    return value;
  }

  public String string(String def, boolean checkblank) {
    return ConvertKit.string(this.value, def, checkblank);
  }

  public String string(String def) {
    return ConvertKit.string(this.value, def);
  }

  public String string() {
    return ConvertKit.string(this.value);
  }

  public Number number(Number def) {
    return ConvertKit.number(this.string(), def);
  }

  public Number number() {
    return ConvertKit.number(this.string());
  }

  public Integer integer(Integer def) {
    return ConvertKit.integer(this.string(), def);
  }

  public Integer integer() {
    return ConvertKit.integer(this.string());
  }

  public Long longer(Long def) {
    return ConvertKit.longer(this.string(), def);
  }

  public Long longer() {
    return ConvertKit.longer(this.string());
  }

  public Double doubler(Double def) {
    return ConvertKit.doubler(this.string(), def);
  }

  public Double doubler() {
    return ConvertKit.doubler(this.string());
  }

  public Float floater(Float def) {
    return ConvertKit.floater(this.string(), def);
  }

  public Float floater() {
    return ConvertKit.floater(this.string());
  }

  public Short shorter(Short def) {
    return ConvertKit.shorter(this.string(), def);
  }

  public Short shorter() {
    return ConvertKit.shorter(this.string());
  }

  public BigInteger bigint(BigInteger def) {
    return ConvertKit.bigint(this.string(), def);
  }

  public BigInteger bigint() {
    return ConvertKit.bigint(this.string());
  }

  public BigDecimal bigdecimal(BigDecimal def) {
    return ConvertKit.bigdecimal(this.string(), def);
  }

  public BigDecimal bigdecimal() {
    return ConvertKit.bigdecimal(this.string());
  }

  public Boolean bool(Boolean def) {
    return ConvertKit.bool(this.string(), def);
  }

  public Boolean bool() {
    return ConvertKit.bool(this.string());
  }

  public Date date(String format, Date def) {
    return ConvertKit.date(this.string(), format, def);
  }

  public Date date(String format) {
    return ConvertKit.date(this.string(), format);
  }

  public Date date() {
    return ConvertKit.date(this.string());
  }

  public Timestamp timestamp(String format, Timestamp def) {
    return ConvertKit.timestamp(this.string(), format, def);
  }

  public Timestamp timestamp(String format) {
    return ConvertKit.timestamp(this.string(), format);
  }

  public Timestamp timestamp() {
    return ConvertKit.timestamp(this.string());
  }

  public <T> T as(T def) {
    return ConvertKit.as(this.origin(), def);
  }

  public <T> T as() {
    return ConvertKit.as(this.origin());
  }

  public <R> R to(IConverter<R, EnoaValue> converter) {
    return ConvertKit.to(this, converter);
  }

  public <T> T to(Class<T> clazz) {
    return ConvertKit.to(this.string(), clazz);
  }

  public boolean isNull() {
    return this.value == null;
  }

  @Override
  public int hashCode() {
    if (this.value == null)
      return 0;
    return this.value.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof EnoaValue))
      return false;
    if (this == other)
      return true;
    EnoaValue that = (EnoaValue) other;
    if (that.value == null && this.value == null)
      return true;
    return that.value != null && that.value.equals(this.value);
  }

  @Override
  public String toString() {
    return this.string();
  }
}
