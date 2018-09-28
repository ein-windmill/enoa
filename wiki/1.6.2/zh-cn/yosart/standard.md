

# 标准

Yosart 主要功能均是由扩展去实现的, 因此 Yosart 会定义一套标准, 具体是否支持这套标准, 将视你所使用的扩展.

## 路由

Yosart 路由有三种形式 `Action`, `Control`, `Router`, Router 在创建后会被分析为 `Control` 或者 `Action`, 因此一个路由扩展只要实现 `Action` 以及 `Control` 类型的路由处理即可.


### Action

```java
.handle("/", req -> Resp.with(request).render("action anno").end())
```

Action 的声明很简单, 直接使用 hanle 方法定义路由, 以及实现的类即可. 同时 handle 可以指定请求类型.

```java
.handle(Method.GET, "/", req -> Resp.with(request).render("action anno").end())
```

此处的含义是, 这个 Action 只能够被 GET 类型请求可以访问, 其他类型, 例如 POST, 需要反馈 404 错误.

## Control

Control 定义需要提供一个继承自 `YoControl` 或者 `YeControl` 以及 `YaControl` 等类都可以, 关于三者的区别


|                            | YoControl | YeControl | YaControl |
| -------------------------- | --------- | --------- | --------- |
| 获取 Request 对象           | Y          | Y        | Y         |
| 渲染系列方法 & header 方法    | Y        |  Y        | N         | 
| 获取请求参数                 | Y         |  N        | N          |

区别在于是否有提供快速的方法获取 Request 中的数据, 三者都支持获取 Request 对象, 那么就算不支持快速获取的 Api, 也可以通过 Request 对象获取, 具体如何选择, 看使用者自己取舍.

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

上方代码注册后会生成三个 Api

```text
ALL /my/name
ALL /my/age
GET /my/mems
```

handle 注册 Control 不提供 Method 选项, 而是由 Control 中的方法自己决定; Control 中需要注意的有两点

1. Yosart 不限定 Control 方法是否必须要有返回值, 如果有返回值也不限定任何类型; 不限定方法是否有参数; 完全由路由扩展自行决定.
2. Action 注解是 Yosart 提供的注解 [io.enoa.yosart.kernel.anno.Action](https://github.com/fewensa/enoa/blob/master/enoa-yosart/src/main/java/io/enoa/yosart/kernel/anno/Action.java), 也是 Yosart 中唯一提供的注解, 用于描述路由信息.
   Action 注解需要理解的是, method 用于表述该接口支持的请求类型, 如果请求的类型不正确应该回馈 404 错误, method 支持多个, 写做: method = \{Method.GET, Method.POST}; value 用于描述接口的名称, 如果不指定将会使用方法名, 区分大小写; uri 于 value 是相同的作用.


## Router

Router 是多个路由的集合, 可以将部分路由打包在一起.

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

这段代码将会生成下列路由

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

Router 注册分两种, 如果第一个参数是 String, 表明是这个 Router 的所有内部路由的前缀.

## RESTful

Yosart 不强制要求路由扩展要支持 RESTful 风格 Api 定义; 但是如果支持, 需要按照以 `:` 定义的方式. 

e.g.

```text
POST   /animal/:animal
PUT    /animal/:animal
DELETE /animal/:animal
```

`:animal` 便是定义的变量, 此变量的值可以通过 `request.variable("animal")` 来获取.

支持定义多个变量

```text
POST /animal/:animal/:action
```

:::warning
虽然 Yosart 要求使用 `:` 来定义, 并且在 Yosart 启动时检查路由表时, 会对 RESTful 风格进行检查, 但是仍然有机会使用其他风格, 当然这得由路由扩展支持.
:::

