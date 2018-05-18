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

public class EnqueueTrydb implements TrydbCommandEnqueue {

  private EnoaTrydb trydb;

  private EnqueueTrydb(EnoaTrydb trydb) {
    this.trydb = trydb;
  }

  public static EnqueueTrydb with(EnoaTrydb trydb) {
    return new EnqueueTrydb(trydb);
  }

  @Override
  public TrydbPromise find(String sql) {
    return null;
  }

  @Override
  public TrydbPromise find(String sql, Object... paras) {
    return null;
  }

  @Override
  public TrydbPromise first(String sql) {
    return null;
  }

  @Override
  public TrydbPromise first(String sql, Object... paras) {
    return null;
  }

  @Override
  public <T> TrydbPromise beans(String sql, Class<T> clazz) {
    return null;
  }

  @Override
  public <T> TrydbPromise beans(String sql, Class<T> clazz, Object... paras) {
    return null;
  }

  @Override
  public <T> TrydbPromise bean(String sql, Class<T> clazz) {
    return null;
  }

  @Override
  public <T> TrydbPromise bean(String sql, Class<T> clazz, Object... paras) {
    return null;
  }

  @Override
  public TrydbPromise tx(IAtom atom) {
    return null;
  }

  @Override
  public TrydbPromise tx(TxLevel level, IAtom atom) {
    return null;
  }

  @Override
  public TrydbPromise update(String sql) {
    return null;
  }

  @Override
  public TrydbPromise update(String sql, Object... paras) {
    return null;
  }

  @Override
  public TrydbPromise pagekv(IPSql ipsql, int pn, int ps, String sql) {
    return null;
  }

  @Override
  public TrydbPromise pagekv(IPSql ipsql, int pn, int ps, String sql, Object... paras) {
    return null;
  }

  @Override
  public <T> TrydbPromise page(IPSql ipsql, int pn, int ps, String sql, Class<T> clazz) {
    return null;
  }

  @Override
  public <T> TrydbPromise page(IPSql ipsql, int pn, int ps, String sql, Class<T> clazz, Object... paras) {
    return null;
  }
}
