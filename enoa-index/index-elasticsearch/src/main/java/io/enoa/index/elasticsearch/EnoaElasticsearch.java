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
import io.enoa.http.protocol.HttpMethod;
import io.enoa.index.elasticsearch.eql.Eql;

public class EnoaElasticsearch {

  private ElasticsearchConfig config;

  public EnoaElasticsearch(ElasticsearchConfig config) {
    this.config = config;
  }

  private EoUrl url(String uri) {
    return EoUrl.with(this.config.host()).subpath(uri);
  }

  public EsEmiter post(String uri, Eql eql) {
    return new EsEmiter(this.config)
      .method(HttpMethod.POST)
      .url(this.url(uri))
      .eql(eql);
  }

  public EsEmiter delete(String uri) {
    return this.delete(uri, null);
  }

  public EsEmiter delete(String uri, Eql eql) {
    return new EsEmiter(this.config)
      .method(HttpMethod.DELETE)
      .url(this.url(uri))
      .eql(eql);
  }

  public EsEmiter search(String uri) {
    return this.search(uri, null);
  }

  public EsEmiter search(String uri, Eql eql) {
    return new EsEmiter(this.config)
      .method(HttpMethod.GET)
      .url(this.url(uri).subpath("_search"))
      .eql(eql);
  }

  public EsEmiter update(String uri) {
    return this.update(uri, null);
  }

  public EsEmiter update(String uri, Eql eql) {
    return new EsEmiter(this.config)
      .method(HttpMethod.POST)
      .url(this.url(uri).subpath("_update"))
      .eql(eql);
  }

  public EsEmiter bulk(Eql eql) {
    return new EsEmiter(this.config)
      .method(HttpMethod.POST)
      .url(EoUrl.with(this.config.host()).subpath("_bulk"))
      .eql(eql);
  }

}
