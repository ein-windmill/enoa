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

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import io.enoa.db.mapper.BaseMapper;
import io.enoa.db.mapper.UserMapper;
import io.enoa.db.provider.db.activerecord.ActiveRecordConfig;
import io.enoa.db.provider.db.activerecord.ActiveRecordProvider;
import io.enoa.db.provider.db.mybatis.MybatisConfig;
import io.enoa.db.provider.db.mybatis.MybatisKit;
import io.enoa.db.provider.db.mybatis.MybatisProvider;
import io.enoa.db.provider.db.trydb.EoTrydbConfig;
import io.enoa.db.provider.db.trydb.TrydbProvider;
import io.enoa.db.provider.ds.c3p0.C3p0Config;
import io.enoa.db.provider.ds.c3p0.C3p0Provider;
import io.enoa.db.provider.ds.druid.DruidConfig;
import io.enoa.db.provider.ds.druid.DruidProvider;
import io.enoa.db.provider.ds.hikaricp.HikariCpConfig;
import io.enoa.db.provider.ds.hikaricp.HikariCpProvider;
import io.enoa.json.Json;
import io.enoa.log.Log;
import io.enoa.provider.db.beetlsql.BeetlSQLConfig;
import io.enoa.provider.db.beetlsql.BeetlSQLKit;
import io.enoa.provider.db.beetlsql.BeetlSQLProvider;
import io.enoa.stove.firetpl.enjoy.EnjoyFiretpl;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.path.PathKit;
import io.enoa.trydb.Trydb;
import io.enoa.trydb.tsql.Trysql;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.core.db.PostgresStyle;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class EPMDbTest {

  private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/enoa?useUnicode=true&characterEncoding=utf-8&useSSL=false&nullNamePatternMatchesAll=true";
  private static final String URL_PGSQL = "jdbc:postgresql://localhost:5432/enoa";
  private static final String USER_MYSQL = "root";
  private static final String USER_PGSQL = "postgres";
  private static final String PASSWD = "passwd";

  private enum DS {
    DRUID_MYSQL(new DruidProvider(), new DruidConfig.Builder().name("mysql").url(URL_MYSQL).user(USER_MYSQL).passwd(PASSWD).build()),
    C3P0_MYSQL(new C3p0Provider(), new C3p0Config.Builder().name("mysql").url(URL_MYSQL).user(USER_MYSQL).passwd(PASSWD).build()),
    HIKARICP_MYSQL(new HikariCpProvider(), new HikariCpConfig.Builder().name("mysql").url(URL_MYSQL).user(USER_MYSQL).passwd(PASSWD).build()),

    DRUID_PGSQL(new DruidProvider(), new DruidConfig.Builder().name("pgsql").url(URL_PGSQL).user(USER_PGSQL).passwd(PASSWD).build()),
    C3P0_PGSQL(new C3p0Provider(), new C3p0Config.Builder().name("pgsql").url(URL_PGSQL).user(USER_PGSQL).passwd(PASSWD).build()),
    HIKARICP_PGSQL(new HikariCpProvider(), new HikariCpConfig.Builder().name("pgsql").url(URL_PGSQL).user(USER_PGSQL).passwd(PASSWD).build())
    //
    ;
    private final EoDsFactory ds;
    private final EoDsConfig config;

    DS(EoDsFactory ds, EoDsConfig config) {
      this.ds = ds;
      this.config = config;
    }

  }

  private void testBeetlSQL() {

    // mysql test
    EoDbConfig dbc1 = new BeetlSQLConfig.Builder()
      .load("/beetl")
      .ds(DS.DRUID_MYSQL.ds, DS.DRUID_MYSQL.config)
      .style(new MySqlStyle())
      .build();
    EPMDb.install(new BeetlSQLProvider(), dbc1);
    List<Map> rets1 = BeetlSQLKit.select("User.list", Map.class);


    // postgresql test
    EoDbConfig dbc2 = new BeetlSQLConfig.Builder()
      .name("pgsql")
      .load("/beetl")
      .ds(DS.C3P0_PGSQL.ds, DS.C3P0_PGSQL.config)
      .style(new PostgresStyle())
      .build();
    EPMDb.install(new BeetlSQLProvider(), dbc2);
    List<Map> rets2 = BeetlSQLKit.use("pgsql").select("User.list", Map.class);


    // print
    Log.debug(Json.toJson(rets1));
    Log.debug(Json.toJson(rets2));
  }

  private void testMybatis() {
    EoDbConfig dbc1 = new MybatisConfig.Builder()
      .scan("io.enoa.db.mapper", BaseMapper.class)
//      .mapper(PathKit.path().concat("/src/test/sqls/mybatis"))
      .mapper(PathKit.path("mybatis").toString())
      .suffix("xml")
      .ds(DS.HIKARICP_MYSQL.ds, DS.HIKARICP_MYSQL.config)
      .build();
    EPMDb.install(new MybatisProvider(), dbc1);
    List<Map> rets1 = MybatisKit.with(UserMapper.class).list();

    EoDbConfig dbc2 = new MybatisConfig.Builder()
      .scan("io.enoa.db.mapper", BaseMapper.class)
//      .mapper(PathKit.path().concat("/src/test/sqls/mybatis"))
      .mapper(PathKit.path("mybatis").toString())
      .suffix("xml")
      .name("pgsql")
      .ds(DS.DRUID_PGSQL.ds, DS.DRUID_PGSQL.config)
      .build();
    EPMDb.install(new MybatisProvider(), dbc2);
    List<Map> rets2 = MybatisKit.use("pgsql").with(UserMapper.class).list();

    // print
    Log.debug(Json.toJson(rets1));
    Log.debug(Json.toJson(rets2));
  }

  private void testActiveRecord() {
    EoDbConfig dbc1 = new ActiveRecordConfig.Builder()
//      .baseSqlTemplatePath(PathKit.path().concat("/src/test/sqls/activerecord"))
      .baseSqlTemplatePath(PathKit.path("activerecord").toString())
      .sqlTemplate("template.sql")
      .dialect(new MysqlDialect())
      .ds(DS.C3P0_MYSQL.ds, DS.C3P0_MYSQL.config)
      .build();
    EPMDb.install(new ActiveRecordProvider(), dbc1);
    List<Record> rets1 = Db.find(Db.getSql("User.list"));

    EoDbConfig dbc2 = new ActiveRecordConfig.Builder()
      .name("pgsql")
      .baseSqlTemplatePath(PathKit.path("activerecord").toString())
      .sqlTemplate("template.sql")
      .dialect(new MysqlDialect())
      .ds(DS.HIKARICP_PGSQL.ds, DS.HIKARICP_PGSQL.config)
      .build();
    EPMDb.install(new ActiveRecordProvider(), dbc2);
    List<Record> rets2 = Db.use("pgsql").find(Db.getSql("User.list"));


    Log.debug(Json.toJson(rets1));
    Log.debug(Json.toJson(rets2));
  }

  private void testTrydb() {
    EoDbConfig dbc1 = new EoTrydbConfig.Builder()
      .debug(true)
      .dialect(new io.enoa.trydb.dialect.MysqlDialect())
      .showSql()
      .ds(DS.C3P0_MYSQL.ds, DS.C3P0_MYSQL.config)
      .build();
    EPMDb.install(new TrydbProvider(), dbc1);
    List<Kv> rets0 = Trydb.find("select * from t_user");


    EoDbConfig dbc2 = new EoTrydbConfig.Builder()
      .name("pgsql")
      .debug(true)
      .dialect(new io.enoa.trydb.dialect.PostgreSQLDialect())
      .showSql()
      .ds(DS.DRUID_PGSQL.ds, DS.DRUID_PGSQL.config)
      .template(new EnjoyFiretpl(PathKit.path("trydb"), "template.sql"))
      .build();
    EPMDb.install(new TrydbProvider(), dbc2);
    List<Kv> rets2 = Trydb.template("pgsql").find("User.list");
    List<Kv> rets3 = Trydb.use("pgsql").find(Trysql.tsql("pgsql", "User.list"));

    System.out.println(rets0);
    System.out.println(rets2);
    System.out.println(rets3);

  }

  @Test
  @Ignore
  public void testStart() {
    try {
//      this.testBeetlSQL();
//      this.testMybatis();
//      this.testActiveRecord();
      this.testTrydb();
    } catch (Exception e) {
      Log.error(e.getMessage(), e);
    }
  }

}
