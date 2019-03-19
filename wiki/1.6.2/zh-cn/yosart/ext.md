


# ext

Yosart 扩展用于进行高级功能扩展, Yosart 整个框架是完全基于扩展所实现的, 包括路由, 渲染等等.

Yosart 支持以下类型扩展

- BOOT_HOOK

  启动钩子, 将会在 Yosart 调用 Repeater 之前调用; 允许多个扩展

- ROUTER

  路由扩展, Yosart 所接收到的请求会全部分发到注册的路由扩展上; 只允许一个扩展.

- ASSETS

  静态资源文件扩展; 只允许一个

- RENDER

  渲染扩展; 允许多个

- SESSION

  Session 扩展; 只允许一个


在 Yosart 中, 如果是只允许一个的扩展, 多次添加以最后一个为标准. 例如:

```java
.ext(new AnostExt())
.ext(new AnostExt())
```

将会已第二个 AnostExt 的实例, 第一次添加的将会被抛弃.

## Anost

Anost 是 Yosart 默认搭载的路由扩展. 无需做任何配置即可使用. Anost 支持 Yosart 的多种路由形式, 包括 Action, Control 以及 Router. 支持 RESTful 风格 Api. 并且提供 Hook 进行请求过滤, 拦截, 数据验证等功能.
如果有些特殊的需求, 例如自定义异常渲染, 全局 Hook, 你可以主动创建一个 Anost 扩展, 加入到 Yosart 替换默认的 Anost 扩展.

Anost 的路由实现完全遵照 Yosart 标准, 因此 Yosart 标准提供的所有语法都可正常运行. 那么这里就详细探讨一下 Anost 提供的另外的功能, Anost 的配置, Hook 以及 Para 注解.

### Para

Yosart 提供的 Request 已经非常方便, 可以简单快速的获取请求内容. Anost 只是对其进行了扩展, 提供了 Paras 以及 Para 注解, 用于 Control 中, 可以通过注解获取请求参数.

我们先从比较复杂的案例来理解

e.g.

```java
@Action(method = {Method.GET, Method.POST}, value = "go")
@Paras({
  @Para(index = 3, value = "ts", format = "yyyy-MM-dd"),
  @Para(index = 1, value = "where", def = "street #1"),
  @Para(value = "zone", def = "9")
})
public EoResp go(YoRequest request, String where, int zone, Timestamp ts) {
  return EoResp.ok("OK");
}
```

先不着急看代码, 来看看一下 Para 注解提供的方法

- index

  描述参数对应索引位置

- value

  参数名

- def

  默认值

- format

  时间格式化

- summary

  字段描述

`value` 以及 `def` 很容易理解, `value` 是请求参数名, `def` 如果不存在该参数填充的默认值(空字符串也算不存在).

`format` 作用于时间类型的参数, 当提供 format 时 Anost 则会将参数看作是时间类型, 就会根据提供的 format 进行时间转换. 例如:

```text
?date=2001-02-01
@Para(value="date", format="yyyy-MM-dd")
```

`format` 使用什么格式取决于请求传递的参数格式.

`summary` 描述字段作用, 暂时不具备任何作用.

`index` 相对而言是比较容易混乱的设置, 来对照上面的代码来看, 方法签名上提供了 3 个 Para 注解, 但是方法确有 4 个参数. 关于这个问题, 首先要知道的是, Para 不处理的几个数据类型

- Request
- Renderer
- UFile
- File

当识别到方法参数是上面的类型时, Para 是不做处理的, 因此无需为其设置 Para 注解.
另外一个概念是, Para 的顺序和方法参数的顺序是一一对应的, 都是从 0 开始.
那么结合上面两个概念, `index` 的作用就是打破第二个概念, 使其不一一对应. `index` 去指定其应该对应哪个参数; 另外一点就是如果没有指定 index 的 Para 注解仍然按照顺序取索引.

再来看这段代码:

```java
@Paras({
  @Para(index = 3, value = "ts", format = "yyyy-MM-dd"),
  @Para(index = 1, value = "where", def = "street #1"),
  @Para(value = "zone", def = "9")
})
public EoResp go(YoRequest request, String where, int zone, Timestamp ts) {
  return EoResp.ok("ok");
}
```

第一个 index 是 3, 那么对应的就是 `Timestamp ts`, 第二个 index 是 1, 对应的就是 `String where`, 第三个没有指定 index, 那么取的索引就是 2, 也就对应着 `int zone`

