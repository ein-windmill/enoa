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
package io.enoa.toolkit.number;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.text.TextKit;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class NumberKit {

  private NumberKit() {

  }

  /**
   * 解码 BigInteger
   *
   * @param text value
   * @return BigInteger
   */
  private static BigInteger decodeBigInteger(String text) {
    int radix = 10; //进制
    int index = 0;  //脚标
    boolean negative = false; //负标记

    // 处理减
    if (text.startsWith("-")) {
      negative = true;
      index++;
    }

    // 处理进制：16进制，脚标移动2
    if (text.startsWith("0x", index) || text.startsWith("0X", index)) {
      index += 2;
      radix = 16;
    }
    // 处理进制：16进制，脚标移动1
    if (text.startsWith("#", index)) {
      index++;
      radix = 16;
    }
    // 处理进制：8进制，脚标移动1
    if (text.startsWith("0", index) && text.length() > 1 + index) {
      index++;
      radix = 8;
    }

    //返回BigInteger
    BigInteger result = new BigInteger(text.substring(index), radix);
    return (negative ? result.negate() : result);
  }


  public static Number to(Number origin, Class to) {
    if (origin == null) {
//      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.number_to_origin_null"));
      return null;
    }
    if (to == null) {
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.number_to_target_null"));
    }

    if (to.isInstance(origin)) {
      return origin;
    }

    // change to integer
    if (Integer.class == to || int.class == to) {
      long value = origin.longValue();
      if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
        throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.number_to_cant", origin, origin.getClass().getName(), to.getName()));
      }
      return origin.intValue();
    }

    // change to long
    if (Long.class == to || long.class == to) {
      BigInteger bigInt = null;
      if (origin instanceof BigInteger) {
        bigInt = (BigInteger) origin;
      }
      if (origin instanceof BigDecimal) {
        bigInt = ((BigDecimal) origin).toBigInteger();
      }
      // Effectively analogous to JDK 8's BigInteger.longValueExact()
      if (bigInt != null && (bigInt.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) < 0 || bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0)) {
        throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.number_to_cant", origin, origin.getClass().getName(), to.getName()));
      }
      return origin.longValue();
    }

    // change to double
    if (Double.class == to || double.class == to) {
      return origin.doubleValue();
    }

    // change to float
    if (Float.class == to || float.class == to) {
      return origin.floatValue();
    }

    // change to bigdecimal
    if (BigDecimal.class == to) {
      return new BigDecimal(origin.toString());
    }

    // change to short
    if (Short.class == to || short.class == to) {
      long value = origin.longValue();
      if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
        throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.number_to_cant", origin, origin.getClass().getName(), to.getName()));
      }
      return origin.shortValue();
    }

    // change to byte
    if (Byte.class == to || byte.class == to) {
      long value = origin.longValue();
      if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
        throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.number_to_cant", origin, origin.getClass().getName(), to.getName()));
      }
      return origin.byteValue();
    }

    //  change to biginteger
    if (BigInteger.class == to) {
      if (origin instanceof BigDecimal) {
        // do not lose precision - use BigDecimal's own conversion
        return ((BigDecimal) origin).toBigInteger();
      } else {
        // original value is not a Big* number - use standard long conversion
        return BigInteger.valueOf(origin.longValue());
      }
    }

    throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.number_to_cant", origin, origin.getClass().getName(), to.getName()));
  }

  public static <T extends Number> T to(String text, Class<T> to) {
    if (text == null) {
//      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.number_str_to_origin_null"));
      return null;
    }

    if (to == null) {
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.number_to_target_null"));
    }

    String trimmed = TextKit.nospace(text, true);

    if (trimmed.equalsIgnoreCase("true"))
      return (T) to(1, to);
    if (trimmed.equalsIgnoreCase("false"))
      return (T) to(0, to);

    if (to.equals(Integer.class)) {
      trimmed = trimmedNoDot(trimmed);
      return (T) (HexKit.isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed));
    }
    if (to.equals(Long.class)) {
      trimmed = trimmedNoDot(trimmed);
      return (T) (HexKit.isHexNumber(trimmed) ? Long.decode(trimmed) : Long.valueOf(trimmed));
    }
    if (to.equals(Float.class)) {
      return (T) Float.valueOf(trimmed);
    }
    if (to.equals(Double.class)) {
      return (T) Double.valueOf(trimmed);
    }
    if (to.equals(BigDecimal.class) || to.equals(Number.class)) {
      return (T) new BigDecimal(trimmed);
    }
    if (to.equals(Short.class)) {
      trimmed = trimmedNoDot(trimmed);
      return (T) (HexKit.isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed));
    }
    if (to.equals(BigInteger.class)) {
      trimmed = trimmedNoDot(trimmed);
      return (T) (HexKit.isHexNumber(trimmed) ? decodeBigInteger(trimmed) : new BigInteger(trimmed));
    }
    if (to.equals(Byte.class)) {
      trimmed = trimmedNoDot(trimmed);
      return (T) (HexKit.isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed));
    }
    throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.number_str_to_cant", text, to.getName()));
  }

  private static String trimmedNoDot(String text) {
    if (Is.not().truthy(text))
      return "0";
    if (text.contains("."))
      text = text.substring(0, text.indexOf("."));
    return Is.not().truthy(text) ? "0" : text;
  }

  public static Integer integer(Number number) {
    return (Integer) to(number, Integer.class);
  }

  public static Double doubler(Number number) {
    return (Double) to(number, Double.class);
  }

  public static Float floater(Number number) {
    return (Float) to(number, Float.class);
  }

  public static Long longer(Number number) {
    return (Long) to(number, Long.class);
  }

  public static BigDecimal bigdecimal(Number number) {
    return (BigDecimal) to(number, BigDecimal.class);
  }

  public static BigInteger bigint(Number number) {
    return (BigInteger) to(number, BigInteger.class);
  }

  public static Short shorter(Number number) {
    return (Short) to(number, Short.class);
  }

//  public static Byte toByte(Number number) {
//    return (Byte) to(number, Byte.class);
//  }


  public static Integer integer(String text) {
    return to(text, Integer.class);
  }

  public static Double doubler(String text) {
    return to(text, Double.class);
  }

  public static Float floater(String text) {
    return to(text, Float.class);
  }

  public static Long longer(String text) {
    return to(text, Long.class);
  }

  public static BigDecimal bigdecimal(String text) {
    return to(text, BigDecimal.class);
  }

  public static BigInteger bigint(String text) {
    return to(text, BigInteger.class);
  }

  public static Short shorter(String text) {
    return to(text, Short.class);
  }


}
