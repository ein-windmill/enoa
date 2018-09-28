


# Database

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-db</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Database 專案整合了現有的資料庫操作框架以及資料庫連線池, 使用時進行自由組合即可.


## 連線池

目前支援的連線池有:

- c3p0
- druid
- hikaricp

> 不同的連線池, 並非在同一個專案中, 而是單獨拆開使用的.


## c3p0

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-ds-c3p0</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
EnoaDs ds = new C3p0Provider().datasource(
  new C3p0Config.Builder()
    .driver("com.mysql.jdbc.Driver")
    .url("jdbc:mysql://localhost:3306/test")
    .user("root")
    .passwd("pwd")
    .build()
  );
DataSource dataSource = ds.open();
```

## druid

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-ds-druid</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
EnoaDs ds = new DruidProvider().datasource(
  new DruidConfig.Builder()
    .driver("com.mysql.jdbc.Driver")
    .url("jdbc:mysql://localhost:3306/test")
    .user("root")
    .passwd("pwd")
    .build()
  );
DataSource dataSource = ds.open();
```

## hikaricp

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-ds-hikaricp</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
EnoaDs ds = new HikariCpProvider().datasource(
  new HikariCpConfig.Builder()
    .driver("com.mysql.jdbc.Driver")
    .url("jdbc:mysql://localhost:3306/test")
    .user("root")
    .passwd("pwd")
    .build()
  );
DataSource dataSource = ds.open();
```

## 資料庫操作

資料庫操作目前支援:

- ActiveRecord
- BeetlSQL
- Mybatis
- Trydb

> 不同的資料庫操作框架, 並非在同一個專案中, 而是單獨拆開使用的.

:::warning
因各資料操作框架不同, 無法使用統一的特性, db 專案中沒有提供 epm() 方法進行初始化, 而是使用 EPMDb 類進行初始化.
:::

:::info
下方有關資料庫操作的程式碼, 篇幅原因, 資料庫連線池部分變數此處定義. 程式碼中不再體現
```java
EoDsFactory DS = new C3p0Provider();

EoDsConfig DSCONFIG = new C3p0Config.Builder()
  .driver("com.mysql.jdbc.Driver")
  .url("jdbc:mysql://localhost:3306/test")
  .user("root")
  .passwd("pwd")
  .build()
```
:::

## ActiveRecord

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-db-activerecord</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
EoDbConfig dbc1 = new ActiveRecordConfig.Builder()
  .baseSqlTemplatePath(PathKit.path("activerecord").toString())
  .sqlTemplate("template.sql")
  .dialect(new MysqlDialect())
  .ds(DS, DSCONFIG)
  .build();
EPMDb.install(new ActiveRecordProvider(), dbc1);
List<Record> rets1 = Db.find(Db.getSql("User.list"));
```

resources/activerecord/template.sql

```text
#namespace('User')
 #include('user.sql')
#end
```

resources/activerecord/user.sql

```text
#sql('list')
select * from t_user
#end
```


## BeetlSQL

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-db-beetlsql</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
EoDbConfig dbc1 = new BeetlSQLConfig.Builder()
  .load("/beetl")
  .ds(DS, DSCONFIG)
  .style(new MySqlStyle())
  .build();
EPMDb.install(new BeetlSQLProvider(), dbc1);
List<Map> rets = BeetlSQLKit.select("User.list", Map.class);
```

resources/beetl/User.md

```text
list
===
select * from t_user
```

## Mybatis

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-db-mybatis</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
EoDbConfig dbc1 = new MybatisConfig.Builder()
  .scan("io.enoa.db.mapper", BaseMapper.class)
  .mapper(PathKit.path("mybatis").toString())
  .suffix("xml")
  .ds(DS, DSCONFIG)
  .build();
EPMDb.install(new MybatisProvider(), dbc1);
List<Map> rets1 = MybatisKit.with(UserMapper.class).list();
```

BaseMapper.java

```java
public interface BaseMapper {
}
```

UserMapper.java

```java
public interface UserMapper extends BaseMapper {
  List<Map> list();
}
```

resources/mybatis/user.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "mybatis-3-mapper.dtd" >

<mapper namespace="io.enoa.db.mapper.UserMapper">
  <select id="list" resultType="java.util.Map">
    select * from t_user
  </select>
</mapper>
```

## Trydb

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-db-trydb</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
EoDbConfig dbc1 = new EoTrydbConfig.Builder()
  .debug(true)
  .dialect(new io.enoa.trydb.dialect.MysqlDialect())
  .showSql()
  .ds(DS, DSCONFIG)
  .build();
EPMDb.install(new TrydbProvider(), dbc1);
List<Kv> rets0 = Trydb.find("select * from t_user");
```

## 其他

除了 Enoa 提供的這些, 使用者也可以自己實現, DB 元件實現 `EoDbFactory` 介面, DS 元件實現 `EoDsFactory` 介面.
Enoa 的 DB 元件都支援多個數據源操作, 以 Mybatis 為例:

```java
EoDbConfig dbc1 = new MybatisConfig.Builder()
  .scan("io.enoa.db.mapper", BaseMapper.class)
  .mapper(PathKit.path("mybatis").toString())
  .suffix("xml")
  .ds(DS, DSCONFIG)
  .build();
EPMDb.install(new MybatisProvider(), dbc1);
List<Map> rets1 = MybatisKit.with(UserMapper.class).list();

EoDbConfig dbc2 = new MybatisConfig.Builder()
  .scan("io.enoa.db.mapper", BaseMapper.class)
  .mapper(PathKit.path("mybatis").toString())
  .suffix("xml")
  .name("pgsql")
  .ds(DS, DSCONFIG)
  .build();
EPMDb.install(new MybatisProvider(), dbc2);
List<Map> rets2 = MybatisKit.use("pgsql").with(UserMapper.class).list();
```

關鍵點在於建立 `EoDbConfig` 時, 給予的 name, 在使用時, 獲取相應的 name 即可.

關於 Datasource 一個 DB 操作框架的設定以及使用, Enoa 完全提供於原框架完全相同的配置進行, 沒有做任何改變. Enoa 所做的只是統一了各個框架以相同的方式呈現出來.

這裡列出的程式碼可在 [example-db](https://github.com/fewensa/enoa/tree/master/enoa-example/example-db) 取得.


