# Introduction


Enoa 是一個體積輕巧的 Java 框架, 鑑於當下的多數 Java 框架, 使用上相對較繁瑣, 過多的配置檔案, 過多繁瑣的環境設定. 而 Enoa 則沒有這些.

Enoa 包含了眾多子專案, 從工具庫到 Web 框架以及閘道器, 大資料搜尋引擎的包裝等等, 為了更簡單的使用, Enoa 提供統一, 最簡單的 API 介面.

但是比較可惜的是, 因為專案之間功能分得很細導致組合使用時內部依賴相對較多, 這是一種取捨, 是選擇將所有功能組合在一個專案或者是將不同的功能區分開. 明顯的分開將是一個不錯的決定, 實際中並不一定會用到所有的功能, 僅僅是部分而已, Enoa 的所有模組都可以獨立使用到其他任何系統中. 儘管如此, Enoa 依然保持著輕巧的體積, 簡單易用的本質.

:::info
> 忽略:

事實上當你閱讀了文章之後, 你可能會覺得 Enoa 很多餘, 譬如 Json 我可以直接用 Jackson/Fastjson/Gson 就可以, 再如 Log 可以直接使用 Log4j/Logback 等等, 為什麼用 Enoa 提供的方案了.

是的, 就如你所想到的, 你完全可以直接使用, 而沒必要通過 Enoa, 然而你可以看下面的一些案例.

```java
// Jackson
ObjectMapper mapper = new ObjectMapper();
try {
  mapper.writeValueAsString(object);
} catch (JsonProcessingException e) {
  e.printStackTrace();
}

// Gson
Gson gson = new GsonBuilder().create();
gson.toJson(object);

// Fastjson
JSON.toJSONString(object);
```

不同的框架實現的方式並不相同, 這些就是差異. 當然, 你會說在工程開發中, 只會用一種, 不會產生混淆的問題.
沒錯, 但是不同的框架, 都有相應的學習成本, 如果未能完全掌握可能會在使用中發生諸多問題, 使用 Enoa 則不必擔心, 因為不管你使用的是哪一個, 這些都由 Enoa 去實現, 你只需要知道 Enoa 的介面是如何使用, 而最終的實現由 Enoa 去驅動.
此外, 來看看這個.

```java
StringUtils.isNull(object)
```

如果, 你的工程是以 Spring 為基礎的專案, 你可以看一下, 你的 IDE 會給你多少個 `StringUtils`. 再想以下, 如果是多人研發的團隊, 一整個專案裡面到底用了多少個不同包下面的 `StringUtils`.

以上, 只是 Enoa 存在的一部分目的. 另外一個重要的也是直接目的是為了簡化開發流程.
至於怎麼簡化, 現在請你的腦海裡想象一下, 你最熟悉的 Java 語言框架中建立一個 Web 服務的工程結構要怎麼搭建; 然後和下面的程式碼進行對比.

```java
Yosart.createServer()
  .handle(Method.GET, "/", (req) -> Resp.with(req).render("Hello world").end())
  .listen();
```

[example-yosart-simple](https://github.com/fewensa/enoa/blob/master/enoa-example/example-yosart-simple/src/main/java/io/enoa/example/yosart/simple/YosartSimpleBoot.java "example-yosart-simple")

好, 到此為止, 相信你已經發現了 Enoa 的諸多便利之處.

Enoa 的目的很簡單: 消除不同框架之間的歧義, 統一介面實現, 書寫簡單優美的程式碼.

:::

