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
package io.enoa.toolkit.value;

import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.convert.IConverter;
import io.enoa.toolkit.number.NumberKit;
import io.enoa.toolkit.text.TextKit;

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

  public String string(String def) {
    return ConvertKit.string(this.value, def);
  }

  public String string() {
    return this.string(null);
  }

  public Number number(Number def) {
    String string = this.string();
    return TextKit.blanky(string) ? def : NumberKit.to(string, Number.class);
  }

  public Number number() {
    return this.number(null);
  }

  public Integer integer(Integer def) {
    String string = this.string();
    return TextKit.blanky(string) ? def : NumberKit.integer(string);
  }

  public Integer integer() {
    return this.integer(null);
  }

  public Long longer(Long def) {
    String string = this.string();
    return TextKit.blanky(string) ? def : NumberKit.longer(string);
  }

  public Long longer() {
    return this.longer(null);
  }

  public Double doubler(Double def) {
    String string = this.string();
    return TextKit.blanky(string) ? def : NumberKit.doubler(string);
  }

  public Double doubler() {
    return this.doubler(null);
  }

  public Float floater(Float def) {
    String string = this.string();
    return TextKit.blanky(string) ? def : NumberKit.floater(string);
  }

  public Float floater() {
    return this.floater(null);
  }

  public Short shorter(Short def) {
    String string = this.string();
    return TextKit.blanky(string) ? def : NumberKit.shorter(string);
  }

  public Short shorter() {
    return this.shorter(null);
  }

  public BigInteger bigint(BigInteger def) {
    Number number = this.number();
    return number == null ? def : NumberKit.bigint(this.number());
  }

  public BigInteger bigint() {
    return this.bigint(null);
  }

  public BigDecimal bigdecimal(BigDecimal def) {
    String string = this.string();
    return TextKit.blanky(string) ? def : NumberKit.bigdecimal(string);
  }

  public BigDecimal bigdecimal() {
    return this.bigdecimal(null);
  }

  public Boolean bool(Boolean def) {
    String string = this.string();
    return TextKit.blanky(string) ? def : ConvertKit.bool(string);
  }

  public Boolean bool() {
    return this.bool(null);
  }

  public Date date(String format, Date def) {
    return ConvertKit.date(this.string(), format, def);
  }

  public Date date(String format) {
    return ConvertKit.date(this.string(), format, null);
  }

  public Date date() {
    return ConvertKit.date(this.string(), "yyyy-MM-dd", null);
  }

  public Timestamp timestamp(String format, Timestamp def) {
    Date date = this.date(format);
    return date == null ? def : new Timestamp(date.getTime());
  }

  public Timestamp timestamp(String format) {
    return this.timestamp(format, null);
  }

  public Timestamp timestamp() {
    return this.timestamp("yyyy-MM-dd", null);
  }

  public <T> T as(T def) {
    return this.value == null ? def : this.as();
  }

  public <T> T as() {
    return (T) this.value;
  }

  public <R, P> R to(IConverter<R, P> converter) {
    return converter.convert(this.as());
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
