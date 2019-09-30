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
import io.enoa.index.elasticsearch.eql.Eql;
import io.enoa.index.elasticsearch.eql.fireql.EoFireql;
import io.enoa.index.elasticsearch.eql.fireql.Fireql;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.text.TextKit;

import java.util.HashMap;
import java.util.Map;

public class Elasticsearch {

  private static Map<String, TemplateElasticsearch> tplcache;

  public static EPMElasticsearch epm() {
    return EPMElasticsearch.instance();
  }

  public static EnoaElasticsearch use(String name) {
    return epm().use(name);
  }

  public static EnoaElasticsearch use() {
    return epm().use();
  }

  public static TemplateElasticsearch template() {
    return template("main", "main");
  }

  public static TemplateElasticsearch template(String esname, String eqlname) {
    EnoaElasticsearch es = epm().use(esname);
    EoFireql fireql = Fireql.use(eqlname);
    if (Is.nullx(es) || Is.nullx(fireql))
      return null;
    String key = TextKit.union(esname, eqlname);
    if (tplcache == null) {
      tplcache = new HashMap<>();
    } else {
      TemplateElasticsearch tples = tplcache.get(key);
      if (tples != null)
        return tples;
    }
    TemplateElasticsearch tples = new TemplateElasticsearch(es, fireql);
    tplcache.put(key, tples);
    return tples;
  }


  public static EsEmiter post(String uri, Eql eql) {
    return use().post(uri, eql);
  }

  public static EsEmiter delete(String uri) {
    return use().delete(uri);
  }

  public static EsEmiter delete(String uri, Eql eql) {
    return use().delete(uri, eql);
  }

  public static EsEmiter search(String uri) {
    return use().search(uri);
  }

  public static EsEmiter search(String uri, Eql eql) {
    return use().search(uri, eql);
  }
}
