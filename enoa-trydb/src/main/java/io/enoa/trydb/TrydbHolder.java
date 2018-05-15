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

import java.util.HashMap;
import java.util.Map;

class TrydbHolder {

  private static class Holder {
    private static final TrydbHolder INSTANCE = new TrydbHolder();
  }

  private Map<String, EnoaTrydb> etdb;
  private Map<String, TemplateTrydb> ttsql;
  private Map<String, EnqueueTrydb> qtdb;

  private TrydbHolder() {
    this.etdb = new HashMap<>();
    this.ttsql = new HashMap<>();
    this.qtdb = new HashMap<>();
  }

  static void reg(String name, EnoaTrydb trydb) {
    Holder.INSTANCE.etdb.put(name, trydb);
  }

  static void reg(String name, TemplateTrydb ttdb) {
    Holder.INSTANCE.ttsql.put(name, ttdb);
  }

  static void reg(String name, EnqueueTrydb qtdb) {
    Holder.INSTANCE.qtdb.put(name, qtdb);
  }

  static EnoaTrydb trydb(String name) {
    return Holder.INSTANCE.etdb.get(name);
  }

  static TemplateTrydb ttsql(String name) {
    return Holder.INSTANCE.ttsql.get(name);
  }

  static EnqueueTrydb qtdb(String name) {
    return Holder.INSTANCE.qtdb.get(name);
  }

  static boolean exists(String name) {
    return trydb(name) != null;
  }

}
