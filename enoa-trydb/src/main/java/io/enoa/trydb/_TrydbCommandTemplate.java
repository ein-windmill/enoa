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

import io.enoa.toolkit.map.Kv;
import io.enoa.trydb.page.Page;
import io.enoa.trydb.tsql.psql.IPSql;

import java.util.List;
import java.util.Map;

interface _TrydbCommandTemplate {

  List<Kv> find(String name, Map<String, ?> para);

  Kv first(String name, Map<String, ?> para);

  <T> List<T> beans(String name, Class<T> clazz, Map<String, ?> para);

  <T> T bean(String name, Class<T> clazz, Map<String, ?> para);

  int update(String name, Map<String, ?> para);

  default Page<Kv> pagekv(int pn, int ps, String name, Map<String, ?> para) {
    return this.pagekv(IPSql.sqlfrom(), pn, ps, name, para);
  }

  default <T> Page<T> page(int pn, int ps, String name, Class<T> clazz, Map<String, ?> para) {
    return this.page(IPSql.sqlfrom(), pn, ps, name, clazz, para);
  }

  Page<Kv> pagekv(IPSql ipsql, int pn, int ps, String name, Map<String, ?> para);

  <T> Page<T> page(IPSql ipsql, int pn, int ps, String name, Class<T> clazz, Map<String, ?> para);

}
