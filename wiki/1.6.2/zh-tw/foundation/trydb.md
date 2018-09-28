
# Trydb

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-trydb</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Trydb 是由 Enoa 所提供的一個數據庫操作框架, 小巧快捷, 支援 Sql 模板化管理, 支援非同步資料庫操作.
開發 Trydb 的原因是, 現有的部分資料庫操作框架, 有的配置繁瑣且效率不高, 部分提供的 API 相對較傳統或相對複雜過多冗餘; 先來看看 Trydb 如何使用.

## EPM

使用 Trydb 於其他 Enoa 的專案一樣, 提供一個數據庫操作的配置檔, 使用 epm 進行安裝即可.

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

TrydbConfig 是 Trydb 啟動的必須要素, 來看看其中的配置.

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

Trydb 也支援多個例項, 依據這裡的 name 進行區分, debug 是否開發者模式, level 事物級別, 於 JDBC 事物級別相同. 配置裡面需要注意的有三項, reporter, template 以及 showSql;

- reporter
  彙報配置, 這裡的彙報要求一個 `ISqlReporter` 介面, 此配置將會在提交資料庫之前將最終呼叫的 sql 語句以及引數呼叫此配置傳入的 `ISqlReporter` 介面
- template
  Trydb 支援從模板獲取 Sql, 這裡配置的便是模板的配置, 需要給一個 `Firetpl` 介面, 所有的 Sql 將會從 template 中獲取.
