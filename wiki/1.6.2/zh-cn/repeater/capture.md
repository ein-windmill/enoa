

# capture

capture 方法可以用来监控 Repeater 的所有异常, 当我们的代码书写中抛出的异常最终都会被 capture 所拦截, 并且可以返回一些内容.

Repeater 提供了一个默认的异常拦截. [EoxErrorRenderImpl](https://github.com/fewensa/enoa/blob/master/enoa-repeater/src/main/java/io/enoa/repeater/factory/error/EoxErrorRenderImpl.java "EoxErrorRenderImpl.java"), 该类是不允许外部调用的, Repeater 提供了一个简单的用法: `EoxErrorRenderFactory.def()`

来看以下 EoxErrorRenderImpl 的代码:

```java
class EoxErrorRenderImpl implements EoxErrorRenderFactory {

  @Override
  public Response renderError(HttpStatus stat) {
    return new Response.Builder()
      .status(stat)
      .contentType("text/html")
      .body(ResponseBody.create(String.format("error %d", stat.code()), Repeater.config().charset()))
      .build();
  }

  @Override
  public Response renderError(HttpStatus stat, Throwable e) {
    if (e != null)
      Log.error(e.getMessage(), e);
    return new Response.Builder()
      .status(stat)
      .contentType("text/html")
      .body(ResponseBody.create(String.format("error %d <br> <pre>%s</pre>", stat.code(), ThrowableKit.string(e)),
        Repeater.config().charset()))
      .build();
  }

  @Override
  public Response renderError(HttpStatus stat, String message) {
    return this.renderError(stat);
  }

  @Override
  public Response renderError(HttpStatus stat, String message, Throwable e) {
    return this.renderError(stat, e);
  }
}
```

分别有多种情况的 renderError 方法, 不必担心这个, 这只是为了方便调用的地方能更灵活的运用; capture 最终返回一个 Response.

记住: Repeater 中所有异常都会被 capture 捕获, 并且返回 capture 中的 Response.


