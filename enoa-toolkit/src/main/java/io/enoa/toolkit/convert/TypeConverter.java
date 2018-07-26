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


import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.thr.EoException;

import java.util.HashMap;
import java.util.Map;

/**
 * TypeConverter 用于将客户端请求的 String 类型数据转换成指定的数据类型
 * <p>
 * 可以通过实现 IConverter 定制自己的类型转换规则:
 * 1：实现 IConverter 接口，如 MyDateConverter
 * 2：注册： ConvertKit.install(Date.class, new MyDateConverter());
 */
class TypeConverter {

  private static Map<Class<?>, IConverter<?, String>> converterMap;
  private static final TypeConverter me = new TypeConverter();

  private TypeConverter() {
    install(Integer.class, new _IntegerConverter(false));
    install(int.class, new _IntegerConverter(true));
    install(Long.class, new _LongConverter(false));
    install(long.class, new _LongConverter(true));
    install(Double.class, new _DoubleConverter(false));
    install(double.class, new _DoubleConverter(true));
    install(Float.class, new _FloatConverter(false));
    install(float.class, new _FloatConverter(true));
    install(Boolean.class, new _BooleanConverter(false));
    install(boolean.class, new _BooleanConverter(true));
    install(Short.class, new _ShortCoverter(false));
    install(short.class, new _ShortCoverter(true));
    install(Byte.class, new _ByteConverter(false));
    install(byte.class, new _ByteConverter(true));
    install(java.util.Date.class, new _DateConverter());
    install(java.sql.Date.class, new _SqlDateConverter());
    install(java.sql.Time.class, new _TimeConverter());
    install(java.sql.Timestamp.class, new _TimestampConverter());
    install(java.math.BigDecimal.class, new _BigDecimalConverter());
    install(java.math.BigInteger.class, new _BigIntegerConverter());
    install(byte[].class, new _ByteArrayConverter());
    install(Object.class, new _ObjectConverter());
  }

  static <T> TypeConverter install(Class<T> type, IConverter<T, String> converter) {
    if (converterMap == null)
      converterMap = new HashMap<>();
    converterMap.put(type, converter);
    return me;
  }

  /**
   * 将 String 数据转换为指定的类型
   *
   * @param type 需要转换成为的数据类型
   * @param text 被转换的 String 类型数据
   * @return 转换成功的数据
   */
  static Object convert(String text, Class<?> type) {
    if (type == String.class)
      return "".equals(text) ? null : text;

    if (text != null) {
      text = text.trim();
      if ("".equals(text))
        return null;
    }

    IConverter<?, String> converter = converterMap.get(type);
    if (converter != null)
      return converter.convert(text);

    throw new EoException(EnoaTipKit.message("eo.tip.toolkit.type_convert_unsupport", type.getName()));
  }

}







