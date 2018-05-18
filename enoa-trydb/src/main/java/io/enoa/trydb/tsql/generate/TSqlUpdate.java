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
package io.enoa.trydb.tsql.generate;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.trydb.dialect.IDialect;
import io.enoa.trydb.thr.TrysqlException;
import io.enoa.trydb.tsql.Trysql;

import java.util.ArrayList;
import java.util.List;

public class TSqlUpdate implements Trysql<TSqlUpdate> {

  private String table;
  private IDialect dialect;
  private TSqlWhere<TSqlUpdate> where;
  private List<Kv> sets;

  public TSqlUpdate(String table) {
    this.table = table;
  }

  public TSqlUpdate() {
  }

  @Override
  public TSqlUpdate dialect(IDialect dialect) {
    this.dialect = dialect;
    return this;
  }

  @Override
  public String sql() {
    if (this.table == null)
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_table_null"));
    if (this.sets == null)
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_sets_null"));
    StringBuilder sb = new StringBuilder();
    sb.append("update ")
      .append(this.table)
      .append(" set");
    int size = this.sets.size();
    for (int i = 0; i < size; i++) {
      Kv kv = this.sets.get(i);
      sb.append(" ")
        .append(kv.string("column"))
        .append("=");
      sb.append(TSqlCorrect.safeValue(kv.as("value")));
      if (i + 1 < size)
        sb.append(",");
    }
    if (this.where != null) {
      sb.append(" ").append("where ").append(this.where.sql());
    }
    sb.append(";");
    return sb.toString();
  }

  @Override
  public String toString() {
    return this.sql();
  }

  public TSqlUpdate table(String table) {
    this.table = table;
    return this;
  }

  private TSqlUpdate set(String column, Object value) {
    if (this.sets == null)
      this.sets = new ArrayList<>();
    this.sets.add(Kv.by("column", column).set("value", value));
    return this;
  }

  public TSqlUpdate set(String column, String value) {
    return this.set(column, (Object) value);
  }

  public TSqlUpdate set(String column, Number value) {
    return this.set(column, (Object) value);
  }

  /**
   * deprecated see TSqlWhere
   */
  @Deprecated
  public TSqlWhere<TSqlUpdate> where() {
    if (this.where == null)
      this.where = new TSqlWhere<>(this);
    return this.where;
  }
}
