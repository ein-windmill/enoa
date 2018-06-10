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
package io.enoa.example.index.solr;

import io.enoa.http.Http;
import io.enoa.http.protocol.enoa.HttpHandler;
import io.enoa.index.solr.EoSolr;
import io.enoa.index.solr.Solr;
import io.enoa.index.solr.SolrConfig;
import io.enoa.index.solr.cqp.Fq;

public class SolrExample {

  private void testSelect() {
    EoSolr solr = Solr.core("barcode");
    Http http = solr.http();
    http.handler(HttpHandler.def());

    solr.select()
      .fq(Fq.create("name", "药"))
      .rows(0)
      .emit();
  }

  private void testUpdate() {
    Solr.core("barcode")
      .update()
      .emit();
  }

  public static void main(String[] args) {
    SolrConfig config = new SolrConfig.Builder()
      .host("http://localhost:7000/solr")
      .build();
    Solr.epm().install(config);

    SolrExample e = new SolrExample();
    e.testSelect();
    e.testUpdate();
  }
}
