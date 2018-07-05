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
package io.enoa.tryjson.eson;

import io.enoa.tryjson.Tsonfig;
import io.enoa.tryjson.eson.converter.EsonConverter;
import io.enoa.tryjson.eson.parser.EsonParser;
import io.enoa.tryjson.json.Ja;
import io.enoa.tryjson.json.Jo;
import io.enoa.tryjson.thr.TryjsonException;

import java.util.Date;
import java.util.Map;

public class Eson {

  private Eson() {

  }

  public static String json(Object object, int depth, Tsonfig conf) {
    if (object == null)
      return null;

    if (depth-- < 0)
      return null;

    if (object instanceof String)
      return EsonConverter.string().json((String) object, depth, conf);

    if (object instanceof Character)
      return EsonConverter.string().json(String.valueOf(object), depth, conf);

    if (object instanceof Number)
      return EsonConverter.number().json((Number) object, depth, conf);

    if (object instanceof Boolean)
      return EsonConverter.bool().json((Boolean) object, depth, conf);

    if (object instanceof Date)
      return EsonConverter.date().json((Date) object, depth, conf);

    if (object instanceof Iterable)
      return EsonConverter.iterable().json((Iterable) object, depth, conf);

    if (object instanceof Map)
      return EsonConverter.map().json((Map) object, depth, conf);

    if (object instanceof Enum)
      return EsonConverter.enumx().json((Enum) object, depth, conf);

    String json = EsonConverter.object().json(object, depth, conf);
    if (json != null)
      return json;

    return EsonConverter.string().json(object.toString(), depth, conf);
  }

  public static Jo object(String json, Tsonfig conf) throws TryjsonException {
    return EsonParser.def().object(json, conf);
  }

  public static Ja array(String json, Tsonfig conf) throws TryjsonException {
    return EsonParser.def().array(json, conf);
  }

}
