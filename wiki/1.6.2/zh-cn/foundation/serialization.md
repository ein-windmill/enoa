

# Serialization

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-serialization</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Serialization 是一个序列化框架的包装, 默认采用 Java 提供的序列化方案实现.

受支持的序列化方案

- Java
- fst
- hessian
- kryo

## EPM

Serializer 提供 EPM 进行初始化, 目前版本 (1.6-beta.2) 中不支持多个实例, 下个版本将会支持. 

```java
Serializer.epm().install(new JdkSerializeProvider());
```

## fst

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-serialization-fst</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
Serializer.epm().install(new FstProvider());
```

## hessian

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-serialization-hessian</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```


```java
Serializer.epm().install(new HessianProvider());
```


## kryo

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-serialization-kryo</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```


```java
Serializer.epm().install(new KryoProvider());
```

## 使用

```java
Kv kv = Kv.create()
  .set("id", 1)
  .set("name", "Jack");

byte[] bytes = Serializer.serialize(kv);

Kv red = Serializer.reduction(bytes);
```

