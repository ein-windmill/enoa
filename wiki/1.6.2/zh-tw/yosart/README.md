
# Yosart

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-yosart</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

## 簡介

Yosart 是基於 [Repeater](#Repeater) 的高階封裝, 為 Repeater 提供了一個 [EoxAccessor](#EoxAccessor); Yosart 定位於 Web 框架. 依託於 Repeater, Yosart 也有著豐富的 Web 伺服器支援, 所有支援 Rpeater 的 Web 伺服器均可用於 Yosart 中.

Yosart 擁有完善的路由機制, 並且支援 RESTFul 風格 Api 定義. 簡單易用的資料驗證, 異常控制, 擴充套件以及外掛的支援等.

關於 Yosart 的擴充套件以及外掛, 你需要了解這兩者的差別:

- 外掛
  外掛負責提供 Yosart 自身所沒有的功能
- 擴充套件
  擴充套件用於完善 Yosart 內部執行功能


## 簡單案例

在說明所有事項之前, 我們來先看一個最簡單的案例, 瞭解下 Yosart 是如何使用的.

```java
Yosart.createServer()
  .handle(Method.GET, "/", req -> Resp.with(req).render("Hello World").end())
  .listen(9001);
```

