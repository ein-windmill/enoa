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
package io.enoa.trydb.async;

import io.enoa.toolkit.map.Kv;
import io.enoa.trydb.TemplateTrydb;
import io.enoa.trydb.page.Page;
import io.enoa.trydb.tsql.psql.IPSql;
import io.enoa.trydb.tx.IAtom;
import io.enoa.trydb.tx.TxLevel;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class TemplateEnqueueTrydb {

  private TemplateTrydb trydb;

  public TemplateEnqueueTrydb(TemplateTrydb trydb) {
    this.trydb = trydb;
  }

  public TemplateEnqueueTrydb conn(Connection conn) {
    this.trydb = this.trydb.conn(conn);
    return this;
  }

  public EnqueueTrydb<List<Kv>> find(String name) {
    return EnqueueTrydb.create(() -> this.trydb.find(name));
  }

  public EnqueueTrydb<List<Kv>> find(String name, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.find(name, paras));
  }

  public EnqueueTrydb<Kv> first(String name) {
    return EnqueueTrydb.create(() -> this.trydb.first(name));
  }

  public EnqueueTrydb<Kv> first(String name, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.first(name, paras));
  }

  public <T> EnqueueTrydb<List<T>> beans(String name, Class<T> clazz) {
    return EnqueueTrydb.create(() -> this.trydb.beans(name, clazz));
  }

  public <T> EnqueueTrydb<List<T>> beans(String name, Class<T> clazz, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.beans(name, clazz, paras));
  }

  public <T> EnqueueTrydb<T> bean(String name, Class<T> clazz) {
    return EnqueueTrydb.create(() -> this.trydb.bean(name, clazz));
  }

  public <T> EnqueueTrydb<T> bean(String name, Class<T> clazz, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.bean(name, clazz, paras));
  }

  public EnqueueTrydb<Boolean> tx(IAtom atom) {
    return EnqueueTrydb.create(() -> this.trydb.tx(atom));
  }

  public EnqueueTrydb<Boolean> tx(TxLevel level, IAtom atom) {
    return EnqueueTrydb.create(() -> this.trydb.tx(level, atom));
  }

  public EnqueueTrydb<Integer> update(String name) {
    return EnqueueTrydb.create(() -> this.trydb.update(name));
  }

  public EnqueueTrydb<Integer> update(String name, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.update(name, paras));
  }

  public EnqueueTrydb<List<Kv>> find(String name, Map<String, ?> para) {
    return EnqueueTrydb.create(() -> this.trydb.find(name, para));
  }

  public EnqueueTrydb<Kv> first(String name, Map<String, ?> para) {
    return EnqueueTrydb.create(() -> this.trydb.first(name, para));
  }

  public <T> EnqueueTrydb<List<T>> beans(String name, Class<T> clazz, Map<String, ?> para) {
    return EnqueueTrydb.create(() -> this.trydb.beans(name, clazz, para));
  }

  public <T> EnqueueTrydb<T> bean(String name, Class<T> clazz, Map<String, ?> para) {
    return EnqueueTrydb.create(() -> this.trydb.bean(name, clazz, para));
  }

  public EnqueueTrydb<Integer> update(String name, Map<String, ?> para) {
    return EnqueueTrydb.create(() -> this.trydb.update(name, para));
  }

  public EnqueueTrydb<Page<Kv>> pagekv(IPSql ipsql, int pn, int ps, String name) {
    return EnqueueTrydb.create(() -> this.trydb.pagekv(ipsql, pn, ps, name));
  }

  public EnqueueTrydb<Page<Kv>> pagekv(IPSql ipsql, int pn, int ps, String name, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.pagekv(ipsql, pn, ps, name, paras));
  }

  public EnqueueTrydb<Page<Kv>> pagekv(IPSql ipsql, int pn, int ps, String name, Map<String, ?> para) {
    return EnqueueTrydb.create(() -> this.trydb.pagekv(ipsql, pn, ps, name, para));
  }

  public <T> EnqueueTrydb<Page<T>> page(IPSql ipsql, int pn, int ps, String name, Class<T> clazz) {
    return EnqueueTrydb.create(() -> this.trydb.page(ipsql, pn, ps, name, clazz));
  }

  public <T> EnqueueTrydb<Page<T>> page(IPSql ipsql, int pn, int ps, String name, Class<T> clazz, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.page(ipsql, pn, ps, name, clazz, paras));
  }

  public <T> EnqueueTrydb<Page<T>> page(IPSql ipsql, int pn, int ps, String name, Class<T> clazz, Map<String, ?> para) {
    return EnqueueTrydb.create(() -> this.trydb.page(ipsql, pn, ps, name, clazz, para));
  }

  public EnqueueTrydb<Page<Kv>> pagekv(int pn, int ps, String sql) {
    return EnqueueTrydb.create(() -> this.trydb.pagekv(pn, ps, sql));
  }

  public EnqueueTrydb<Page<Kv>> pagekv(int pn, int ps, String sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.pagekv(pn, ps, sql, paras));
  }

  public <T> EnqueueTrydb<Page<T>> page(int pn, int ps, String sql, Class<T> clazz) {
    return EnqueueTrydb.create(() -> this.trydb.page(pn, ps, sql, clazz));
  }

  public <T> EnqueueTrydb<Page<T>> page(int pn, int ps, String sql, Class<T> clazz, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.page(pn, ps, sql, clazz, paras));
  }

  public EnqueueTrydb<Page<Kv>> pagekv(int pn, int ps, String name, Map<String, ?> para) {
    return EnqueueTrydb.create(() -> this.trydb.pagekv(pn, ps, name, para));
  }

  public <T> EnqueueTrydb<Page<T>> page(int pn, int ps, String name, Class<T> clazz, Map<String, ?> para) {
    return EnqueueTrydb.create(() -> this.trydb.page(pn, ps, name, clazz, para));
  }

}
