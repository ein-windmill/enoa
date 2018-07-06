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

public class JsonReader {

  private String json;
  private int len;
  private int position;

  public JsonReader(String json) {
    this.json = json;
    this.len = json.length();
  }

  public char peek() {
    if (this.position - 1 >= this.len)
      return (char) -1;
    return this.json.charAt(this.position);
  }

  public char next() {
    if (!this.hasNext())
      return (char) -1;
    char ch = this.json.charAt(this.position);
    this.position += 1;
    return ch;
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
