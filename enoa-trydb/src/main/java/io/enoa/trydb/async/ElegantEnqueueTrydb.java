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

import io.enoa.trydb.ElegantTrydb;
import io.enoa.trydb.dialect.IDialect;
import io.enoa.toolkit.page.Page;
import io.enoa.trydb.tsql.Trysql;
import io.enoa.trydb.tsql.psql.IPSql;

import java.sql.Connection;
import java.util.List;

public class ElegantEnqueueTrydb {

  private ElegantTrydb trydb;

  public ElegantEnqueueTrydb(ElegantTrydb trydb) {
    this.trydb = trydb;
  }


  public ElegantEnqueueTrydb target(Class clazz) {
    this.trydb = this.trydb.target(clazz);
    return this;
  }

  public ElegantEnqueueTrydb conn(Connection conn) {
    this.trydb = this.trydb.conn(conn);
    return this;
  }

  public ElegantEnqueueTrydb dialect(IDialect dialect) {
    this.trydb = this.trydb.dialect(dialect);
    return this;
  }

  public <T> EnqueueTrydb<List<T>> beans(String sql) {
    return EnqueueTrydb.create(() -> this.trydb.beans(sql));
  }

  public <T> EnqueueTrydb<T> bean(String sql) {
    return EnqueueTrydb.create(() -> this.trydb.bean(sql));
  }

  public <T> EnqueueTrydb<List<T>> beans(String sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.beans(sql, paras));
  }

  public <T> EnqueueTrydb<T> bean(String sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.bean(sql, paras));
  }

  public <T> EnqueueTrydb<List<T>> beans(Trysql sql) {
    return EnqueueTrydb.create(() -> this.trydb.beans(sql));
  }

  public <T> EnqueueTrydb<T> bean(Trysql sql) {
    return EnqueueTrydb.create(() -> this.trydb.bean(sql));
  }

  public <T> EnqueueTrydb<List<T>> beans(Trysql sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.beans(sql, paras));
  }

  public <T> EnqueueTrydb<T> bean(Trysql sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.bean(sql, paras));
  }

  public <T> EnqueueTrydb<Page<T>> page(int pn, int ps, String sql) {
    return EnqueueTrydb.create(() -> this.trydb.page(pn, ps, sql));
  }

  public <T> EnqueueTrydb<Page<T>> page(int pn, int ps, String sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.page(pn, ps, sql, paras));
  }

  public <T> EnqueueTrydb<Page<T>> page(int pn, int ps, Trysql sql) {
    return EnqueueTrydb.create(() -> this.trydb.page(pn, ps, sql));
  }

  public <T> EnqueueTrydb<Page<T>> page(int pn, int ps, Trysql sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.page(pn, ps, sql, paras));
  }

  public <T> EnqueueTrydb<Page<T>> page(IPSql ipsql, int pn, int ps, String sql) {
    return EnqueueTrydb.create(() -> this.trydb.page(ipsql, pn, ps, sql));
  }

  public <T> EnqueueTrydb<Page<T>> page(IPSql ipsql, int pn, int ps, String sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.page(ipsql, pn, ps, sql, paras));
  }

  public <T> EnqueueTrydb<Page<T>> page(IPSql ipsql, int pn, int ps, Trysql sql) {
    return EnqueueTrydb.create(() -> this.trydb.page(ipsql, pn, ps, sql));
  }

  public <T> EnqueueTrydb<Page<T>> page(IPSql ipsql, int pn, int ps, Trysql sql, Object... paras) {
    return EnqueueTrydb.create(() -> this.trydb.page(ipsql, pn, ps, sql, paras));
  }

}
