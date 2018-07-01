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
package io.enoa.trydb.async;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.trydb.EnoaTrydb;
import io.enoa.trydb.dialect.IDialect;
import io.enoa.trydb.page.Page;
import io.enoa.trydb.tsql.Trysql;
import io.enoa.trydb.tsql.psql.IPSql;
import io.enoa.trydb.tx.IAtom;
import io.enoa.trydb.tx.TxLevel;

import java.sql.Connection;
import java.util.List;

public class EnoaEnqueueTrydb {

  private EnoaTrydb trydb;

  public EnoaEnqueueTrydb(EnoaTrydb trydb) {
    this.trydb = trydb;
  }

  public EnoaEnqueueTrydb conn(Connection conn) {
    this.trydb = this.trydb.conn(conn);
    return this;
  }

  public EnoaEnqueueTrydb dialect(IDialect dialect) {
    this.trydb = this.trydb.dialect(dialect);
    return this;
  }

  public EnqueueTrydb<List<Kv>> find(String sql) {
    return EnqueueTrydb.create(() -> this.trydb.find(sql, CollectionKit.emptyArray(Object.class)));
  }

  public EnqueueTrydb<List<Kv>> find(String sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.find(sql, paras));
  }

  public EnqueueTrydb<List<Kv>> find(Trysql sql) {
    return EnqueueTrydb.create(() -> this.trydb.find(sql));
  }

  public EnqueueTrydb<List<Kv>> find(Trysql sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.find(sql, paras));
  }

  public EnqueueTrydb<Kv> first(String sql) {
    return EnqueueTrydb.create(() -> this.trydb.first(sql, CollectionKit.emptyArray(Object.class)));
  }

  public EnqueueTrydb<Kv> first(String sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.first(sql, paras));
  }

  public EnqueueTrydb<Kv> first(Trysql sql) {
    return EnqueueTrydb.create(() -> this.trydb.first(sql));
  }

  public EnqueueTrydb<Kv> first(Trysql sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.first(sql, paras));
  }

  public <T> EnqueueTrydb<List<T>> beans(String sql, Class<T> clazz) {
    return EnqueueTrydb.create(() -> this.trydb.beans(sql, clazz, CollectionKit.emptyArray(Object.class)));
  }

  public <T> EnqueueTrydb<List<T>> beans(String sql, Class<T> clazz, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.beans(sql, clazz, paras));
  }

  public <T> EnqueueTrydb<List<T>> beans(Trysql sql, Class<T> clazz) {
    return EnqueueTrydb.create(() -> this.trydb.beans(sql, clazz));
  }

  public <T> EnqueueTrydb<List<T>> beans(Trysql sql, Class<T> clazz, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.beans(sql, clazz, paras));
  }

  public <T> EnqueueTrydb<T> bean(String sql, Class<T> clazz) {
    return EnqueueTrydb.create(() -> this.trydb.bean(sql, clazz));
  }

  public <T> EnqueueTrydb<T> bean(String sql, Class<T> clazz, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.bean(sql, clazz, paras));
  }

  public <T> EnqueueTrydb<T> bean(Trysql sql, Class<T> clazz) {
    return EnqueueTrydb.create(() -> this.trydb.bean(sql, clazz));
  }

  public <T> EnqueueTrydb<T> bean(Trysql sql, Class<T> clazz, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.bean(sql, clazz, paras));
  }

  public EnqueueTrydb<Boolean> tx(IAtom atom) {
    return EnqueueTrydb.create(() -> this.trydb.tx(atom));
  }

  public EnqueueTrydb<Boolean> tx(TxLevel level, IAtom atom) {
    return EnqueueTrydb.create(() -> this.trydb.tx(level, atom));
  }

  public EnqueueTrydb<Integer> update(String sql) {
    return EnqueueTrydb.create(() -> this.trydb.update(sql, CollectionKit.emptyArray(Object.class)));
  }

  public EnqueueTrydb<Integer> update(String sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.update(sql, paras));
  }

  public EnqueueTrydb<Integer> update(Trysql sql) {
    return EnqueueTrydb.create(() -> this.trydb.update(sql));
  }

  public EnqueueTrydb<Integer> update(Trysql sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.update(sql, paras));
  }

  public EnqueueTrydb<Page<Kv>> pagekv(IPSql ipsql, int pn, int ps, Trysql sql) {
    return EnqueueTrydb.create(() -> this.trydb.pagekv(ipsql, pn, ps, sql));
  }

  public EnqueueTrydb<Page<Kv>> pagekv(IPSql ipsql, int pn, int ps, Trysql sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.pagekv(ipsql, pn, ps, sql, paras));
  }

  public EnqueueTrydb<Page<Kv>> pagekv(IPSql ipsql, int pn, int ps, String sql) {
    return EnqueueTrydb.create(() -> this.trydb.pagekv(ipsql, pn, ps, sql));
  }

  public EnqueueTrydb<Page<Kv>> pagekv(IPSql ipsql, int pn, int ps, String sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.pagekv(ipsql, pn, ps, sql, paras));
  }

  public EnqueueTrydb<Page<Kv>> pagekv(int pn, int ps, String sql) {
    return EnqueueTrydb.create(() -> this.trydb.pagekv(pn, ps, sql));
  }

  public EnqueueTrydb<Page<Kv>> pagekv(int pn, int ps, String sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.pagekv(pn, ps, sql, paras));
  }

  public EnqueueTrydb<Page<Kv>> pagekv(int pn, int ps, Trysql sql) {
    return EnqueueTrydb.create(() -> this.trydb.pagekv(pn, ps, sql));
  }

  public EnqueueTrydb<Page<Kv>> pagekv(int pn, int ps, Trysql sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.pagekv(pn, ps, sql, paras));
  }

  public <T> EnqueueTrydb<Page<T>> page(IPSql ipsql, int pn, int ps, Trysql sql, Class<T> clazz) {
    return EnqueueTrydb.create(() -> this.trydb.page(ipsql, pn, ps, sql, clazz));
  }

  public <T> EnqueueTrydb<Page<T>> page(IPSql ipsql, int pn, int ps, Trysql sql, Class<T> clazz, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.page(ipsql, pn, ps, sql, clazz, paras));
  }

  public <T> EnqueueTrydb<Page<T>> page(IPSql ipsql, int pn, int ps, String sql, Class<T> clazz) {
    return EnqueueTrydb.create(() -> this.trydb.page(ipsql, pn, ps, sql, clazz));
  }

  public <T> EnqueueTrydb<Page<T>> page(IPSql ipsql, int pn, int ps, String sql, Class<T> clazz, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.page(ipsql, pn, ps, sql, clazz, paras));
  }

  public <T> EnqueueTrydb<Page<T>> page(int pn, int ps, String sql, Class<T> clazz) {
    return EnqueueTrydb.create(() -> this.trydb.page(pn, ps, sql, clazz));
  }

  public <T> EnqueueTrydb<Page<T>> page(int pn, int ps, String sql, Class<T> clazz, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.page(pn, ps, sql, clazz, paras));
  }

  public <T> EnqueueTrydb<Page<T>> page(int pn, int ps, Trysql sql, Class<T> clazz) {
    return EnqueueTrydb.create(() -> this.trydb.page(pn, ps, sql, clazz));
  }

  public <T> EnqueueTrydb<Page<T>> page(int pn, int ps, Trysql sql, Class<T> clazz, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.page(pn, ps, sql, clazz, paras));
  }

}
