


# Database

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-db</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Database 项目整合了现有的数据库操作框架以及数据库连接池, 使用时进行自由组合即可.


## 连接池

目前支持的连接池有:

- c3p0
- druid
- hikaricp

> 不同的连接池, 并非在同一个项目中, 而是单独拆开使用的.


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

## 数据库操作

数据库操作目前支持:

- ActiveRecord
- BeetlSQL
- Mybatis
- Trydb

> 不同的数据库操作框架, 并非在同一个项目中, 而是单独拆开使用的.

:::warning
因各数据操作框架不同, 无法使用统一的特性, db 项目中没有提供 epm() 方法进行初始化, 而是使用 EPMDb 类进行初始化.
:::

:::info
下方有关数据库操作的代码, 篇幅原因, 数据库连接池部分变量此处定义. 代码中不再体现
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

除了 Enoa 提供的这些, 使用者也可以自己实现, DB 组件实现 `EoDbFactory` 接口, DS 组件实现 `EoDsFactory` 接口.
Enoa 的 DB 组件都支持多个数据源操作, 以 Mybatis 为例:

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

关键点在于创建 `EoDbConfig` 时, 给予的 name, 在使用时, 获取相应的 name 即可.

关于 Datasource 一个 DB 操作框架的设定以及使用, Enoa 完全提供于原框架完全相同的配置进行, 没有做任何改变. Enoa 所做的只是统一了各个框架以相同的方式呈现出来.

这里列出的代码可在 [example-db](https://github.com/fewensa/enoa/tree/master/enoa-example/example-db) 取得.


