

# Http Client

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-http</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Http 客戶端, 預設採用 Java 提供的 HttpURLConnection 進行資源請求. 可以第三方 Http 客戶端實現, 不過 Enoa 尚未提供其他第三方實現. Http Client 所有提供的介面宗旨是儘量簡單易於理解的方式去呈現, 和現存於 Java 中的其他框架有很大的不同, 傳統的 Http Client 會需要進行很多變數的預設定, 例如 Header, Request Body 等等, 在 Enoa Http Client 中不需要, 一切都是鏈式 API, 無需外部定義, 無需瞭解很多類.
Http Client 實際上沒有完全的遵守 Http 協議, 最明顯的案例是 GET 請求允許 Body(raw) 的存在, 標準的 Http 協議中並不建議這麼使用, 有兩個因素導致了這個結果的存在, 首先, 按照標準確實是不建議 GET 請求支援 Body(raw), 但是, 協議本身其實是支援的, Enoa 只是原始體現而已; 其次, 部分 Http 介面其實是存在 GET 請求存放 Body 的情況, 著名的案例是 Elasticsearch

Elasticsearch Query <sup>[Source](https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-query.html)</sup>

```bash
curl -X GET "localhost:9200/_search" -H 'Content-Type: application/json' -d'
{
  "query" : {
    "term" : { "user" : "kimchy" }
  }
}
'
```

如果按照 Http 標準協議, 不允許 GET 請求包含 Body(raw), 則會導致後續工作無法開展. 但是不用擔心, 除了這些需要特殊處理的未全部遵守協議, 其他部分均按照標準 Http 協議進行 (Enoa Http Client 介面無法保證第三方實現是否同樣保持此特性).

## 使用方法

## GET

```java
HttpResponse resp = Http.request("https://httpbin.org/get")
  .method(HttpMethod.GET)
  .para("id", "1")
  .para("name", "Jack")
  .header("user-agent", "Mozilla/5.0 firefox 54")
  .header("origin", "http://localhost:100")
  .emit();
```

## POST

```java
HttpResponse resp = Http.request(EoUrl.with("https://httpbin.org").subpath("post").para("id", 1))
  .method(HttpMethod.POST)
  .para("name", "Jack")
  .cookie("SESSID", "session")
  .emit();
```

EoUrl 中的 para 方法新增的引數將會以 GET 形式傳送, 而 Http 中的 para 方法新增的引數會以 POST 傳送.

## PUT

```java
HttpResponse resp = Http.request(HttpMethod.PUT, EoUrl.with("https://httpbin.org").subpath("put"))
  .para("id", 1)
  .para("name", "Jack")
  .emit();
```

## RAW

```java
HttpResponse resp = Http.request(HttpMethod.POST, EoUrl.with("https://httpbin.org/post?id=1"))
  .raw("{\"name\": \"Jack\"}", "application/json")
  .para("ts", 1537713494767)
  .emit();
```

para 方法新增的引數將會加入到 GET 引數中


## UPLOAD

```java
HttpResponse resp = Http.request("https://httpbin.org/post?id=1")
  .method(HttpMethod.POST)
  .para("name", "Jack")
  .para("file", Paths.get(EnvKit.string("os.name").toLowerCase().contains("win") ? "c:\\windows\\system32\\cmd.exe" : "/usr/bin/ls"))
  .emit();
```

URL 中引數以 GET 方式傳送, Http para 方法引數以 `multipart/form-data` 傳送.

## BINARY

```java
HttpResponse resp = Http.request(EoUrl.with("https://httpbin.org").subpath("post"))
  .method(HttpMethod.POST)
  .contentType("application/octet-stream")
  .para("id", 1)
  .para("name", "Jack")
  .binary("Hello World".getBytes())
  .emit();
```

binary 模式, para 引數將會以 GET 形式請求

## ARRAY

```java
HttpResponse resp = Http.request(HttpMethod.POST, "https://httpbin.org/post?id=1")
  .traditional(false)
  .para("array", new int[]{0, 1, 2})
  .emit();
```

依據請求型別不同, GET 將會把引數放入在 Url 中, 而其他則在 Request Body 中, traditional 是否傳統模式, 如果設定為 false, 則會在引數名增加 \[].
例如這個請求, 實際請求的資料為:
array\[]=0&array\[]=1&array\[]=2

## AUTH

