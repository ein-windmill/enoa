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

public class Dismax extends _AdvancedSelect {

  private SolrSelect select;

  private String qalt;
  private String qf;
  private String mm;
  private String pf;
  private String ps;
  private String qs;
  private String tie;
  private String bq;
  private String bf;

  Dismax(SolrSelect select) {
    this.select = select;
  }

  public Dismax qalt(String qalt) {
    this.qalt = qalt;
    return this;
  }

  /**
   * query fileds
   *
   * @param qf qf
   * @return Dismax
   */
  public Dismax qf(String qf) {
    this.qf = qf;
    return this;
  }

  public Dismax mm(String mm) {
    this.mm = mm;
    return this;
  }

  public Dismax pf(String pf) {
    this.pf = pf;
    return this;
  }

  public Dismax ps(String ps) {
    this.ps = ps;
    return this;
  }

  public Dismax qs(String qs) {
    this.qs = qs;
    return this;
  }

  public Dismax tie(String tie) {
    this.tie = tie;
    return this;
  }

  public Dismax bq(String bq) {
    this.bq = bq;
    return this;
  }

  public Dismax bf(String bf) {
    this.bf = bf;
    return this;
  }

  @Override
  public SolrSelect end() {
    return this.select;
  }

  @Override
  protected Set<HttpPara> paras() {
    Set<HttpPara> paras = new HashSet<>();
    paras.add(new HttpPara("defType", "dismax"));
    if (this.qalt != null)
      paras.add(new HttpPara("q.alt", this.qalt));
    if (this.qs != null)
      paras.add(new HttpPara("qs", this.qs));
    if (this.ps != null)
      paras.add(new HttpPara("ps", this.ps));
    if (this.bf != null)
      paras.add(new HttpPara("bf", this.bf));
    if (this.qf != null)
      paras.add(new HttpPara("qf", this.qf));
    if (this.pf != null)
      paras.add(new HttpPara("pf", this.pf));
    return paras;
  }

}
