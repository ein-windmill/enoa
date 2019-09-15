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

import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.number.NumberKit;

/**
 * TextKit.
 */
public class TextKit {

  /**
   * 首字母变小写
   */
  public static String lowerFirst(String str) {
    char firstChar = str.charAt(0);
    if (firstChar >= 'A' && firstChar <= 'Z') {
      char[] arr = str.toCharArray();
      arr[0] += ('a' - 'A');
      return new String(arr);
    }
    return str;
  }

  /**
   * 首字母变大写
   */
  public static String upperFirst(String str) {
    char firstChar = str.charAt(0);
    if (firstChar >= 'a' && firstChar <= 'z') {
      char[] arr = str.toCharArray();
      arr[0] -= ('a' - 'A');
      return new String(arr);
    }
    return str;
  }


//  public static String join(String[] strings) {
//    return join(strings, "");
//  }
//
//  public static String join(String[] strings, String separator) {
//    return String.join(separator, strings);
//  }

  public static String lower(String str) {
    return str == null ? null : str.toLowerCase();
  }

  public static String upper(String str) {
    return str == null ? null : str.toUpperCase();
  }

//  public static boolean slowEquals(String a, String b) {
//    byte[] aBytes = (a != null ? a.getBytes() : null);
//    byte[] bBytes = (b != null ? b.getBytes() : null);
//    return DigestKit.slowEquals(aBytes, bBytes);
//  }

  /**
   * 刪除空白字符
   * 如果選擇激進的方案, 會一併刪除 換行 tab 等字符
   *
   * @param text    text
   * @param extreme 激進方案選擇
   * @return String
   */
  public static String nospace(String text, boolean extreme) {
    if (Is.not().truthy(text))
      return text;
    if (!extreme)
      return text.trim();

    StringBuilder sb = new StringBuilder(text);
    int ix = 0;
    while (sb.length() > ix) {
      switch (sb.charAt(ix)) {
        case ' ':
        case '\t':
        case '\n':
        case '\r':
          sb.deleteCharAt(ix);
          break;
        default:
      }
      ix++;
    }
    return sb.toString();
  }

  public static String nospace(String text) {
    return nospace(text, false);
  }

  public static String union(int capacity, String text, Object... union) {
    if (union == null)
      return text;
    StringBuilder ret = new StringBuilder(capacity);
    ret.append(text);
    for (Object u : union) {
      ret.append(u);
    }
    return ret.toString();
  }

  public static String union(String text, Object... union) {
    return union(text.length() + 16, text, union);
  }

  public static String ellipsis(String text) {
    return ellipsis(text, 1000);
  }

  public static String ellipsis(String text, int len) {
    if (text == null)
      return null;
    if ("".equals(text))
      return "";
    return union(text.substring(0, text.length() > len ? len : text.length()), "...");
  }

  /**
   * 字符反轉
   *
   * @param text 字符
   * @return String
   */
  public static String reverse(String text) {
    if (text == null)
      return null;
    StringBuilder ret = new StringBuilder();
    for (int i = text.length() - 1; i >= 0; i--) {
      ret.append(text.charAt(i));
    }
    return ret.toString();
  }

  /**
   * 字符串格式化, 格式化方式采用与 MessageFormat 格式相同, 兼容 MessageFormat
   * MessageFormat 使用中如果 formats 中传递有 {} 格式文本, 会抛出异常, 这里的替换方案中不会有此现象
   * <p>
   * Example:
   * This is text from {0} and {1}.
   * arg0 arg1
   *
   * @param message 消息
   * @param formats 格式化
   * @return String
   */
  public static String format(String message, Object... formats) {
    if (message == null)
      return null;
    if (Is.empty(formats))
      return message;
    StringBuilder msg = new StringBuilder();
    StringBuilder ixb = new StringBuilder();
    boolean fillMode = false;
    for (char c : message.toCharArray()) {
      if (c == '}') {
        if (ixb.length() == 0) {
          msg.append(c);
          continue;
        }
        int _ix = NumberKit.integer(ixb.toString());
        if (_ix + 1 > formats.length) {
          msg.append("{").append(_ix).append("}");
        } else {
          msg.append(formats[_ix]);
        }
        ixb.delete(0, ixb.length());
        fillMode = false;
        continue;
      }

      if (!fillMode) {
        if (c == '{') {
          fillMode = true;
          continue;
        } else {
          msg.append(c);
          continue;
        }
      }

      if (Is.not().digit(String.valueOf(c))) {
        msg.append('{').append(c);
        ixb.delete(0, ixb.length());
        fillMode = false;
        continue;
//        throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.toolkit.text_format_cant_parse_arg", message));
      }
      ixb.append(c);
    }
    ixb.delete(0, ixb.length());
    return msg.toString();
  }

  public static String removeRightChar(String text, char c) {
    return removeChar(text, c, 0);
  }

  public static String removeLeftChar(String text, char c) {
    return removeChar(text, c, 1);
  }

  private static String removeChar(String text, char c, int direct) {
    StringBuilder _text = new StringBuilder(text);
    int len = _text.length();
    boolean has = Boolean.FALSE;
    int leftOffset = 0;
    for (int i = len; i-- > 0; ) {
      int ix = direct == 1 ? leftOffset : i;
      char _c = _text.charAt(ix);
      if (has) {
        if (_c == c) {
          _text.deleteCharAt(ix);
          continue;
        }
        break;
      }
      if ((c != ' ' && c != '\t' && c != '\n' && c != '\r') && (_c == ' ' || _c == '\t' || _c == '\n' || _c == '\r')) {
        if (direct == 1)
          leftOffset += 1;
        continue;
      }
      if (_c != c)
        break;
      _text.deleteCharAt(ix);
      has = Boolean.TRUE;
    }
    return _text.toString();
  }

  public static String safeBlank(String text) {
    return safeBlank(text, null);
  }

  public static String safeBlank(String text, String def) {
    return Is.not().truthy(text) ? def : text;
  }

}
