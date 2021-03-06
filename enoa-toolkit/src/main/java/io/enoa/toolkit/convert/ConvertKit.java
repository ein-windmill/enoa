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
package io.enoa.toolkit.convert;

import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.number.NumberKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;
import io.enoa.toolkit.value.Vae;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

public class ConvertKit {
  private ConvertKit() {
  }

  public static String string(Object obj) {
    return string(obj, null, Boolean.TRUE);
  }

  public static String string(Object obj, boolean checkblank) {
    return string(obj, null, checkblank);
  }

  public static String string(Object obj, String def) {
    return string(obj, def, Boolean.TRUE);
  }

  public static String string(Object obj, String def, boolean checkblank) {
    if (obj == null)
      return def;

    String _string = obj.toString();
    return checkblank ?
      TextKit.blanky(_string) ? def : _string
      : _string;
  }

  public static Number number(String text, Number def) {
    return TextKit.blanky(text) ? def : NumberKit.to(text, Number.class);
  }

  public static Number number(String text) {
    return number(text, null);
  }

  public static Number number(Number number, Number def) {
    Number ret = NumberKit.to(number, Number.class);
    return Vae.nulldef(ret, def);
  }

  public static Number number(Number number) {
    return number(number, null);
  }

  public static Integer integer(String text) {
    return integer(text, null);
  }

  public static Integer integer(String text, Integer def) {
    return TextKit.blanky(text) ? def : NumberKit.integer(text);
  }

  public static Integer integer(Number number, Integer def) {
    Integer ret = NumberKit.integer(number);
    return Vae.nulldef(ret, def);
  }

  public static Integer integer(Number number) {
    return integer(number, null);
  }

  public static Long longer(String text) {
    return longer(text, null);
  }

  public static Long longer(String text, Long def) {
    return TextKit.blanky(text) ? def : NumberKit.longer(text);
  }

  public static Long longer(Number number, Long def) {
    Long ret = NumberKit.longer(number);
    return Vae.nulldef(ret, def);
  }

  public static Long longer(Number number) {
    return longer(number, null);
  }

  public static Double doubler(String text) {
    return doubler(text, null);
  }

  public static Double doubler(String text, Double def) {
    return TextKit.blanky(text) ? def : NumberKit.doubler(text);
  }

  public static Double doubler(Number number, Double def) {
    Double ret = NumberKit.doubler(number);
    return Vae.nulldef(ret, def);
  }

  public static Double doubler(Number number) {
    return doubler(number, null);
  }

  public static Float floater(String text, Float def) {
    return TextKit.blanky(text) ? def : NumberKit.floater(text);
  }

  public static Float floater(String text) {
    return floater(text, null);
  }

  public static Float floater(Number number, Float def) {
    Float ret = NumberKit.floater(number);
    return Vae.nulldef(ret, def);
  }

  public static Float floater(Number number) {
    return floater(number, null);
  }

  public static Short shorter(String text, Short def) {
    return TextKit.blanky(text) ? def : NumberKit.shorter(text);
  }

  public static Short shorter(String text) {
    return shorter(text, null);
  }

  public static Short shorter(Number number, Short def) {
    Short ret = NumberKit.shorter(number);
    return Vae.nulldef(ret, def);
  }

  public static Short shorter(Number number) {
    return shorter(number, null);
  }

  public static BigInteger bigint(String text, BigInteger def) {
    return TextKit.blanky(text) ? def : NumberKit.bigint(text);
  }

  public static BigInteger bigint(String text) {
    return bigint(text, null);
  }

  public static BigInteger bigint(Number number, BigInteger def) {
    BigInteger ret = NumberKit.bigint(number);
    return Vae.nulldef(ret, def);
  }

  public static BigInteger bigint(Number number) {
    return bigint(number, null);
  }

  public static BigDecimal bigdecimal(String text, BigDecimal def) {
    return TextKit.blanky(text) ? def : NumberKit.bigdecimal(text);
  }

  public static BigDecimal bigdecimal(String text) {
    return bigdecimal(text, null);
  }

  public static BigDecimal bigdecimal(Number number, BigDecimal def) {
    BigDecimal ret = NumberKit.bigdecimal(number);
    return Vae.nulldef(ret, def);
  }

  public static BigDecimal bigdecimal(Number number) {
    return bigdecimal(number, null);
  }

  public static Boolean bool(String text) {
    return bool(text, null);
  }

  public static Boolean bool(String text, Boolean def) {
    if (TextKit.blanky(text))
      return def;
    text = text.trim().toLowerCase();
    if ("1".equals(text) || "true".equals(text))
      return Boolean.TRUE;
    if ("0".equals(text) || "false".equals(text))
      return Boolean.FALSE;
    throw new EoException(EnoaTipKit.message("eo.tip.toolkit.convert_cant_bool", text));
  }

  public static Boolean bool(Number number, Boolean def) {
    if (number == null)
      return def;
    return longer(number) == 1;
  }

  public static Boolean bool(Number number) {
    return bool(number, Boolean.FALSE);
  }

  public static Date date(String text) {
    return date(text, EoConst.DEF_FORMAT_DATE, null);
  }

  public static Date date(String text, String format) {
    return date(text, format, null);
  }

  public static Date date(String text, String format, Date def) {
    try {
      if (TextKit.blanky(text))
        return def;
      return new java.text.SimpleDateFormat(format).parse(text.trim());
    } catch (ParseException e) {
      throw new EoException(EnoaTipKit.message("eo.tip.toolkit.convert_cant_date", text));
    }
  }

  public static Timestamp timestamp(String text, String format, Timestamp def) {
    Date date = date(text, format);
    if (date == null)
      return def;
    return new Timestamp(date.getTime());
  }

  public static Timestamp timestamp(String text, String format) {
    return timestamp(text, format, null);
  }

  public static Timestamp timestamp(String text) {
    return timestamp(text, EoConst.DEF_FORMAT_DATE, null);
  }

  public static <R> R as(Object value) {
    return as(value, null);
  }

  public static <R> R as(Object value, R def) {
    return value == null ? def : (R) value;
  }

  public static <R, P> R to(P value, IConverter<R, P> converter) {
    return converter.convert(value);
  }

  public static <T> T to(String value, Class<T> clazz) {
    Object ret = TypeConverter.convert(value, clazz);
    return (T) ret;
  }

  public static boolean supportConvert(Class clazz) {
    return TypeConverter.support(clazz);
  }

  public static <T> void install(Class<T> type, IConverter<T, String> converter) {
    TypeConverter.install(type, converter);
  }

}
