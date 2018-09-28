

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

Eml 提供 EPM 進行管理, 可通過 epm 方法設定郵件伺服器相關資訊.

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

1.6-beta 版本中, Eml 類存在 `with` 介面, `with` 介面要求 `EoEmlSession` 介面, 該介面是一個郵件類例項, 可以用來發送或收取郵件.

```java
Eml.with(new EnoaEmlSession(config))
```

:::danger
但是, 請知道, 這個方法將會在下一個版本中直接移除, 因此請不要使用. 請通過 `epm` 方法進行管理, 獲取直接使用 `EoEmlSession` 在你的專案中.
:::

## EmlConfig

EmlConfig 是 Eml 元件 傳送/接收 郵件伺服器等相關設定, 傳送於接收使用的是同一個配置, 下面表格會介紹相關配置含義:

| Name      | Default | Description                                          |
| --------- | ------- | -----------                                          |
| user      |         | 郵件伺服器驗證賬戶                                      |
| passwd    |         | 郵件伺服器驗證密碼                                      |
| host      |         | 郵件伺服器地址 SMTP/POP3/IMAP                           |
| port      |         | 郵件伺服器埠                                          |
| debug     | false   | 是否除錯模式                                           |
| ssl       | false   | 是否開啟 SSL                                           |
| auth      | false   | 郵件伺服器是否需要驗證                                   |
| prop      |         | 1.6 版本呼叫 other 方法, 用於郵件伺服器的屬性配置           |
| skipError | false   | 是否跳過錯誤, 傳送郵件如果失敗, 依據此設定判斷是丟擲異常或跳過. |

Eml 預設使用 javax.mail 實現郵件功能, 因此配置最終會轉交給 javax.mail.

## 傳送郵件

通常傳送郵件使用的是 SMTP 協議, 要成功使用傳送郵件之前, 先設定好 SMTP 伺服器資訊.

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

傳送郵件案例:

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
  傳送郵件模式
- richtext
  是否開啟富文字
- from
  郵件傳送人, 如果不提供, 則會選取 config 中的 user 欄位, 多次呼叫將會選取最後一次傳送的值
- to
  郵件接收人, 可以多個
- cc
  郵件抄送人, 可以多個
- bcc
  郵件密送, 可以多個
- subject
  郵件標題
- body
  郵件正文, 多次呼叫將會選取最後一次傳送的值
- attachment
  附件, 可以多個
- reporter
  郵件彙報, 郵件傳送前將會呼叫的方法, 將會把傳送的郵件請求資訊傳送過來, 此方法會非同步呼叫, 不影響郵件傳送


## 收取郵件

:::warning
收取郵件尚未完成.
:::

## 非同步收取

Eml 元件也支援非同步操作, 同樣基於 [Promise](#Promise), 使用 `DonePromise`. 因此受支援的 Promise 方法包括

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

