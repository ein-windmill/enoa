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
package io.enoa.index.solr.parser;

import io.enoa.http.protocol.HttpResponse;
import io.enoa.index.solr.ret.SError;
import io.enoa.index.solr.ret.SRet;
import io.enoa.json.EnoaJson;
import io.enoa.json.EoJsonFactory;
import io.enoa.json.Json;

import java.lang.reflect.Type;

class JsonParser<T> implements SParser<SRet<T>> {

  private EnoaJson json;
  private Type type;

  private JsonParser(EnoaJson json, Type type) {
    this.type = type;
    this.json = json;
  }

  private JsonParser(Type type) {
    this.type = type;
  }

  public static <R> JsonParser<R> create(EoJsonFactory ejf, Class<R> type) {
    return new JsonParser<>(ejf.json(), type);
  }

  public static <R> JsonParser<R> create(EoJsonFactory ejf, Type type) {
    return new JsonParser<>(ejf.json(), type);
  }

  public static <R> JsonParser<R> create(Class<R> type) {
    return new JsonParser<>(type);
  }

  public static <R> JsonParser<R> create(Type type) {
    return new JsonParser<>(type);
  }

  public static JsonParser<Void> none(EoJsonFactory ejf) {
    return new JsonParser<>(ejf.json(), Void.TYPE);
  }

  public static JsonParser<Void> none() {
    return new JsonParser<>(Void.TYPE);
  }

  @Override
  public SRet<T> result(HttpResponse resp) {
    String ctype = resp.header("content-type");
    if (ctype == null)
      return null;

    ctype = ctype.toLowerCase();
    if (ctype.contains("/json") || ctype.contains("text/plain")) {
      String body = resp.body().string();
      if (body == null)
        return null;
      EnoaJson json = this.json == null ? Json.epm().json() : this.json;
      return json.parse(body, new ParameterizedTypeImpl(SRet.class, new Type[]{this.type}));
    }
    if (ctype.contains("text/html")) {
      SRet<T> ret = new SRet<>();
      SError error = new SError();
      error.setMsg(resp.body().string());
      ret.setError(error);
      return ret;
    }
    return null;
  }
}
