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
package io.enoa.db;

import io.enoa.db.mapper.BaseMapper;
import io.enoa.db.provider.db.mybatis.MybatisConfig;
import io.enoa.db.provider.db.mybatis.MybatisProvider;
import io.enoa.db.provider.ds.c3p0.C3p0Config;
import io.enoa.db.provider.ds.c3p0.C3p0Provider;
import io.enoa.log.Log;
import io.enoa.toolkit.path.PathKit;
import org.junit.Ignore;
import org.junit.Test;

public class DbTest {

  private void mybatis() {
    EoDbFactory<MybatisConfig> factory = new MybatisProvider();
    EnoaDb db = factory.db(new MybatisConfig.Builder()
        .name("main")
        .ds(new C3p0Provider(), new C3p0Config.Builder()
          .driver("com.mysql.jdbc.Driver")
          .url("jdbc://xxx")
          .user("root")
          .passwd("pwd").build())
        .scan("io.enoa.db.mapper", BaseMapper.class)
//      .mapper(PathKit.path().concat("/src/test/sqls/mybatis"))
        .mapper(PathKit.path("mybatis").toString())
        .suffix("xml")
        .build()
    );
    db.start();
  }

  @Test
  @Ignore
  public void testMybatis() {
    try {
      this.mybatis();
    } catch (Exception e) {
      Log.error(e.getMessage(), e);
    }
  }

}
