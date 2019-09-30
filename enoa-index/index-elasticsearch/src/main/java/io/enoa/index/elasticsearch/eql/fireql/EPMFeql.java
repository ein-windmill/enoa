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
package io.enoa.index.elasticsearch.eql.fireql;

import io.enoa.firetpl.Firetpl;

import java.util.HashMap;
import java.util.Map;

public class EPMFeql {

  private static class Holder {
    private static final EPMFeql INSTANCE = new EPMFeql();
  }

  public static EPMFeql instance() {
    return Holder.INSTANCE;
  }

  public Map<String, EoFireql> cache;

  EPMFeql() {
    this.cache = new HashMap<>();
  }

  public void install(Firetpl firetpl) {
    this.install("main", firetpl);
  }

  public void install(String name, Firetpl firetpl) {
    this.cache.put(name, new EnoaFireql(firetpl));
  }

  EoFireql use() {
    return this.use("main");
  }

  EoFireql use(String name) {
    return this.cache.get(name);
  }

}
