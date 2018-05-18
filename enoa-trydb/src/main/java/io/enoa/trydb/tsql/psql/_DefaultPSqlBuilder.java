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
package io.enoa.trydb.tsql.psql;

import io.enoa.toolkit.text.TextKit;

import java.util.regex.Pattern;

class _DefaultPSqlBuilder {

  private static final int START = "select ".length();

  private static class Holder {
    private static final _DefaultPSqlBuilder INSTANCE = new _DefaultPSqlBuilder();
    private static final Pattern ORDER_BY_PATTERN = Pattern.compile(
      "order\\s+by\\s+[^,\\s]+(\\s+asc|\\s+desc)?(\\s*,\\s*[^,\\s]+(\\s+asc|\\s+desc)?)*",
      Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private static char[] CHAR_TABLE = buildCharTable();

    private static char[] buildCharTable() {
      char[] ret = new char[128];
      for (char i = 128; i-- > 0; ) {
        ret[i] = 0;
      }

      ret['('] = '(';
      ret[')'] = ')';

      ret['f'] = 'f';
      ret['F'] = 'f';
      ret['r'] = 'r';
      ret['R'] = 'r';
      ret['o'] = 'o';
      ret['O'] = 'o';
      ret['m'] = 'm';
      ret['M'] = 'm';

      ret[' '] = ' ';
      ret['\r'] = ' ';
      ret['\n'] = ' ';
      ret['\t'] = ' ';
      return ret;
    }


    private static IPSql SUBQUERY = sql -> {
      String _sql0 = rmEndSymbol(sql);
      String _sql1 = TextKit.union("select count(1) from (", _sql0, ") as t");
      return PSql.create(_sql1, sql);
    };


    private static IPSql SQLFROM = sql -> {
      String _sql0 = rmEndSymbol(sql);
      int fix = fix(_sql0);
      if (fix == -1)
        return null;

      String _from = _sql0.substring(fix);
      _from = ORDER_BY_PATTERN.matcher(_from).replaceAll("");
      String _sql1 = TextKit.union("select count(1) ", _from);
      return PSql.create(_sql1, sql);
    };
  }

  private _DefaultPSqlBuilder() {
  }

  public static _DefaultPSqlBuilder instance() {
    return Holder.INSTANCE;
  }


  /**
   * 移除 sql 结尾 ; 符号
   *
   * @param sql sql
   * @return StringBuilder
   */
  private static String rmEndSymbol(String sql) {
    sql = Holder.ORDER_BY_PATTERN.matcher(sql).replaceAll("");
    return TextKit.removeRightChar(sql, ';');
//    StringBuilder _sql0 = new StringBuilder(sql);
//    boolean has = Boolean.FALSE;
//    for (int i = _sql0.length(); i-- > 0; ) {
//      char c = _sql0.charAt(i);
//      if (has) {
//        if (c == ';') {
//          _sql0.deleteCharAt(i);
//          continue;
//        }
//        break;
//      }
//      if (c != ';')
//        continue;
//      _sql0.deleteCharAt(i);
//      has = Boolean.TRUE;
//    }
//    return _sql0;
  }


  private static int fix(String sql) {
    int parenDepth = 0;
    char c;
    for (int i = START, end = sql.length() - 5; i < end; i++) {
      c = sql.charAt(i);
      if (c >= 128) {
        continue;
      }

      c = Holder.CHAR_TABLE[c];
      if (c == 0) {
        continue;
      }

      if (c == '(') {
        parenDepth++;
        continue;
      }

      if (c == ')') {
        if (parenDepth == 0) {
          throw new RuntimeException("Can not match left paren '(' for right paren ')': " + sql);
        }
        parenDepth--;
        continue;
      }
      if (parenDepth > 0) {
        continue;
      }

      if (c == 'f'
        && Holder.CHAR_TABLE[sql.charAt(i + 1)] == 'r'
        && Holder.CHAR_TABLE[sql.charAt(i + 2)] == 'o'
        && Holder.CHAR_TABLE[sql.charAt(i + 3)] == 'm') {
        c = sql.charAt(i + 4);
        if (Holder.CHAR_TABLE[c] == ' ' || c == '(') {
          c = sql.charAt(i - 1);
          if (Holder.CHAR_TABLE[c] == ' ' || c == ')') {
            return i;
          }
        }
      }
    }
    return -1;
  }

  IPSql subquery() {
    return Holder.SUBQUERY;
  }


  IPSql sqlfrom() {
    return Holder.SQLFROM;
  }
}
