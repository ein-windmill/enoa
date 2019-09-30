/*
 * Copyright 2016 ikidou
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
package io.enoa.index.elasticsearch;

import io.enoa.http.Http;
import io.enoa.http.auth.basic.BasicAuth;
import io.enoa.http.protocol.enoa.IHttpHandler;
import io.enoa.http.protocol.enoa.IHttpReporter;
import io.enoa.index.elasticsearch.types.search.ESearch;
import io.enoa.json.provider.fastjson.FastjsonProvider;
import io.enoa.toolkit.map.Kv;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ElasticsearchTest {


  @Before
  @Ignore
  public void setUp() throws Exception {
    Elasticsearch.epm().install(new ElasticsearchConfig.Builder()
      .host("http://localhost:9200")
      .json(new FastjsonProvider())
      .http(() -> Http.use()
        .reporter(IHttpReporter.def())
        .handler(IHttpHandler.def())
        .auth(new BasicAuth("elastic", "passwd"))
      )
      .build());
  }

  @Test
  @Ignore
  public void testSearch() {
    ESearch<Kv> dat = Elasticsearch.search("/iktest")
      .emit(EParser.search(Kv.class));
    System.out.println(dat);
  }

}
