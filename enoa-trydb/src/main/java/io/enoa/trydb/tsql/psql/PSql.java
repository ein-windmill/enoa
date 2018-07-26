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
package io.enoa.trydb.tsql.psql;

import io.enoa.toolkit.text.PadKit;
import io.enoa.toolkit.text.TextKit;

import java.io.Serializable;

public class PSql implements Serializable {

  private String countSql;
  private String selectSql;

  private PSql(String countSql, String selectSql) {
    this.countSql = countSql;
    this.selectSql = selectSql;
  }

  public static PSql create(String countSql, String selectSql) {
    return new PSql(countSql, selectSql);
  }

  public String countSql() {
    return this.countSql;
  }

  public String selectSql() {
    return this.selectSql;
  }

  @Override
  public String toString() {
    String _sql0 = this.countSql.replace("\n", PadKit.rpad("\n", " ", 14));
    String _sql1 = this.selectSql.replace("\n", PadKit.rpad("\n", " ", 14));
    return TextKit.union("SQL_COUNT  => ", _sql0, '\n', "SQL_SELECT => ", _sql1);
  }
}
