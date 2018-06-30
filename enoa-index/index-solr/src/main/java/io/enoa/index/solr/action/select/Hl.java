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

public class Hl extends _AdvancedSelect {

  private SSelect select;

  private String fl;
  private String pre;
  private String post;
  private boolean match;
  private boolean use;
  private boolean term;

  Hl(SSelect select) {
    this.select = select;
    this.match = Boolean.FALSE;
    this.use = Boolean.FALSE;
    this.term = Boolean.FALSE;
  }

  public Hl hlFl(String fl) {
    this.fl = fl;
    return this;
  }

  public Hl hlSimplePre(String pre) {
    this.pre = pre;
    return this;
  }

  public Hl hlSimplePost(String post) {
    this.post = post;
    return this;
  }

  public Hl hlRequireFieldMatch() {
    return this.hlRequireFieldMatch(true);
  }

  public Hl hlRequireFieldMatch(boolean match) {
    this.match = match;
    return this;
  }

  public Hl hlUsePhraseHighlighter() {
    return this.hlUsePhraseHighlighter(true);
  }

  public Hl hlUsePhraseHighlighter(boolean use) {
    this.use = use;
    return this;
  }

  public Hl hlHighlightMultiTerm() {
    return this.hlHighlightMultiTerm(true);
  }

  public Hl hlHighlightMultiTerm(boolean term) {
    this.term = term;
    return this;
  }

  @Override
  public SSelect end() {
    return this.select;
  }

  @Override
  protected Set<HttpPara> paras() {
    Set<HttpPara> paras = new HashSet<>();
    paras.add(new HttpPara("hl", "on"));

    if (this.fl != null)
      paras.add(new HttpPara("hl.fl", this.fl));
    if (this.pre != null)
      paras.add(new HttpPara("hl.simple.pre", this.pre));
    if (this.post != null)
      paras.add(new HttpPara("hl.simple.post", this.post));

//    if (this.match)
//      paras.add(new HttpPara("key", "val"));
//    if (this.use)
//      paras.add(new HttpPara("key", "val"));
//    if (this.term)
//      paras.add(new HttpPara("key", "val"));

    return paras;
  }
}
