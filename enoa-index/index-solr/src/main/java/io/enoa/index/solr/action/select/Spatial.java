/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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

public class Spatial extends _AdvancedSelect {

  private SolrSelect select;

  private String pt;
  private String d;
  private String sfield;

  Spatial(SolrSelect select) {
    this.select = select;
  }

  public Spatial pt(String pt) {
    return this;
  }

  public Spatial sfield(String sfield) {
    return this;
  }

  public Spatial d(String d) {
    return this;
  }

  @Override
  public SolrSelect end() {
    return this.select;
  }

  @Override
  protected Set<HttpPara> paras() {
    Set<HttpPara> paras = new HashSet<>();
    if (this.pt != null)
      paras.add(new HttpPara("pt", this.pt));
    if (this.d != null)
      paras.add(new HttpPara("d", this.d));
    if (this.sfield != null)
      paras.add(new HttpPara("sfield", this.sfield));
    return paras;
  }

}
