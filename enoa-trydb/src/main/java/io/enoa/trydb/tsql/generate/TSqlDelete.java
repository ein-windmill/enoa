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
package io.enoa.trydb.tsql.generate;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.trydb.dialect.IDialect;
import io.enoa.trydb.thr.TrysqlException;
import io.enoa.trydb.tsql.Trysql;

public class TSqlDelete implements Trysql<TSqlDelete> {

  private String table;
  private IDialect dialect;
  private TSqlWhere<TSqlDelete> where;

  public TSqlDelete(String table) {
    this.table = table;
  }

  public TSqlDelete() {
  }

  @Override
  public TSqlDelete dialect(IDialect dialect) {
    this.dialect = dialect;
    return this;
  }

  @Override
  public String sql() {
    if (this.table == null)
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_table_null"));
    StringBuilder sb = new StringBuilder();
    sb.append("delete from ").append(this.table);
    if (this.where != null) {
      sb.append(" where ").append(this.where.sql());
    }
    sb.append(";");
    return sb.toString();
  }

  @Override
  public String toString() {
    return this.sql();
  }


  public TSqlDelete table(String table) {
    this.table = table;
    return this;
  }

  /**
   * deprecated see TSqlWhere
   */
  @Deprecated
  public TSqlWhere<TSqlDelete> where() {
    if (this.where == null)
      this.where = new TSqlWhere<>(this);
    return this.where;
  }
}
