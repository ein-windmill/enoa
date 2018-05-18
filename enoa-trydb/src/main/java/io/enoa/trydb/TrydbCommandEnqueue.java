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

import io.enoa.trydb.promise.TrydbPromise;
import io.enoa.trydb.tsql.psql.IPSql;
import io.enoa.trydb.tx.IAtom;
import io.enoa.trydb.tx.TxLevel;

interface TrydbCommandEnqueue {

  TrydbPromise find(String sql);

  TrydbPromise find(String sql, Object... paras);

  TrydbPromise first(String sql);

  TrydbPromise first(String sql, Object... paras);

  <T> TrydbPromise beans(String sql, Class<T> clazz);

  <T> TrydbPromise beans(String sql, Class<T> clazz, Object... paras);

  <T> TrydbPromise bean(String sql, Class<T> clazz);

  <T> TrydbPromise bean(String sql, Class<T> clazz, Object... paras);

  TrydbPromise tx(IAtom atom);

  TrydbPromise tx(TxLevel level, IAtom atom);

  TrydbPromise update(String sql);

  TrydbPromise update(String sql, Object... paras);

  default TrydbPromise pagekv(int pn, int ps, String sql) {
    return this.pagekv(IPSql.sqlfrom(), pn, ps, sql);
  }

  default TrydbPromise pagekv(int pn, int ps, String sql, Object... paras) {
    return this.pagekv(IPSql.sqlfrom(), pn, ps, sql, paras);
  }

  default <T> TrydbPromise page(int pn, int ps, String sql, Class<T> clazz) {
    return this.page(IPSql.sqlfrom(), pn, ps, sql, clazz);
  }

  default <T> TrydbPromise page(int pn, int ps, String sql, Class<T> clazz, Object... paras) {
    return this.page(IPSql.sqlfrom(), pn, ps, sql, clazz, paras);
  }

  TrydbPromise pagekv(IPSql ipsql, int pn, int ps, String sql);

  TrydbPromise pagekv(IPSql ipsql, int pn, int ps, String sql, Object... paras);

  <T> TrydbPromise page(IPSql ipsql, int pn, int ps, String sql, Class<T> clazz);

  <T> TrydbPromise page(IPSql ipsql, int pn, int ps, String sql, Class<T> clazz, Object... paras);

}
