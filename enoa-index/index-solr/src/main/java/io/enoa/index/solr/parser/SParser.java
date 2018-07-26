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
import io.enoa.json.EoJsonFactory;

import java.lang.reflect.Type;

@FunctionalInterface
public interface SParser<T> {

  static OriginParser string() {
    return OriginParser.create();
  }

  static <R> JsonParser<R> json(EoJsonFactory ejf, Class<R> type) {
    return JsonParser.create(ejf, type);
  }

  static <R> JsonParser<R> json(EoJsonFactory ejf, Type type) {
    return JsonParser.create(ejf, type);
  }

  static <R> JsonParser<R> json(Class<R> type) {
    return JsonParser.create(type);
  }

  static <R> JsonParser<R> json(Type type) {
    return JsonParser.create(type);
  }

  static JsonParser<Void> json(EoJsonFactory ejf) {
    return JsonParser.create(ejf);
  }

  static JsonParser<Void> json() {
    return JsonParser.create();
  }

  T result(HttpResponse resp);

}
