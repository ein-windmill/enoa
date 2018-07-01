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
package io.enoa.tryjson;


import io.enoa.tryjson.converter.ConvConf;
import io.enoa.tryjson.json.Ja;
import io.enoa.tryjson.json.Jo;

import java.util.List;

public interface Tryjson {


  static EPMTryjson epm() {
    return EPMTryjson.instance();
  }

  static String json(Object object) {
    return Eson.json(object, 15);
  }

  static String json(Object object, ConvConf conf) {
    return Eson.json(object, 15, conf);
  }

  static String json(Object object, int depth) {
    return Eson.json(object, depth);
  }

  static String json(Object object, int depth, ConvConf conf) {
    return Eson.json(object, depth, conf);
  }

  static Ja array(String json) {
    return null;
  }

  static <T> List<T> array(String json, Class<T> clazz) {
    return null;
  }

  static Jo object(String json) {
    return null;
  }

  static <T> T object(String json, Class<T> clazz) {
    return null;
  }

}
