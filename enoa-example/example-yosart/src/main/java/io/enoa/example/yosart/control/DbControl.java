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
package io.enoa.example.yosart.control;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import io.enoa.db.provider.db.mybatis.MybatisKit;
import io.enoa.example.yosart.mapper.UserMapper;
import io.enoa.provider.db.beetlsql.BeetlSQLKit;
import io.enoa.yosart.kernel.resources.YoControl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DbControl extends YoControl<DbControl> {

  /**
   * @api {GET} /db/mybatis Mybatis test
   * @apiVersion 0.1.0
   * @apiName Mybatis test
   * @apiGroup db
   * @apiDescription Database mybatis plugin test
   */
  public void mybatis() {
    UserMapper user = MybatisKit.use("mybatis").with(UserMapper.class);
    List<Map> rets = user.list();
    super.renderJson(rets);
  }

  public void beetlsql() {
    List<Map> rets = BeetlSQLKit.use("beetlsql").select("User.list", Map.class);
    super.renderJson(rets);
  }

  public void activerecord() {
    List<Record> rets = Db.use("activerecord").find(Db.getSql("User.list"));
    super.renderJson(rets.stream().map(Record::getColumns).collect(Collectors.toList()));
  }

}
