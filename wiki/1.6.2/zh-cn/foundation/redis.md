

# Redis

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>nosql-redis</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Redis 项目是对 Jedis 进行的再次封装, 提供了更加好用的接口.

## EPM

Redis 使用 EPM 进行初始化, 支持多个实例. 同时要求提供一个 [Serialization](#Serialization), 用于对象的序列化, 默认使用的是 JdkSerializeProvider.

### simple

```java
// install redis instance with name 'main' and use jdk serialize
Redis.epm().install("localhost", 6379, new JdkSerializeProvider());
// install redis instance with name 'other' and use jdk serialize
Redis.epm().install("other", "localhost", 6379);
```

### sentinel

```java
// install redis sentinel instance with name 'sentinel' and use jdk serialize
RedisSentinelConfig config = new RedisSentinelConfig.Builder()
  .name("sentinel")
  .database(1)
  .passwd("passwd")
  .soTimeout(1000)
  .masterName("master")
  .sentinels(new HashSet<String>() {{
    add("192.168.1.19:63790");
    add("192.168.1.20:63791");
    add("192.168.1.21:63792");
    add("192.168.1.22:63793");
  }})
  .build();
Redis.epm().install(config, new JdkSerializeProvider());
```

### use

```java
Redis.use(); // use main instance
Redis.use("main"); // use main instance
Redis.use("sentinel"); // use sentinel instance
```

## run

Redis 在扩展 Jedis 时新增了 `run` 方法, 改方法是 Redis 组件所有方法最终执行的方法, `run` 可以获取到初始化 Redis 是创建的 Jedis 对象, 通过此对象可以直接操作 Jedis.

```java
String ret = Redis.run(new EoRedisRunner() {
  @Override
  public String run(Jedis jedis, EoSerializer serializer) {
    return jedis.get("key");
  }
});
```

当然, 也可以用 Java 8 及以上版本提供的 Lambda 表达式来书写.

```java
String ret = Redis.run((jedis, serializer) -> jedis.get("key"));
```

run 方法要求提供一个 EoRedisRunner 接口的实现类, 该实现类的 run 方法是最终执行的方法, 会传递两个参数, `jedis` 以及 `serializer`, jedis 便是初始化时创建的 jedis 对象, serializer 是初始化是提供的序列化对象.

这里要说明的是, 所有在 run 方法中执行的代码无需调用 `jedis.close()`, 这由 Redis 组件实现了. 包括所有 Redis 提供的方法, 都会自动 close; 但是有一个情况例外, 就是使用者主动在 Redis 组件中获取 Jedis 对象, 那么这个对象就由使用者进行释放, 这是合理的.

需要主动释放 Jedis 的情况:

```java
Jedis jedis = Redis.jedis();
jedis.set("key", "jedis");
// if not use run interface, jedis need do close()
jedis.close();
```

## string

```java
Redis.set("key", "val0");
Redis.append("key", "-val1");
Redis.get("key");
```

和 Redis 中提供的其他方法不同的是, Redis string 相关方法, 像是 `set`, `get` 只允许给予 String 类型的值; 这是因为 `set`, `get`, `append` 是为 String 而设计的, 官方的 Jedis 也是如此, 因此 Enoa 也是如此实现. 当然实际上, `set`, `get` 这些方法是支持传递 Object 值的, 在 Jedis 中是已 byte\[] 形式发送 `public String set(final byte[] key, final byte[] value)`. 因此 Redis 组件也是支持的; 使用 run 方法, 直接获取 Jedis 调用 set bytes 的方法. 


```java
Map<String, String> data = new HashMap<>();
data.put("key0", "val0");
// set object
Redis.run((jedis, serializer) -> jedis.set(SafeEncoder.encode("key"), serializer.serialize(data)));
// get object
Map<String, String> newData = Redis.run((jedis, serializer) -> serializer.reduction(jedis.get(SafeEncoder.encode("key"))));
```

## hash

```java
Redis.hset("hash", "f0", "v0");
Redis.hset("hash", "f1", "v1");

Boolean ef0 = Redis.hexists("hash", "f0");
Boolean ef1 = Redis.hexists("hash", "f1");
Boolean ef2 = Redis.hexists("hash", "f2");

Map<String, String> hgetall = Redis.hgetall("hash");

String f0 = Redis.hget("hash", "f0");
```

## list

```java
Redis.lpush("list", 0, 1, 2, 3, 4);

Long len = Redis.llen("list");

List<Object> lrange = Redis.lrange("list", 0, -1);
```

## set

```java
Redis.sadd("key", 0, 1, 2, 3, 3, 3, 4);

Long ret = Redis.srem("key", 0, 4);

Set<Object> smembers = Redis.smembers("key");
```

## sort

```java
Redis.zadd("sort_set", 0.2D, 5);
Redis.zadd("sort_set", 0.2D, 4);
Redis.zadd("sort_set", 0.2D, 2);

Set<Object> zrange = Redis.zrange("sort_set", 0, -1);
```

## 其他

这里列出的只是部分方法; 需要知道的是, 只要是 Jedis 中提供的方法, Redis 组件都有实现, 如果发现有没被实现的 Jedis 方法, 可以通过 run 方法获取 Jedis 调用.

