
# Gateway

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-gateway</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

## 介绍

Gateway 是一款基于 Repeater 的网关组件. 可以用在微服务架构中.


Gateway 的使用相对非常简单, 只需要提供相应的配置即可, 先来看一个最简单的案例.


```java
Gateway.createServer()
  .provider(new NanoHTTPDProvider())
  .auth(request -> System.out.print("auth"))
  .mapping("/", "http://localhost:2000")
  .mapping("/account", "http://localhost;2001")
  .mapping("/admin", "http://localhost:20002")
  .listen(9103);
```

使用 mapping 方法提供映射, 匹配到响应的 URI 则会进入到对应的服务上. auth 方法用于全局的验证, 请求是否有效, 单个 mapping 也支持独立的授权验证.


