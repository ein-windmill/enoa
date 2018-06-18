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
package io.enoa.index.solr.action.update;

import io.enoa.http.EoUrl;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.index.solr.SolrConfig;
import io.enoa.index.solr.action._SolrAction;
import io.enoa.index.solr.cqp.Wt;
import io.enoa.index.solr.parser.SParser;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.text.TextKit;

public class SUpdate implements _SolrAction {

  private SolrConfig config;
  private String core;
  private Http http;

  private Boolean autoCommit;
  private Integer commitWithin;
  private boolean overwrite;
  private Wt wt;
  private String body;
  private String contentType;

  public SUpdate(Http http, SolrConfig config, String core) {
    this.http = http;
    this.config = config;
    this.core = core;

    this.overwrite = Boolean.TRUE;
    this.contentType = "application/json";
  }

//  public SUpdate autoCommit() {
//    this.autoCommit = Boolean.TRUE;
//    return this;
//  }

  public SUpdate commitWithin() {
    return this.commitWithin(1000);
  }

  public SUpdate commitWithin(int size) {
    this.commitWithin = size;
    return this;
  }

  public SUpdate overwrite() {
    return this.overwrite(Boolean.TRUE);
  }

  public SUpdate overwrite(boolean overwrite) {
    this.overwrite = overwrite;
    return this;
  }

  public SUpdate wt(Wt wt) {
    this.wt = wt;
    return this;
  }

  public SUpdate contentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

  public SUpdate body(String body) {
    this.body = body;
    return this;
  }


  @Override
  public <T> T emit(SParser<T> parser) {

    if (TextKit.isBlank(this.body))
      return null;

    this.http.method(HttpMethod.POST)
      .url(EoUrl.with(this.config.host()).subpath(this.core).subpath("update"))
      .charset(EoConst.CHARSET)
      .encode();

    int _cwn = this.commitWithin == null ? 1000 : this.commitWithin;

    this.http.para("_", System.currentTimeMillis())
      .para("commitWithin", _cwn)
      .para("overwrite", this.overwrite)
      .raw(this.body)
      .header("Content-Type", this.contentType);

    if (this.wt != null)
      this.http.para("wt", this.wt.val());

    HttpResponse response = this.http.emit();

    return parser.result(response);
  }
}
