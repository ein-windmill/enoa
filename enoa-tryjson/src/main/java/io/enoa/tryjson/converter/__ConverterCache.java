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
package io.enoa.tryjson.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class __ConverterCache {

  private static Map<String, DateFormat> CACHE_DATEFORMAT = new ConcurrentHashMap<>();

  static void dateFormat(String format0, DateFormat format1) {
    CACHE_DATEFORMAT.put(format0, format1);
  }

  static DateFormat dateFormat(String format0) {
    DateFormat format1 = CACHE_DATEFORMAT.get(format0);
    if (format1 != null)
      return format1;
    format1 = new SimpleDateFormat(format0);
    dateFormat(format0, format1);
    return format1;
  }

}
