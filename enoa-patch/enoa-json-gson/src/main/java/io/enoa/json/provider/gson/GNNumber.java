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
package io.enoa.json.provider.gson;

class GNNumber {


  private static boolean isHex(char ch) {
    return ((ch >= '0' && ch <= '9') || ('a' <= ch && ch <= 'f')
      || ('A' <= ch && ch <= 'F'));
  }

  /**
   * 字符串是否數字, 支持 10 進制 16 進制 以及 科學計數法
   *
   * @param text text
   * @return boolean
   */
  static boolean isNumber(String text) {
//    if (text == null || "".equals(text))
//      return false;
//    int len = text.length();
//    char ch = text.charAt(0);
//
//    int position = 0;
//    boolean negative = ch == '-';
//    if (negative)
//      position += 1;
//
//    ch = text.charAt(position);
//    boolean zero0 = ch == '0';
//    boolean dot0 = ch == '.';
//    if (position + 1 == len) {
//      if (dot0)
//        return false;
//    }
//    if (zero0 || dot0)
//      position += 1;
//
//    boolean hexmode = false;
//    while (true) {
//      if (position == len)
//        return true;
//      ch = text.charAt(position);
//      if (hexmode) {
//        if (!isHex(ch))
//          return false;
//        position += 1;
//        continue;
//      }
//
//      if (Character.isDigit(ch)) {
//        position += 1;
//        continue;
//      }
//
//      // 1.
//      if (ch == '.') {
//        // 0..
//        if (dot0)
//          return false;
//        position += 1;
//        continue;
//      }
//      // 0x0af
//      if (ch == 'x') {
//        if (!zero0)
//          return false;
//        hexmode = true;
//        position += 1;
//        continue;
//      }
//
//      // 0.e1 0.e+1 0.e-1
//      if (ch == 'e' || ch == 'E') {
//        if (position + 1 == len)
//          return false;
//        char next = text.charAt(position + 1);
//        if (next == '-' || next == '+' || Character.isDigit(next)) {
//          position += 2;
//          continue;
//        }
//        return false;
//      }
//      return false;
//    }

    if (text.contains("-")) {
      if (text.lastIndexOf("-") != 0)
        return false;
    }
    if (text.contains("+")) {
      if (text.lastIndexOf("+") != 0)
        return false;
    }
    if (text.contains(".")) {
      if (text.indexOf(".") != text.lastIndexOf("."))
        return false;
    }
    try {
      Double.parseDouble(text);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  static Number number(String text) {
    if (text == null || "".equals(text))
      throw new RuntimeException("Invalid number. => " + text);
    if (text.contains("."))
      return Double.parseDouble(text);
    try {
      return Integer.parseInt(text);
    } catch (Exception e) {
      return Long.parseLong(text);
    }
  }

}
