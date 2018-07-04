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
package io.enoa.tryjson.eson.parser.def;

import io.enoa.tryjson.Tsonfig;
import io.enoa.tryjson.json.Jo;

class JsonObjectParser extends AbstractJsonParser<Jo> {

  private Jo jo;
  private StringBuilder json;
  private int len;
  private Tsonfig config;

  JsonObjectParser(StringBuilder json, Tsonfig config) {
    this.json = json;
    this.len = json.length();
    this.config = config;
  }

  private int ix = 0;

  private Symbol symbol = Symbol.NONE;

  @Override
  public Jo parse() {
    for (int i = 0; i < this.len; i++) {
      this.ix += 1;
      char ch0 = this.json.charAt(i);

      // 無效字符跳過
      if (super.skip(ch0))
        continue;
      if (ch0 == '{') {
        // todo json ast object open
        this.symbol = Symbol.JO_OPEN;
        this.jo = Jo.create();
        this.entryParse();
        continue;
      }
    }

    return this.jo;
  }

  private void entryParse() {
    for (int i = this.ix; i < this.len; i++) {
      this.ix += 1;
      char ch0 = this.json.charAt(i);
      if (super.skip(ch0))
        continue;


    }
  }

}
