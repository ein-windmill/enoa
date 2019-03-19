



# 案例

## Simple

```java
Gateway.createServer()
  .provider(new NanoHTTPDProvider())
  .auth(request -> System.out.print("auth"))
  .mapping("/", "http://localhost:2000")
  .mapping("/account", "http://localhost;2001")
  .mapping("/admin", "http://localhost:20002")
  .listen(9103);
```


## 完整案例


```java
public class GatewayApp {

  private List<Header> CROS_HEADERS = new ArrayList<Header>() {{
    add(new Header("Access-Control-Allow-Origin", "*"));
    add(new Header("Access-Control-Allow-Credentials", "true"));
    add(new Header("Access-Control-Allow-Method", "GET,POST,PUT,PATCH,DELETE"));
    add(new Header("Access-Control-Allow-Headers", String.join(",", new String[]{
      "Content-Type",
      "X-HTTP-Method-Override",
      "Access-Control-Request-Headers",
      "Access-Control-Request-Method",
      "cache-control",
      "x-requested-with"
    })));
  }};

  private String[] NOAUTHS = {
    "/account/login",
    "/account/register"
  };

  private EoxConfig config() {
    return EoxConfig.def()
      .newBuilder()
      .tmp(PathKit.debugPath().resolve("_tmp"))
      .other(Kv.create().set("provider.netty.upload.vendor", "cos"))
      .holdFile()
      .maxUploadSize(8)
      .build();
  }

  private void start() {
    Gateway.createServer()
      .provider(new NettyProvider())
      .log(new Slf4JLogProvider())
      .auth(request -> System.out.print("auth"))
      .noauth(this.NOAUTHS)
      .config(this.config())
      .capture(new GatewayCaptureRender())
      .cros(this.CROS_HEADERS)
      .mapping("/", "http://localhost:2000")
      .mapping("/account", "http://localhost;2001")
      .mapping("/admin", "http://localhost:20002")
      .listen(9001);
  }

  public static void main(String[] args) {
    GatewayApp app = new GatewayApp();
    app.start();
  }

}
```

GatewayCaptureRender.java

```java
public class GatewayCaptureRender implements GErrorRenderFactory {
  @Override
  public Response renderError(HttpStatus stat, Throwable e) {
    if (e instanceof GatewayAuthException) {
      Log.error(e.getMessage());
    } else {
      Log.error(e.getMessage(), e);
    }
    return new Response.Builder()
      .contentType("application/json")
      .body(ResponseBody.create(this.buildResp(e.getMessage())))
      .build();
  }

  private String buildResp(String message) {
//    return TextKit.union("{\"stat\": 2, \"message\":\"", message, "\"}");
    return Json.toJson(EoResp.fail(message));
  }
}

```

