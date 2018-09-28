

# capture

capture 方法可以用來監控 Repeater 的所有異常, 當我們的程式碼書寫中丟擲的異常最終都會被 capture 所攔截, 並且可以返回一些內容.

Repeater 提供了一個預設的異常攔截. [EoxErrorRenderImpl](https://github.com/fewensa/enoa/blob/master/enoa-repeater/src/main/java/io/enoa/repeater/factory/error/EoxErrorRenderImpl.java "EoxErrorRenderImpl.java"), 該類是不允許外部呼叫的, Repeater 提供了一個簡單的用法: `EoxErrorRenderFactory.def()`

來看以下 EoxErrorRenderImpl 的程式碼:

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

分別有多種情況的 renderError 方法, 不必擔心這個, 這只是為了方便呼叫的地方能更靈活的運用; capture 最終返回一個 Response.

記住: Repeater 中所有異常都會被 capture 捕獲, 並且返回 capture 中的 Response.


