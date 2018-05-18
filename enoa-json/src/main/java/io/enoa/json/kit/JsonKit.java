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
package io.enoa.json.kit;

import io.enoa.json.EMgrJson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * enoa - io.enoa.json.kit
 */
public class JsonKit {

  public static String toJson(Object object) {
    return EMgrJson.instance().json().toJson(object);
  }

  public static <T> T parse(String json, Class<T> type) {
    return EMgrJson.instance().json().parse(json, type);
  }

  public static <T> T parse(String json, Type type) {
    return EMgrJson.instance().json().parse(json, type);
  }

  public static <T> List<T> parseArray(String json, Class<T> type) {
    return EMgrJson.instance().json().parseArray(json, type);
  }
}
