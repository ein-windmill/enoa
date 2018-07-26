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

public class Spellcheck extends _AdvancedSelect {

  private SSelect select;


  private boolean build;
  private boolean reload;
  private String q;
  private String dictionary;
  private String count;
  private boolean only;
  private boolean extended;
  private boolean collate;
  private String maxCollation;
  private String maxCollationTries;
  private String accuracy;

  Spellcheck(SSelect select) {
    this.select = select;

    this.build = Boolean.FALSE;
    this.reload = Boolean.FALSE;
    this.only = Boolean.FALSE;
    this.extended = Boolean.FALSE;
    this.collate = Boolean.FALSE;
  }

  public Spellcheck spellcheckBuild() {
    return this.spellcheckBuild(true);
  }

  public Spellcheck spellcheckBuild(boolean build) {
    this.build = build;
    return this;
  }

  public Spellcheck spellcheckReload() {
    return this.spellcheckReload(true);
  }

  public Spellcheck spellcheckReload(boolean reload) {
    this.reload = reload;
    return this;
  }

  public Spellcheck spellcheckQ(String q) {
    this.q = q;
    return this;
  }

  public Spellcheck spellcheckDictionary(String dictionary) {
    this.dictionary = dictionary;
    return this;
  }

  public Spellcheck spellcheckCount(String count) {
    this.count = count;
    return this;
  }

  public Spellcheck spellcheckOnlyMorePopular() {
    return this.spellcheckOnlyMorePopular(true);
  }

  public Spellcheck spellcheckOnlyMorePopular(boolean only) {
    this.only = only;
    return this;
  }

  public Spellcheck spellcheckExtendedResults() {
    return this.spellcheckExtendedResults(true);
  }

  public Spellcheck spellcheckExtendedResults(boolean ended) {
    this.extended = ended;
    return this;
  }

  public Spellcheck spellcheckCollate() {
    return this.spellcheckCollate(true);
  }

  public Spellcheck spellcheckCollate(boolean collate) {
    this.collate = collate;
    return this;
  }

  public Spellcheck spellcheckMaxCollations(String max) {
    this.maxCollation = max;
    return this;
  }

  public Spellcheck spellcheckMaxCollationTries(String max) {
    this.maxCollationTries = max;
    return this;
  }

  public Spellcheck spellcheckAccuracy(String accuracy) {
    this.accuracy = accuracy;
    return this;
  }

  @Override
  public SSelect end() {
    return this.select;
  }

  @Override
  protected Set<HttpPara> paras() {

    Set<HttpPara> paras = new HashSet<>();
    paras.add(new HttpPara("spellcheck", "on"));
    if (this.q != null)
      paras.add(new HttpPara("spellcheck.q", this.q));
    if (this.dictionary != null)
      paras.add(new HttpPara("spellcheck.dictionary", this.dictionary));
    if (this.accuracy != null)
      paras.add(new HttpPara("spellcheck.accuracy", this.accuracy));
    if (this.maxCollation != null)
      paras.add(new HttpPara("spellcheck.maxCollations", this.maxCollation));
    if (this.count != null)
      paras.add(new HttpPara("spellcheck.count", this.count));
    if (this.maxCollationTries != null)
      paras.add(new HttpPara("spellcheck.maxCollationTries", this.maxCollationTries));
    return paras;
  }

}
