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
package io.enoa.json;

import java.lang.reflect.Type;
import java.util.List;

/**
 * enoa - io.enoa.json.kit
 */
public class Json {

  private Json() {

  }

  public static EPMJson epm() {
    return EPMJson.instance();
  }

  public static String toJson(Object object) {
//    return EMgrJson.json().toJson(object);
    return epm().json().toJson(object);
  }

  public static String toJson(Object object, String datePattern) {
//    return EMgrJson.json().toJson(object);
    return epm().json().toJson(object, datePattern);
  }

  public static <T> T parse(String json, Class<T> type) {
//    return EMgrJson.json().parse(json, type);
    return epm().json().parse(json, type);
  }

  public static <T> T parse(String json, Type type) {
//    return EMgrJson.json().parse(json, type);
    return epm().json().parse(json, type);
  }

  public static <T> List<T> parseArray(String json, Class<T> type) {
//    return EMgrJson.json().parseArray(json, type);
    return epm().json().parseArray(json, type);
  }

}
