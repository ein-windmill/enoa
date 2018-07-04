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


import io.enoa.tryjson.eson.Eson;
import io.enoa.tryjson.json.Ja;
import io.enoa.tryjson.json.Jo;
import io.enoa.tryjson.thr.TryJsonException;

import java.util.List;

public interface Tryjson {


  static EPMTryjson epm() {
    return EPMTryjson.instance();
  }

  static String json(Object object) {
    return json(object, 15);
  }

  static String json(Object object, Tsonfig conf) {
    return json(object, 15, conf);
  }

  static String json(Object object, int depth) {
    return json(object, depth, epm().config());
  }

  static String json(Object object, int depth, Tsonfig conf) {
    String json = Eson.json(object, depth, conf);
    return conf.jsonFormat().format(json);
  }

  static Ja array(String json) throws TryJsonException {
    return array(json, epm().config());
  }

  static <T> List<T> array(String json, Class<T> clazz) throws TryJsonException {
    return array(json, clazz, epm().config());
  }

  static Ja array(String json, Tsonfig conf) throws TryJsonException {
    return Eson.array(json, conf);
  }

  static <T> List<T> array(String json, Class<T> clazz, Tsonfig conf) throws TryJsonException {
    return null;
  }

  static Jo object(String json) throws TryJsonException {
    return object(json, epm().config());
  }

  static <T> T object(String json, Class<T> clazz) throws TryJsonException {
    return null;
  }

  static Jo object(String json, Tsonfig conf) throws TryJsonException {
    return Eson.object(json, conf);
  }

  static <T> T object(String json, Class<T> clazz, Tsonfig conf) throws TryJsonException {
    return null;
  }

}
