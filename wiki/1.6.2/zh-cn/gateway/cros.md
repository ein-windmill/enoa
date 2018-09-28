
# 跨域

## cros

考虑到现阶段大多数 Web 功能开发很多是前后端分离开发, 浏览器会有跨域的限制, cros 方法就是为了解决跨域问题而存在的.

cros 方法要求提供允许跨域的 Header, 给予响应的 Header 列表即可.

e.g.

```java
List<Header> CROS_HEADERS = new ArrayList<Header>() {{
  add(new Header("Access-Control-Allow-Origin", "*"));
  add(new Header("Access-Control-Allow-Credentials", "true"));
  add(new Header("Access-Control-Allow-Method", "GET,POST,PUT,PATCH,DELETE"));
  add(new Header("Access-Control-Allow-Headers", String.join(",", new String[]{
    TConst.SESSID_TRAFFIC,
    "Content-Type",
    "X-HTTP-Method-Override",
    "Access-Control-Request-Headers",
    "Access-Control-Request-Method",
    "cache-control",
    "x-requested-with"
  })));
}};

Gateway.createServer()
  .cros(CROS_HEADERS)
  .listen();
```


