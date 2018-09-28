

# plugin

Yosart 插件, 用于将第三方功能接入到 Yosart 中, 或者说就是启动第三方功能. 需要理解的是, 并非所有第三方功能都必须要有一个 plugin 才可以在 Yosart 中使用, plugin 只是提供了快速简便的调用方式. 例如 redis 插件, 完全可以使用 Redis 提供的 EPM 初始化, 而不需要通过 plugin. 但是建议全部通过 plugin 接入, 便于代码的维护.

下面将会介绍现已支持的插件

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

非 plugin 方式启动数据库操作组件. [Database](#Database)


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
json 插件注意, 插件中附带 JsonRenderExt 扩展, 可通过 .ext 加入 Yosart, ext 见后文描述

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

