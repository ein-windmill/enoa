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

import io.enoa.http.EoUrl;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpPara;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.index.solr.SolrConfig;
import io.enoa.index.solr.action.SolrAction;
import io.enoa.index.solr.cqp.Fq;
import io.enoa.index.solr.cqp.Qop;
import io.enoa.index.solr.cqp.Sort;
import io.enoa.index.solr.cqp.Wt;
import io.enoa.toolkit.EoConst;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SolrSelect implements SolrAction {

  private SolrConfig config;
  private Http http;
  private String core;

  private String q;
  private Qop qop;
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

  public SolrSelect(Http http, SolrConfig config, String core) {
    this.http = http;
    this.config = config;
    this.core = core;
    this.q = "*:*";
    this.start = 0;
    this.rows = 10;
    this.wt = Wt.JSON;
    this.debug = Boolean.FALSE;
    this.indent = Boolean.TRUE;
  }

  /**
   * 查询字符串
   *
   * @param q q
   * @return SolrSelect
   */
  public SolrSelect q(String q) {
    this.q = q;
    return this;
  }

  /**
   * query operation
   * AND OR
   *
   * @param qop qop
   * @return SolrSelect
   */
  public SolrSelect qop(Qop qop) {
    this.qop = qop;
    return this;
  }

  public SolrSelect fq(String fq) {
    if (this.fqs == null)
      this.fqs = new HashSet<>();
    this.fqs.add(fq);
    return this;
  }

  public SolrSelect fq(String... fqs) {
    for (String fq : fqs)
      this.fq(fq);
    return this;
  }

  public SolrSelect fq(Fq fq) {
    return this.fq(fq.string());
  }

  public SolrSelect fq(Fq... fqs) {
    for (Fq fq : fqs)
      this.fq(fq.string());
    return this;
  }

  public SolrSelect fq(Collection<Fq> fqs) {
    fqs.forEach(fq -> this.fq(fq.string()));
    return this;
  }

  public SolrSelect sort(String sort) {
    if (this.sorts == null)
      this.sorts = new HashSet<>();
    this.sorts.add(sort);
    return this;
  }

  public SolrSelect sort(String... sorts) {
    for (String sort : sorts)
      this.sort(sort);
    return this;
  }

  public SolrSelect sort(Sort sort) {
    return this.sort(sort.string());
  }

  public SolrSelect sort(Sort... sorts) {
    for (Sort sort : sorts)
      this.sort(sort.string());
    return this;
  }

  public SolrSelect sort(Collection<Sort> sorts) {
    sorts.forEach(sort -> this.sort(sort.string()));
    return this;
  }

  public SolrSelect start(long offset) {
    this.start = offset;
    return this;
  }

  public SolrSelect rows(long rows) {
    this.rows = rows;
    return this;
  }

  public SolrSelect limit(long offset, long rows) {
    this.start = offset;
    this.rows = rows;
    return this;
  }

  /**
   * field list
   *
   * @param fl fl
   * @return SolrSelect
   */
  public SolrSelect fl(String fl) {
    if (this.fls == null)
      this.fls = new HashSet<>();
    this.fls.add(fl);
    return this;
  }

  public SolrSelect fl(String... fls) {
    for (String fl : fls)
      this.fl(fl);
    return this;
  }

  public SolrSelect fl(Collection<String> fls) {
    fls.forEach(this::fl);
    return this;
  }

  /**
   * default filed
   *
   * @param df df
   * @return SolrSelect
   */
  public SolrSelect df(String df) {
    this.df = df;
    return this;
  }

  /**
   * raw query param
   *
   * @param raw raw
   * @return SolrSelect
   */
  public SolrSelect raw(String raw) {
    this.raw = raw;
    return this;
  }

  public SolrSelect wt(Wt wt) {
    this.wt = wt;
    return this;
  }

  /**
   * 是否縮進
   *
   * @return SolrSelect
   */
  public SolrSelect indent() {
    return this.indent(true);
  }

  public SolrSelect indent(boolean indent) {
    this.indent = indent;
    return this;
  }

  public SolrSelect debug() {
    return this.debug(true);
  }

  public SolrSelect debug(boolean debug) {
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
  public void emit() {
    this.http.method(HttpMethod.GET)
      .url(EoUrl.with(this.config.host()).subpath(this.core).subpath("select"))
      .charset(EoConst.CHARSET)
      .encode();

    this.http.para("q", this.q);
    if (this.fqs != null)
      this.http.para("fq", this.fqs);

    if (this.sorts != null)
      this.http.para("sort", String.join(",", this.sorts));

    this.http.para("start", this.start);
    this.http.para("rows", this.rows);

    if (this.fls != null)
      this.http.para("fl", this.fls);

    if (this.df != null)
      this.http.para("df", this.df);

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

    // todo solr emit callbak
    System.out.println(response.body().string());
  }

}
