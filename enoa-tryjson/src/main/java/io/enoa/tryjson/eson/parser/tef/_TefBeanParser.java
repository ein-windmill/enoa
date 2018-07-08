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

import io.enoa.tryjson.Tsonfig;
import io.enoa.tryjson.eson.parser.BsonParser;
import io.enoa.tryjson.eson.parser.EsonParser;
import io.enoa.tryjson.json.Toa;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class _TefBeanParser implements BsonParser {

  private static class Holder {
    private static final _TefBeanParser INSTANCE = new _TefBeanParser();
  }

  public static _TefBeanParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public <T> T object(String json, Type type, Tsonfig config) {
    Toa toa = EsonParser.def().parse(json, config);
    ParameterizedType d= (ParameterizedType) type;
    System.out.println(type.getTypeName());
    return null;
  }

  @Override
  public <T> List<T> array(String json, Type type, Tsonfig config) {
    return null;
  }
}
