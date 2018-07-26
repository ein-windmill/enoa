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

/**
 * 下劃線轉為駝峰風格
 */
public class CamelCaseLower implements INameCase {
  @Override
  public String convert(String text) {
    return this.camelCase(text);
  }


  private String camelCase(String text) {
    if (text == null)
      return null;

    if (text.indexOf('_') == -1)
      return text;

    text = text.toLowerCase();
    char[] fromArray = text.toCharArray();
    char[] toArray = new char[fromArray.length];
    int j = 0;
    for (int i = 0; i < fromArray.length; i++) {
      if (fromArray[i] != '_') {
        toArray[j++] = fromArray[i];
        continue;
      }
      // 当前字符为下划线时，将指针后移一位，将紧随下划线后面一个字符转成大写并存放
      i++;
      if (i < fromArray.length) {
        toArray[j++] = Character.toUpperCase(fromArray[i]);
      }
    }
    return new String(toArray, 0, j);
  }

}
