

# Typebuilder

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-typebuilder</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

TypeBuilder 是用來生成泛型型別的專案, 快速而簡單的 API, 不用大篇幅的使用 ParameterizedType 等泛型型別類, TypeBuilder 將這些封裝起來直接使用.

### 宣告

> 特別感謝: [ikidou](https://github.com/ikidou)/[TypeBuilder](https://github.com/ikidou/TypeBuilder), 本模組的程式碼全部來自於 ikidou 的 TypeBuilder, 本想直接依賴該專案, 但是考慮到其介面於 Enoa 的程式碼風格相差較大, 因此 Enoa 將其命名做了較大的更改.

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


