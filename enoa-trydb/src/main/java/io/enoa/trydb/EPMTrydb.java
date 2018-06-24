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
package io.enoa.trydb;

import io.enoa.toolkit.eo.tip.EnoaTipKit;

import java.util.HashMap;
import java.util.Map;

public class EPMTrydb {

  private static class Holder {
    private static final EPMTrydb INSTANCE = new EPMTrydb();
  }

  static EPMTrydb instance() {
    return Holder.INSTANCE;
  }

  private Map<String, EnoaTrydb> etdb = new HashMap<>();
  private Map<String, TemplateTrydb> ttsql = new HashMap<>();

  private boolean exists(String name) {
    return this.etdb.containsKey(name);
  }

  public void install(TrydbConfig config) {
    if (this.exists(config.name()))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.trydb.name_exists", config.name()));

    this.etdb.put(config.name(), new EnoaTrydb(config));
  }

  void install(String name, TemplateTrydb trydb) {
    this.ttsql.put(name, trydb);
  }

  public EnoaTrydb trydb(String name) {
    return this.etdb.get(name);
  }

  TemplateTrydb ttsql(String name) {
    return this.ttsql.get(name);
  }

}
