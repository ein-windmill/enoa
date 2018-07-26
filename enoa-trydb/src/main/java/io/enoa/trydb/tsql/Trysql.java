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
package io.enoa.trydb.tsql;

import io.enoa.firetpl.FireBody;
import io.enoa.firetpl.Firetpl;
import io.enoa.stove.api.StoveException;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.trydb.Trydb;
import io.enoa.trydb.TrydbConfig;
import io.enoa.trydb.dialect.IDialect;
import io.enoa.trydb.thr.TrydbException;
import io.enoa.trydb.thr.TrysqlException;
import io.enoa.trydb.tsql.generate.TSqlDelete;
import io.enoa.trydb.tsql.generate.TSqlInsert;
import io.enoa.trydb.tsql.generate.TSqlSelect;
import io.enoa.trydb.tsql.generate.TSqlUpdate;
import io.enoa.trydb.tsql.template.SqlNotfoundException;
import io.enoa.trydb.tsql.template.TSql;

import java.util.Map;

public interface Trysql<T extends Trysql> {

  /**
   * undone, not recommended for use
   *
   * @return TSqlSelect
   */
  @Deprecated
  static TSqlSelect select() {
    return new TSqlSelect();
  }


  /**
   * undone, not recommended for use
   *
   * @return TSqlSelect
   */
  @Deprecated
  static TSqlSelect select(String table) {
    return new TSqlSelect(table);
  }

  static TSqlUpdate update() {
    return new TSqlUpdate();
  }

  static TSqlUpdate update(String table) {
    return new TSqlUpdate(table);
  }

  static TSqlDelete delete() {
    return new TSqlDelete();
  }

  static TSqlDelete delete(String table) {
    return new TSqlDelete(table);
  }

  static TSqlInsert insert() {
    return new TSqlInsert();
  }

  static TSqlInsert insert(String table) {
    return new TSqlInsert(table);
  }

  static TSql tsql(String key) {
    return tsql("main", key);
  }

  static TSql tsql(String key, Map<String, ?> para) {
    return tsql("main", key, para);
  }

  static TSql tsql(String name, String key) {
    return tsql(name, key, null);
  }

  static TSql tsql(String name, String key, Map<String, ?> para) {
    TrydbConfig config = Trydb.config(name);
    Firetpl template = config.template();
//    TSql tsql;
    FireBody body;
    try {
      if (para == null) {
        body = template.render(key);
      } else {
        body = template.render(key, para);
      }
    } catch (Exception e) {
      if (e instanceof StoveException)
        throw new SqlNotfoundException(e.getMessage(), e);
      throw new TrysqlException(e.getMessage(), e);
    }
    if (body == null)
      throw new TrydbException(EnoaTipKit.message("eo.tip.trydb.sql_null"));
    return TSql.create(body);
  }

  /**
   * 數據庫方言
   *
   * @param dialect dialect
   * @return T extends Trysql
   */
  T dialect(IDialect dialect);

  /**
   * sql 生成
   *
   * @return String
   */
  String sql();

}
