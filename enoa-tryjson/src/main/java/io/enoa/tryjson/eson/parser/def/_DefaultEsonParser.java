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

import io.enoa.toolkit.text.TextKit;
import io.enoa.tryjson.Tsonfig;
import io.enoa.tryjson.eson.parser.EsonParser;
import io.enoa.tryjson.json.Ja;
import io.enoa.tryjson.json.Jo;
import io.enoa.tryjson.thr.TryJsonException;

public class _DefaultEsonParser implements EsonParser {

  private static class Holder {
    private static final _DefaultEsonParser INSTANCE = new _DefaultEsonParser();
  }

  public static _DefaultEsonParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public Jo object(String json, Tsonfig conf) throws TryJsonException {
    if (TextKit.isBlank(json))
      return null;
    return JsonParser.with(ParseType.OBJECT, json).config(conf).jo();
  }

  @Override
  public Ja array(String json, Tsonfig conf) throws TryJsonException {
    if (TextKit.isBlank(json))
      return Ja.emptyJa();
    return JsonParser.with(ParseType.ARRAY, json).config(conf).ja();
  }

}
