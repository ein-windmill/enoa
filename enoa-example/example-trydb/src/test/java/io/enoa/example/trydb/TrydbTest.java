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
package io.enoa.example.trydb;

import io.enoa.example.trydb.entity.EBinary;
import io.enoa.json.kit.JsonKit;
import io.enoa.log.kit.LogKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.trydb.Trydb;
import io.enoa.trydb.TrydbBootstrap;
import io.enoa.trydb.TrydbConfig;
import io.enoa.trydb.dialect.PostgreDialect;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrydbTest {

  @Before
  public void ibe() {
    DruidDs druid = new DruidDs();
    DataSource ds = druid.ds();
    TrydbConfig config = new TrydbConfig.Builder()
      .name("main")
      .debug(true)
      .ds(ds)
      .dialect(new PostgreDialect())
      .build();
    TrydbBootstrap bootstrap = new TrydbBootstrap(config);
    bootstrap.start();
  }

  @Test
  public void find() {
    List<Kv> kvs = Trydb.find("select * from t_binary");
    LogKit.debug(JsonKit.toJson(kvs));

    List<EBinary> beans = Trydb.beans("select * from t_binary", EBinary.class);
    LogKit.debug(JsonKit.toJson(beans));

    List<EBinary> beans2 = Trydb.elegant()
      .target(EBinary.class)
      .beans("select * from t_binary");
    LogKit.debug(JsonKit.toJson(beans2));
  }

  @Test
  public void first() {
    Kv kv = Trydb.first("select * from t_binary limit 1");
    LogKit.debug(JsonKit.toJson(kv));

    EBinary bean = Trydb.bean("select * from t_binary limit 1", EBinary.class);
    LogKit.debug(JsonKit.toJson(bean));

    EBinary bean1 = Trydb.elegant()
      .target(EBinary.class)
      .bean("select * from t_binary");
    LogKit.debug(JsonKit.toJson(bean1));
  }

  @Test
  public void tx0() {
    this.first();
    this.updateBin(4);
    boolean ret = Trydb.tx(() -> {
      this.first();
      return this.tx1();
//      return true;
    });
    this.first();
    LogKit.debug("TX RET: {}", ret);
  }

  private boolean tx1() {
    this.first();
    return Trydb.tx(() -> {
      this.first();
      return this.updateBin(5);
    });
  }


  private boolean updateBin(int times) {
    byte[] bytes = {1, 3, 4, 5, 6, 3, 4, 5, 6, 3, 4, 5, 6, (byte) times};
    return Trydb.update("update t_binary set bin=? where id=1", new Object[]{bytes}) != 0;
  }

  @Before
  public void before() {
//    EnoaTSqlTemplateMgr.install();
  }

  @Test
  public void template() {
    Map<String, Object> paras = new HashMap<>();
    List<Kv> kvs = Trydb.template()
      .find("Test.find", paras);
    LogKit.debug(JsonKit.toJson(kvs));
  }

}
