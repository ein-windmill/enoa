
# Yosart

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-yosart</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

## 简介

Yosart 是基于 [Repeater](#Repeater) 的高级封装, 为 Repeater 提供了一个 [EoxAccessor](#EoxAccessor); Yosart 定位于 Web 框架. 依托于 Repeater, Yosart 也有着丰富的 Web 服务器支持, 所有支持 Rpeater 的 Web 服务器均可用于 Yosart 中.

Yosart 拥有完善的路由机制, 并且支持 RESTFul 风格 Api 定义. 简单易用的数据验证, 异常控制, 扩展以及插件的支持等.

关于 Yosart 的扩展以及插件, 你需要了解这两者的差别:

- 插件
  插件负责提供 Yosart 自身所没有的功能
- 扩展
  扩展用于完善 Yosart 内部运行功能


## 简单案例

在说明所有事项之前, 我们来先看一个最简单的案例, 了解下 Yosart 是如何使用的.

```java
Yosart.createServer()
  .handle(Method.GET, "/", req -> Resp.with(req).render("Hello World").end())
  .listen(9001);
```

