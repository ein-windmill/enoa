

# 授权

## auth

auth 用于对请求进行认证, auth 方法添加的认证将会是全局的, 要求提供一个 `GatewayAuth` 的实现类. 验证若验证失败直接抛出 GatewayAuthException 即可.

```java
GatewayAuth auth = request -> {
  if (!request.header("token").equal("abc"))
    throw new GatewayAuthException("Auth fail.");
};

Gateway.createServer()
  .auth(auth)
  .listen();
```

## noauth

noauth 用于取消授权认证, 配置相应的 Api, 当匹配到请求的 Api 在不认证的列表中, 将不会进行认证.

```java
Gateway.createServer()
  .auth(request -> System.out.println("Auth"))
  .noauth("/user/login", "/user/register")
  .listen();
```

`/user/login` 以及 `/user/register` 将不会进行授权认证.

