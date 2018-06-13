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
package io.enoa.trydb;

import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.text.PadKit;
import io.enoa.toolkit.text.TextKit;

import java.util.Arrays;

class _TrydbSqlReporter implements ISqlReporter {

  private static class Holder {
    private static _TrydbSqlReporter INSTANCE = new _TrydbSqlReporter();
  }

  public static ISqlReporter instance() {
    return Holder.INSTANCE;
  }

  @Override
  public void report(String mark, String sql, Object[] paras) {
    sql = TextKit.removeRightChar(sql, '\n');
    sql = sql.replace("\n ", "\n");
    sql = sql.replace("\n", PadKit.rpad("\n", " ", 14));
    StringBuilder sb = new StringBuilder();
    sb.append("SQL   ").append('(').append(mark).append(")=> ")
      .append(sql);
    if (CollectionKit.notEmpty(paras)) {
      sb.append("\n");
      sb.append("PARAS ").append('(').append(mark).append(")=> ");
      sb.append(Arrays.toString(paras));
    }
    String _sql = sb.toString();
    System.out.println(_sql);
  }
}
