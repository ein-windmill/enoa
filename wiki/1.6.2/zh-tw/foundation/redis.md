

# Redis

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>nosql-redis</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Redis 專案是對 Jedis 進行的再次封裝, 提供了更加好用的介面.

## EPM

Redis 使用 EPM 進行初始化, 支援多個例項. 同時要求提供一個 [Serialization](#Serialization), 用於物件的序列化, 預設使用的是 JdkSerializeProvider.

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

Redis 在擴充套件 Jedis 時新增了 `run` 方法, 改方法是 Redis 元件所有方法最終執行的方法, `run` 可以獲取到初始化 Redis 是建立的 Jedis 物件, 通過此物件可以直接操作 Jedis.

```java
String ret = Redis.run(new EoRedisRunner() {
  @Override
  public String run(Jedis jedis, EoSerializer serializer) {
    return jedis.get("key");
  }
});
```

當然, 也可以用 Java 8 及以上版本提供的 Lambda 表示式來書寫.

```java
String ret = Redis.run((jedis, serializer) -> jedis.get("key"));
```

run 方法要求提供一個 EoRedisRunner 介面的實現類, 該實現類的 run 方法是最終執行的方法, 會傳遞兩個引數, `jedis` 以及 `serializer`, jedis 便是初始化時建立的 jedis 物件, serializer 是初始化是提供的序列化物件.

這裡要說明的是, 所有在 run 方法中執行的程式碼無需呼叫 `jedis.close()`, 這由 Redis 元件實現了. 包括所有 Redis 提供的方法, 都會自動 close; 但是有一個情況例外, 就是使用者主動在 Redis 元件中獲取 Jedis 物件, 那麼這個物件就由使用者進行釋放, 這是合理的.

需要主動釋放 Jedis 的情況:

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

和 Redis 中提供的其他方法不同的是, Redis string 相關方法, 像是 `set`, `get` 只允許給予 String 型別的值; 這是因為 `set`, `get`, `append` 是為 String 而設計的, 官方的 Jedis 也是如此, 因此 Enoa 也是如此實現. 當然實際上, `set`, `get` 這些方法是支援傳遞 Object 值的, 在 Jedis 中是已 byte\[] 形式傳送 `public String set(final byte[] key, final byte[] value)`. 因此 Redis 元件也是支援的; 使用 run 方法, 直接獲取 Jedis 呼叫 set bytes 的方法. 


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

這裡列出的只是部分方法; 需要知道的是, 只要是 Jedis 中提供的方法, Redis 元件都有實現, 如果發現有沒被實現的 Jedis 方法, 可以通過 run 方法獲取 Jedis 呼叫.

