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
package io.enoa.provider.db.beetlsql;

import io.enoa.db.EnoaDb;
import io.enoa.db.EnoaDs;
import org.beetl.sql.core.*;

class _BeetlSQL extends EnoaDb {

  private BeetlSQLConfig config;
  private EnoaDs ds;

  _BeetlSQL(BeetlSQLConfig config) {
    this.config = config;
    this.ds = this.config.ds().dataSource(this.config.dsConfig());
  }

  @Override
  public void start() {
    ConnectionSource source = ConnectionSourceHelper.getSingle(this.ds.open());
//    DBStyle mysql = new MySqlStyle();
    // sql语句放在classpagth的/sql 目录下
    SQLLoader loader = new ClasspathLoader(this.config.load(), this.config.style());
    // 数据库命名跟java命名一样，所以采用DefaultNameConversion，还有一个是UnderlinedNameConversion，下划线风格的
//    UnderlinedNameConversion nc = new UnderlinedNameConversion();
    // 最后，创建一个SQLManager,DebugInterceptor 不是必须的，但可以通过它查看sql执行情况
    SQLManager sqlManager = new SQLManager(this.config.style(),
      loader, source, this.config.nameConversion(),
      this.config.interceptors());
    BeetlSQLKit.add(this.config.name(), sqlManager);
  }

  @Override
  public void stop() {
    this.ds.close();
  }
}