- showSql
  執行過程中是否列印 Sql, 實質上 showSql 會往 reporter 中寫入一個 Trydb 提供的預設彙報類 [_TrydbSqlReporter](https://github.com/fewensa/enoa/blob/master/enoa-trydb/src/main/java/io/enoa/trydb/_TrydbSqlReporter.java "_TrydbSqlReporter.java")


至於不同的例項使用, 根據 TrydbConfig 中提供的名稱:

```java
Trydb.use(); // use main
Trydb.use("pgsql"); // use pgsql
Trydb.template("pgsql"); use pgsql on template mode
```

## find

find 方法用於資料庫列表查詢, 返回 List<[Kv](#Kv)> 型別資料

```java
List<Kv> kvs = Trydb.find("select * from t_binary");
```

## first

first 方法, 查詢第一條記錄, 需要注意的是, first 返回的第一條是 Sql 語句返回後的第一條, 因此無論呼叫 Sql 語句後, 返回的是多少條記錄, 最終只取第一條; 建議在使用的過程中, 為了效能考慮, 可以在 Sql 中限定只返回一條記錄(例如在 Mysql 中使用 `limit 1`); 返回 Kv 型別資料.

```java
Kv kv = Trydb.first("select * from t_binary limit 1");
```

## beans

beans 方法於 find 方法類似, 返回的是 List 集合, 不同的是, beans 方法可以指定返回的資料型別將型別轉換為指定的實體類, 而不是 Kv.

```java
List<EBinary> beans = Trydb.beans("select * from t_binary", EBinary.class);
```

## bean

同樣的, bean 所對應的是 first 方法, 返回單條記錄的實體類.

```java
EBinary bean = Trydb.bean("select * from t_binary limit 1", EBinary.class);
```

## update

update 方法, 用於執行 Sql 中的 `update`, `delete`, `insert` 語句, 沒有單獨新增 delete, insert 是因為, delete 以及 insert 實際也是修改操作, 因此可通用 update 方法.
update 呼叫後會返回執行 Sql 後的資料影響行數.

:::warning
特別說明: update 方法用於執行 `insert` 語句時, 不支援獲取表的自增 ID 值. 為什麼?
[Why Auto Increment Is A Terrible Idea](https://www.clever-cloud.com/blog/engineering/2015/05/20/why-auto-increment-is-a-terrible-idea/)

可以替換的方案其實很多, 比如 [SnowflakeKit](#SnowflakeKit)
:::

```java
byte[] bytes = {0, 1, 3, 4, 5, 6, 3, 4, 5, 6, 3, 4, 5, 6};
Trydb.update("insert into t_binary (id, bin) values (?, ?)", 1, bytes);
Trydb.update("update t_binary set bin=? where id=?", new Object[]{bytes, 1});
Trydb.update("delete from t_binary where id=?", 1);
```

## pagekv

pagekv 用於資料分頁查詢, 並返回 Page\<Kv> 物件; pagekv 有四個引數:

1. pn
   當前頁碼
2. ps
   每頁資料量
3. sql
   sql
4. paras/map
   可選項, sql 佔位符處理, 支援陣列或 map 型, map 型只有用於模板時才可使用.

```java
Page<Kv> pkv0 = Trydb.pagekv(3, 1, "select * from t_binary");
```

來看一下 Page 物件. Page 中包含如下欄位

- pageNumber
  當前頁碼
- pageSize
  一頁資料大小
- totalPage
  總共多少頁
- offset
  分頁遊標偏移值
- totalRow
  總共多少資料
- rows
  當前頁資料

:::info
Page 物件是一個存在 Getter Setter 方法的物件, 上述的所有欄位均可通過 get set 方法進行設定; 是的, 這並不符合 Enoa 的習慣, 但這是一個妥協的結果, 因為 Page 物件將會有很高的機率用於和外部互動, 那麼如果不這麼做就有可能會導致無法解析的問題.
:::

## page

page 方法於 pagekv 相似, 同樣是進行分頁查詢, 不同的是支援自定義返回的資料型別.

```java
Page<EBinary> pkv3 = Trydb.page(3, 1, "select * from t_binary", EBinary.class);
```

## tx

事物管理. 使用者無需進行完備的事物操作程式碼, 只需要在呼叫 tx 方法是返回一個 boolean 值告知是否成功, 如果成功自動進行 commit 否則會進行 rollback;

```java
Trydb.tx(() -> Trydb.update("delete from t_binary where id=?", 1) != 0);
```

一個相對較複雜的案例 [TrydbTest](https://github.com/fewensa/enoa/blob/master/enoa-example/example-trydb/src/test/java/io/enoa/example/trydb/TrydbTest.java "TrydbTest.java"): 

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

elegant 模式為為了讓 Trydb 寫出優雅程式碼的模式, 其所擁有的功能於上述所說明的一樣的, 只是和 bean 相關的方法呼叫方式有所改變, 將實體 class 單體提取出來提前設定; 之所以有此模式, 是因為 Trydb 的方法均是靜態方法, 如果單獨設定會有執行緒安全問題, 此模式是以解決執行緒安全問題的優雅展現形式. 不過相應的, 效能肯定會低於正常使用的方式.

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

Trydb 藉助於 [Firetpl](#Firetpl) 實現基於模板的 Sql 管理策略, 可以將 Sql 剝離處 Java 程式碼使用檔案或者其他方式進行管理, 開啟 template 模式, 需要在啟動 Trydb 時配置 template, 提供一個 Firetpl 模板, 然後使用 `Trydb.template` 進入 template 模式即可.

```java
List<Kv> kvs = Trydb.template("main")
  .find("Binary.list", paras);
```

具體模板如何存放, 參照所使用 Firetpl 文件. Trydb 提供引數以 Map 傳送至 Firetpl, 如何使用, 也參照 Firetpl 文件.

## async

Trydb 支援非同步的資料庫操作, 並且支援 [Promise](#Promise), 呼叫 async 方法則會進入非同步模式.

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

Trydb 選用的 Promise 是 DoneArgPromise, 因此受支援的 Promise 方法有

- done
- capture
- always


:::warning
elegant 模式非同步需要了解, 因為建立 Promise 後, 使用 target 方法傳入的型別無法帶入到 Promise 泛型中, 導致 async 之後無法識別 target 的型別, 因此 elegant 非同步操作後無法正確識別泛型.
:::



## Trysql

Trydb 提供一個生成 Sql 的工具 [Trysql](https://github.com/fewensa/enoa/blob/master/enoa-trydb/src/main/java/io/enoa/trydb/tsql/Trysql.java "Trysql.java"), 可以使用方法生成 Sql 語句, 此外, 如果配置了 Firetpl, Trysql 還可以從模板中獲取 Sql.

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

然而, 必須要說明的是, 事實上 Trysql 還尚不完善, 生成的 Sql 可能不盡入人意, 此外, 這種生成方式寫出來的 Sql 可能比直接寫 Sql 語句還要複雜, 因此不建議使用. Trysql 之所以存在, 是為了呼叫 Firetpl 從模板中提取 Sql, 而其他的方法, select update 這些, 都是附加產品, 因此並不完全好用; 部分方法其實是被標記為 Deprecated.

