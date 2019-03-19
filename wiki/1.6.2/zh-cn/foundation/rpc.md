

# Rpc

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-rpc</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

### 警告

> Rpc 项目属于不稳定项目, 诸多功能尚未完善, 可能未来的变动很大, 不建议未掌握的情况下使用.

## EPM

Rpc 使用 EPM 进行管理, 提供 Http 以及 Socket 两种方式. 目前只实现 Http 方式.

```java
Rpc.epm()
  .json(new FastjsonProvider())
  .log(new Slf4JLogProvider());

Rpc.epm().register()
  .http("passport", EoUrl.with("http://localhost:20003/"))
  .http("publib", EoUrl.with("http://localhost:20005"));
```

## http

```java
HttpRpcResult<Kv> ret0 = Rpc.http("passport", "application")
  .method(HttpMethod.POST)
  .para("a", "b")
  .emit(Kv.class);
System.out.println(ret0);

HttpRpcResult<Kv> ret1 = Rpc.http("publib", "/area/lst")
  .emit(Kv.class);
System.out.println(ret1.result());

HttpRpcResult<Result<List<Area>>> ret2 = Rpc.http("publib", "/area/lst")
  .emit(TypeBuilder.with(Result.class).beginSubType(List.class).type(Area.class).endSubType().build());
System.out.println(ret2.result());

HttpRpcResult<byte[]> ret3 = Rpc.http("passport", "/avatar/1").emit((body, type) -> body.bytes());
System.out.println(Arrays.toString(ret3.result()));
```

## enqueue

```java
Rpc.http("passport", "application")
  .method(HttpMethod.POST)
  .para("a", "b")
  .enqueue(Kv.class)
  .done(System.out::println);

Rpc.http("publib", "/area/lst")
  .enqueue(Kv.class)
  .done(System.out::println);

HttpRpcPromise<Result<List<Area>>> ret2 = Rpc.http("publib", "/area/lst")
  .enqueue(TypeBuilder.with(Result.class).beginSubType(List.class).type(Area.class).endSubType().build());
ret2.done(System.out::println);

Rpc.http("passport", "/avatar/1").enqueue((body, type) -> body.bytes())
  .done(ret -> System.out.println(Arrays.toString(ret.result())));
```

