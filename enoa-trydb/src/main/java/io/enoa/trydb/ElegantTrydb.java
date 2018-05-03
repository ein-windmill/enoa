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

import io.enoa.trydb.dialect.IDialect;
import io.enoa.trydb.tsql.Trysql;

import java.sql.Connection;
import java.util.List;

//@SuppressWarnings("unchecked")
public class ElegantTrydb {

  private Class clazz;
  private EnoaTrydb trydb;

  private ElegantTrydb(EnoaTrydb trydb) {
    this.trydb = trydb;
  }

  public static ElegantTrydb with(EnoaTrydb trydb) {
    return new ElegantTrydb(trydb);
  }

  public ElegantTrydb target(Class clazz) {
    this.clazz = clazz;
    return this;
  }

  public ElegantTrydb conn(Connection conn) {
    this.trydb = this.trydb.conn(conn);
    return this;
  }

  public ElegantTrydb dialect(IDialect dialect) {
    this.trydb = this.trydb.dialect(dialect);
    return this;
  }

  public <T> List<T> beans(String sql) {
    return this.trydb.beans(sql, (Class<T>) this.clazz);
  }

  public <T> T bean(String sql) {
    return this.trydb.bean(sql, (Class<T>) this.clazz);
  }

  public <T> List<T> beans(String sql, Object... paras) {
    return this.trydb.beans(sql, (Class<T>) this.clazz, paras);
  }

  public <T> T bean(String sql, Object... paras) {
    return this.trydb.bean(sql, (Class<T>) this.clazz, paras);
  }

  public <T> List<T> beans(Trysql sql) {
    return this.trydb.beans(sql, (Class<T>) this.clazz);
  }

  public <T> T bean(Trysql sql) {
    return this.trydb.bean(sql, (Class<T>) this.clazz);
  }

  public <T> List<T> beans(Trysql sql, Object... paras) {
    return this.trydb.beans(sql, (Class<T>) this.clazz, paras);
  }

  public <T> T bean(Trysql sql, Object... paras) {
    return this.trydb.bean(sql, (Class<T>) this.clazz, paras);
  }
}
