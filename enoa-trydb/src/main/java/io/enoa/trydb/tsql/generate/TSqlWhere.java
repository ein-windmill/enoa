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
import io.enoa.trydb.tsql.generate.where.TSqlCond;
import io.enoa.trydb.tsql.generate.where.TSqlRelation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * sql 构建 where 区块
 * <p>
 * Deprecated 这是一个尚未完全完善的类, 后续可能会作出改动, 因此当前使用可能会导致
 * 后续版本升级不兼容问题.
 *
 * @param <T>
 */
@Deprecated
public class TSqlWhere<T extends Trysql> implements TSqlBlock<T> {

  private IDialect dialect;
  private Trysql origin;
  private List<Kv> conds;
  private TSqlRelation relation;
  private List<String> isnulls;
  private List<String> notnulls;

  public TSqlWhere(Trysql origin) {
    this.origin = origin;
  }

  @Override
  public T dialect(IDialect dialect) {
    this.dialect = dialect;
    return (T) this.origin;
  }

  @Override
  public T end() {
    return (T) this.origin;
  }

  public TSqlWhere<T> relation(TSqlRelation relation) {
    this.relation = relation;
    return this;
  }

  private int chooseType(TSqlCond cond) {
    switch (cond) {
      case IN:
        return 1;
//      case ISNULL:
//        return 2;
//      case NOTNULL:
//        return 3;
      default:
        return 0;
    }
  }

  private TSqlWhere<T> cond(String column, TSqlCond cond, Object value, int type) {
    if (this.conds == null)
      this.conds = new ArrayList<>();
    if (column == null)
      throw new TrysqlException(EnoaTipKit.message("eo.tip.trydb.tsql_cols_null"));
    this.conds.add(Kv.by("column", column).set("cond", cond).set("value", value).set("type", type));
    return this;
  }

  public TSqlWhere<T> cond(String column, TSqlCond cond, String value) {
    if (cond == TSqlCond.IN)
      this.in(column, value);
    int type = this.chooseType(cond);
    return this.cond(column, cond, value, type);
  }

  public TSqlWhere<T> cond(String column, TSqlCond cond, Number value) {
    if (cond == TSqlCond.IN)
      return this.in(column, value);
    int type = this.chooseType(cond);
    return this.cond(column, cond, value, type);
  }

  public TSqlWhere<T> in(String column, String... values) {
    return this.in(column, Arrays.asList(values));
  }

  public TSqlWhere<T> in(String column, Number... values) {
    return this.in(column, Arrays.asList(values));
  }

  public TSqlWhere<T> in(String column, Collection values) {
    return this.cond(column, TSqlCond.IN, values, 1);
  }

  public TSqlWhere<T> isnull(String column) {
    return this.cond(column, null, null, 2);
  }

  public TSqlWhere<T> isnotnull(String column) {
    return this.cond(column, null, null, 3);
  }

  String sql() {
    if (this.conds == null)
      return null;
    TSqlRelation relation = this.relation == null ? TSqlRelation.AND : this.relation;
    StringBuilder sb = new StringBuilder();
    int csz = this.conds.size();
    for (int i = 0; i < csz; i++) {
      Kv cond = this.conds.get(i);
      switch (cond.integer("type")) {
        /*
        sql in handle
         */
        case 1: {
          sb.append(TSqlCorrect.columnName(this.dialect, cond.string("column")))
            .append(" ")
            .append(((TSqlCond) cond.as("cond")).val());
          Collection v1 = cond.as("value");
          sb.append(" (");
          int a = 0, z = v1.size();
          for (Object aV1 : v1) {
            sb.append(TSqlCorrect.safeValue(aV1));
            if (a + 1 < z)
              sb.append(", ");
            a += 1;
          }
          sb.append(")");
          break;
        }
        /*
        sql is null handle
         */
        case 2: {
          sb.append(TSqlCorrect.columnName(this.dialect, cond.string("column"))).append(" is null");
          break;
        }
        /*
        sql is not null handle
         */
        case 3: {
          sb.append(TSqlCorrect.columnName(this.dialect, cond.string("column"))).append(" is not null");
          break;
        }
        /*
        0 and default is default cond
         */
//        case 0:
        default: {
          sb.append(TSqlCorrect.columnName(this.dialect, cond.string("column")))
            .append(" ")
            .append(((TSqlCond) cond.as("cond")).val());
          sb.append(" ").append(TSqlCorrect.safeValue(cond.as("value")));
          break;
        }
      }
      if (i + 1 < csz)
        sb.append(" ").append(relation.val()).append(" ");
    }
    return sb.toString();
  }
}
