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
import io.enoa.http.EoUrl;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.enoa.IHttpHandler;
import io.enoa.index.solr.Solr;
import io.enoa.index.solr.SolrConfig;
import io.enoa.index.solr.cqp.Fq;
import io.enoa.index.solr.cqp.OrderBy;
import io.enoa.index.solr.cqp.Sort;
import io.enoa.index.solr.cqp.Wt;
import io.enoa.index.solr.parser.JsonParser;
import io.enoa.index.solr.parser.SParser;
import io.enoa.index.solr.ret.SRet;
import io.enoa.json.Json;
import io.enoa.json.provider.fastjson.FastjsonProvider;
import io.enoa.toolkit.digest.DigestKit;
import io.enoa.toolkit.digest.UUIDKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.text.TextKit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SolrExample {

  private void selectWithHttpInfo() {
    SRet<Barcode> ret = Solr.core("barcode")
      .http(() -> Http.use().handler(IHttpHandler.def()).reporter(System.out::println))
      .select()
      .fq(Fq.create("name", "药"))
      .rows(2)
      .emit(SParser.json(Barcode.class));

    System.out.println(Json.toJson(ret));
    System.out.println("=====================> selectWithHttpInfo");
  }

  private void testSelect() {
    SRet<Barcode> ret = Solr.core("barcode")
      .select()
      .fq(Fq.create("name", "药"))
      .rows(2)
      .sort(Sort.create("ctime", OrderBy.DESC))
      .emit(JsonParser.create(Barcode.class));

    System.out.println(Json.toJson(ret));
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
    SRet<Void> ret = Solr.core("stest")
      .http(() -> Http.use().handler(IHttpHandler.def()).reporter(System.out::println))
      .update()
      .overwrite(true)
      .commitWithin(1000)
      .wt(Wt.JSON)
      .body(Json.toJson(this.kvs(20)))
      .emit(JsonParser.create());

    System.out.println(ret);
    System.out.println("=====================> testUpdate");
  }

  private void enqueueUpdate() {
    Solr.core("stest")
      .update()
      .body(Json.toJson(this.kvs(30)))
      .enqueue()
      .done(System.out::println)
      .capture(Throwable::printStackTrace)
      .always(() -> System.out.println("=====================> enqueueUpdate"));
  }

  private List<Kv> kvs(int size) {
    List<Kv> kvs = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      Kv kv = Kv.create()
        .set("id", DigestKit.sha1(UUIDKit.next()))
        .set("title", this.moreText(10, 15))
        .set("body", this.moreText(100, 150))
        .set("stat", i % 2 == 0 ? 1 : 2)
        .set("ctime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
      kvs.add(kv);
    }
    return kvs;
  }

  private String moreText(int mix, int max) {
    String text = Http.request(EoUrl.with("http://more.handlino.com/sentences.json"))
      .handler(IHttpHandler.def())
      .method(HttpMethod.GET)
      .para("limit", TextKit.union(String.valueOf(mix), ",", max))
      .emit()
      .body()
      .string();
    Kv kv = Json.parse(text, Kv.class);
    return ((List<String>) kv.as("sentences")).get(0);
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
    e.enqueueUpdate();
  }
}
