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
package io.enoa.tryjson.eson.parser;

import io.enoa.tryjson.Tsonfig;
import io.enoa.tryjson.eson.parser.tef._TefBeanParser;

import java.lang.reflect.Type;
import java.util.List;

public interface BsonParser {

  static BsonParser def() {
    return _TefBeanParser.instance();
  }

  <T> T object(String json, Type type, Tsonfig config);

  <T> List<T> array(String json, Type type, Tsonfig config);

}
