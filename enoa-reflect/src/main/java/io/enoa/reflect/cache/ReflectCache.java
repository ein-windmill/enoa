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
package io.enoa.reflect.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectCache {

  private static Map<String, Object> CACHES = new ConcurrentHashMap<>();

  public static void cache(String name, Object instance) {
    if (name == null)
      throw new IllegalArgumentException("Reflect cache name can not be null.");
    if (instance == null)
      throw new IllegalArgumentException("Reflect cache instance can not be null.");
    CACHES.put(name, instance);
  }

  public static <T> T instance(String name) {
    return (T) CACHES.get(name);
  }

}
