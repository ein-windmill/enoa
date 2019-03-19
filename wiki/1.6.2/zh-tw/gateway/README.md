
# Gateway

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-gateway</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

## 介紹

Gateway 是一款基於 Repeater 的閘道器元件. 可以用在微服務架構中.


Gateway 的使用相對非常簡單, 只需要提供相應的配置即可, 先來看一個最簡單的案例.


```java
Gateway.createServer()
  .provider(new NanoHTTPDProvider())
  .auth(request -> System.out.print("auth"))
  .mapping("/", "http://localhost:2000")
  .mapping("/account", "http://localhost;2001")
  .mapping("/admin", "http://localhost:20002")
  .listen(9103);
```

使用 mapping 方法提供對映, 匹配到響應的 URI 則會進入到對應的服務上. auth 方法用於全域性的驗證, 請求是否有效, 單個 mapping 也支援獨立的授權驗證.


