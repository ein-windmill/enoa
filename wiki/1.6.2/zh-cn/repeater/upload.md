

# 文件上传

各 Web 服务器 对文档上传的支持也不禁相同, 有的有, 有的没有, 下面列出目前已支持的服务器对文档上传的支持情况

| SERVER         | SUPPORT | DESCRIPTION                            |
| -------------- | ------- | -------------------------------------- |
| FastCGI        | NO      | 不支持                                    |
| Jetty          | YES     | Jetty 自身支持文档上传, 需要设置参数 (Repeater 中已设置) |
| NanoHttpd      | -       | NanoHttpd 自身是有文档上传的, 但是存在很多问题          |
| Netty          | YES     |                                        |
| tio-httpserver | YES     | tio 支持文档上传, 不过其实现方式和其他服务器的方式完全不同       |
| Tomcat         | YES     | 与 Jetty 类似.                            |

- NanoHttpd

  NanoHttpd 实际上是支持文档上传的, 不过存在较多问题, 其上传的文档强制按照其内部实现方式并不容易进行提取, 且放置与 http 参数中, 因此 Repeater 中, 未支持 Nanohttp 的默认文档上传. 实际上其官方提供的文档上传[案例](https://github.com/NanoHttpd/nanohttpd)也不是使用默认方式, 而是通过 `common-upload` 组件实现. 

- tio-httpserver

  tio-httpserver 支持文档上传, 但是其实现方式与其他服务器的支持完全不同, 其他服务器对于文档上传都是通过 InputStream 或者提供相应的方法进行获取. tio-httpserver 不同的是, 其不提供获取 InputStream, 而是将上传的文档放置与参数中, 该参数值的类型是 UplodFile, 且获取的值是 byte 数组, 因此 Repeater 的做法是直接将该数组保存到文档中去.


**注意**:

Repeater 的文档上传机制实际上对各提供者做了改造, 通常文档上传到临时文档并不会在接收到请求就马上进行保存, 而是在调用获取文档的方法后再保存到临时文档去.

然而这是不必要的, 在 Repeater 中, 文档上传接收到请求后就会将文档写入到预先配置的临时目录中. 后续通过 Request 获取到的已经是存储后的文档信息.

本身文档上传就应该是 Web 服务器应该做的事情, 而上游的应用服务应该专注与业务逻辑. 且在 Servlet 中也有一些限制, 文档上传的表单, 必须要先获取文档后才能获取到其他参数, 否则会导致无法获取文档的问题, 在 Repeater 的统一结构下, 避免了这个问题, 上游可以随意顺序调用, 因为文档上传已经被 Repeater 处理过.


## cos

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>repeater-cos</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

为了弥补各服务器的文档上传的支持不同, 而引入的第三方上传组件, COS 组件来自于 [oreilly cos](http://www.servlets.com/cos/) 是一个高性能的文档上传组件, cos 上传组件是基于 Servlet 实现的, 这里将 cos 组件拿过来剔除了 Servlet 依赖, 且删除了和文档上传无关的类.

各服务器对 cos 上传组件的支持


| SERVER         | SUPPORT |
| -------------- | ------- |
| FastCGI        | YES     |
| Jetty          | YES     |
| NanoHttpd      | YES     |
| Netty          | YES     |
| tio-httpserver | NO      |
| Tomcat         | YES     |

- NanoHttpd
  cos 上传也是 Repeater 中 NanoHttpd 唯一支持上传的方式

- tio-httpserver
  tio 因其特殊性, 无法支持 cos, cos 上传通过 InputStream 进行写入, tio 若要兼容就需要将 byte 数组转化为 InputStream, 这样完全是浪费.

对于本身已支持文档上传的提供者, 默认走的就是提供者自己的上传方式, 若要切换为 cos 上传, 在 Repeater 启动时提供配置告知即可, 下列表格是相应的服务器告知键.

| SERVER         | KEY                           | VALUE | DESCRIPTION                         |
| -------------- | ----------------------------- | ----- | ----------------------------------- |
| FastCGI        |                               |       | 无需提供, FastCGI 文档上传必须使用 cos   |
| NanoHttpd      |                               |       | 无需提供, NanoHttpd 文档上传必须使用 cos |
| tio-httpserver |                               |       | 不支持                                |
| Jetty          | provider.jetty.upload.vendor  | cos   | -                                    |
| Netty          | provider.netty.upload.vendor  | cos   | -                                    |
| Tomcat         | provider.tomcat.upload.vendor | cos   | -                                    |

## 使用案例

cos 的使用很简单, 首先确认所使用的 Web 服务器支持 cos 组件, 引入 cos pom, 然后在启动 Repeater 时配置相应的值即可.

### Jetty

```java
EoxConfig config = new EoxConfig.Builder()
  .other(Kv.by("provider.jetty.upload.vendor", "cos"))
  .build();
Repeater.createServer(new JettyProvider())
  .config(config)
  .listen(9001);
```

### Netty

```java
EoxConfig config = new EoxConfig.Builder()
  .other(Kv.by("provider.netty.upload.vendor", "cos"))
  .build();
Repeater.createServer(new NettyProvider())
  .config(config)
  .listen(9001);
```

### Tomcat

```java
EoxConfig config = new EoxConfig.Builder()
  .other(Kv.by("provider.tomcat.upload.vendor", "cos"))
  .build();
Repeater.createServer(new TomcatProvider())
  .config(config)
  .listen(9001);
```

### NanoHttpd

无需设置, NanoHttpd 默认使用 cos 组件, 因此引入 cos pom 即可.

### FastCGI

无需设置, FastCGI 默认使用 cos 组件, 因此引入 cos pom 即可.

