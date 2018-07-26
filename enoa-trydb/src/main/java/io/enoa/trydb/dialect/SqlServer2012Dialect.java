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
package io.enoa.trydb.dialect;

public class SqlServer2012Dialect implements IDialect {

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
    StringBuilder builder = new StringBuilder(psql);
    if (!psql.matches("(?i).* order by[^)]+$")) {
      builder.append(" order by current_timestamp");
    }
    builder.append(" offset ")
      .append(offset)
      .append(" rows fetch next ")
      .append(size)
      .append(" rows only ");
    return builder.toString();
  }
}
