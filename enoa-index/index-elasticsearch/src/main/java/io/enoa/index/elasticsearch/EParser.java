/*
 * Copyright 2016 ikidou
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
package io.enoa.index.elasticsearch;

import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.protocol.HttpResponseBody;
import io.enoa.index.elasticsearch.types.bulk.EBulk;
import io.enoa.index.elasticsearch.types.search.EHits;
import io.enoa.index.elasticsearch.types.search.ESearch;
import io.enoa.index.elasticsearch.types.search.ESearchHits;
import io.enoa.index.elasticsearch.types.search.ETotal;
import io.enoa.json.EnoaJson;
import io.enoa.toolkit.convert.ConvertKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.page.Page;
import io.enoa.typebuilder.TypeBuilder;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface EParser<R> {


  R parse(EnoaJson json, HttpResponse response, Integer size, Long from);

  default R parse(EnoaJson json, HttpResponse response) {
    return this.parse(json, response, -1, 0L);
  }

  static EParser<HttpResponse> response() {
    return (json, response, size, from) -> response;
  }

  static EParser<String> json() {
    return (json, response, size, from) -> {
      if (Is.nullx(response))
        return null;
      HttpResponseBody body = response.body();
      if (Is.nullx(body))
        return null;
      return body.string();
    };
  }

  static <T> EParser<T> to(Class<T> clazz) {
    return to((Type) clazz);
  }

  static <T> EParser<T> to(Type type) {
    return (json, response, size, from) -> {
      String _json = EParser.json().parse(json, response);
      if (Is.not().truthy(_json))
        return null;
      return json.parse(_json, type);
    };
  }

  static <T> EParser<ESearch<T>> search(Class<T> clazz) {
    return search((Type) clazz);
  }

  static <T> EParser<ESearch<T>> search(Type type) {
    return (json, response, size, from) -> {
      Type tp = TypeBuilder.with(ESearch.class)
        .type(type)
        .build();
      EParser<ESearch<T>> to = EParser.to(tp);
      return to.parse(json, response);
    };
  }

  static <T> EParser<EBulk<T>> bulk(Class<T> clazz) {
    return bulk((Type) clazz);
  }

  static <T> EParser<EBulk<T>> bulk(Type type) {
    return (json, response, size, from) -> {
      Type tp = TypeBuilder.with(EBulk.class)
        .type(type)
        .build();
      EParser<EBulk<T>> to = EParser.to(tp);
      return to.parse(json, response);
    };
  }

  static <T> EParser<List<T>> beans(Class<T> clazz) {
    return beans((Type) clazz);
  }

  static <T> EParser<List<T>> beans(Type type) {
    return (json, response, size, from) -> {
      EParser<ESearch<T>> search = search(type);
      ESearch<T> _sch = search.parse(json, response);
      if (Is.nullx(_sch.getHits()))
        return Collections.emptyList();
      if (Is.empty(_sch.getHits().getHits()))
        return Collections.emptyList();
      return _sch.getHits().getHits()
        .stream()
        .map(EHits::get_source)
        .collect(Collectors.toList());
    };
  }

  static <T> EParser<T> bean(Class<T> clazz) {
    return bean((Type) clazz);
  }

  static <T> EParser<T> bean(Type type) {
    return (json, response, size, from) -> {
      EParser<List<T>> lparser = beans(type);
      List<T> rets = lparser.parse(json, response);
      return Is.empty(rets) ? null : rets.get(0);
    };
  }

  static EParser<List<Kv>> kvs() {
    return beans(Kv.class);
  }

  static EParser<Kv> kv() {
    return bean(Kv.class);
  }

  static <T> EParser<Page<T>> page(Class<T> clazz) {
    return page((Type) clazz);
  }

  static <T> EParser<Page<T>> page(Type type) {
    return (json, response, size, from) -> {
      Integer _size = ConvertKit.integer(size, 10);
      Long _from = ConvertKit.longer(from, 0L);
      EParser<ESearch<T>> search = search(type);
      ESearch<T> _sch = search.parse(json, response);
      if (Is.nullx(_sch.getHits()))
        return Page.empty();
      ESearchHits<T> schits = _sch.getHits();
      if (Is.nullx(schits))
        return Page.empty();

      ETotal _total = schits.getTotal();
      if (Is.nullx(_total))
        return Page.empty();
      Long total = _total.getValue();
      int pn = ConvertKit.integer(Math.floor(ConvertKit.doubler(_from) / ConvertKit.doubler(_size))) + 1;
      int tpg = ConvertKit.integer(Math.ceil(ConvertKit.doubler(total) / ConvertKit.doubler(size)));

      if (Is.empty(schits.getHits()))
        return new Page<>(pn, _size, tpg, _from, total, Collections.emptyList());

      List<T> rows = schits.getHits()
        .stream()
        .map(EHits::get_source)
        .collect(Collectors.toList());
      return new Page<>(pn, _size, tpg, _from, total, rows);
    };
  }


}
