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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import io.enoa.json.EnoaJson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * enoa - io.enoa.json.provider.gson
 */
class _Gson extends EnoaJson {

//  private Gson gson;

  private Map<String, Gson> CACHE = new HashMap<>();

  private static class Holder {
    private static final _Gson INSTANCE = new _Gson();
  }

  static _Gson instance() {
    return Holder.INSTANCE;
  }

  private Gsonfig gsonfig;

  private _Gson() {

  }


  _Gson gsonfig(Gsonfig gsonfig) {
    this.gsonfig = gsonfig;
    return this;
  }

  private Gson gson(String datePattern) {
    Gson _gson = CACHE.get(datePattern == null ? "def" : datePattern);
    if (_gson != null)
      return _gson;

    GsonBuilder builder = new GsonBuilder();
    if (this.gsonfig.fixPrecision()) {
      builder
        .registerTypeAdapterFactory(new MapTypeAdapterFactory())
        .registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
          if (src == src.longValue())
            return new JsonPrimitive(src.longValue());
          return new JsonPrimitive(src);
        });
    }

    if (this.gsonfig == null) {
      builder.disableHtmlEscaping()
        .setDateFormat(datePattern == null ? "yyyy-MM-dd HH:mm:ss.SSS" : datePattern);
      _gson = builder.create();
      CACHE.put(datePattern, _gson);
      return _gson;
    }
//    GsonBuilder builder = new GsonBuilder();
    if (this.gsonfig.disableHtmlEscaping()) {
      builder.disableHtmlEscaping();
    }
    if (this.gsonfig.dateFormat() != null) {
      builder.setDateFormat(datePattern == null ? this.gsonfig.dateFormat() : datePattern);
    } else {
      builder.setDateFormat(datePattern == null ? "yyyy-MM-dd HH:mm:ss.SSS" : datePattern);
    }
    _gson = builder.create();
    CACHE.put(datePattern, _gson);
    return _gson;
  }

  private Gson gson() {
    return this.gson(null);
  }

//  @Override
//  public Object origin() {
//    return this.gson();
//  }

  @Override
  public String toJson(Object object) {
    return this.gson().toJson(object);
  }

  @Override
  public String toJson(Object object, String datePattern) {
    return this.gson(datePattern).toJson(object);
  }

//  private <T> T jotomap(JsonObject jo) {
//
//  }

  @Override
  public <T> T parse(String json, Class<T> type) {
//    if (Map.class.isAssignableFrom(type)) {
//      JsonObject jo = this.gson().fromJson(json, JsonObject.class);
//
//    }
    return this.gson().fromJson(json, type);
  }

  @Override
  public <T> T parse(String json, Type type) {
    return this.gson().fromJson(json, type);
  }

  @Override
  public <T> List<T> parseArray(String json, Class<T> type) {
    return this.gson().fromJson(json, new _ParameterizedTypeImpl(List.class, new Class[]{type}));
  }

  /**
   * Serial type
   * <p>
   * http://www.jianshu.com/p/d62c2be60617
   */
  static class _ParameterizedTypeImpl implements ParameterizedType {
    private final Class raw;
    private final Type[] args;

    private _ParameterizedTypeImpl(Class raw, Type[] args) {
      this.raw = raw;
      this.args = args != null ? args : new Type[0];
    }

    @Override
    public Type[] getActualTypeArguments() {
      return args;
    }

    @Override
    public Type getRawType() {
      return raw;
    }

    @Override
    public Type getOwnerType() {
      return null;
    }
  }

}
