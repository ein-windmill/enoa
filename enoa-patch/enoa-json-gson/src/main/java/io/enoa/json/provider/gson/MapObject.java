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
package io.enoa.json.provider.gson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.*;

class MapObject {


  static JsonObject maptojsonobject(Gson gson, Map map) {
    Set<Map.Entry> set = map.entrySet();
    JsonObject jo = new JsonObject();
    set.forEach(entry -> {
      String key = entry.getKey() == null ? null : entry.getKey().toString();
      if (key == null)
        return;
      Object value = entry.getValue();
      if (value == null) {
        jo.addProperty(key, (String) null);
        return;
      }
      if (value instanceof Map) {
        jo.add(key, maptojsonobject(gson, (Map) value));
        return;
      }
      if (value instanceof Collection) {
        jo.add(key, tojsonobjectcollection(gson, (Collection) value));
        return;
      }
      if (value instanceof Boolean) {
        jo.addProperty(key, (Boolean) value);
        return;
      }
      if (value instanceof Number) {
        jo.addProperty(key, (Number) value);
        return;
      }
      if (value instanceof String) {
        jo.addProperty(key, (String) value);
        return;
      }
      if (value instanceof Character) {
        jo.addProperty(key, (Character) value);
        return;
      }
//      throw new RuntimeException("Unknown type key => " + key + ", value => " + value);
      jo.add(key, gson.fromJson(gson.toJson(value), JsonObject.class));
    });
    return jo;
  }

  private static JsonArray tojsonobjectcollection(Gson gson, Collection collection) {
    JsonArray rets = new JsonArray(collection.size());
    collection.forEach(c -> {
      if (c == null) {
        rets.add((String) null);
        return;
      }
      if (c instanceof Map) {
        rets.add(maptojsonobject(gson, (Map) c));
        return;
      }
      if (c instanceof Collection) {
        rets.add(tojsonobjectcollection(gson, (Collection) c));
        return;
      }
      if (c instanceof Number) {
        rets.add((Number) c);
        return;
      }
      if (c instanceof Boolean) {
        rets.add((Boolean) c);
        return;
      }
      if (c instanceof Character) {
        rets.add((Character) c);
        return;
      }
      if (c instanceof String) {
        rets.add((String) c);
        return;
      }
//      throw new RuntimeException("Unknown type => " + c);
      rets.add(gson.fromJson(gson.toJson(c), JsonObject.class));
    });
    return rets;
  }

  static <T> Map jsonobjecttomap(JsonObject jo, TypeToken<T> type) {
    Map map = refmap(type);
    Set<Map.Entry<String, JsonElement>> entries = jo.entrySet();
    entries.forEach(entry -> {
      String key = entry.getKey();
      JsonElement value = entry.getValue();
      if (value.isJsonObject()) {
        map.put(key, jsonobjecttomap(value.getAsJsonObject(), type));
        return;
      }
      if (value.isJsonArray()) {
        map.put(key, tomapcollection(value.getAsJsonArray(), type));
        return;
      }
      if (value.isJsonNull()) {
        map.put(key, null);
        return;
      }
      map.put(key, value(entry.getValue().getAsString()));
    });
    return map;
  }

  private static <T> Collection<Map> tomapcollection(JsonArray array, TypeToken<T> type) {
    List rets = new ArrayList<>(array.size());
    array.forEach(a -> {
      if (a.isJsonArray()) {
        rets.add(tomapcollection(a.getAsJsonArray(), type));
        return;
      }
      if (a.isJsonObject()) {
        rets.add(jsonobjecttomap(a.getAsJsonObject(), type));
        return;
      }
      if (a.isJsonNull()) {
        rets.add(null);
        return;
      }
      String string = a.getAsString();
      rets.add(value(string));
    });
    return rets;
  }

  private static <T> Map refmap(TypeToken<T> type) {
    try {
      Class<? super T> clazz = type.getRawType();
      if (clazz == Map.class) {
        return new HashMap();
      }
      Map map = (Map) clazz.getConstructor().newInstance();
      return map;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  private static Object value(String text) {
    if ("true".equals(text))
      return Boolean.TRUE;
    if ("false".equals(text))
      return Boolean.FALSE;
    if (GNNumber.isNumber(text))
      return GNNumber.number(text);
    return text;
  }


}
