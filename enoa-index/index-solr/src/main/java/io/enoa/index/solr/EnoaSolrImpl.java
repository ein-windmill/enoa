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
package io.enoa.index.solr;

import io.enoa.http.EoHttp;
import io.enoa.http.Http;
import io.enoa.index.solr.action.select.SSelect;
import io.enoa.index.solr.action.update.SUpdate;
import io.enoa.toolkit.eo.tip.EnoaTipKit;

class EnoaSolrImpl implements EoSolr {

  private SolrConfig config;
  private String core;
  private Http http;

  EnoaSolrImpl(SolrConfig config) {
    this.config = config;
  }

  private Http http() {
    if (this.http == null)
      this.http = Http.request();
    return this.http;
  }

  @Override
  public EoSolr http(EoHttp http) {
    this.http = http.http();
    return this;
  }

  @Override
  public EoSolrOperate core(String core) {
    this.core = core;
    return new EoSolrOperate() {
      @Override
      public SSelect select() {
        if (core == null)
          throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.solr.core_null"));
        return new SSelect(http(), config, core);
      }

      @Override
      public SUpdate update() {
        if (core == null)
          throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.solr.core_null"));
        return new SUpdate(http(), config, core);
      }
    };
  }

//  @Override
//  public SSelect select() {
//    if (this.core == null)
//      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.solr.core_null"));
//    return new SSelect(this.http(), this.config, this.core);
//  }
//
//  @Override
//  public SUpdate update() {
//    if (this.core == null)
//      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.solr.core_null"));
//    return new SUpdate(this.http(), this.config, this.core);
//  }

}
