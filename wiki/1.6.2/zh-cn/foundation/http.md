

# Http Client

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-http</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Http 客户端, 默认采用 Java 提供的 HttpURLConnection 进行资源请求. 可以第三方 Http 客户端实现, 不过 Enoa 尚未提供其他第三方实现. Http Client 所有提供的接口宗旨是尽量简单易于理解的方式去呈现, 和现存于 Java 中的其他框架有很大的不同, 传统的 Http Client 会需要进行很多变量的预设置, 例如 Header, Request Body 等等, 在 Enoa Http Client 中不需要, 一切都是链式 API, 无需外部定义, 无需了解很多类.
Http Client 实际上没有完全的遵守 Http 协议, 最明显的案例是 GET 请求允许 Body(raw) 的存在, 标准的 Http 协议中并不建议这么使用, 有两个因素导致了这个结果的存在, 首先, 按照标准确实是不建议 GET 请求支持 Body(raw), 但是, 协议本身其实是支持的, Enoa 只是原始体现而已; 其次, 部分 Http 接口其实是存在 GET 请求存放 Body 的情况, 著名的案例是 Elasticsearch

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

如果按照 Http 标准协议, 不允许 GET 请求包含 Body(raw), 则会导致后续工作无法开展. 但是不用担心, 除了这些需要特殊处理的未全部遵守协议, 其他部分均按照标准 Http 协议进行 (Enoa Http Client 接口无法保证第三方实现是否同样保持此特性).

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

EoUrl 中的 para 方法添加的参数将会以 GET 形式传送, 而 Http 中的 para 方法添加的参数会以 POST 传送.

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

para 方法添加的参数将会加入到 GET 参数中


## UPLOAD

```java
HttpResponse resp = Http.request("https://httpbin.org/post?id=1")
  .method(HttpMethod.POST)
  .para("name", "Jack")
  .para("file", Paths.get(EnvKit.string("os.name").toLowerCase().contains("win") ? "c:\\windows\\system32\\cmd.exe" : "/usr/bin/ls"))
  .emit();
```

URL 中参数以 GET 方式传送, Http para 方法参数以 `multipart/form-data` 传送.

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

binary 模式, para 参数将会以 GET 形式请求

## ARRAY

```java
HttpResponse resp = Http.request(HttpMethod.POST, "https://httpbin.org/post?id=1")
  .traditional(false)
  .para("array", new int[]{0, 1, 2})
  .emit();
```

依据请求类型不同, GET 将会把参数放入在 Url 中, 而其他则在 Request Body 中, traditional 是否传统模式, 如果设置为 false, 则会在参数名增加 \[].
例如这个请求, 实际请求的数据为:
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

