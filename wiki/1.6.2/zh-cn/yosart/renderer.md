
# 渲染辅助工具

## Renderer

Yosart 提供的快速创建 Response 工具类. 可用此类快速调用 render 系列方法生成 Response 对象.

```java
Response response = Renderer.renderText("Hello World").end();
```


## Resp

Resp 继承自 Renderer, 多提供更多 Response 对象操作的方法, header, cookie 等.

```java
Response response = Resp.stat(HttpStatus.OK)
  .header(new Header("Content-Type", "text"))
  .renderText("Hello World")
  .end();
```

