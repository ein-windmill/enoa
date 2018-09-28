

# Solr

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>index-solr</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Solr 元件提供一個基於 Http api 的 Solr 客戶端, 於官方提供的 SolrJ 相比, 使用更簡單, 也完全符合 Solr Web 段完全相同的介面.

:::warning
部分介面注意: Solr 的沒個介面都要求提供一個 Core name, 1.6 中的 EoSolr 介面, 將 core 的設置於 select update 放置於一起, 因此在使用的過程中可能會造成不良的使用習慣, 因此在下一個版本中, EoSolr 中剔除了 select 以及 update, 只有設定了 core 之後才可進行 select, update, 使用程式碼不會有太大的更改; 如果你使用了 http 方法來自定義設定一個 http 提供者, 那麼就需要注意, 不要在 1.6 中呼叫了 core 之後再呼叫 http, 這在下一個版本中將不會受到支援, 因此需要先呼叫 http 在設定 core 即可在下次升級是不用作出改動.
:::

## EPM

Solr 使用 EPM 進行管理, 並且支援多個例項.

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

使用

```java
Solr.use(); // get solr instance with name 'main'
Solr.use("main"); // get solr instance with name 'main'
Solr.use("other"); // get solr instance with name 'other'
```

## http

Solr 元件是一個基於 http 的客戶端, http 使用的是 Enoa 提供的 http 元件, 預設使用 HttpURLConnection, 如果想要更改, 可呼叫 http 方法進行更改. 該方法要求提供一個 EoUrl, 後面會根據這個介面拿取一個可用的 Http 元件.

同時, 自定義 Http 還有另外一個作用, Http 元件提供了 [handler-ampamp-reporter](#handler-ampamp-reporter) 功能, 該功能可以用來列印請求以及響應的內容, 可以自定義一個 Http 用來輸出請求 Solr 伺服器, 以及從 Solr 伺服器響應的內容, 進行除錯.

e.g. of handler && reporter

```java
Solr.use().http(() -> Http.use()
  .handler(IHttpHandler.def())
  .reporter(System.out::println));
```

當加入這樣的設定後, 請求 Solr 時, 會在控制檯輸出 Request 以及 Response 的原始資訊.

## select

select 提供的方法名於 Solr web 控制檯中的名稱完成相同, 例如: `q`, `fq`, `rows` 等等, 只要用過 Solr 控制檯, 那麼對於使用此元件將會有很大的幫助.

### 獲取響應內容

```java
String body = Solr.core("barcode")
  .select()
  .fq(Fq.create("name", "Abc"))
  .rows(2)
  .emit();
```

### 獲取 HttpResponse

```java
HttpResponse resp = Solr.core("barcode")
  .select()
  .fq(Fq.create("name", "Abc"))
  .rows(2)
  .emit(resp -> resp);
```

### 訊息體轉換

```java
SRet<Barcode> ret = Solr.core("barcode")
  .select()
  .fq(Fq.create("name", "Abc"))
  .rows(2)
  .sort(Sort.create("ctime", OrderBy.DESC))
  .emit(SParser.json(Barcode.class));
```

### SParser

上方的案例中, 已經可以看出, 呼叫的方法於 Solr web 中提供的是完全相同的. 這裡要說明一下 Sparser, 因為使用 Http 請求, emit 傳送請求之後, 返回的是 `HttpResponse` 物件, 因此, emit 方法提供一個引數, 要求傳入 SParser 介面, 用於對 HttpResponse 進行轉換成目標物件, Solr 提供了一個簡單的實現, SParser 有幾個靜態方法用於簡單的還原.
當然 SParser 提供的資料轉換不一定完全符合你的需求, 因此你可以自己建立一個轉換類.

```java
.emit(resp -> resp.body().string()); // 將返回響應體
```

### 其他

預設的 emit 方法, 返回響應體, 但是在後續的版本中, 考慮到更加完善的控制, 會作出改變, 直接返回 HttpResponse, 如果當前版本有使用 `.emit()` 方法, 升級可通過 `.emit(resp -> resp.body().string())` 替換.

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

## 非同步操作

Solr 元件支援非同步操作, 基於 [Promise](#Promise), 使用 `DoneArgPromise` 因此受支援的方法包括:

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


