

# 网关映射

## mapping

添加网关映射, 网关映射采用 Api 匹配的模式, 当匹配到规则后, 就会进入到对应的地址. 采取前匹配策略, 已第一个为准.


```java
Gateway.createServer()
  .mapping("/", "http://localhost:2000")
  .mapping("/account", "http://localhost;2001")
  .mapping("/admin", "http://localhost:20002")
  .listen(9103);
```


