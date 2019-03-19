
# 渲染輔助工具

## Renderer

Yosart 提供的快速建立 Response 工具類. 可用此類快速呼叫 render 系列方法生成 Response 物件.

```java
Response response = Renderer.renderText("Hello World").end();
```


## Resp

Resp 繼承自 Renderer, 多提供更多 Response 物件操作的方法, header, cookie 等.

```java
Response response = Resp.stat(HttpStatus.OK)
  .header(new Header("Content-Type", "text"))
  .renderText("Hello World")
  .end();
```

