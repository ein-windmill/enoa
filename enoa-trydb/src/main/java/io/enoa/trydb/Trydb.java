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
import io.enoa.toolkit.map.Kv;
import io.enoa.trydb.async.EnoaEnqueueTrydb;
import io.enoa.trydb.page.Page;
import io.enoa.trydb.thr.TrydbException;
import io.enoa.trydb.tsql.Trysql;
import io.enoa.trydb.tsql.psql.IPSql;
import io.enoa.trydb.tx.IAtom;
import io.enoa.trydb.tx.TxLevel;

import java.util.List;

public interface Trydb {

  static EPMTrydb epm() {
    return EPMTrydb.instance();
  }

  static EnoaTrydb use() {
    return use("main");
  }

  static EnoaTrydb use(String name) {
    EnoaTrydb trydb = epm().trydb(name);
    if (trydb == null)
      throw new TrydbException(EnoaTipKit.message("eo.tip.trydb.trydb_null"));
    return trydb;
  }

  /**
   * class 链式调用传递
   *
   * @return ElegantTrydb
   */
  static ElegantTrydb elegant() {
    return elegant("main");
  }

  static ElegantTrydb elegant(String name) {
    return ElegantTrydb.with(use(name));
  }

  /**
   * 依据模板调用获取 sql 语句进行数据库操作
   *
   * @return TemplateTrydb
   */
  static TemplateTrydb template() {
    return template("main");
  }

  static TemplateTrydb template(String name) {
    TemplateTrydb ttsql = epm().ttsql(name);
    if (ttsql != null)
      return ttsql;
    ttsql = TemplateTrydb.with(name, use(name));
    epm().install(name, ttsql);
    return ttsql;
  }

  static EnoaEnqueueTrydb async() {
    return use().async();
  }

  static EnoaEnqueueTrydb async(String name) {
    return use(name).async();
  }

  static TrydbConfig config() {
    return config("main");
  }

  static TrydbConfig config(String name) {
    EnoaTrydb trydb = use(name);
    if (trydb == null)
      throw new TrydbException(EnoaTipKit.message("eo.tip.trydb.tsql_template_notfound", name));
    return trydb.config();
  }

//  static boolean exists(String name) {
//    return TrydbHolder.exists(name);
//  }

  static List<Kv> find(String sql) {
    return use().find(sql);
  }

  static List<Kv> find(String sql, Object... paras) {
    return use().find(sql, paras);
  }

  static List<Kv> find(Trysql sql) {
    return use().find(sql);
  }

  static List<Kv> find(Trysql sql, Object... paras) {
    return use().find(sql, paras);
  }

  static Kv first(String sql) {
    return use().first(sql);
  }

  static Kv first(String sql, Object... paras) {
    return use().first(sql, paras);
  }

  static Kv first(Trysql sql) {
    return use().first(sql);
  }

  static Kv first(Trysql sql, Object... paras) {
    return use().first(sql, paras);
  }

  static <T> List<T> beans(String sql, Class<T> clazz) {
    return use().beans(sql, clazz);
  }

  static <T> List<T> beans(String sql, Class<T> clazz, Object... paras) {
    return use().beans(sql, clazz, paras);
  }

  static <T> List<T> beans(Trysql sql, Class<T> clazz) {
    return use().beans(sql, clazz);
  }

  static <T> List<T> beans(Trysql sql, Class<T> clazz, Object... paras) {
    return use().beans(sql, clazz, paras);
  }

  static <T> T bean(String sql, Class<T> clazz) {
    return use().bean(sql, clazz);
  }

  static <T> T bean(String sql, Class<T> clazz, Object... paras) {
    return use().bean(sql, clazz, paras);
  }

  static <T> T bean(Trysql sql, Class<T> clazz) {
    return use().bean(sql, clazz);
  }

  static <T> T bean(Trysql sql, Class<T> clazz, Object... paras) {
    return use().bean(sql, clazz, paras);
  }

  static boolean tx(IAtom atom) {
    return use().tx(atom);
  }

  static boolean tx(TxLevel level, IAtom atom) {
    return use().tx(level, atom);
  }

  static int update(String sql) {
    return use().update(sql);
  }

  static int update(String sql, Object... paras) {
    return use().update(sql, paras);
  }

  static int update(Trysql sql) {
    return use().update(sql);
  }

  static int update(Trysql sql, Object... paras) {
    return use().update(sql, paras);
  }

  static Page<Kv> pagekv(int pn, int ps, String sql) {
    return use().pagekv(pn, ps, sql);
  }

  static Page<Kv> pagekv(int pn, int ps, String sql, Object... paras) {
    return use().pagekv(pn, ps, sql, paras);
  }

  static Page<Kv> pagekv(int pn, int ps, Trysql sql) {
    return use().pagekv(pn, ps, sql);
  }

  static Page<Kv> pagekv(int pn, int ps, Trysql sql, Object... paras) {
    return use().pagekv(pn, ps, sql, paras);
  }

  static <T> Page<T> page(int pn, int ps, String sql, Class<T> clazz) {
    return use().page(pn, ps, sql, clazz);
  }

  static <T> Page<T> page(int pn, int ps, String sql, Class<T> clazz, Object... paras) {
    return use().page(pn, ps, sql, clazz, paras);
  }

  static <T> Page<T> page(int pn, int ps, Trysql sql, Class<T> clazz) {
    return use().page(pn, ps, sql, clazz);
  }

  static <T> Page<T> page(int pn, int ps, Trysql sql, Class<T> clazz, Object... paras) {
    return use().page(pn, ps, sql, clazz, paras);
  }

  static Page<Kv> pagekv(IPSql ipsql, int pn, int ps, String sql) {
    return use().pagekv(ipsql, pn, ps, sql);
  }

  static Page<Kv> pagekv(IPSql ipsql, int pn, int ps, String sql, Object... paras) {
    return use().pagekv(ipsql, pn, ps, sql, paras);
  }

  static Page<Kv> pagekv(IPSql ipsql, int pn, int ps, Trysql sql) {
    return use().pagekv(ipsql, pn, ps, sql);
  }

  static Page<Kv> pagekv(IPSql ipsql, int pn, int ps, Trysql sql, Object... paras) {
    return use().pagekv(ipsql, pn, ps, sql, paras);
  }

  static <T> Page<T> page(IPSql ipsql, int pn, int ps, String sql, Class<T> clazz) {
    return use().page(ipsql, pn, ps, sql, clazz);
  }

  static <T> Page<T> page(IPSql ipsql, int pn, int ps, String sql, Class<T> clazz, Object... paras) {
    return use().page(ipsql, pn, ps, sql, clazz, paras);
  }

  static <T> Page<T> page(IPSql ipsql, int pn, int ps, Trysql sql, Class<T> clazz) {
    return use().page(ipsql, pn, ps, sql, clazz);
  }

  static <T> Page<T> page(IPSql ipsql, int pn, int ps, Trysql sql, Class<T> clazz, Object... paras) {
    return use().page(ipsql, pn, ps, sql, clazz, paras);
  }

}
