
# 跨域

## cros

考慮到現階段大多數 Web 功能開發很多是前後端分離開發, 瀏覽器會有跨域的限制, cros 方法就是為了解決跨域問題而存在的.

cros 方法要求提供允許跨域的 Header, 給予響應的 Header 列表即可.

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


