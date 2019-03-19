

# Solr

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>index-solr</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Solr 组件提供一个基于 Http api 的 Solr 客户端, 于官方提供的 SolrJ 相比, 使用更简单, 也完全符合 Solr Web 段完全相同的接口.

### 警告
> Solr 的每个接口都要求提供一个 Core name, 1.6 中的 EoSolr 接口, 将 core 的设置于 select update 放置于一起, 因此在使用的过程中可能会造成不良的使用习惯, 因此在下一个版本中, EoSolr 中剔除了 select 以及 update, 只有设置了 core 之后才可进行 select, update, 使用代码不会有太大的更改; 如果你使用了 http 方法来自定义设置一个 http 提供者, 那么就需要注意, 不要在 1.6 中调用了 core 之后再调用 http, 这在下一个版本中将不会受到支持, 因此需要先调用 http 在设置 core 即可在下次升级是不用作出改动.

## EPM

Solr 使用 EPM 进行管理, 并且支持多个实例.

```java
// install solr instance with name 'main'
Solr.epm().install(new SolrConfig.Builder()
  .host("http://localhost:7000/solr")
  .build());

// install solr instance with name 'other'
Solr.epm().install("other", new SolrConfig.Builder()
  .host("http://localhost:7000/solr")
  .build());
```

## USE

```java
Solr.use(); // get solr instance with name 'main'
Solr.use("main"); // get solr instance with name 'main'
Solr.use("other"); // get solr instance with name 'other'
```

## http

Solr 组件是一个基于 http 的客户端, http 使用的是 Enoa 提供的 http 组件, 默认使用 HttpURLConnection, 如果想要更改, 可调用 http 方法进行更改. 该方法要求提供一个 EoUrl, 后面会根据这个接口拿取一个可用的 Http 组件.

同时, 自定义 Http 还有另外一个作用, Http 组件提供了 [handler-ampamp-reporter](#handler-ampamp-reporter) 功能, 该功能可以用来打印请求以及响应的内容, 可以自定义一个 Http 用来输出请求 Solr 服务器, 以及从 Solr 服务器响应的内容, 进行调试.

e.g. of handler && reporter

```java
Solr.use().http(() -> Http.use()
  .handler(IHttpHandler.def())
  .reporter(System.out::println));
```

当加入这样的设置后, 请求 Solr 时, 会在控制台输出 Request 以及 Response 的原始信息.

## select

select 提供的方法名于 Solr web 控制台中的名称完成相同, 例如: `q`, `fq`, `rows` 等等, 只要用过 Solr 控制台, 那么对于使用此组件将会有很大的帮助.

### 获取响应内容

```java
String body = Solr.core("barcode")
  .select()
  .fq(Fq.create("name", "Abc"))
  .rows(2)
  .emit();
```

### 获取 HttpResponse

```java
HttpResponse resp = Solr.core("barcode")
  .select()
  .fq(Fq.create("name", "Abc"))
  .rows(2)
  .emit(resp -> resp);
```

### 消息体转换

```java
SRet<Barcode> ret = Solr.core("barcode")
  .select()
  .fq(Fq.create("name", "Abc"))
  .rows(2)
  .sort(Sort.create("ctime", OrderBy.DESC))
  .emit(SParser.json(Barcode.class));
```

### SParser

上方的案例中, 已经可以看出, 调用的方法于 Solr web 中提供的是完全相同的. 这里要说明一下 Sparser, 因为使用 Http 请求, emit 发送请求之后, 返回的是 `HttpResponse` 对象, 因此, emit 方法提供一个参数, 要求传入 SParser 接口, 用于对 HttpResponse 进行转换成目标对象, Solr 提供了一个简单的实现, SParser 有几个静态方法用于简单的还原.
当然 SParser 提供的数据转换不一定完全符合你的需求, 因此你可以自己创建一个转换类.

```java
.emit(resp -> resp.body().string()); // 将返回响应体
```

### 其他

默认的 emit 方法, 返回响应体, 但是在后续的版本中, 考虑到更加完善的控制, 会作出改变, 直接返回 HttpResponse, 如果当前版本有使用 `.emit()` 方法, 升级可通过 `.emit(resp -> resp.body().string())` 替换.

## update

```java
SRet<Void> ret = Solr.core("stest")
  .update()
  .overwrite(true)
  .commitWithin(1000)
  .wt(Wt.JSON)
  .body(Json.toJson(new ArrayList<Kv>(){{
    add(Kv.cretee().set("id", 1).set("name", "Jack"));
    add(Kv.create().set("id", 2).set("name", "Tom"));
  }}))
  .emit(SParser.json());
```

## 异步操作

Solr 组件支持异步操作, 基于 [Promise](#Promise), 使用 `DoneArgPromise` 因此受支持的方法包括:

- done
- capture
- always

### select enqueue

```java
Solr.core("barcode")
  .select()
  .fq(Fq.create("name", "Abc"))
  .rows(2)
  .enqueue()
  .done(System.out::println)
  .capture(Throwable::printStackTrace)
  .always(() -> System.out.println("always"));
```

### update enqueue

```java
Solr.core("stest")
  .update()
  .body(Json.toJson(new ArrayList<Kv>(){{
    add(Kv.cretee().set("id", 1).set("name", "Jack"));
    add(Kv.create().set("id", 2).set("name", "Tom"));
  }}))
  .enqueue()
  .done(System.out::println)
  .capture(Throwable::printStackTrace)
  .always(() -> System.out.println("always"));
```


