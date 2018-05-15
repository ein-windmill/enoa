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
package io.enoa.trydb.tsql;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.trydb.dialect.IDialect;
import io.enoa.trydb.thr.TrysqlException;
import io.enoa.trydb.tsql.generate.TSqlDelete;
import io.enoa.trydb.tsql.generate.TSqlInsert;
import io.enoa.trydb.tsql.generate.TSqlSelect;
import io.enoa.trydb.tsql.generate.TSqlUpdate;
import io.enoa.trydb.tsql.template.TPM;
import io.enoa.trydb.tsql.template.TSqlTemplate;

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

  static TSqlTemplate template() {
    return template("main");
  }

  static TSqlTemplate template(String name) {
    TSqlTemplate template = TPM.instance().tsql(name);
    if (template == null)
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_template_null"));
    return template;
  }

  static TPM tpm() {
    return TPM.instance();
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
