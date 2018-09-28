
# Trydb

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-trydb</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Trydb 是由 Enoa 所提供的一个数据库操作框架, 小巧快捷, 支持 Sql 模板化管理, 支持异步数据库操作.
开发 Trydb 的原因是, 现有的部分数据库操作框架, 有的配置繁琐且效率不高, 部分提供的 API 相对较传统或相对复杂过多冗余; 先来看看 Trydb 如何使用.

## EPM

使用 Trydb 于其他 Enoa 的项目一样, 提供一个数据库操作的配置档, 使用 epm 进行安装即可.

```java
// install trydb with name 'main'
TrydbConfig config = new TrydbConfig.Builder()
  .name("main")
  .debug(true)
  .ds(DS)
  .showSql()
  .dialect(new PostgreSQLDialect())
  .template(new EnjoyFiretpl(PathKit.debugPath().resolve("src/test/resources/sqls"), "template.sql", true))
  .build();
Trydb.epm().install(config);

// use main trydb
Trydb.use(); //
Trydb.use("main"); //
```

## TrydbConfig

TrydbConfig 是 Trydb 启动的必须要素, 来看看其中的配置.

| Field    | Must | Default   | Description   |
| -------- | ---- | --------- | ------------- |
| name     | No   | main      | Instance name |
| debug    | No   | false     | debug mode    |
| ds       | Yes  |           | datasource    |
| dialect  | Yes  |           | db dialect    |
| level    | Yes  | 4         | tx level      |
| reporter | No   |           | sql reporter  |
| namecase | No   | UNDERLINE | namecase      |
| template | No   |           | template sql  |

Trydb 也支持多个实例, 依据这里的 name 进行区分, debug 是否开发者模式, level 事物级别, 于 JDBC 事物级别相同. 配置里面需要注意的有三项, reporter, template 以及 showSql;

- reporter
  汇报配置, 这里的汇报要求一个 `ISqlReporter` 接口, 此配置将会在提交数据库之前将最终调用的 sql 语句以及参数调用此配置传入的 `ISqlReporter` 接口
- template
  Trydb 支持从模板获取 Sql, 这里配置的便是模板的配置, 需要给一个 `Firetpl` 接口, 所有的 Sql 将会从 template 中获取.
