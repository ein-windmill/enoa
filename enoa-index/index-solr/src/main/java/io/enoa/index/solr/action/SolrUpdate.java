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
package io.enoa.index.solr.action;

import io.enoa.http.Http;
import io.enoa.index.solr.SolrConfig;

public class SolrUpdate implements SolrAction {

  private SolrConfig config;
  private String core;
  private Http http;

  public SolrUpdate(Http http, SolrConfig config, String core) {
    this.http = http;
    this.config = config;
    this.core = core;
  }

  @Override
  public void emit() {

  }

}
