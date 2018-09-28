
# 習慣

Enoa 有著一套特定的編碼習慣, 但是你仍然可以按照自己的習慣進行編碼. 下面列出部分編碼習慣.


#  Java

使用 Enoa 意味著你的 Java 版本必須是 8 或者以上, Enoa 程式碼中存在著很多 Java 8 的特性, 低於此版本將無法使用.

# 鏈式呼叫

Enoa 中存在著大量的鏈式呼叫語法, 例如 Repeater, Yosart 的初始化.

# Getter && Setter


不建議使用 Getter Setter 方法, 過多的 get set 方法, 導致程式碼呼叫會非常難看, 但是無 Getter Setter 方法, 只是欄位又不便於管理, 因此 Enoa 建議 Getter Setter 使用與欄位同名方法.

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
使用這種方式的弊處是, 大多數的序列化框架, 例如 Json 的序列化框架, 有可能會無法進行反序列化, 因為沒有 Getter Setter 方法. 這個時候需要考驗你的判斷力進行取捨. (常用框架中 Gson 支援這種方式, Gson 是通過欄位進行反序列化的)
:::

:::danger
事實上, Enoa 有在開發一款 Json 解析器 (Tryjson). [尚未完成](https://github.com/fewensa/enoa/tree/tryjson "Tryjson tree").
:::


# Builder


實際上, 同名 Getter Setter 方法並不完全是推崇的方案, 只是在實體類中如此建議; 而如果不是實體類, 則建議通過 Builder 的方式進行構建, 這樣會有很多好處: 結構化的語法; 一旦類進行建立, 執行時將不可被更改(重新 Builder); 等等.

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



Enoa 開發中, 使用 [EditorConfig](https://editorconfig.org/ "EditorConfig") 進行編碼規範檢查.

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


# 命名優化


在 Enoa 程式碼中充斥著大量以 `_` 或其他符號等不規則的命名方式. 初衷是為了讓使用著在使用中可以免受 IDE 的提示干擾, 減少 IDE 類名或方法名提示時出來的大量結果. 減少誤用的概率.
簡單說明就是基本上以 `_` 為代表開頭的類或者方法, 正常使用中基本上是用不到的.


