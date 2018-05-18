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
package io.enoa.trydb.dialect;

public class SqlServerDialect implements IDialect {

  private static final String[] KEYWORDS = {
    "select", "update", "insert", "delete",
    "where", "order", "by", "asc", "desc",
    "primary", "key", "index", "create",
    "function", "table", "database",
    "group", "having", "limit", "int", "bool",
    "serialize", "varchar", "char", "bytea"
  };

  @Override
  public String identifierQuoteStringLeft() {
    return "[";
  }

  @Override
  public String identifierQuoteStringRight() {
    return "]";
  }

  @Override
  public String[] keywords() {
    return KEYWORDS;
  }

  @Override
  public String pageSql(long offset, int size, String psql) {

    /*
    with query as (
     select inq.*, row_number() over (order by current_timestamp) as rn from (
      select * from table
     ) as inq
    ) select * from query where rn between offset_start and offset_end
     */

    long _offset1 = offset + size - 1;
    psql = psql.replaceFirst("(?i)select(\\s+distinct\\s+)?", "$0 top(" + _offset1 + ") ");
    StringBuilder builder = new StringBuilder();
    builder.append("with query as (\n");
    builder.append("  select inq.*, row_number() over (order by current_timestamp) as rn from (\n");
    builder.append("   ").append(psql);
    builder.append("  ) as inq\n");
    builder.append(") select * from query where rn between ");
    builder.append(offset).append(" and ").append(_offset1);
    return null;
  }
}
