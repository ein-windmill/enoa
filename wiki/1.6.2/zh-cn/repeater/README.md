
# Repeater

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-repeater</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

## 简介

在说明 Repeater 之前, 来先看一下 Repeater 的结构图:

![Repeater](https://raw.githubusercontent.com/iaceob/gallery/master/enoa/repeater.svg?sanitize=true)

简单说, Repeater 就是一个 Web 服务器中继器.

那么详细分析下 Repeater 的原理以及实现方式; 首先来理解下中继器的概念, 我们都知道有很多 Web 服务器软件, 像是 Java 中被大家熟知的 Tomcat, Jetty 等, 我们管这些叫 Web 服务器(容器). Repeater 所做的事情就是消除这些 Web 服务器的差异化, 打造统一的接口. 这就是中继器的概念.
当我们理解了中继器的概念之后, 再来看上面的结构图. 第一个方块, Repeater 这里准确的来说应该是 Request. 当一个请求发起到 Repeater 之后, 进入到第二行 `RepeaterServerFactory`, 这是一个 Web 服务器工厂, 再往下看到红色的 `NanoHttpd` 以及 `Jetty`, 这是 Web 服务器的实现者, 继续往下, 不同的实现者都会提供一个翻译器 `RepeaterTranslateFactory`, 将 Request 翻译成 Repeater Request, 接下来就是调用 `EoxAccessor` 接口, 此接口便是 Repeater 提供给使用者的接口, 使用者实现此接口进行 Request 的实际操作, 并返回 Repeater Response, 最后, 再次调用翻译器将 Response 还原成 Web 服务器所识别的 Response.

文字可能会乱乱的, 实际上, 你无需担心, 因为要使用 Repeater, 只需要提供一个 `EoxAccessor` 的实现类给 Repeater 即可, 其他的均有 Repeater 搞定.

## 创建服务器

来看一个比较完整的案例, 看下 Repeater 如何运行

```java
EoxConfig config = new EoxConfig.Builder()
  .debug(true)
  .info(true)
  .infoUseLog(false)
  .context("/example")
  .tmp(PathKit.debugPath().resolve("tmp"))
  .build();

Repeater.createServer(new FastCGIProvider())
  .config(config)
  .log(new Slf4jProvider())
  .capture(EoxErrorRenderFactory.def())
  .accessor(request -> new Response.Builder()
    .header("Content-Type", "text/html")
    .body(ResponseBody.create("Hello World"))
    .build())
  .listen(9001);
```

> 这里多了一个 log 方法, 这是宣告 Repeater 中所使用什么 Log 框架进行日志打印.
