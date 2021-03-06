

# Json

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-json</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Json 项目用来整合 Json 框架. 统一处理接口. 受支持的框架包括:

- fastjson
- gson
- jackson

此外, Enoa 提供了一个默认的 Json 实现, 不过不支持 Json 的反序列化.

## EPM

Json 可以使用 epm 进行安装实现者, Json epm 不支持多实例, 多次安装只有最后一次有效.

```java
Json.epm().install(new GsonProvider());
```


## fastjson

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-json-fastjson</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
Json.epm().install(new FastjsonProvider());
```

## gson

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-json-gson</artifactId>
  <version>${project.version}</version>
</dependency>
```

```java
Json.epm().install(new GsonProvider());
```

## jackson

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-json-jackson</artifactId>
  <version>${project.version}</version>
</dependency>
```

```java
Json.epm().install(new JacksonProvider());
```

## 使用

序列化

```java
Kv kv = Kv.create()
  .set("id", 1)
  .set("name", "Jack");

String json = Json.toJson(kv);
```

反序列化对象

```java
String json = "{\"id\": 1, \"name\": \"Jack\"}";
Kv kv = Json.parse(json, Kv.class);
User u0 = Json.parse(json, User.class);
```

反序列化集合

```java
String json = "[{\"id\": 1, \"name\": \"Jack\"},{\"id\": 1, \"name\": \"Jack\"}]"
List<Kv> kvs0 = Json.parse(json, TypeBuilder.with(List.class).type(Kv.class).build());
List<Kv> kvs1 = Json.parseArray(json, Kv.class);
```

> TypeBuilder 类来自于 [typebuilder](#typebuilder) 项目

