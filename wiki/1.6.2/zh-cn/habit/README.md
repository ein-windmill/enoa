
# 习惯

Enoa 有着一套特定的编码习惯, 但是你仍然可以按照自己的习惯进行编码. 下面列出部分编码习惯.


#  Java

使用 Enoa 意味着你的 Java 版本必须是 8 或者以上, Enoa 代码中存在着很多 Java 8 的特性, 低于此版本将无法使用.

# 链式调用

Enoa 中存在着大量的链式调用语法, 例如 Repeater, Yosart 的初始化.

# Getter && Setter


不建议使用 Getter Setter 方法, 过多的 get set 方法, 导致代码调用会非常难看, 但是无 Getter Setter 方法, 只是字段又不便于管理, 因此 Enoa 建议 Getter Setter 使用与字段同名方法.

e.g.

```java
public class User {
  private String id;
  private String name;
  private Integer age;

  public String id(){
    return this.id;
  }

  public User id(String id) {
    this.id = id;
    return this;
  }

  public String name() {
    return this.name;
  }

  public User name(String name) {
    this.name = name;
    return this;
  }

  public Integer age() {
    return this.age;
  }

  public User age(Integer age) {
    this.age = age;
    return this;
  }
}
```

:::warning
使用这种方式的弊处是, 大多数的序列化框架, 例如 Json 的序列化框架, 有可能会无法进行反序列化, 因为没有 Getter Setter 方法. 这个时候需要考验你的判断力进行取舍. (常用框架中 Gson 支持这种方式, Gson 是通过字段进行反序列化的)
:::

:::danger
事实上, Enoa 有在开发一款 Json 解析器 (Tryjson). [尚未完成](https://github.com/fewensa/enoa/tree/tryjson "Tryjson tree").
:::


# Builder


实际上, 同名 Getter Setter 方法并不完全是推崇的方案, 只是在实体类中如此建议; 而如果不是实体类, 则建议通过 Builder 的方式进行构建, 这样会有很多好处: 结构化的语法; 一旦类进行建立, 运行时将不可被更改(重新 Builder); 等等.

```java
public class User {
  private final String id;
  private final String name;
  private final Integer age;

  private User(Builder builder){
    this.id = builder.id;
    this.name = builder.name;
    this.age = builder.age;
  }

  public Builder builder(){
    return new Builder(this);
  }

  public String id() {
    return this.id;
  }

  public String name() {
    return this.name;
  }

  public Integer age() {
    return this.age;
  }

  public static class Builder {
    private String id;
    private String name;
    private Integer age;

    public Builder() {}

    public Builder(User user) {
      this();
      this.id = user.id;
      this.name = user.name;
      this.age = user.age;
    }

    public User build() {
      return new User(this);
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder age(Integer age) {
      this.age = age;
      return this;
    }
  }
}
```

使用

```java
// create user instace
User jack = new User.Builder()
  .id("1")
  .name("jack")
  .age(4)
  .build();

// update user
User tom = jack.builder()
  .id("2")
  .name("tom")
  .build();
```

# editorconfig



Enoa 开发中, 使用 [EditorConfig](https://editorconfig.org/ "EditorConfig") 进行编码规范检查.

```text
root = true

[*]
charset = utf-8
indent_style = space
trim_trailing_whitespace = true
end_of_line = lf
insert_final_newline = true

[*.{xml,html,css,js,py,json,yml,sh}]
indent_size = 2

[*.java]
indent_size = 2
```


# 命名优化


在 Enoa 代码中充斥着大量以 `_` 或其他符号等不规则的命名方式. 初衷是为了让使用着在使用中可以免受 IDE 的提示干扰, 减少 IDE 类名或方法名提示时出来的大量结果. 减少误用的概率.
简单说明就是基本上以 `_` 为代表开头的类或者方法, 正常使用中基本上是用不到的.


