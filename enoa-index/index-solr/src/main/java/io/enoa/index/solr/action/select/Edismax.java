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

import java.util.Set;

public class Edismax extends Dismax {

  private String uf;
  private String pf2;
  private String pf3;
  private String ps2;
  private String ps3;
  private String boost;
  private boolean stopwords;
  private boolean operators;

  Edismax(SSelect select) {
    super(select);
  }

  @Override
  public Edismax qalt(String qalt) {
    super.qalt(qalt);
    return this;
  }

  @Override
  public Edismax qf(String qf) {
    super.qf(qf);
    return this;
  }

  @Override
  public Edismax mm(String mm) {
    super.mm(mm);
    return this;
  }

  @Override
  public Edismax pf(String pf) {
    super.pf(pf);
    return this;
  }

  @Override
  public Edismax ps(String ps) {
    super.ps(ps);
    return this;
  }

  @Override
  public Edismax qs(String qs) {
    super.qs(qs);
    return this;
  }

  @Override
  public Edismax tie(String tie) {
    super.tie(tie);
    return this;
  }

  @Override
  public Edismax bq(String bq) {
    super.bq(bq);
    return this;
  }

  @Override
  public Edismax bf(String bf) {
    super.bf(bf);
    return this;
  }


  public Edismax uf(String uf) {
    this.uf = uf;
    return this;
  }

  public Edismax pf2(String pf2) {
    this.pf2 = pf2;
    return this;
  }

  public Edismax pf3(String pf3) {
    this.pf3 = pf3;
    return this;
  }

  public Edismax ps2(String ps2) {
    this.ps2 = ps2;
    return this;
  }

  public Edismax ps3(String ps3) {
    this.ps3 = ps3;
    return this;
  }

  public Edismax boost(String boost) {
    this.boost = boost;
    return this;
  }

  public Edismax stopwords() {
    return this.stopwords(Boolean.TRUE);
  }

  public Edismax stopwords(boolean stopwords) {
    this.stopwords = stopwords;
    return this;
  }

  public Edismax lowercaseOperators() {
    return this.lowercaseOperators(Boolean.TRUE);
  }

  public Edismax lowercaseOperators(boolean operators) {
    this.operators = operators;
    return this;
  }

  @Override
  public SSelect end() {
    return super.end();
  }

  @Override
  protected Set<HttpPara> paras() {
    Set<HttpPara> paras = super.paras();
    paras.add(new HttpPara("defType", "edismax"));
    if (this.uf != null)
      paras.add(new HttpPara("uf", this.uf));
    if (this.pf2 != null)
      paras.add(new HttpPara("pf2", this.pf2));
    if (this.pf3 != null)
      paras.add(new HttpPara("pf3", this.pf3));
    if (this.ps2 != null)
      paras.add(new HttpPara("ps2", this.ps2));
    if (this.ps3 != null)
      paras.add(new HttpPara("ps3", this.ps3));
    if (this.boost != null)
      paras.add(new HttpPara("boost", this.boost));
//    if (this.stopwords)
//      paras.add(new HttpPara("key", "val"));
//    if (this.operators)
//      paras.add(new HttpPara("key", "val"));
    return paras;
  }
}
