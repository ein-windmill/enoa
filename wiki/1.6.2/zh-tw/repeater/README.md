
# Repeater

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-repeater</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

## 簡介

在說明 Repeater 之前, 來先看一下 Repeater 的結構圖:

![Repeater](https://hackmd.0u0.me/uploads/upload_248f7643211af94ea3ed44ba01d2c0c6.svg)

簡單說, Repeater 就是一個 Web 伺服器中繼器.

那麼詳細分析下 Repeater 的原理以及實現方式; 首先來理解下中繼器的概念, 我們都知道有很多 Web 伺服器軟體, 像是 Java 中被大家熟知的 Tomcat, Jetty 等, 我們管這些叫 Web 伺服器(容器). Repeater 所做的事情就是消除這些 Web 伺服器的差異化, 打造統一的介面. 這就是中繼器的概念.
當我們理解了中繼器的概念之後, 再來看上面的結構圖. 第一個方塊, Repeater 這裡準確的來說應該是 Request. 當一個請求發起到 Repeater 之後, 進入到第二行 `RepeaterServerFactory`, 這是一個 Web 伺服器工廠, 再往下看到紅色的 `NanoHttpd` 以及 `Jetty`, 這是 Web 伺服器的實現者, 繼續往下, 不同的實現者都會提供一個翻譯器 `RepeaterTranslateFactory`, 將 Request 翻譯成 Repeater Request, 接下來就是呼叫 `EoxAccessor` 介面, 此介面便是 Repeater 提供給使用者的介面, 使用者實現此介面進行 Request 的實際操作, 並返回 Repeater Response, 最後, 再次呼叫翻譯器將 Response 還原成 Web 伺服器所識別的 Response.

文字可能會亂亂的, 實際上, 你無需擔心, 因為要使用 Repeater, 只需要提供一個 `EoxAccessor` 的實現類給 Repeater 即可, 其他的均有 Repeater 搞定.

## 建立伺服器

來看一個比較完整的案例, 看下 Repeater 如何執行

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

> 這裡多了一個 log 方法, 這是宣告 Repeater 中所使用什麼 Log 框架進行日誌列印.
