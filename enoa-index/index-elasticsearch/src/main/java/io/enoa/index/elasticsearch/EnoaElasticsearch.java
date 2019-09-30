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

import io.enoa.http.EoUrl;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.index.elasticsearch.edat.Edat;
import io.enoa.index.elasticsearch.eql.Eql;
import io.enoa.toolkit.is.Is;

public class EnoaElasticsearch {

  private ElasticsearchConfig config;

  public EnoaElasticsearch(ElasticsearchConfig config) {
    this.config = config;
  }

  private Edat call(HttpMethod method, EoUrl url, Eql eql) {
    Http http = this.config.http()
      .method(method)
      .url(url);

    if (Is.not().nullx(eql)) {
      String json = eql.eql();
      if (Is.truthy(json)) {
        http.raw(json).contentType("application/json");
      }
    }
    HttpResponse response = http.emit();
    return new Edat(this.config, response);
  }

  private EoUrl url(String uri) {
    return EoUrl.with(this.config.host()).subpath(uri);
  }

  public Edat post(String uri, Eql eql) {
    return this.call(HttpMethod.POST, this.url(uri), eql);
  }

  public Edat delete(String uri) {
    return this.call(HttpMethod.DELETE, this.url(uri), null);
  }

  public Edat delete(String uri, Eql eql) {
    return this.call(HttpMethod.DELETE, this.url(uri), eql);
  }

  public Edat search(String uri) {
    return this.call(HttpMethod.GET, this.url(uri).subpath("_search"), null);
  }

  public Edat search(String uri, Eql eql) {
    return this.call(HttpMethod.GET, this.url(uri).subpath("_search"), eql);
  }

  public Edat update(String uri) {
    return this.call(HttpMethod.POST, this.url(uri).subpath("_update"), null);
  }

  public Edat update(String uri, Eql eql) {
    return this.call(HttpMethod.POST, this.url(uri).subpath("_update"), eql);
  }

  public Edat bulk(Eql eql) {
    return this.call(HttpMethod.POST, EoUrl.with(this.config.host()).subpath("_bulk"), eql);
  }

}
