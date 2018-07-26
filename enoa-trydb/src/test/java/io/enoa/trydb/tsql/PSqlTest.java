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
package io.enoa.trydb.tsql;

import io.enoa.trydb.tsql.psql.IPSql;
import org.junit.Test;

public class PSqlTest {

  @Test
  public void testPSql() {
    String sql = "select (select id from t_user group by id order by id desc limit 1), name from t_user limit 1;";

    System.out.println(IPSql.subquery().psql(sql));
    System.out.println(IPSql.sqlfrom().psql(sql));

    sql = "select id, name from t_user as u\n" +
      "right join t_fio as i on u.id=i.uid\n" +
      "where i.lev>5\n" +
      "group by u.id, u.name\n" +
      "order by u.id desc, u.name";

    System.out.println(IPSql.subquery().psql(sql));
    System.out.println(IPSql.sqlfrom().psql(sql));

  }

}
