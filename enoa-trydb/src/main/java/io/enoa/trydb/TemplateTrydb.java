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
import io.enoa.trydb.thr.TrydbException;
import io.enoa.trydb.tsql.Trysql;
import io.enoa.trydb.tsql.template.TSql;
import io.enoa.trydb.tx.IAtom;
import io.enoa.trydb.tx.TxLevel;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class TemplateTrydb implements TrydbCommandBase<TemplateTrydb>, TrydbCommandTemplate {

  private String trysqlName;
  private EnoaTrydb trydb;

  private TemplateTrydb(String trysqlName, EnoaTrydb trydb) {
    this.trysqlName = trysqlName;
    this.trydb = trydb;
  }

  public static TemplateTrydb with(String trysqlName, EnoaTrydb trydb) {
    return new TemplateTrydb(trysqlName, trydb);
  }

  private TSql tsql(String name) {
    return this.tsql(name, null);
  }

  private TSql tsql(String name, Map kv) {
    TSql tsql;
    if (kv == null) {
      tsql = Trysql.template(this.trysqlName).render(name);
    } else {
      tsql = Trysql.template(this.trysqlName).render(name, kv);
    }
    if (tsql == null)
      throw new TrydbException(EnoaTipKit.message("eo.tip.trydb.sql_null"));
    return tsql;
  }

  @Override
  public TemplateTrydb conn(Connection conn) {
    return with(this.trysqlName, this.trydb.conn(conn));
  }

  @Override
  public List<Kv> find(String name) {
    TSql tsql = this.tsql(name);
    return this.trydb.find(tsql.sql());
  }

  @Override
  public List<Kv> find(String name, Object... paras) {
    TSql tsql = this.tsql(name);
    return this.trydb.find(tsql.sql(), paras);
  }

  @Override
  public Kv first(String name) {
    TSql tsql = this.tsql(name);
    return this.trydb.first(tsql.sql());
  }

  @Override
  public Kv first(String name, Object... paras) {
    TSql tsql = this.tsql(name);
    return this.trydb.first(tsql.sql(), paras);
  }

  @Override
  public <T> List<T> beans(String name, Class<T> clazz) {
    TSql tsql = this.tsql(name);
    return this.trydb.beans(tsql.sql(), clazz);
  }

  @Override
  public <T> List<T> beans(String name, Class<T> clazz, Object... paras) {
    TSql tsql = this.tsql(name);
    return this.trydb.beans(tsql.sql(), clazz, paras);
  }

  @Override
  public <T> T bean(String name, Class<T> clazz) {
    TSql tsql = this.tsql(name);
    return this.trydb.bean(tsql.sql(), clazz);
  }

  @Override
  public <T> T bean(String name, Class<T> clazz, Object... paras) {
    TSql tsql = this.tsql(name);
    return this.trydb.bean(tsql.sql(), clazz, paras);
  }

  @Override
  public boolean tx(IAtom atom) {
    return this.trydb.tx(atom);
  }

  @Override
  public boolean tx(TxLevel level, IAtom atom) {
    return this.trydb.tx(level, atom);
  }

  @Override
  public int update(String name) {
    TSql tsql = this.tsql(name);
    return this.trydb.update(tsql.sql());
  }

  @Override
  public int update(String name, Object... paras) {
    TSql tsql = this.tsql(name);
    return this.trydb.update(tsql.sql(), paras);
  }

  @Override
  public List<Kv> find(String name, Map<String, ?> para) {
    TSql tsql = this.tsql(name, para);
    return this.trydb.find(tsql.sql(), tsql.paras());
  }

  @Override
  public Kv first(String name, Map<String, ?> para) {
    TSql tsql = this.tsql(name, para);
    return this.trydb.first(tsql.sql(), tsql.paras());
  }

  @Override
  public <T> List<T> beans(String name, Class<T> clazz, Map<String, ?> para) {
    TSql tsql = this.tsql(name, para);
    return this.trydb.beans(tsql.sql(), clazz, tsql.paras());
  }

  @Override
  public <T> T bean(String name, Class<T> clazz, Map<String, ?> para) {
    TSql tsql = this.tsql(name, para);
    return this.trydb.bean(tsql.sql(), clazz, tsql.paras());
  }

  @Override
  public int update(String name, Map<String, ?> para) {
    TSql tsql = this.tsql(name, para);
    return this.trydb.update(tsql.sql(), tsql.paras());
  }
}
