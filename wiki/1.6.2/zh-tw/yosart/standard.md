

# 標準

Yosart 主要功能均是由擴充套件去實現的, 因此 Yosart 會定義一套標準, 具體是否支援這套標準, 將視你所使用的擴充套件.

## 路由

Yosart 路由有三種形式 `Action`, `Control`, `Router`, Router 在建立後會被分析為 `Control` 或者 `Action`, 因此一個路由擴充套件只要實現 `Action` 以及 `Control` 型別的路由處理即可.


### Action

```java
.handle("/", req -> Resp.with(request).render("action anno").end())
```

Action 的宣告很簡單, 直接使用 hanle 方法定義路由, 以及實現的類即可. 同時 handle 可以指定請求型別.

```java
.handle(Method.GET, "/", req -> Resp.with(request).render("action anno").end())
```

此處的含義是, 這個 Action 只能夠被 GET 型別請求可以訪問, 其他型別, 例如 POST, 需要反饋 404 錯誤.

## Control

Control 定義需要提供一個繼承自 `YoControl` 或者 `YeControl` 以及 `YaControl` 等類都可以, 關於三者的區別


|                            | YoControl | YeControl | YaControl |
| -------------------------- | --------- | --------- | --------- |
| 獲取 Request 物件           | Y          | Y        | Y         |
| 渲染系列方法 & header 方法    | Y        |  Y        | N         | 
| 獲取請求引數                 | Y         |  N        | N          |

區別在於是否有提供快速的方法獲取 Request 中的資料, 三者都支援獲取 Request 物件, 那麼就算不支援快速獲取的 Api, 也可以通過 Request 物件獲取, 具體如何選擇, 看使用者自己取捨.

```java
.handle("/my", MyControl.clsss);
```

**MyControl.java**

```java
public class MyControl extends YoControl {
  public void name() {
    super.renderText("Jack");
  }

  public Integer age() {
    return 5;
  }
  
  @Action(method = Method.GET, value = "mems")
  public Kv members() {
    return Kv.create()
      .set("father", "Koins")
      .set("Mother", "Andy");
  }
}
```

上方程式碼註冊後會生成三個 Api

```text
ALL /my/name
ALL /my/age
GET /my/mems
```

handle 註冊 Control 不提供 Method 選項, 而是由 Control 中的方法自己決定; Control 中需要注意的有兩點

1. Yosart 不限定 Control 方法是否必須要有返回值, 如果有返回值也不限定任何型別; 不限定方法是否有引數; 完全由路由擴充套件自行決定.
2. Action 註解是 Yosart 提供的註解 [io.enoa.yosart.kernel.anno.Action](https://github.com/fewensa/enoa/blob/master/enoa-yosart/src/main/java/io/enoa/yosart/kernel/anno/Action.java), 也是 Yosart 中唯一提供的註解, 用於描述路由資訊.
   Action 註解需要理解的是, method 用於表述該介面支援的請求型別, 如果請求的型別不正確應該回饋 404 錯誤, method 支援多個, 寫做: method = \{Method.GET, Method.POST}; value 用於描述介面的名稱, 如果不指定將會使用方法名, 區分大小寫; uri 於 value 是相同的作用.


## Router

Router 是多個路由的集合, 可以將部分路由打包在一起.

```java
.handle(new HomeRouter());
.handle("/jack", new HomeRouter());
```

**HomeRouter**

```java
public class HomeRouter extends YoRouter {
  @Override
  public String context() {
    return "/home";
  }

  @Override
  protected void register() {
    super.add(MyControl.class)
      .add("/my", MyControl.class)
      .add(Method.GET, "/where", req -> Resp.with(req).render("Italy").end());
  }
}
```

> MyControl.class 是前文的 MyControl.java

這段程式碼將會生成下列路由

```text
ALL /home/name
ALL /home/age
GET /home/mems
ALL /home/my/name
ALL /home/my/age
GET /home/my/mems
GET /home/where

ALL /jack/home/name
ALL /jack/home/age
GET /jack/home/mems
ALL /jack/home/my/name
ALL /jack/home/my/age
GET /jack/home/my/mems
GET /jack/home/where
```

Router 註冊分兩種, 如果第一個引數是 String, 表明是這個 Router 的所有內部路由的字首.

## RESTful

Yosart 不強制要求路由擴充套件要支援 RESTful 風格 Api 定義; 但是如果支援, 需要按照以 `:` 定義的方式. 

e.g.

```text
POST   /animal/:animal
PUT    /animal/:animal
DELETE /animal/:animal
```

`:animal` 便是定義的變數, 此變數的值可以通過 `request.variable("animal")` 來獲取.

支援定義多個變數

```text
POST /animal/:animal/:action
```

:::warning
雖然 Yosart 要求使用 `:` 來定義, 並且在 Yosart 啟動時檢查路由表時, 會對 RESTful 風格進行檢查, 但是仍然有機會使用其他風格, 當然這得由路由擴充套件支援.
:::

