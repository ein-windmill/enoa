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

public class UnicodeKit {

  private UnicodeKit() {
  }


  /**
   * 字符转 unicode 编码
   *
   * @param text text
   * @return String
   */
  public static String unicode(String text) {
    StringBuilder unicode = new StringBuilder();
    int len = text.length();
    for (int i = 0; i < len; i++) {
      char c = text.charAt(i);
      unicode.append("\\u").append(Integer.toHexString(c));
    }
    return unicode.toString();
  }

  /**
   * unicode 编码转文本字符
   *
   * @param text unicode 编码字符
   * @return String
   */
  public static String character(String text) {
    StringBuilder string = new StringBuilder();
    String[] hex = text.split("\\\\u");
    for (int i = 1; i < hex.length; i++) {
      int data = Integer.parseInt(hex[i], 16);
      string.append((char) data);
    }
    return string.toString();
  }

}
