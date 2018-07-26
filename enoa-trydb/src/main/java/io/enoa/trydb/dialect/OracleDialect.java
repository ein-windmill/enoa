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

public class OracleDialect implements IDialect {

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
    return "\"";
  }

  @Override
  public String identifierQuoteStringRight() {
    return "\"";
  }

  @Override
  public String[] keywords() {
    return KEYWORDS;
  }

  @Override
  public String pageSql(long offset, int size, String psql) {
    /*
      SELECT * FROM (
       SELECT T1.*, ROWNUM rn FROM (
        select * from table
       ) AS T1
       WHERE ROWNUM < offset_end
      ) AS T0 WHERE T0.rn >= offset_start
     */
    long _offset1 = offset + size;
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT * FROM (\n")
      .append("  SELECT T1.*, ROWNUM AS rn FROM (\n")
      .append("   ").append(psql)
      .append("  ) AS T1\n")
      .append("  WHERE ROWNUM < ").append(_offset1).append("\n")
      .append(") AS T0 WHERE T0.rn >= ").append(offset);
    return builder.toString();
  }
}
