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
package io.enoa.toolkit.convert;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.number.NumberKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.thr.EoException;

import java.text.ParseException;
import java.util.Date;

public class ConvertKit {
  private ConvertKit() {
  }

  public static String string(Object obj) {
    return string(obj, null);
  }

  public static String string(Object obj, String def) {
    if (obj == null)
      return def;
    return obj.toString();
  }

  public static String ruleString(String text) {
    return ruleString(text, null);
  }

  public static String ruleString(String text, String def) {
    return TextKit.isBlank(text) ? def : text;
  }

  public static Integer integer(String text) {
    return integer(text, null);
  }

  public static Integer integer(String text, Integer def) {
    return TextKit.isBlank(text) ? def : NumberKit.integer(text);
  }

  public static Long longx(String text) {
    return longx(text, null);
  }

  public static Long longx(String text, Long def) {
    return TextKit.isBlank(text) ? def : NumberKit.longx(text);
  }

  public static Double doublex(String text) {
    return doublex(text, null);
  }

  public static Double doublex(String text, Double def) {
    return TextKit.isBlank(text) ? def : NumberKit.doublex(text);
  }

  public static Float floatx(String text, Float def) {
    return TextKit.isBlank(text) ? def : NumberKit.floatx(text);
  }

  public static Float floatx(String text) {
    return floatx(text, null);
  }

  public static Short shortx(String text, Short def) {
    return TextKit.isBlank(text) ? def : NumberKit.shortx(text);
  }

  public static Short shortx(String text) {
    return shortx(text, null);
  }

  public static Boolean bool(String text) {
    return bool(text, null);
  }

  public static Boolean bool(String text, Boolean def) {
    if (TextKit.isBlank(text))
      return def;
    text = text.trim().toLowerCase();
    if ("1".equals(text) || "true".equals(text))
      return Boolean.TRUE;
    if ("0".equals(text) || "false".equals(text))
      return Boolean.FALSE;
    throw new EoException(EnoaTipKit.message("eo.tip.toolkit.convert_cant_bool", text));
  }

  public static Date date(String text) {
    return date(text, "yyyy-MM-dd", null);
  }

  public static Date date(String text, String format) {
    return date(text, format, null);
  }

  public static Date date(String text, String format, Date def) {
    try {
      if (TextKit.isBlank(text))
        return def;
      return new java.text.SimpleDateFormat(format).parse(text.trim());
    } catch (ParseException e) {
      throw new EoException(EnoaTipKit.message("eo.tip.toolkit.convert_cant_date", text));
    }
  }

  public static <R, P> R to(P value, IConverter<R, P> converter) {
    return converter.convert(value);
  }

  public static <T> T to(String value, Class<T> clazz) {
    Object ret = TypeConverter.convert(value, clazz);
    return (T) ret;
  }

  public static <T> void install(Class<T> type, IConverter<T, String> converter) {
    TypeConverter.install(type, converter);
  }

}
