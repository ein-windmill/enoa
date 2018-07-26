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

import io.enoa.http.EoUrl;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpPara;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.index.solr.SolrConfig;
import io.enoa.index.solr.action._SolrAction;
import io.enoa.index.solr.cqp.Fq;
import io.enoa.index.solr.cqp.Sort;
import io.enoa.index.solr.cqp.Wt;
import io.enoa.index.solr.parser.SParser;
import io.enoa.toolkit.EoConst;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SSelect implements _SolrAction {

  private SolrConfig config;
  private Http http;
  private String core;

  private String q;
  //  private Qop qop;
  private Set<String> fqs;
  private Set<String> sorts;
  private long start;
  private long rows;
  private Set<String> fls;
  private String df;
  private String raw;
  private Wt wt;
  private boolean indent;
  private boolean debug;


  private Dismax dismax;
  private Edismax edismax;
  private Facet facet;
  private Hl hl;
  private Spatial spatial;
  private Spellcheck spellcheck;


  public SSelect(Http http, SolrConfig config, String core) {
    this.http = http;
    this.config = config;
    this.core = core;
    this.q = "*:*";
    this.start = 0;
    this.rows = 10;
    this.debug = Boolean.FALSE;
    this.indent = Boolean.TRUE;
  }

  /**
   * 查询字符串
   *
   * @param q q
   * @return SSelect
   */
  public SSelect q(String q) {
    this.q = q;
    return this;
  }

//  /**
//   * query operation
//   * AND OR
//   *
//   * @param qop qop
//   * @return SSelect
//   */
//  public SSelect qop(Qop qop) {
//    this.qop = qop;
//    return this;
//  }

  public SSelect fq(String fq) {
    if (fq == null)
      return this;
    if (this.fqs == null)
      this.fqs = new HashSet<>();
    this.fqs.add(fq);
    return this;
  }

  public SSelect fq(String... fqs) {
    for (String fq : fqs)
      this.fq(fq);
    return this;
  }

  public SSelect fq(Fq fq) {
    return this.fq(fq.string());
  }

  public SSelect fq(Fq... fqs) {
    for (Fq fq : fqs)
      this.fq(fq.string());
    return this;
  }

  public SSelect fq(Collection<Fq> fqs) {
    fqs.forEach(fq -> this.fq(fq.string()));
    return this;
  }

  public SSelect sort(String sort) {
    if (sort == null)
      return this;
    if (this.sorts == null)
      this.sorts = new HashSet<>();
    this.sorts.add(sort);
    return this;
  }

  public SSelect sort(String... sorts) {
    for (String sort : sorts)
      this.sort(sort);
    return this;
  }

  public SSelect sort(Sort sort) {
    return this.sort(sort.string());
  }

  public SSelect sort(Sort... sorts) {
    for (Sort sort : sorts)
      this.sort(sort.string());
    return this;
  }

  public SSelect sort(Collection<Sort> sorts) {
    sorts.forEach(sort -> this.sort(sort.string()));
    return this;
  }

  public SSelect start(long offset) {
    this.start = offset;
    return this;
  }

  public SSelect rows(long rows) {
    this.rows = rows;
    return this;
  }

  public SSelect limit(long offset, long rows) {
    this.start = offset;
    this.rows = rows;
    return this;
  }

  /**
   * field list
   *
   * @param fl fl
   * @return SSelect
   */
  public SSelect fl(String fl) {
    if (fl == null)
      return this;
    if (this.fls == null)
      this.fls = new HashSet<>();
    this.fls.add(fl);
    return this;
  }

  public SSelect fl(String... fls) {
    for (String fl : fls)
      this.fl(fl);
    return this;
  }

  public SSelect fl(Collection<String> fls) {
    fls.forEach(this::fl);
    return this;
  }

  /**
   * default filed
   *
   * @param df df
   * @return SSelect
   */
  public SSelect df(String df) {
    this.df = df;
    return this;
  }

  /**
   * raw query param
   *
   * @param raw raw
   * @return SSelect
   */
  public SSelect raw(String raw) {
    this.raw = raw;
    return this;
  }

  public SSelect wt(Wt wt) {
    this.wt = wt;
    return this;
  }

  /**
   * 是否縮進
   *
   * @return SSelect
   */
  public SSelect indent() {
    return this.indent(true);
  }

  public SSelect indent(boolean indent) {
    this.indent = indent;
    return this;
  }

  public SSelect debug() {
    return this.debug(true);
  }

  public SSelect debug(boolean debug) {
    this.debug = debug;
    return this;
  }

  public Dismax dismax() {
    this.dismax = new Dismax(this);
    return this.dismax;
  }

  public Edismax edismax() {
    this.edismax = new Edismax(this);
    return this.edismax;
  }

  public Hl hl() {
    this.hl = new Hl(this);
    return this.hl;
  }

  public Facet facet() {
    this.facet = new Facet(this);
    return this.facet;
  }

  public Spatial spatial() {
    this.spatial = new Spatial(this);
    return this.spatial;
  }

  public Spellcheck spellcheck() {
    this.spellcheck = new Spellcheck(this);
    return this.spellcheck;
  }

  @Override
  public <T> T emit(SParser<T> parser) {
    this.http.method(HttpMethod.GET)
      .url(EoUrl.with(this.config.host()).subpath(this.core).subpath("select"))
      .charset(EoConst.CHARSET)
      .encode();

    this.http.para("_", System.currentTimeMillis());

    this.http.para("q", this.q);
    if (this.fqs != null)
      this.fqs.forEach(fq -> this.http.para("fq", fq));

    if (this.sorts != null)
      this.http.para("sort", String.join(",", this.sorts));

    this.http.para("start", this.start);
    this.http.para("rows", this.rows);

    if (this.fls != null)
      this.http.para("fl", String.join(",", this.fls));

    if (this.df != null)
      this.http.para("df", this.df);

    if (this.wt != null)
      this.http.para("wt", this.wt.val());

    if (this.raw != null)
      this.http.para(HttpPara.parse(this.raw));

    if (!this.indent)
      this.http.para("indent", "off");

    if (this.debug)
      this.http.para("debugQuery", "on");

    if (this.dismax != null)
      this.http.para(this.dismax.paras());

    if (this.edismax != null)
      this.http.para(this.edismax.paras());

    if (this.hl != null)
      this.http.para(this.hl.paras());

    if (this.facet != null)
      this.http.para(this.facet.paras());

    if (this.spatial != null)
      this.http.para(this.spatial.paras());

    if (this.spellcheck != null)
      this.http.para(this.spellcheck.paras());

    HttpResponse response = this.http.emit();
    return parser.result(response);
  }

}
