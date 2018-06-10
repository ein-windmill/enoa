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
package io.enoa.index.solr;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.thr.EoException;

import java.util.HashMap;
import java.util.Map;

public class EPMSolr {

  private EPMSolr() {

  }

  private static class Holder {
    private static final EPMSolr INSTANCE = new EPMSolr();
  }

  static EPMSolr instance() {
    return Holder.INSTANCE;
  }

  private Map<String, SolrConfig> cache = new HashMap<>();

  private boolean exists(String name) {
    return this.cache.containsKey(name);
  }

  public void install(SolrConfig config) {
    this.install("main", config);
  }

  public void install(String name, SolrConfig config) {
    if (this.exists(name))
      throw new EoException(EnoaTipKit.message("eo.tip.solr.name_exists", name));
    this.cache.put(name, config);
  }

  public SolrConfig config(String name) {
    return this.cache.get(name);
  }

}
