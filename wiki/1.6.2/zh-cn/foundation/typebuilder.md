

# Typebuilder

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-typebuilder</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

TypeBuilder 是用来生成泛型类型的项目, 快速而简单的 API, 不用大篇幅的使用 ParameterizedType 等泛型类型类, TypeBuilder 将这些封装起来直接使用.

:::success
特别感谢: [ikidou](https://github.com/ikidou)/[TypeBuilder](https://github.com/ikidou/TypeBuilder), 本模块的代码全部来自于 ikidou 的 TypeBuilder, 本想直接依赖该项目, 但是考虑到其接口于 Enoa 的代码风格相差较大, 因此 Enoa 将其命名做了较大的更改.
:::

## 使用


### Example for List&lt;String&gt;
```java
Type type = TypeBuilder.with(List.class)
  .type(String.class)
  .build();
```

### Example for List&lt;? super String&gt;
```java
Type type = TypeBuilder.with(List.class)
  .typeSuper(String.class)
  .build();
```

### Example for List&lt;? extends CharSequence&gt;
```java
Type type = TypeBuilder.with(List.class)
  .typeExtends(CharSequence.class)
  .build();
```

### Example for Map&lt;String, String\[]&gt;
```java
Type type = TypeBuilder.with(HashMap.class)
  .type(String.class)
  .type(String[].class)
  .build();
```

### Example for Map&lt;String, List&lt;String&gt;&gt;
```java
Type type = TypeBuilder.with(Map.class)
  .type(String.class)
  .beginSubType(List.class) // begin part of List<String>
  .type(String.class) // set List generic type
  .endSubType() // end part of List<String>
  .build();
```


