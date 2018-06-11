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

import io.enoa.example.index.entity.Barcode;
import io.enoa.http.Http;
import io.enoa.http.protocol.enoa.HttpHandler;
import io.enoa.index.solr.EoSolr;
import io.enoa.index.solr.Solr;
import io.enoa.index.solr.SolrConfig;
import io.enoa.index.solr.cqp.Fq;
import io.enoa.index.solr.parser.SParser;
import io.enoa.index.solr.parser.JsonParser;
import io.enoa.index.solr.ret.SRet;
import io.enoa.json.Json;
import io.enoa.json.provider.fastjson.FastjsonProvider;

public class SolrExample {

  private void selectWithHttpInfo() {
    EoSolr solr = Solr.core("barcode");
    Http http = solr.http();
    http.handler(HttpHandler.def());

    SRet<Barcode> ret = solr.select()
      .fq(Fq.create("name", "药"))
      .rows(2)
      .emit(JsonParser.create(Barcode.class));

    System.out.println(ret);
    System.out.println("=====================> selectWithHttpInfo");
  }

  private void testSelect() {
    SRet<Barcode> ret = Solr.core("barcode")
      .select()
      .fq(Fq.create("name", "药"))
      .rows(2)
      .emit(JsonParser.create(Barcode.class));

    System.out.println(ret);
    System.out.println("=====================> testSelect");
  }

  private void defaultParserSelect() {
    String ret = Solr.core("barcode")
      .select()
      .fq(Fq.create("name", "药"))
      .rows(2)
      .emit();
    System.out.println(ret);
    System.out.println("=====================> defaultParserSelect");
  }

  private void enqueueSelect() {
    Solr.core("barcode")
      .select()
      .fq(Fq.create("name", "药"))
      .rows(2)
      .enqueue()
      .done(System.out::println)
      .capture(Throwable::printStackTrace)
      .always(() -> System.out.println("=====================> enqueueSelect"));
  }

  private void testUpdate() {
    Solr.core("barcode")
      .update()
      .emit(SParser.def());
  }

  public static void main(String[] args) {

    Json.epm()
      .install(new FastjsonProvider());

    SolrConfig config = new SolrConfig.Builder()
      .host("http://localhost:7000/solr")
      .build();
    Solr.epm().install(config);

    SolrExample e = new SolrExample();

    e.selectWithHttpInfo();
    e.testSelect();
    e.enqueueSelect();
    e.defaultParserSelect();
    e.testUpdate();
  }
}
