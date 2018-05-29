package io.enoa.example.trydb.tsql;

import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.path.PathKit;
import io.enoa.trydb.dialect.PostgreSQLDialect;
import io.enoa.trydb.tsql.Trysql;
import io.enoa.trydb.tsql.generate.where.TSqlCond;
import io.enoa.trydb.tsql.generate.where.TSqlRelation;
import io.enoa.trydb.tsql.template.TSql;
import io.enoa.trydb.tsql.template.TSqlTemplate;
import io.enoa.trydb.tsql.template.enjoy.EnjoyTSqlTemplate;
import org.junit.Test;

public class TrysqlTest {

  @Test
  public void select() {
    String sql = Trysql.select()
      .dialect(new PostgreSQLDialect())
      .table("user")
      .column("id", "name")
      .column("age", "key")
      .where()
      .relation(TSqlRelation.AND)
      .cond("name", TSqlCond.LIKE, "Ja%")
      .cond("ctime", TSqlCond.GT, "2019-09-01")
      .cond("age", TSqlCond.LTE, 20)
      .end()
      .sql();

    System.out.println(sql);
  }

  @Test
  public void insert() {
    String sql = Trysql.insert()
      .table("user")
      .dialect(new PostgreSQLDialect())
      .multiple()
      .column("id", "name", "age")
      .value(1, "xiaoming", 23)
      .value(2, "xiaoqiang", 12)
      .sql();
    System.out.println(sql);
  }

  @Test
  public void update() {
    String sql = Trysql.update("user")
      .dialect(new PostgreSQLDialect())
      .set("name", "Tom'v")
      .set("age", 19)
      .where()
      .cond("ctime", TSqlCond.GT, "2019-01-02")
      .cond("stat", TSqlCond.IN, 1)
      .in("type", 1, 2, 3, null)
      .in("category", "C1", null, "C2")
      .isnull("col0")
      .isnotnull("col1")
      .end()
      .sql();
    System.out.println(sql);
  }

  @Test
  public void delete() {
    String sql = Trysql.delete()
      .table("user")
      .where()
      .cond("id", TSqlCond.EQ, 1)
      .end()
      .sql();
    System.out.println(sql);
  }


  @Test
  public void template() {
    TSqlTemplate template = new EnjoyTSqlTemplate(PathKit.debugPath().resolve("src/test/resources/sqls"), "template.sql", true);
    TSql sql = template.render("Binary.list", Kv.by("id", 2345678945678L));
    System.out.println(sql);
  }

}
