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
import io.enoa.trydb.dialect.IDialect;
import io.enoa.trydb.page.Page;
import io.enoa.trydb.tsql.Trysql;
import io.enoa.trydb.tsql.psql.IPSql;

import java.util.List;

interface _TrydbCommandTSql<S extends _TrydbCommandTSql> {

  S dialect(IDialect dialect);

  List<Kv> find(Trysql sql);

  List<Kv> find(Trysql sql, Object... paras);

  Kv first(Trysql sql);

  Kv first(Trysql sql, Object... paras);

  <T> List<T> beans(Trysql sql, Class<T> clazz);

  <T> List<T> beans(Trysql sql, Class<T> clazz, Object... paras);

  <T> T bean(Trysql sql, Class<T> clazz);

  <T> T bean(Trysql sql, Class<T> clazz, Object... paras);

  int update(Trysql sql);

  int update(Trysql sql, Object... paras);

  default Page<Kv> pagekv(int pn, int ps, Trysql sql) {
    return this.pagekv(IPSql.sqlfrom(), pn, ps, sql);
  }

  default Page<Kv> pagekv(int pn, int ps, Trysql sql, Object... paras) {
    return this.pagekv(IPSql.sqlfrom(), pn, ps, sql, paras);
  }

  default <T> Page<T> page(int pn, int ps, Trysql sql, Class<T> clazz) {
    return this.page(IPSql.sqlfrom(), pn, ps, sql, clazz);
  }

  default <T> Page<T> page(int pn, int ps, Trysql sql, Class<T> clazz, Object... paras) {
    return this.page(IPSql.sqlfrom(), pn, ps, sql, clazz, paras);
  }

  Page<Kv> pagekv(IPSql ipsql, int pageNumber, int pageSize, Trysql sql);

  Page<Kv> pagekv(IPSql ipsql, int pageNumber, int pageSize, Trysql sql, Object... paras);

  <T> Page<T> page(IPSql ipsql, int pageNumber, int pageSize, Trysql sql, Class<T> clazz);

  <T> Page<T> page(IPSql ipsql, int pageNumber, int pageSize, Trysql sql, Class<T> clazz, Object... paras);

}
