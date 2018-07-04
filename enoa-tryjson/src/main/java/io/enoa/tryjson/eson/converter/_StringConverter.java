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
package io.enoa.tryjson.eson.converter;

import io.enoa.toolkit.text.TextKit;
import io.enoa.tryjson.Tsonfig;

class _StringConverter implements EsonConverter<String> {

  private static class Holder {
    private static final _StringConverter INSTANCE = new _StringConverter();
  }

  static _StringConverter instance() {
    return Holder.INSTANCE;
  }

  @Override
  public String json(String text, int depth, Tsonfig conf) {
    return text == null ? null :
      TextKit.union("\"", this.escape(text), "\"");
  }

  private String escape(String text) {
    int len = text.length();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < len; i++) {
      char ch = text.charAt(i);
      switch (ch) {
        case '"':
          sb.append("\\\"");
          break;
        case '\\':
          sb.append("\\\\");
          break;
        case '\b':
          sb.append("\\b");
          break;
        case '\f':
          sb.append("\\f");
          break;
        case '\r':
          sb.append("\\r");
          break;
        case '\n':
          sb.append("\\n");
          break;
        case '\t':
          sb.append("\\t");
          break;
        //case '/':
        //	sb.append("\\/");
        //	break;
        default:
          if (!((ch >= '\u0000' && ch <= '\u001F') || (ch >= '\u007F' && ch <= '\u009F') || (ch >= '\u2000' && ch <= '\u20FF'))) {
            sb.append(ch);
            continue;
          }
          String str = Integer.toHexString(ch);
          sb.append("\\u");
          for (int k = 0; k < 4 - str.length(); k++) {
            sb.append('0');
          }
          sb.append(str.toUpperCase());
      }
    }
    return sb.toString();
  }
}