受支持的授权见 [auth](https://github.com/fewensa/enoa/tree/master/enoa-http/src/main/java/io/enoa/http/auth), 如果有特殊的授权方式, 请实现 [HttpAuth](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/HttpAuth.java "HttpAuth.java") 接口.


## PROXY

```java
HttpResponse resp = Http.request("http://cip.cc")
  .proxy(new SocketProxy("127.0.0.1", 2310))
  .header("user-agent", "curl/7.57.0")
  .emit();
```

受支持的代理方式 [TcpProxy](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/proxy/TcpProxy.java "TcpProxy.java"), [SocketProxy](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/proxy/SocketProxy.java "SocketProxy.java"), 如果有特殊的代理方式, 请实现 [HttpProxy](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/proxy/HttpProxy.java "HttpProxy.java") 接口.


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

异步请求, 调用`enqueue` 方法将进入异步模式, 关于异步请求后的各个方法, 参照以下流程图理解:

![Http Promise](https://raw.githubusercontent.com/iaceob/gallery/master/enoa/http-promise.svg?sanitize=true)


受限于流程图的表达形式以及篇幅的问题, 该图中有部分错误, capture 并非是最后一步检查, 而是进入 enqueue 之后的任何一步错误都会进入到 capture 然后进入 always, 另外, 因为 execute 以及 fail 都是支持多个的, 因此也会一直检查是否调用完毕, 才会进入到下一个流程.

该图用文字可以表达为:

1. 首先根据响应的结果判断状态是否为 20x, 如果是, 调用 ok 否则调用 error(这里需要注意的是 ok error 并非是必须的, 而且就算失败也不会影响后面的方法调用, 仅仅是用来做数据标记等作用, 视实际情况看是否使用);
2. 接着判断是否有 then 进行数据转换, 如果有则逐个调用 then;
3. 判定是否有 valid, 如果有逐个调用 valid 进行数据校验, 校验完成并全部通过后进入 execute, 其中有任何一个校验失败将进入 fail; 如果没有, 则直接进入 execute;
4. 无论前面的结果如何最后进入 always
5. capture 将会监控上面除了 always 以外的所有步骤, 是否抛出异常, 并且最终会进入到 always

泛型注意:

```java
.then(HttpResponse::body) // type HttpResponseBody
.then(HttpResponseBody::bytes) // type byte[]
.<byte[]>then(body -> EnoaBinary.create(body, EoConst.CHARSET).string()) // type string
.<String>valid(TextKit::blankn)
.<String>valid(data -> data.length() > 30)
```

可以从上面的代码中看出, then 以及 valid 都需要指明泛型, 否则参数中将无法进行类型识别, 因为 then 的作用就是进行数据类型转换, 当 then 将前一个数据的类型转换了, 后面的每一步类型将会转变为前一个 then 转换后的类型, 直到最后一个 then

实际使用, 这些 Promise 方法, 并不是每个都需要, 一个比较常见的案例:

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

对 URL 以及参数进行编码, 此处请求的实际链接为:

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

Enoa 提供了快速创建 Url 的接口, EoUrl 的功能非常丰富, 上方代码最终将会生成的链接为:

```text
https://example.com/enoa/manage/user/login?array[]=2&array[]=3&array[]=4&array[]=5&collection[]=0&name=Jack&password=password&array[]=0&array[]=1&collection[]=2&collection[]=1
```

## config

请求设置项目, 超时时间等相关设置, Http 提供的接口 [EoHttpConfig](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/EoHttpConfig.java "EoHttpConfig.java") 仅有三项设置:

- connectionTimeout
- soTimeout
- debug

具体由实现者自己提供, 例如默认的 [HttpHelperConfig](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/provider/httphelper/HttpHelperConfig.java "HttpHelperConfig.java")

请求中如果要设置定义配置, 通过 `config` 方法即可

```java
Http.request("http://httpbin.org/get")
  .config(new HttpHelperConfig.Builder().connectionTimeout(100).build())
  .emit();
```

## HttpRequest

Http 中存在 HttpRequest 接口, 仅限于 Http 内部使用, 使用者通常不需要自己创建也无法直接维护 HttpRequest; 但是仍然有机会获得 HttpRequest, 通过 [handler](#handler-ampamp-reporter) 即可. 不过无法对其进行修改, 只能够查阅.

- version

  Http request version

- url

  请求链接

- method

  Http request method

- headers

  请求头

- body

  请求体

- config

  请求相关配置, 包括超时时间等.

- charset

  字符编码

- proxy

  代理信息



## HttpResponse

HttpResponse 的方法相对较多.

- code

  Http response code

- version

  Http version

- ok

  Response code 在 20x 区间返回 true, 否则 false

- uri

  Request uri

- url

  Request url

- protocol

  HTTP or HTTPS

- host

  Request host

- charset

  字符编码

- message

  Response message

- isRedirect

  响应是否重定向

- cookieNames

  所有 Cookie 名称

- cookieObject

  获取单个 Cookie Object

- cookie

  获取单个 Cookie String 值

- headerNames

  所有 Response Header 名称

- header

  获取单个 Header 值

- headers

  获取数组 Header 值

- body

  响应体

- clear

  清除 Response


着重说明 body, body 方法, 返回的是 HttpResponseBody 对象, 该对象可以直接获取响应体的 byte\[], 或者直接转换为 string.

```java
HttpResponse resp = Http.with("https://httpbin.org/get").emit();
HttpResponseBody body = resp.body();
byte[] bytes = body.bytes(); // bytes body
String string = body.string(); // string body
```

## handler && reporter

Http 提供了 handler 以及 reporter 方法, handler 用于捕获 request, reporter 用于捕获 response, 这两个方法都是异步进行的. 在实际使用中, 可通过这两个方法做额外的事情. 关于 reporter 需要注意的是, 如果外部调用了 response.clear() 或者是 reporter 中调用了 clear 都有可能会导致对方丢失 response 中的数据.

```java
Http.request(EoUrl.with("https://httpbin.org").subpath("get"))
  .handler(System.out::println) //async
  .reporter(System.out::println) // async
  .emit();
```

## Third party

Http 支持第三方 Http Client 框架, 但是 Enoa 中没有进行实现, 如果想要使用第三方实现, 可通过实现 [EoHttp](https://github.com/fewensa/enoa/blob/master/enoa-http/src/main/java/io/enoa/http/Http.java "EoHttp.java") 接口, 来获取一个 Http 实例.

e.g.

```java
Http.use(new HttpHelperProvider())
  .url(EoUrl.with("https://httpbin.org").subpath("get"))
  .emit();
```


