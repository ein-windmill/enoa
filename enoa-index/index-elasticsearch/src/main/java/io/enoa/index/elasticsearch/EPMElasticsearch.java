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

import java.util.HashMap;
import java.util.Map;

public class EPMElasticsearch {

  private static class Holder {
    private static final EPMElasticsearch INSTANCE = new EPMElasticsearch();
  }

  public static EPMElasticsearch instance() {
    return Holder.INSTANCE;
  }


  private Map<String, EnoaElasticsearch> cache;

  public EPMElasticsearch() {
    this.cache = new HashMap<>();
  }

  public void install(ElasticsearchConfig config) {
    this.install("main", config);
  }

  public void install(String name, ElasticsearchConfig config) {
    this.cache.put(name, new EnoaElasticsearch(config));
  }


  EnoaElasticsearch use() {
    return this.use("main");
  }

  EnoaElasticsearch use(String name) {
    return this.cache.get(name);
  }

}