相信你已经理解了.

可以看到还有 Paras 注解, 这个就处理多个参数的情况, 如果说请求只有一个参数时是可以省略的:

```java
@Para("name")
public String func(String name) {
  return "ok";
}
```

### hook

Anost 提供 Hook 功能, 用于在请求到达路由之前可以进行部分处理. 支持使用 Hook 注解方式追加到路由控制器中.

Hook 按功能区分有两种, 一种是全局的, 另外就是局部使用.

#### 全局

全局 Hook 需要在 AnostExt 对象中获取 AnostHookMgr 进行添加:

```java
AnostExt anost = new AnostExt();
AnostHookMgr mgr = anost.hookMgr();
mgr.load(new GlobalHook())
  .load("/manager", new ManagerHook())
  .load("/manager/pre_*", new PreHook(), AnostHookMgr.Mode.REGEX)
```

load 有两种模式, 限定 Api 和不限定; 当限定 Api 时, 需要指定匹配 Api 的模式, 默认是全匹配.


```java
mgr.load(new GlobalHook())
```

不限定 Api 的 Hook 将会作用于所有的接口.

```java
mgr.load("/manager", new ManagerHook());
mgr.load("/manager/pre_*", new PreHook(), AnostHookMgr.Mode.REGEX);
```

限定 Api 的 Hook 仅会作用于匹配到的接口, 匹配模式有三种

- FULL

  Api 全匹配

- PREFIX

  前缀匹配

- REGEX

  正则匹配

默认全匹配模式


#### 局部

除了全局 Hook 之后, 也可以针对单个路由指定 Hook. 使用 `@Hook` 注解添加即可.

```java
@Hook({ValidHook.class})
public String index(){
  return "ok";
}
```

#### 消除

Hook 的消除同样使用 AnostHookMgr 来完成, Hook 消除完全依据规则进行消除, 可以消除为某个接口设定的 Hook, 只要规则能匹配上即可

```java
AnostExt anost = new AnostExt();
AnostHookMgr mgr = anost.hookMgr();
mgr.unload(GlobalHook.class)
  .unload(ManagerHook.class, "/manager")
  .unload(PreHook.class, AnostHookMgr.Mode.REGEX, "/manager/pre_*");
```

于装载全局 Hook 相同, 消除 Hook 也是两种模式, 作用完全于装载全局 Hook 相同. 并且支持消除使用 `@Hook` 注解添加的 Hook.


### IHook

Hook 对象的编写很简单, 实现 IHook 接口即可.

e.g.

```java
public class IndexHook implements IHook {
  @Override
  public void hook(YoRequest request, Resp resp) throws HookException {

  }
}

```

在 hook 方法中即可以在进入到 Control 之前进行部分操作.

### 声明

> hook 方法中会传递一个 Resp 对象, 该对象是 Anost 收到请求后就会创建的一个响应对象, 因此在这里使用 resp 添加的数据最终都会写入到 Response 中.