- showSql
  运行过程中是否打印 Sql, 实质上 showSql 会往 reporter 中写入一个 Trydb 提供的默认汇报类 [_TrydbSqlReporter](https://github.com/fewensa/enoa/blob/master/enoa-trydb/src/main/java/io/enoa/trydb/_TrydbSqlReporter.java "_TrydbSqlReporter.java")


至于不同的实例使用, 根据 TrydbConfig 中提供的名称:

```java
Trydb.use(); // use main
Trydb.use("pgsql"); // use pgsql
Trydb.template("pgsql"); use pgsql on template mode
```

## find

find 方法用于数据库列表查询, 返回 List<[Kv](#Kv)> 类型数据

```java
List<Kv> kvs = Trydb.find("select * from t_binary");
```

## first

first 方法, 查询第一条记录, 需要注意的是, first 返回的第一条是 Sql 语句返回后的第一条, 因此无论调用 Sql 语句后, 返回的是多少条记录, 最终只取第一条; 建议在使用的过程中, 为了效能考虑, 可以在 Sql 中限定只返回一条记录(例如在 Mysql 中使用 `limit 1`); 返回 Kv 类型数据.

```java
Kv kv = Trydb.first("select * from t_binary limit 1");
```

## beans

beans 方法于 find 方法类似, 返回的是 List 集合, 不同的是, beans 方法可以指定返回的数据类型将类型转换为指定的实体类, 而不是 Kv.

```java
List<EBinary> beans = Trydb.beans("select * from t_binary", EBinary.class);
```

## bean

同样的, bean 所对应的是 first 方法, 返回单条记录的实体类.

```java
EBinary bean = Trydb.bean("select * from t_binary limit 1", EBinary.class);
```

## update

update 方法, 用于执行 Sql 中的 `update`, `delete`, `insert` 语句, 没有单独添加 delete, insert 是因为, delete 以及 insert 实际也是修改操作, 因此可通用 update 方法.
update 调用后会返回执行 Sql 后的数据影响行数.

:::warning
特别说明: update 方法用于执行 `insert` 语句时, 不支持获取表的自增 ID 值. 为什么?
[Why Auto Increment Is A Terrible Idea](https://www.clever-cloud.com/blog/engineering/2015/05/20/why-auto-increment-is-a-terrible-idea/)

可以替换的方案其实很多, 比如 [SnowflakeKit](#SnowflakeKit)
:::

```java
byte[] bytes = {0, 1, 3, 4, 5, 6, 3, 4, 5, 6, 3, 4, 5, 6};
Trydb.update("insert into t_binary (id, bin) values (?, ?)", 1, bytes);
Trydb.update("update t_binary set bin=? where id=?", new Object[]{bytes, 1});
Trydb.update("delete from t_binary where id=?", 1);
```

## pagekv

pagekv 用于数据分页查询, 并返回 Page\<Kv> 对象; pagekv 有四个参数:

1. pn
   当前页码
2. ps
   每页数据量
3. sql
   sql
4. paras/map
   可选项, sql 占位符处理, 支持数组或 map 型, map 型只有用于模板时才可使用.

```java
Page<Kv> pkv0 = Trydb.pagekv(3, 1, "select * from t_binary");
```

来看一下 Page 对象. Page 中包含如下字段

- pageNumber
  当前页码
- pageSize
  一页数据大小
- totalPage
  总共多少页
- offset
  分页游标偏移值
- totalRow
  总共多少数据
- rows
  当前页数据

:::info
Page 对象是一个存在 Getter Setter 方法的对象, 上述的所有字段均可通过 get set 方法进行设定; 是的, 这并不符合 Enoa 的习惯, 但这是一个妥协的结果, 因为 Page 对象将会有很高的机率用于和外部交互, 那么如果不这么做就有可能会导致无法解析的问题.
:::

## page

page 方法于 pagekv 相似, 同样是进行分页查询, 不同的是支持自定义返回的数据类型.

```java
Page<EBinary> pkv3 = Trydb.page(3, 1, "select * from t_binary", EBinary.class);
```

## tx

事物管理. 使用者无需进行完备的事物操作代码, 只需要在调用 tx 方法是返回一个 boolean 值告知是否成功, 如果成功自动进行 commit 否则会进行 rollback;

```java
Trydb.tx(() -> Trydb.update("delete from t_binary where id=?", 1) != 0);
```

一个相对较复杂的案例 [TrydbTest](https://github.com/fewensa/enoa/blob/master/enoa-example/example-trydb/src/test/java/io/enoa/example/trydb/TrydbTest.java "TrydbTest.java"): 

```java
@Test
public void tx0() {
  this.first();
  this.updateBin(4);
  boolean ret = Trydb.tx(() -> {
    this.first();
    return this.tx1();
//    return true;
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

```

## elegant

elegant 模式为为了让 Trydb 写出优雅代码的模式, 其所拥有的功能于上述所说明的一样的, 只是和 bean 相关的方法调用方式有所改变, 将实体 class 单体提取出来提前设置; 之所以有此模式, 是因为 Trydb 的方法均是静态方法, 如果单独设置会有线程安全问题, 此模式是以解决线程安全问题的优雅展现形式. 不过相应的, 性能肯定会低于正常使用的方式.

```java
List<EBinary> beans2 = Trydb.elegant()
  .target(EBinary.class)
  .beans("select * from t_binary");
```

```java
EBinary bean1 = Trydb.elegant()
  .target(EBinary.class)
  .bean("select * from t_binary");
```

## template

Trydb 借助于 [Firetpl](#Firetpl) 实现基于模板的 Sql 管理策略, 可以将 Sql 剥离处 Java 代码使用文件或者其他方式进行管理, 开启 template 模式, 需要在启动 Trydb 时配置 template, 提供一个 Firetpl 模板, 然后使用 `Trydb.template` 进入 template 模式即可.

```java
List<Kv> kvs = Trydb.template("main")
  .find("Binary.list", paras);
```

具体模板如何存放, 参照所使用 Firetpl 文档. Trydb 提供参数以 Map 传送至 Firetpl, 如何使用, 也参照 Firetpl 文档.

## async

Trydb 支持异步的数据库操作, 并且支持 [Promise](#Promise), 调用 async 方法则会进入异步模式.

```java
Trydb.async()
  .find("select * from t_binary")
  .enqueue()
  .done(System.out::println)
  .capture(System.out::println)
  .always(() -> System.out.println("always"));

Trydb.async()
  .bean("select * from t_binary", EBinary.class)
  .enqueue()
  .done(System.out::println)
  .capture(System.out::println)
  .always(() -> System.out.println("always"));

Trydb.use()
  .async()
  .find("select * from t_binary")
  .enqueue()
  .done(System.out::println)
  .capture(System.out::println)
  .always(() -> System.out.println("always"));

Trydb.elegant()
  .async()
  .bean("select * from t_binary")
  .enqueue()
  .done(System.out::println)
  .capture(System.out::println)
  .always(() -> System.out.println("always"));

Trydb.template()
  .async()
  .find("Binary.list")
  .enqueue()
  .done(System.out::println)
  .capture(System.out::println)
  .always(() -> System.out.println("always"));
```

Trydb 选用的 Promise 是 DoneArgPromise, 因此受支持的 Promise 方法有

- done
- capture
- always


:::warning
elegant 模式异步需要了解, 因为创建 Promise 后, 使用 target 方法传入的类型无法带入到 Promise 泛型中, 导致 async 之后无法识别 target 的类型, 因此 elegant 异步操作后无法正确识别泛型.
:::



## Trysql

Trydb 提供一个生成 Sql 的工具 [Trysql](https://github.com/fewensa/enoa/blob/master/enoa-trydb/src/main/java/io/enoa/trydb/tsql/Trysql.java "Trysql.java"), 可以使用方法生成 Sql 语句, 此外, 如果配置了 Firetpl, Trysql 还可以从模板中获取 Sql.

e.g.

```java
/*
select id, name, age, "key" from user
where name like 'Ja%' and ctime > '2019-09-01' and age <= 20;
 */
Trysql.select()
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

/*
insert into user (id, name, age) values 
(1, 'xiaoming', 23),
(2, 'xiaoqiang', 12);
 */
Trysql.insert()
  .table("user")
  .dialect(new PostgreSQLDialect())
  .multiple()
  .column("id", "name", "age")
  .value(1, "xiaoming", 23)
  .value(2, "xiaoqiang", 12)
  .sql();

/*
update user set name='Tom\'v', age=19
where ctime > '2019-01-02' and stat in (1)
and type in (1, 2, 3, null)
and category in ('C1', null, 'C2') and col0 is null
and col1 is not null;
 */
Trysql.update("user")
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

/*
delete from user where id = 1;
 */
Trysql.delete()
  .table("user")
  .where()
  .cond("id", TSqlCond.EQ, 1)
  .end()
  .sql();

Trysql.tsql("main", "Binary.list");
```

然而, 必须要说明的是, 事实上 Trysql 还尚不完善, 生成的 Sql 可能不尽入人意, 此外, 这种生成方式写出来的 Sql 可能比直接写 Sql 语句还要复杂, 因此不建议使用. Trysql 之所以存在, 是为了调用 Firetpl 从模板中提取 Sql, 而其他的方法, select update 这些, 都是附加产品, 因此并不完全好用; 部分方法其实是被标记为 Deprecated.

