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
package io.enoa.tryjson.eson.parser.tef;

class JsonReader {

  private String json;
  private int len;
  private int position; // 總字符串遊標位置
  private int line; // 當前行
  private int cursor; // 行遊標位置

  JsonReader(String json) {
    this.json = json;
    this.len = json.length();
    this.line = 1;
  }

  public char peek() {
    if (this.position - 1 >= this.len)
      return (char) -1;
    return this.json.charAt(this.position - 1);
  }

  public char next() {
    if (!this.hasNext())
      return (char) -1;
    char ch = this.json.charAt(this.position);
    this.position += 1;
    this.cursor += 1;
    if (ch == '\n') {
      this.line += 1;
      this.cursor = 0;
    }
    return ch;
  }

  public int position() {
    return this.position;
  }

  public int line() {
    return this.line;
  }

  public int cursor() {
    return this.cursor;
  }

  public JsonReader back() {
    if (this.position == 0)
      return this;
    this.position -= 1;
    return this;
  }

  public boolean hasNext() {
    return this.position < this.len;
  }

}
