

# plugin

Yosart 外掛, 用於將第三方功能接入到 Yosart 中, 或者說就是啟動第三方功能. 需要理解的是, 並非所有第三方功能都必須要有一個 plugin 才可以在 Yosart 中使用, plugin 只是提供了快速簡便的呼叫方式. 例如 redis 外掛, 完全可以使用 Redis 提供的 EPM 初始化, 而不需要通過 plugin. 但是建議全部通過 plugin 接入, 便於程式碼的維護.

下面將會介紹現已支援的外掛

## plugin-db

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>yosart-plugin-db</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
C3p0Config dsc = new C3p0Config.Builder()
  .url("jdbc:postgresql://localhost:5432/enoa")
  .user("postgres")
  .passwd("passwd")
  .build();
MybatisConfig config = new MybatisConfig.Builder()
  .name("mybatis")
  .ds(new C3p0Provider(), dsc)
  .scan("io.enoa.example.yosart.mapper", BaseMapper.class)
  .mapper(PathKit.path().resolve("mybatis").toString())
  .suffix("xml")
  .build();

.plugin(new DbPlugin(new MybatisProvider(), config))
```

非 plugin 方式啟動資料庫操作元件. [Database](#Database)


## plugin-json

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>yosart-plugin-json</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
.plugin(new JsonPlugin(new GsonProvider()))
```

:::success
json 外掛注意, 外掛中附帶 JsonRenderExt 擴充套件, 可通過 .ext 加入 Yosart, ext 見後文描述

```java
.ext(new JsonRenderExt())
```
:::

## plugin-redis

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>yosart-plugin-redis</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
.plugin(new RedisPlugin("localhost", 6379, new JdkSerializeProvider()))
.plugin(new RedisPlugin("other", "localhost", 6379, new JdkSerializeProvider()))
```

