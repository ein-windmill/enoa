/*
 * Copyright (c) 2018, enoa (fewensa@enoa.io)
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
import io.enoa.trydb.tx.IAtom;
import io.enoa.trydb.tx.TxLevel;

import java.sql.Connection;
import java.util.List;

interface _TrydbCommandBase<S extends _TrydbCommandBase> {

  S conn(Connection conn);

  List<Kv> find(String sql);

  List<Kv> find(String sql, Object... paras);

  Kv first(String sql);

  Kv first(String sql, Object... paras);

  <T> List<T> beans(String sql, Class<T> clazz);

  <T> List<T> beans(String sql, Class<T> clazz, Object... paras);

  <T> T bean(String sql, Class<T> clazz);

  <T> T bean(String sql, Class<T> clazz, Object... paras);

  boolean tx(IAtom atom);

  boolean tx(TxLevel level, IAtom atom);

  int update(String sql);

  int update(String sql, Object... paras);

  default Page<Kv> pagekv(int pn, int ps, String sql) {
    return this.pagekv(IPSql.sqlfrom(), pn, ps, sql);
  }

  default Page<Kv> pagekv(int pn, int ps, String sql, Object... paras) {
    return this.pagekv(IPSql.sqlfrom(), pn, ps, sql, paras);
  }

  default <T> Page<T> page(int pn, int ps, String sql, Class<T> clazz) {
    return this.page(IPSql.sqlfrom(), pn, ps, sql, clazz);
  }

  default <T> Page<T> page(int pn, int ps, String sql, Class<T> clazz, Object... paras) {
    return this.page(IPSql.sqlfrom(), pn, ps, sql, clazz, paras);
  }

  Page<Kv> pagekv(IPSql ipsql, int pn, int ps, String sql);

  Page<Kv> pagekv(IPSql ipsql, int pn, int ps, String sql, Object... paras);

  <T> Page<T> page(IPSql ipsql, int pn, int ps, String sql, Class<T> clazz);

  <T> Page<T> page(IPSql ipsql, int pn, int ps, String sql, Class<T> clazz, Object... paras);

}
