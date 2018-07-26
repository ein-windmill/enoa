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
package io.enoa.index.solr.action.select;

import io.enoa.http.protocol.HttpPara;

import java.util.HashSet;
import java.util.Set;

public class Facet extends _AdvancedSelect {

  private SSelect select;

  private String query;
  private String field;
  private String prefix;

  Facet(SSelect select) {
    this.select = select;
  }

  public Facet facetQuery(String query) {
    this.query = query;
    return this;
  }

  public Facet facetField(String field) {
    this.field = field;
    return this;
  }

  public Facet facetPrefix(String prefix) {
    this.prefix = prefix;
    return this;
  }

  @Override
  public SSelect end() {
    return this.select;
  }

  @Override
  protected Set<HttpPara> paras() {
    Set<HttpPara> paras = new HashSet<>();
    paras.add(new HttpPara("facet", "on"));
    if (this.query != null)
      paras.add(new HttpPara("facet.query", this.query));
    if (this.field != null)
      paras.add(new HttpPara("facet.field", this.field));
    if (this.prefix != null)
      paras.add(new HttpPara("facet.prefix", this.prefix));
    return paras;
  }

}
