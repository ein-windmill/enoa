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
package io.enoa.trydb.tsql.template;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.trydb.thr.TrysqlException;

import java.util.HashMap;
import java.util.Map;

public class EnoaTSqlTemplateMgr {


  private static class Holder {
    private static final Map<String, TSqlTemplate> CACHE = new HashMap<>();
  }

  private static boolean exists(String name) {
    return Holder.CACHE.keySet().stream().anyMatch(n -> n.equals(name));
  }

  public static void install(TSqlTemplate template) {
    install("main", template);
  }

  public static void install(String name, TSqlTemplate template) {
    if (exists(name))
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_template_name_exists"));
    Holder.CACHE.put(name, template);
  }

  public static TSqlTemplate template(String name) {
    return Holder.CACHE.get(name);
  }

}