IHook 接口, 允许抛出 `HookException`, Anost 并不会对 HookException 进行任何处理, 只会冒泡的上游, 因此建议搭配 [exception-extension](#exception-extension) 扩展一同使用, 效果更加.

### 配置

Anost 路由默认是不需要任何配置就可以使用的, 考虑到开发的实际情况, 使用者可以考虑进行部分扩展, 替换掉默认的 Anost 路由机制.

扩展的方式就是创建一个新的 Anost 扩展, 并在 Yosart 中加入. 从创建的这个 Anost 中可以设置包括 Hook, Anost 序列化方式等等.

```java
AnostExt anost = new AnostExt();
Yosart.createServer()
  .ext(anost)
  .listen(9001)
```

关于 Hook 的设置, 上文已经介绍过, 这里就不再说明, 来看一下 Anost 的序列化方法. Anost 提供一个接口 `AnostSerializer` 用于序列化. 这里说的序列化并不是要进行对象的序列化, 准确的说是提供一种数据转换规则而已.

Anost 支持 Control 方法返回任意一个对象, 那么这个序列化要做的事情就是, 当 Control 中返回了一个对象后, 这个对象应该如何处理这个对象, 将对象序列化成最终的数据返回给请求者. Anost 的默认方案是调用对象的 `toString` 方法, 在大部分情况下, 者肯定是不满足的, 因此可以提供一个你所需要的序列化方案.

序列化的设置, 通过创建 Anost 扩展的构造方法传入.

```java
AnostExt anost = new AnostExt(new SomeSerializer());
Yosart.createServer()
  .ext(anost)
  .listen(9001)
```

一个简单的序列化实现

```java
AnostSerializer serializer = (request, data) -> {
  // create renderer
  Renderer renderer = Renderer.with(request);
  // no data, response empty
  if (data == null)
    return renderer.renderText("").end();
  if (data instanceof String)
    return renderer.renderText(data.toString()).end();
  if (data instanceof EoResp) {
    EoResp resp = (EoResp) data;
    if (TextKit.blankn(resp.message()))
      resp.message(resp.message());
  }
  Respend respend = renderer.renderJson(data);
  return respend.end();
};
AnostExt anost = new AnostExt(serializer);
Yosart.createServer()
  .ext(anost)
  .listen(9001);
```

这个简单的实现就是用来将 Control 返回的对象转为 Json 字符串返回给请求者.

### Valid

Anost 提供了强大, 并且简单易用的数据验证工具. 通常 Valid 建议用在 Hook 中, 提前在进入 Control 之前进行验证.

创建 Valid 验证对象

```java
Valid valid = Valid.with(request);
```

Valid 提供多种不同类型的数据验证, `string`, `number`, 等等, 下面将会都列出说明; Valid 验证失败会抛出 `ValidException` 异常.

1. object


```java
valid.object("id")
  .blank("Please input id")
```

2. string

```java
valid.string("name")
  .blank("Please input name.")
  .len(20, "Exceeded the maximum number of characters") // maximum 20
  .alphanumber("Only support alpha number")
  .email("Illegal mail.");
```

3. number
  - eq
  - gt
  - gte
  - lt
  - lte

```java
valid.number("field")
  .digit(true, "Only support digit")
  .allow(new int[]{-1, 0, 1}, "Only allow -1 or 0 or 1");
```

4. bool

```java
valid.bool("b")
  .is("Valid fail.")
```

5. field

```java
valid.field("mobi")
  .needOne("email", "Email and mobile phone need to fill in one");
```

```java
valid.field("passwd")
  .same("repwd", "Confirm password error")
```

6. array
  - blankMember
  - boolArray
  - numberArray
  - size
  - sizeMax
  - sizeMin
  - row

```java
valid.array("item")
  .row(row -> row.equals("abc"), "Valid fail.")
```


7. value

```java
valid.value("mail@example.com")
  .string()
  .email("Valid fail.")
```

value 用于固定值提前验证. 而非 Valid 自动获取

8. special

```java
valid.special(req -> {
  if (!req.para("code").equal("4582"))
    throw new ValidException("Valid fail.");
})
```

9. exists

```java
valid.exists("email")
  .string()
  .email("Valid fail.")
```

只有存在 email 参数是才会进行验证

## Session

Yosart 提供 Session 扩展, 并且支持使用文件存储或者 Redis 存储两种方案.

**文件存储**

```java
Yosart.createServer()
  .ext(new SessExt(
    new FileSession("YOSESS",
      Paths.get("/tmp/session"),
      new JdkProvider(),
      true))
  )
  .listen(9001);
```

文件存储需要提供四个参数

1. Session 键名
2. 文件储存路径
3. 对象序列化方案
4. 重启是否有效

**Redis 存储**

```java
Yosart.createServer()
  .ext(new SessExt(
    new RedisSession("YOSESS",
      new RedisPlugin("SESS_REDIS","localhost", 6379, new JdkProvider()),
      true))
    )
  .listen(9001);
```

Redis 存储需要提供三个参数

1. Session 键名
2. RedisPlugin
3. 重启是否有效

## render

Yosart 提供渲染扩展, Yosart 自身没有实现所有的渲染扩展, 因为部分渲染扩展需要依赖第三方库进行实现, 因此没有主动继承, 由使用者自行决定, 或者使用者有自己的渲染方案, 同样需要提供支持. 下面是 Yosart 所支持的渲染扩展.

| RENDER         | SUPPORT |
| -------------- | ------- |
| render         | YES     |
| renderText     | YES     |
| renderHtml     | YES     |
| renderTemplate | NO      |
| renderJson     | NO      |
| renderFile     | YES     |
| renderXml      | NO      |
| redirect       | YES     |
| forward        | YES     |
| renderError    | YES     |

标记为 NO 的渲染方式, Yosart 均未直接实现, 需要由第三方库去实现, Yosart 提供了 template 以及 json 的第三方实现.

### render-json

POM 直接使用 Json Plugin 即可, Json Plugin 中提供了一个 Json Render. 使用的时候直接加入 Json 扩展即可.

```java
Yosart.createServer()
  .ext(new JsonRenderExt())
  .listen(9001);
```

### render-template

#### POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>yosart-ext-render-template</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

### 声明
> 这个 POM 只是引入扩展, 具体选用什么模板需要单独引入. [Template](#Template)

#### 使用

```java
Yosart.createServer()
  .ext(new TemplateRenderExt(new FreemarkerProvider(),
    PathKit.debugPath().resolve("src/main/tpl/"),
    "ftl"))
  .listen(9001);
```


### 自定义 Render


除 Yosart 提供的渲染接口之外, 也可以添加自定义渲染插件. 自定义很简单, 只需要实现渲染接口即可, 例如一个简单的案例:

ExampleRenderExt

```java
public class ExampleRenderExt implements YmRenderExt {
  @Override
  public String renderType() {
    return "example";
  }

  @Override
  public YoRender renderer(Request req, Kv attr, Object... args) {
    return new ExampleRender(req, attr, args);
  }

  @Override
  public String name() {
    return "ExampleRender";
  }

  @Override
  public String version() {
    return "1";
  }
}
```

ExampleRender

```java
class ExampleRender implements YoRender {
  private final Request req;
  private final String data;
  private final Charset charset;

  TextRender(Request req, Kv attr, Object[] args) {
    this.req = req;
    this.text = (String) args[0];
    this.charset = args[1] == null ? EoConst.CHARSET : (Charset) args[1];
  }

  @Override
  public HttpStatus stat() {
    return HttpStatus.OK;
  }

  @Override
  public String contentType() {
    return "text/plain";
  }

  @Override
  public Header[] headers() {
    return CollectionKit.emptyArray(Header.class);
  }

  @Override
  public ResponseBody render() {
    return ResponseBody.create(this.text, this.charset);
  }
}
```

在 Yosart 启动时加入该渲染扩展

```java
Yosart.createServer()
  .ext(new ExampleRenderExt())
  .listen();
```

Control 以及 Action 中使用:

```java
Renderer.with(req).use("example").render("Some data").end();
```


## exception extension

Yosart 提供的扩展中, 提供一个 Exception 的扩展, 此扩展的作用是拦截 Yosart 中抛出的所有异常, 并做出响应.

e.g.

```java
public class ExceptionExt implements YmExceptionExt {
  @Override
  public Response handle(YoRequest request, Resp resp, Throwable throwable) {

    Response response;

    if (throwable instanceof ValidException) {
      String msg = throwable.getMessage();
      Ret ret = EoResp.fail(msg);
      response = Renderer.with(request).renderJson(ret).end();
      if (resp != null)
        response = ResponseKit.merge(response, resp.end());
      return response;
    }

    if (throwable instanceof HookException) {
      // do ...
    }

    throwable = ThrowableKit.accurate(throwable);
    Log.error(throwable.getMessage(), throwable);
    response = Renderer.with(request).renderError(HttpStatus.INTERNAL_ERROR, throwable).end();
    if (resp != null)
      response = ResponseKit.merge(response, resp.end());
    return response;
  }

  @Override
  public String name() {
    return "Exception Ext";
  }

  @Override
  public String version() {
    return "1.6-beta.2";
  }
}
```

使用

```java
Yosart.createServer()
  .ext(new ExceptionExt())
  .listen(9001);
```

# assets

assets 方法用于处理资源文件处理请求, assets 脱离路由表, Yosart 提供默认的实现, 使用者可通过扩展使用自己的实现.

```java
Yosart.createServer()
  .assets("/assets", Paths.get(EnvKit.string("java.io.tmpdir")), true)
  .listen(9001);
```

默认的实现提供三个参数, 第一个是请求的 URI, 第二个资源文件路径, 第三个是否显示树形的文件目录.

使用自己实现的静态资源可以参照 [EyAssetsExt](https://github.com/fewensa/enoa/blob/master/enoa-yosart/src/main/java/io/enoa/yosart/ext/assets/EyAssetsExt.java "EyAssetsExt.java")

