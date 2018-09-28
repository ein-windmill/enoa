

# 授權

## auth

auth 用於對請求進行認證, auth 方法新增的認證將會是全域性的, 要求提供一個 `GatewayAuth` 的實現類. 驗證若驗證失敗直接丟擲 GatewayAuthException 即可.

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

noauth 用於取消授權認證, 配置相應的 Api, 當匹配到請求的 Api 在不認證的列表中, 將不會進行認證.

```java
Gateway.createServer()
  .auth(request -> System.out.println("Auth"))
  .noauth("/user/login", "/user/register")
  .listen();
```

`/user/login` 以及 `/user/register` 將不會進行授權認證.

