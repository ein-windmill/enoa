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
package io.enoa.toolkit.text;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.is.Is;

import java.util.Arrays;

/**
 * 文字隐藏
 * 例如 1539****232
 * ab****b@email.com
 * 视不同情况选择隐藏方式, 提供  left right middle 三种方式
 * * 为默认掩码
 */
public class CiphertextKit {

  private CiphertextKit() {
  }

  private static final String PLACEHOLDER = "*";
  private static final int DEF_LEN_BER = 5;

  private enum Direction {
    LEFT,
    RIGHT,
    MIDDLE
  }

  public static String left(String text) {
    return left(text, DEF_LEN_BER, PLACEHOLDER);
  }

  public static String left(String text, int len) {
    return left(text, len, PLACEHOLDER);
  }

  public static String left(String text, int len, String placeholder) {
    if (Is.nullx(placeholder))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.str_ciphertext_placeholder_null"));
    return encrypt(Direction.LEFT, text, len, placeholder.charAt(0));
  }

  public static String right(String text) {
    return right(text, DEF_LEN_BER, PLACEHOLDER);
  }

  public static String right(String text, int len) {
    return right(text, len, PLACEHOLDER);
  }

  public static String right(String text, int len, String placeholder) {
    if (Is.nullx(placeholder))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.str_ciphertext_placeholder_null"));
    return encrypt(Direction.RIGHT, text, len, placeholder.charAt(0));
  }

  public static String middle(String text) {
    return middle(text, DEF_LEN_BER, PLACEHOLDER);
  }

  public static String middle(String text, int len) {
    return middle(text, len, PLACEHOLDER);
  }

  public static String middle(String text, int len, String placeholder) {
    if (Is.nullx(placeholder))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.str_ciphertext_placeholder_null"));
    return encrypt(Direction.MIDDLE, text, len, placeholder.charAt(0));
  }

  public static String full(String text) {
    return full(text, PLACEHOLDER);
  }

  public static String full(String text, String placeholder) {
    if (text == null)
      return null;
    StringBuilder ret = new StringBuilder();
    for (int i = text.length(); i-- > 0; ) {
      ret.append(placeholder);
    }
    return ret.toString();
  }

  private static String encrypt(Direction direction, String text, int len, char placeholder) {
    if (text == null)
      return null;
    if ("".equals(text))
      return text;
    char[] chars = text.toCharArray();
    switch (direction) {
      case LEFT: {
        for (int i = 0; i < chars.length; i++) {
          if (i + 1 > len)
            break;
          chars[i] = placeholder;
        }
        return new String(chars);
      }
      case RIGHT: {
        int times = 1;
        for (int i = chars.length; i-- > 0; ) {
          if (times > len)
            break;
          chars[i] = placeholder;
          times += 1;
        }
        return new String(chars);
      }
      case MIDDLE: {
        if (len > chars.length) {
          Arrays.fill(chars, placeholder);
          return new String(chars);
        }
        int spix = chars.length / 3;
        int remineLen = chars.length - spix;
        if (remineLen < len)
          spix = len - remineLen;
        if (spix < 0)
          spix = 0;
        int toix = len + spix;
        Arrays.fill(chars, spix, toix > chars.length ? chars.length - 1 : toix, placeholder);
        return new String(chars);
      }
      default:
        throw new IllegalArgumentException("Invalid ciphertext direction.");
    }
  }

}
