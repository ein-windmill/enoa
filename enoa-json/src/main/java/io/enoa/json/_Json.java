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
package io.enoa.json;

import java.lang.reflect.Type;
import java.util.List;

/**
 * enoa - io.enoa.json
 */
interface _Json {

//  /**
//   * Origin json class
//   * like
//   * Gson -> Gson
//   * jackson -> ObjectMapper
//   * Note: Not all json providers will have corresponding original class, depending on the specific provider
//   *
//   * @return Object
//   */
//  Object origin();

  String toJson(Object object);

  String toJson(Object object, String datePattern);

  <T> T parse(String json, Class<T> type);

  <T> T parse(String json, Type type);

  <T> List<T> parseArray(String json, Class<T> type);

}
