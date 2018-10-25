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
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Map;

class MapTypeAdapter<T> extends TypeAdapter<Map> {

  private TypeAdapter<JsonObject> jsonobject;
  private TypeToken<T> type;

  MapTypeAdapter(Gson gson, TypeToken<T> type) {
    this.jsonobject = gson.getAdapter(JsonObject.class);
    this.type = type;
  }

  @Override
  public void write(JsonWriter out, Map value) throws IOException {
    JsonObject jo = MapObject.maptojsonobject(value);
    this.jsonobject.write(out, jo);
  }

  @Override
  public Map read(JsonReader in) throws IOException {
    JsonObject jo = this.jsonobject.read(in);
    return MapObject.jsonobjecttomap(jo, this.type);
  }
}
