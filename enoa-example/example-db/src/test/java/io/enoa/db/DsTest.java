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
package io.enoa.db;

import io.enoa.db.provider.ds.c3p0.C3p0Config;
import io.enoa.db.provider.ds.c3p0.C3p0Provider;
import io.enoa.log.kit.LogKit;
import org.junit.Test;

import javax.sql.DataSource;

public class DsTest {

  @Test
  public void tstDs0() {
    try {
      EnoaDs ds = new C3p0Provider().dataSource(
        new C3p0Config.Builder().driver("com.mysql.jdbc.Driver").url("jdbc://xxx").user("root").passwd("pwd").build()
      );
      DataSource dataSource = ds.open();
    } catch (Exception e) {
      LogKit.error(e.getMessage(), e);
    }
  }

  @Test
  public void testDs1() {
    try {
      EnoaDs ds = new C3p0Provider().dataSource(new EoDsConfig() {
        @Override
        public String driver() {
          return "com.mysql.jdbc.Driver";
        }

        @Override
        public String name() {
          return "root";
        }

        @Override
        public String url() {
          return "jdbc://xxx";
        }

        @Override
        public String user() {
          return "root";
        }

        @Override
        public String passwd() {
          return "pwd";
        }
      });
      DataSource dataSource = ds.open();
    } catch (Exception e) {
      LogKit.error(e.getMessage(), e);
    }
  }

}
