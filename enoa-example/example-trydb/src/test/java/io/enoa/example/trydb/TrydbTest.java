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
import io.enoa.json.Json;
import io.enoa.log.Log;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.path.PathKit;
import io.enoa.trydb.Trydb;
import io.enoa.trydb.TrydbBootstrap;
import io.enoa.trydb.TrydbConfig;
import io.enoa.trydb.dialect.PostgreSQLDialect;
import io.enoa.trydb.page.Page;
import io.enoa.trydb.tsql.Trysql;
import io.enoa.trydb.tsql.psql.IPSql;
import io.enoa.trydb.tsql.template.enjoy.EnjoyTSqlTemplate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
public class TrydbTest {

  @Before
  public void ibe() {
    TrydbConfig config = new TrydbConfig.Builder()
      .name("main")
      .debug(true)
      .ds(new DruidDs().ds())
      .showSql()
      .dialect(new PostgreSQLDialect())
      .template(new EnjoyTSqlTemplate(PathKit.debugPath().resolve("src/test/resources/sqls"), "template.sql", true))
      .build();
    TrydbBootstrap bootstrap = new TrydbBootstrap(config);
    bootstrap.start();
  }


  @Test
  public void find() {
    List<Kv> kvs = Trydb.find("select * from t_binary");
    Log.debug(Json.toJson(kvs));

    List<EBinary> beans = Trydb.beans("select * from t_binary", EBinary.class);
    Log.debug(Json.toJson(beans));

    List<EBinary> beans2 = Trydb.elegant()
      .target(EBinary.class)
      .beans("select * from t_binary");
    Log.debug(Json.toJson(beans2));
  }

  @Test
  public void first() {
    Kv kv = Trydb.first("select * from t_binary limit 1");
    Log.debug(Json.toJson(kv));

    EBinary bean = Trydb.bean("select * from t_binary limit 1", EBinary.class);
    Log.debug(Json.toJson(bean));

    EBinary bean1 = Trydb.elegant()
      .target(EBinary.class)
      .bean("select * from t_binary");
    Log.debug(Json.toJson(bean1));


    Trydb.first(Trysql.tsql("Binary.list"));
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
    Log.debug("TX RET: {}", ret);
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

  @Test
  public void template() {
    Map<String, Object> paras = new HashMap<>();
    List<Kv> kvs = Trydb.template("main")
      .find("Binary.list", paras);
    Log.debug(Json.toJson(kvs));
  }

  @Test
  public void page() {
    Page<Kv> pkv0 = Trydb.pagekv(3, 1, "select * from t_binary");
    System.out.println(pkv0);

    Page<Kv> pkv1 = Trydb.template().pagekv(3, 1, "Binary.page1", Kv.by("max", 3456789));
    System.out.println(pkv1);

    Page<Kv> pkv2 = Trydb.elegant()
      .target(Kv.class)
      .page(3, 1, "select * from t_binary");
    System.out.println(pkv2);

    Page<EBinary> pkv3 = Trydb.page(3, 1, "select * from t_binary", EBinary.class);
    System.out.println(pkv3);

    Page<EBinary> pkv4 = Trydb.template().page(3, 1, "Binary.page0", EBinary.class, 3456789);
    System.out.println(pkv4);

    Page<EBinary> pkv5 = Trydb.template().page(IPSql.subquery(), 3, 1, "Binary.page1", EBinary.class, Kv.by("max", 3456789));
    System.out.println(pkv5);

    Page<EBinary> pkv6 = Trydb.elegant()
      .target(EBinary.class)
      .page(3, 1, "select * from t_binary");
    System.out.println(pkv6);

    System.out.println();

  }

}
