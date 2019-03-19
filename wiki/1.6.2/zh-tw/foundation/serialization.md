

# Serialization

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-serialization</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Serialization 是一個序列化框架的包裝, 預設採用 Java 提供的序列化方案實現.

受支援的序列化方案

- Java
- fst
- hessian
- kryo

## EPM

Serializer 提供 EPM 進行初始化, 目前版本 (1.6-beta.2) 中不支援多個例項, 下個版本將會支援. 

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

