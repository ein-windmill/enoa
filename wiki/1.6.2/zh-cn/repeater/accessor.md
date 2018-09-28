

# EoxAccessor

EoxAccessor 是 Repeater 的重点, Repeater 中提供了一个默认的 EoxAccessor, [EnoaRepeaterAccessorImpl](https://github.com/fewensa/enoa/blob/master/enoa-repeater/src/main/java/io/enoa/repeater/EnoaRepeaterAccessorImpl.java "EnoaRepeaterAccessorImpl.java"), 默认的实现仅仅只是简单的显示 `It works!`, 告诉你 Repeater 正在工作.

但是这不妨碍我们来说明 EoxAccessor, 当创建一个 Repeater 服务器之后, 所有的请求最终都会进入到 EoxAccessor 中, 因此我们可以在 EoxAccessor 中书写我们的代码来执行, 来看下 EoxAccessor 的源码.

```java
public interface EoxAccessor {
  Response access(Request request);
}
```

仅仅只是简单的一个接口, EoxAccessor 接口提供 access 方法来获取请求, 会给出 Request 对象, 并且要求返回 Response 对象.

也来看下 Repeater 的默认实现代码:

```java
class EnoaRepeaterAccessorImpl implements EoxAccessor {

  private EoxConfig config = Repeater.config();

  @Override
  public Response access(Request request) {
    String body = "It works!";
    String contentType = "text/html; charset=" + this.config.charset().displayName();
    return new Response.Builder()
      .header("Content-Type", contentType)
      .body(ResponseBody.create(body))
      .build();
  }
}
```

这里我们能看到一些倪端, 并且看到了 Response 对象是如何创建的, Response 对象更详细的资讯, 将会在后面说明.

相信你应该能看懂默认实现的代码原理. 仅仅是返回 Response 并且在 Header 设置 Content-Type 为 text/html, 然后响应体是 "It works!".

那么, 你可以点击这个[链接](https://github.com/fewensa/enoa/blob/master/enoa-example/example-repeater/src/main/java/io/enoa/example/repeater/ExampleRepeaterAccessorImpl.java "ExampleRepeaterAccessorImpl.java"), 来看一个相对较复杂的案例, 该案例返回的 Response 将会展示出请求的所有信息.


## [Request](https://github.com/fewensa/enoa/blob/master/enoa-repeater/src/main/java/io/enoa/repeater/http/Request.java)

Request 是 EoxAccessor 接口中获取到的请求信息, 内部方法相对较多. 不过大致可以分为下面几类:

1. 请求信息
2. 请求参数
3. Header 参数
4. attr

此外还有一个特殊的方式是 `originRequest` 该方法是用来获取原始 Request 类型的, 例如你使用的是 Jetty 服务器, 那么 `originRequest` 方法实际返回的对象是 `HttpServletRequest`.

### 请求信息

请求信息的方法包括

- method
  请求类型 GET POST PATCH DELET 等等
- context
  上下文链接
- uri
  URI
- url
  URL
- body
  请求体, 当请求以 post 形式发起是, 请求数据可以通过 body 方法获取.

### 请求参数

请求参数方法较多, 但是大部分都是为了方便使用而提供的方法, 例如 `paraToInt` 是将获取到的参数转换为 Integer 类型, 同类型的还包括 `paraToLong` 等等.

下面列出一些比较不同的方法

- para
  获取参数值
- paraMap
  获取所有参数
- paraNames
  获取所有参数名
- paraValues
  获取某个参数名下的所有值, 返回数组
- files
  上传的所有文件列表, 同名方法有个带参数, 区别是, 不带参数获取的所有文件, 带参数获取该名称下的所有文件
- file
  获取单个文件

```java
String name = request.para("name");
Map<String, String[]> map = request.paraMap();
String[] names = request.paraNames();
String[] items = request.paraValues("item");
UFile[] ufiles = request.files();
UFile[] ufs = request.files("file");
UFile uf = request.file("file");
```

如果请求是以 post 形式发送, 并且 Content-Type 表明是表单请求, para 都是可以获取到值的, 包括文件上传类型请求(form-data). 但是如果是其他类型, 则无法获取(例如 Json 请求), 需要通过 body 方法.


### Header 参数

Header 参数方法相对较少, 但是要注意的是, Request 中也提供获取 Cookie 的相关方法, 此类方法也归档于 Header 参数中

- header
  获取单个 Header 值
- headerNames
  获取所有 Header 名称
- cookie
  获取单个 Cookie 值
- cookieObject
  获取单个 Cookie 对象
- cookies
  获取所有 Cookie


```java
String[] names = request.headerNames();
String contentType = request.header("content-type");
String sessid = request.cookie("sessid");
Integer some = request.cookieToInt("some");
Cookie sessobj = request.cookie("sessid");
Cookie[] cookies = request.cookies();
```

:::success
header 方法参数名不区分大小写.
:::

### attr

attr 是一个特别的存在, attr 并不属于原始请求所携带的数据, 而是当请求到达时创建的一个对象, 生命周期仅在当次请求中. 并且 Request 提供设置 attr 的方法.

- attrNames
  所有属性名
- attr
  获取或者设置属性
- rmAttr
  删除属性

```java
String[] names = request.attrNames();
String value = request.attr("key"); // get attr
request.attr("name", "Jack")
  .attr("id", 1); // set attr
request.rmAttr("id");
```

## [Response](https://github.com/fewensa/enoa/blob/master/enoa-repeater/src/main/java/io/enoa/repeater/http/Response.java)

Response 对象用于表述, 当次请求需要返回数据给请求者. 相对于 Request, Response 就简单很多

- status
  响应状态码
- contentType
  响应数据类型
- contentLength
  响应体长度
- cookie
  响应 Cookie
- header
  响应 Header
- body
  响应体
- charset
  字符编码

首先, 状态码可以参考 [HttpStatus](https://github.com/fewensa/enoa/blob/master/enoa-repeater/src/main/java/io/enoa/repeater/http/HttpStatus.java "HttpStatus.java"), 提供响应的状态码即可; 通常 200(OK) 表示成功, 30x 表示授权失败, 40x 表示资源不存在, 50x 表示服务器错误.

contentType 响应数据类型, 常见的包括 `text/html`, `application/json`.

contentLength 此数据通常不需要主动设置, Response 会自动设置, 但是要注意, 如果你需要主动设置必须要在调用 body 方法之后, 因为 body 方法会重新计算一次内容长度.

cookie 用于设置响应的数据设置 Cookie 记录.

header 设置响应头, 这里需要特别注意的是, 不建议在 header 中设置 `set-cookie` 属性, 这样你会得到一个警告, 告诉你应该使用 cookie 方法, 因为 cookie 是可以添加多个的, 如果一段代码中你同时使用了 cookie 方法以及 header 中设置 `set-cookie`, 会容易造成混乱, 因此建议统一使用 cookie 方法. 虽然 `set-cookie` 也可以正常工作.

body 要求提供一个 ResponseBody 对象, 响应的消息体, 该对象的创建很简单, 调用 create 方法即可.

charset 响应数据编码.


```java
new Response.Builder()
  .header("Content-Type", contentType)
  .body(ResponseBody.create("Hello World"))
  .build();
```

