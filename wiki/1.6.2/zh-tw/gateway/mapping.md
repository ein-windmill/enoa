

# 閘道器對映

## mapping

新增閘道器對映, 閘道器對映採用 Api 匹配的模式, 當匹配到規則後, 就會進入到對應的地址. 採取前匹配策略, 已第一個為準.


```java
Gateway.createServer()
  .mapping("/", "http://localhost:2000")
  .mapping("/account", "http://localhost;2001")
  .mapping("/admin", "http://localhost:20002")
  .listen(9103);
```


