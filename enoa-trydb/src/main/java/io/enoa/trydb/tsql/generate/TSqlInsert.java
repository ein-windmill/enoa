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
import io.enoa.trydb.dialect.IDialect;
import io.enoa.trydb.thr.TrysqlException;
import io.enoa.trydb.tsql.Trysql;

import java.util.*;

public class TSqlInsert implements Trysql<TSqlInsert> {

  private String table;
  private IDialect dialect;
  private List<String> columns;
  private List<Object> values;
  private List<Collection<Object>> multiValues;
  private boolean multiple = false;


  public TSqlInsert(String table) {
    this.table = table;
  }

  public TSqlInsert() {
  }

  @Override
  public TSqlInsert dialect(IDialect dialect) {
    if (this.dialect == null)
      this.dialect = dialect;
    return this;
  }

  @Override
  public String sql() {
    if (this.table == null)
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_table_null"));
    if (this.values == null && this.multiValues == null)
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_vals_null"));
    StringBuilder sb = new StringBuilder();
    sb.append("insert into ").append(this.table).append(" ");
    if (this.columns != null) {
      sb.append("(");
      int i = 0;
      for (String column : this.columns) {
        sb.append(TSqlCorrect.columnName(this.dialect, column));
        if (i + 1 < this.columns.size())
          sb.append(", ");
        i += 1;
      }
      sb.append(")");
      sb.append(" ");
    }
    sb.append("values ");
    sb.append(this.buildValues());
    return sb.toString();
  }

  private String buildValues() {
    StringBuilder sb = new StringBuilder();
    if (this.multiple) {
      int size = this.multiValues.size();
      for (int i = 0; i < size; i++) {
        Collection<Object> mvals = this.multiValues.get(i);
        sb.append("\n(");
        Iterator<Object> iterator = mvals.iterator();
        int j = 0;
        while (iterator.hasNext()) {
          sb.append(TSqlCorrect.safeValue(iterator.next()));
          if (j + 1 < mvals.size()) {
            sb.append(", ");
          }
          j += 1;
        }
        sb.append(")");
        sb.append(i + 1 < size ? "," : ";");
      }
      return sb.toString();
    }

    sb.append("(");
    int size = this.values.size();
    for (int i = 0; i < size; i++) {
      sb.append(TSqlCorrect.safeValue(this.values.get(i)));
      if (i + 1 < size)
        sb.append(", ");
    }
    sb.append(");");
    return sb.toString();
  }

  @Override
  public String toString() {
    return this.sql();
  }

  public TSqlInsert table(String table) {
    this.table = table;
    return this;
  }

  public TSqlInsert multiple() {
    this.multiple = true;
    return this;
  }

  public TSqlInsert column(String col) {
    if (this.columns == null)
      this.columns = new ArrayList<>();
    this.columns.add(col);
    return this;
  }

  public TSqlInsert column(String... cols) {
    if (cols == null)
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_cols_null"));
    for (String col : cols) {
      this.column(col);
    }
    return this;
  }

  public TSqlInsert column(Collection<String> cols) {
    if (cols == null)
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_cols_null"));
    cols.forEach(this::column);
    return this;
  }

  public TSqlInsert value(Object value) {
    if (this.values == null)
      this.values = new ArrayList<>();
    this.values.add(value);
    return this;
  }

  public TSqlInsert value(Object... values) {
    if (values == null)
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_vals_null"));
    if (this.multiple) {
      if (this.multiValues == null)
        this.multiValues = new ArrayList<>();
      this.multiValues.add(Arrays.asList(values));
    } else {
      for (Object value : values) {
        this.value(value);
      }
    }
    return this;
  }

  public TSqlInsert value(Collection<Object> values) {
    if (values == null)
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_vals_null"));
    if (this.multiple) {
      if (this.multiValues == null)
        this.multiValues = new ArrayList<>();
      this.multiValues.add(values);
    } else {
      values.forEach(this::value);
    }
    return this;
  }

}