```java
HttpResponse resp = Http.request("https://httpbin.org/get")
  .para("id", "1")
  .auth(new BasicAuth("user", "passwd"))
// .auth(new BearerAuth("token"))
// .auth(new OAuth2("token"))
  .emit();
```

受支援的授權見 [auth](https://github.com/fewensa/enoa/tree/master/enoa-http/src/main/java/io/enoa/http/auth), 如果有特殊的授權方式, 請實現 [HttpAuth](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/HttpAuth.java "HttpAuth.java") 介面.


## PROXY

```java
HttpResponse resp = Http.request("http://cip.cc")
  .proxy(new SocketProxy("127.0.0.1", 2310))
  .header("user-agent", "curl/7.57.0")
  .emit();
```

受支援的代理方式 [TcpProxy](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/proxy/TcpProxy.java "TcpProxy.java"), [SocketProxy](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/proxy/SocketProxy.java "SocketProxy.java"), 如果有特殊的代理方式, 請實現 [HttpProxy](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/proxy/HttpProxy.java "HttpProxy.java") 介面.


## ENQUEUE

```java
Http.request(EoUrl.with("https://httpbin.org").subpath("get"))
  .method(HttpMethod.GET)
  .enqueue()
  .ok(resp -> this.print("enqueue ok", resp))
  .error(resp -> this.print("enqueue fail", resp))
  .then(HttpResponse::body)
  .then(HttpResponseBody::bytes)
  .<byte[]>then(body -> EnoaBinary.create(body, EoConst.CHARSET).string())
  .<String>valid(TextKit::blankn)
  .<String>valid(data -> data.length() > 30)
  .execute(System.out::println)
  .fail(System.err::println)
  .capture(System.err::println)
  .always(() -> System.out.println("finally"));
```

非同步請求, 呼叫`enqueue` 方法將進入非同步模式, 關於非同步請求後的各個方法, 參照以下流程圖理解:

![Http Promise](https://raw.githubusercontent.com/iaceob/gallery/master/enoa/http-promise.svg?sanitize=true)


受限於流程圖的表達形式以及篇幅的問題, 該圖中有部分錯誤, capture 並非是最後一步檢查, 而是進入 enqueue 之後的任何一步錯誤都會進入到 capture 然後進入 always, 另外, 因為 execute 以及 fail 都是支援多個的, 因此也會一直檢查是否呼叫完畢, 才會進入到下一個流程.

該圖用文字可以表達為:

1. 首先根據響應的結果判斷狀態是否為 20x, 如果是, 呼叫 ok 否則呼叫 error(這裡需要注意的是 ok error 並非是必須的, 而且就算失敗也不會影響後面的方法呼叫, 僅僅是用來做資料標記等作用, 視實際情況看是否使用);
2. 接著判斷是否有 then 進行資料轉換, 如果有則逐個呼叫 then;
3. 判定是否有 valid, 如果有逐個呼叫 valid 進行資料校驗, 校驗完成並全部通過後進入 execute, 其中有任何一個校驗失敗將進入 fail; 如果沒有, 則直接進入 execute;
4. 無論前面的結果如何最後進入 always
5. capture 將會監控上面除了 always 以外的所有步驟, 是否丟擲異常, 並且最終會進入到 always

泛型注意:

```java
.then(HttpResponse::body) // type HttpResponseBody
.then(HttpResponseBody::bytes) // type byte[]
.<byte[]>then(body -> EnoaBinary.create(body, EoConst.CHARSET).string()) // type string
.<String>valid(TextKit::blankn)
.<String>valid(data -> data.length() > 30)
```

可以從上面的程式碼中看出, then 以及 valid 都需要指明泛型, 否則引數中將無法進行型別識別, 因為 then 的作用就是進行資料型別轉換, 當 then 將前一個數據的型別轉換了, 後面的每一步型別將會轉變為前一個 then 轉換後的型別, 直到最後一個 then

實際使用, 這些 Promise 方法, 並不是每個都需要, 一個比較常見的案例:

```java
Http.request(EoUrl.with("https://httpbin.org/get"))
  .enqueue()
  .<HttpResponse>then(resp -> resp.body().string())
  .<Kv>then(body -> Json.parse(body, Kv.class))
  .<Kv>execute(kv -> System.out.println(kv.string("url")))
  .capture(System.err::println)
  .always(() -> System.out.println("finally"));
```

## ENCODE

```java
Http.request(EoUrl.with("https://httpbin.org").subpath("get"))
  .method(HttpMethod.GET)
  .charset(Charset.forName("UTF-8"))
  .encode()
  .para("k0", "學")
  .para("k0", "あ")
  .para("k1", "A")
  .emit();
```

對 URL 以及引數進行編碼, 此處請求的實際連結為:

```text
https://httpbin.org/get?k0=%E5%AD%B8&k0=%E3%81%82&k1=A
```

## URL

```java
EoUrl url = EoUrl.with("https://example.com/enoa")
  .traditional(false)
  .encode()
  .subpath("manage")
  .subpath("user", "login")
  .para("name", "Jack")
  .para("password", "password")
  .para("array", 0)
  .para("array", "1")
  .para("array", new int[]{2, 3, 4, 5})
  .para("collection", new HashSet<Integer>() {{
    add(0);
    add(1);
    add(2);
  }});
```

Enoa 提供了快速建立 Url 的介面, EoUrl 的功能非常豐富, 上方程式碼最終將會生成的連結為:

```text
https://example.com/enoa/manage/user/login?array[]=2&array[]=3&array[]=4&array[]=5&collection[]=0&name=Jack&password=password&array[]=0&array[]=1&collection[]=2&collection[]=1
```

## config

請求設定專案, 超時時間等相關設定, Http 提供的介面 [EoHttpConfig](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/EoHttpConfig.java "EoHttpConfig.java") 僅有三項設定:

- connectionTimeout
- soTimeout
- debug

具體由實現者自己提供, 例如預設的 [HttpHelperConfig](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/provider/httphelper/HttpHelperConfig.java "HttpHelperConfig.java")

請求中如果要設定定義配置, 通過 `config` 方法即可

```java
Http.request("http://httpbin.org/get")
  .config(new HttpHelperConfig.Builder().connectionTimeout(100).build())
  .emit();
```

## HttpRequest

Http 中存在 HttpRequest 介面, 僅限於 Http 內部使用, 使用者通常不需要自己建立也無法直接維護 HttpRequest; 但是仍然有機會獲得 HttpRequest, 通過 [handler](#handler-ampamp-reporter) 即可. 不過無法對其進行修改, 只能夠查閱.

- version

  Http request version

- url

  請求連結

- method

  Http request method

- headers

  請求頭

- body

  請求體

- config

  請求相關配置, 包括超時時間等.

- charset

  字元編碼

- proxy

  代理資訊



## HttpResponse

HttpResponse 的方法相對較多.

- code

  Http response code

- version

  Http version

- ok

  Response code 在 20x 區間返回 true, 否則 false

- uri

  Request uri

- url

  Request url

- protocol

  HTTP or HTTPS

- host

  Request host

- charset

  字元編碼

- message

  Response message

- isRedirect

  響應是否重定向

- cookieNames

  所有 Cookie 名稱

- cookieObject

  獲取單個 Cookie Object

- cookie

  獲取單個 Cookie String 值

- headerNames

  所有 Response Header 名稱

- header

  獲取單個 Header 值

- headers

  獲取陣列 Header 值

- body

  響應體

- clear

  清除 Response


著重說明 body, body 方法, 返回的是 HttpResponseBody 物件, 該物件可以直接獲取響應體的 byte\[], 或者直接轉換為 string.

```java
HttpResponse resp = Http.with("https://httpbin.org/get").emit();
HttpResponseBody body = resp.body();
byte[] bytes = body.bytes(); // bytes body
String string = body.string(); // string body
```

## handler && reporter

Http 提供了 handler 以及 reporter 方法, handler 用於捕獲 request, reporter 用於捕獲 response, 這兩個方法都是非同步進行的. 在實際使用中, 可通過這兩個方法做額外的事情. 關於 reporter 需要注意的是, 如果外部呼叫了 response.clear() 或者是 reporter 中呼叫了 clear 都有可能會導致對方丟失 response 中的資料.

```java
Http.request(EoUrl.with("https://httpbin.org").subpath("get"))
  .handler(System.out::println) //async
  .reporter(System.out::println) // async
  .emit();
```

## Third party

Http 支援第三方 Http Client 框架, 但是 Enoa 中沒有進行實現, 如果想要使用第三方實現, 可通過實現 [EoHttp](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/Http.java "EoHttp.java") 介面, 來獲取一個 Http 例項.

e.g.

```java
Http.use(new HttpHelperProvider())
  .url(EoUrl.with("https://httpbin.org").subpath("get"))
  .emit();
```


