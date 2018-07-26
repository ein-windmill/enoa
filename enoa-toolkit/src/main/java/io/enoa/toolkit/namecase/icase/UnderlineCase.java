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
package io.enoa.toolkit.namecase.icase;

import io.enoa.toolkit.namecase.INameCase;

public class UnderlineCase implements INameCase {
  @Override
  public String convert(String text) {
    if (text == null)
      return null;

    return this.camelToUnderline(text);
  }

  private String camelToUnderline(String text) {
    if ("".equals(text.trim()))
      return "";

    int len = text.length();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      char c = text.charAt(i);
      if (Character.isUpperCase(c)) {
        if (i > 0)
          sb.append('_');
        sb.append(Character.toLowerCase(c));
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

}
