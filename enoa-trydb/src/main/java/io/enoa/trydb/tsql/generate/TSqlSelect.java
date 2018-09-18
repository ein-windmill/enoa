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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * sql 构建 select 语句
 * <p>
 * Deprecated 这是一个尚未完全完善的类, 后续可能会作出改动, 因此当前使用可能会导致
 * 后续版本升级不兼容问题.
 */
@Deprecated
public class TSqlSelect implements Trysql<TSqlSelect> {

  private String table;
  private IDialect dialect;
  private List<String> columns;
  private TSqlWhere<TSqlSelect> where;

  public TSqlSelect(String table) {
    this.table = table;
  }

  public TSqlSelect() {
  }

  @Override
  public TSqlSelect dialect(IDialect dialect) {
    this.dialect = dialect;
    return this;
  }

  @Override
  public String sql() {
    if (this.table == null)
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_table_null"));
    StringBuilder sb = new StringBuilder();
    sb.append("select ");
    if (this.columns == null) {
      sb.append("* ");
    } else {
      for (int i = 0; i < this.columns.size(); i++) {
        sb.append(TSqlCorrect.columnName(this.dialect, this.columns.get(i)));
        sb.append(i + 1 < this.columns.size() ? ", " : " ");
      }
    }
    sb.append("from ").append(this.table);
    if (this.where != null) {
      sb.append("\nwhere ")
        .append(this.where.sql());
    }
    sb.append(";");
    return sb.toString();
  }

  @Override
  public String toString() {
    return this.sql();
  }

  public TSqlSelect table(String table) {
    this.table = table;
    return this;
  }

  public TSqlSelect column(String col) {
    if (this.columns == null)
      this.columns = new ArrayList<>();
    this.columns.add(col);
    return this;
  }

  public TSqlSelect column(String... cols) {
    if (cols == null)
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_cols_null"));
    for (String col : cols) {
      this.column(col);
    }
    return this;
  }

  public TSqlSelect column(Collection<String> cols) {
    if (cols == null)
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_cols_null"));
    cols.forEach(this::column);
    return this;
  }

  /**
   * deprecated see TSqlWhere
   */
  @Deprecated
  public TSqlWhere<TSqlSelect> where() {
    if (this.where == null)
      this.where = new TSqlWhere<>(this);
    return this.where;
  }
}
