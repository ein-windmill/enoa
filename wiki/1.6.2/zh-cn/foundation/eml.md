

# Eml

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-eml</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

## EPM

Eml 提供 EPM 进行管理, 可通过 epm 方法设定邮件服务器相关信息.

```java
EmlConfig sconf = new EmlConfig.Builder()
  .skipError()
  .debug()
  .ssl()
  .auth()
  .user(this.email)
  .passwd(this.passwd)
  .host("smtp.****.com")
  .port(465)
  .build();
// install eml instance with name 'sender'
Eml.epm().install("sender", sconf);


EmlConfig rconf = new EmlConfig.Builder()
  .skipError()
  .debug()
  .ssl()
  .auth()
  .user(this.email)
  .passwd(this.passwd)
  .host("pop.****.com")
  .port(995)
  .other("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
  .other("mail.pop3.socketFactory.fallback", "false")
  .other("mail.pop3.socketFactory.port", "995")
  .build();
// install eml instance with name 'receiver'
Eml.epm().install("receiver", rconf);
```

使用

```java
Eml.use(); // get eml instance with name 'main'
Eml.use("sender"); // get eml instance with name sender
Eml.use("receiver"); // get eml instance with name receiver
```

## with

1.6-beta 版本中, Eml 类存在 `with` 接口, `with` 接口要求 `EoEmlSession` 接口, 该接口是一个邮件类实例, 可以用来发送或收取邮件.

```java
Eml.with(new EnoaEmlSession(config))
```

:::danger
但是, 请知道, 这个方法将会在下一个版本中直接移除, 因此请不要使用. 请通过 `epm` 方法进行管理, 获取直接使用 `EoEmlSession` 在你的项目中.
:::

## EmlConfig

EmlConfig 是 Eml 组件 发送/接收 邮件服务器等相关设置, 发送于接收使用的是同一个配置, 下面表格会介绍相关配置含义:

| Name      | Default | Description                                          |
| --------- | ------- | -----------                                          |
| user      |         | 邮件服务器验证账户                                      |
| passwd    |         | 邮件服务器验证密码                                      |
| host      |         | 邮件服务器地址 SMTP/POP3/IMAP                           |
| port      |         | 邮件服务器端口                                          |
| debug     | false   | 是否调试模式                                           |
| ssl       | false   | 是否开启 SSL                                           |
| auth      | false   | 邮件服务器是否需要验证                                   |
| prop      |         | 1.6 版本调用 other 方法, 用于邮件服务器的属性配置           |
| skipError | false   | 是否跳过错误, 发送邮件如果失败, 依据此设置判断是抛出异常或跳过. |

Eml 默认使用 javax.mail 实现邮件功能, 因此配置最终会转交给 javax.mail.

## 发送邮件

通常发送邮件使用的是 SMTP 协议, 要成功使用发送邮件之前, 先设置好 SMTP 服务器信息.

```java
EmlConfig sconf = new EmlConfig.Builder()
  .skipError()
  .debug()
  .ssl()
  .auth()
  .user("user@example.com")
  .passwd(this.passwd)
  .host("smtp.****.com")
  .port(465)
  .build();
Eml.epm().install("sender", sconf);
```

发送邮件案例:

```java
EmlSender sender = Eml.use("sender")
  .sender()
  .charset(EoConst.CHARSET)
  .richtext()
  .from("user@example.com")
  .to("user@example.com")
  .cc("user@example.com")
  .bcc("user@example.com")
  .subject(TextKit.union("SUBJECT -> ", UUIDKit.next()))
  .body("<h1>BODY</h1>")
  .attachment(PathKit.debugPath().resolve("file/attachment.txt"))
  .reporter(stream -> System.out.println("FROM -> " + stream))
  .reporter(stream -> System.out.println("TO -> " + stream.tos()))
  .reporter(stream -> System.out.println("REQ BODY -> " + stream.req().string()))
  .reporter(stream -> System.out.println("EML -> " + stream.eml().string()))
  .reporter(stream -> System.out.println("SUBJECT -> " + stream.subject()))
  .emit();
```


- sender
  发送邮件模式
- richtext
  是否开启富文本
- from
  邮件发送人, 如果不提供, 则会选取 config 中的 user 字段, 多次调用将会选取最后一次传送的值
- to
  邮件接收人, 可以多个
- cc
  邮件抄送人, 可以多个
- bcc
  邮件密送, 可以多个
- subject
  邮件标题
- body
  邮件正文, 多次调用将会选取最后一次传送的值
- attachment
  附件, 可以多个
- reporter
  邮件汇报, 邮件发送前将会调用的方法, 将会把发送的邮件请求信息传送过来, 此方法会异步调用, 不影响邮件发送


## 收取邮件

:::warning
收取邮件尚未完成.
:::

## 异步收取

Eml 组件也支持异步操作, 同样基于 [Promise](#Promise), 使用 `DonePromise`. 因此受支持的 Promise 方法包括

- done
- capture
- always

e.g.

```java
EmlSender sender = Eml.use("sender")
  .sender()
  .charset(EoConst.CHARSET)
  .richtext()
  .from("user@example.com")
  .to("user@example.com")
  .cc("user@example.com")
  .bcc("user@example.com")
  .subject(TextKit.union("SUBJECT -> ", UUIDKit.next()))
  .body("<h1>BODY</h1>")
  .attachment(PathKit.debugPath().resolve("file/attachment.txt"))
  .reporter(stream -> System.out.println("FROM -> " + stream.from()))
  .reporter(stream -> System.out.println("TO -> " + stream.tos()))
  .reporter(stream -> System.out.println("REQ BODY -> " + stream.req().string()))
  .reporter(stream -> System.out.println("EML -> " + stream.eml().string()))
  .reporter(stream -> System.out.println("SUBJECT -> " + stream.subject()))
  .enqueue()
  .done(() -> System.out.println("Done..."))
  .capture(System.err::println)
  .always(() -> System.out.println("Always echo.."));
```

