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

import io.enoa.index.elasticsearch.edat.Edat;
import io.enoa.index.elasticsearch.eql.fireql.EoFireql;
import io.enoa.toolkit.map.Kv;

import java.util.Map;

public class TemplateElasticsearch {

  private EnoaElasticsearch elasticsearch;
  private EoFireql fireql;

  public TemplateElasticsearch(EnoaElasticsearch elasticsearch, EoFireql fireql) {
    this.elasticsearch = elasticsearch;
    this.fireql = fireql;
  }

  public EsEmiter post(String uri, String tplname) {
    return this.elasticsearch.post(uri, this.fireql.eql(tplname));
  }

  public EsEmiter post(String uri, String tplname, Kv kv) {
    return this.elasticsearch.post(uri, this.fireql.eql(tplname, kv));
  }

  public EsEmiter post(String uri, String tplname, Map<String, ?> para) {
    return this.elasticsearch.post(uri, this.fireql.eql(tplname, para));
  }

  public EsEmiter delete(String uri) {
    return this.elasticsearch.delete(uri);
  }

  public EsEmiter delete(String uri, String tplname) {
    return this.elasticsearch.delete(uri, this.fireql.eql(tplname));
  }

  public EsEmiter delete(String uri, String tplname, Kv kv) {
    return this.elasticsearch.delete(uri, this.fireql.eql(tplname, kv));
  }

  public EsEmiter delete(String uri, String tplname, Map<String, ?> para) {
    return this.elasticsearch.delete(uri, this.fireql.eql(tplname, para));
  }

  public EsEmiter search(String uri) {
    return this.elasticsearch.search(uri);
  }

  public EsEmiter search(String uri, String tplname) {
    return this.elasticsearch.search(uri, this.fireql.eql(tplname));
  }

  public EsEmiter search(String uri, String tplname, Kv kv) {
    return this.elasticsearch.search(uri, this.fireql.eql(tplname, kv));
  }

  public EsEmiter search(String uri, String tplname, Map<String, ?> para) {
    return this.elasticsearch.search(uri, this.fireql.eql(tplname, para));
  }

  public EsEmiter update(String uri) {
    return this.elasticsearch.update(uri);
  }

  public EsEmiter update(String uri, String tplname) {
    return this.elasticsearch.update(uri, this.fireql.eql(tplname));
  }

  public EsEmiter update(String uri, String tplname, Kv kv) {
    return this.elasticsearch.update(uri, this.fireql.eql(tplname, kv));
  }

  public EsEmiter update(String uri, String tplname, Map<String, ?> para) {
    return this.elasticsearch.update(uri, this.fireql.eql(tplname, para));
  }

  public EsEmiter bulk(String tplname) {
    return this.elasticsearch.bulk(this.fireql.eql(tplname));
  }

  public EsEmiter bulk(String tplname, Kv kv) {
    return this.elasticsearch.bulk(this.fireql.eql(tplname, kv));
  }

  public EsEmiter bulk(String tplname, Map<String, ?> para) {
    return this.elasticsearch.bulk(this.fireql.eql(tplname, para));
  }


}
