

# 檔案上傳

各 Web 伺服器 對文件上傳的支援也不禁相同, 有的有, 有的沒有, 下面列出目前已支援的伺服器對文件上傳的支援情況

| SERVER         | SUPPORT | DESCRIPTION                            |
| -------------- | ------- | -------------------------------------- |
| FastCGI        | NO      | 不支援                                    |
| Jetty          | YES     | Jetty 自身支援文件上傳, 需要設定引數 (Repeater 中已設定) |
| NanoHttpd      | -       | NanoHttpd 自身是有文件上傳的, 但是存在很多問題          |
| Netty          | YES     |                                        |
| tio-httpserver | YES     | tio 支援文件上傳, 不過其實現方式和其他伺服器的方式完全不同       |
| Tomcat         | YES     | 與 Jetty 類似.                            |

- NanoHttpd

  NanoHttpd 實際上是支援文件上傳的, 不過存在較多問題, 其上傳的文件強制按照其內部實現方式並不容易進行提取, 且放置與 http 引數中, 因此 Repeater 中, 未支援 Nanohttp 的預設文件上傳. 實際上其官方提供的文件上傳[案例](https://github.com/NanoHttpd/nanohttpd)也不是使用預設方式, 而是通過 `common-upload` 元件實現. 

- tio-httpserver

  tio-httpserver 支援文件上傳, 但是其實現方式與其他伺服器的支援完全不同, 其他伺服器對於文件上傳都是通過 InputStream 或者提供相應的方法進行獲取. tio-httpserver 不同的是, 其不提供獲取 InputStream, 而是將上傳的文件放置與引數中, 該引數值的型別是 UplodFile, 且獲取的值是 byte 陣列, 因此 Repeater 的做法是直接將該陣列儲存到文件中去.


**注意**:

Repeater 的文件上傳機制實際上對各提供者做了改造, 通常文件上傳到臨時文件並不會在接收到請求就馬上進行儲存, 而是在呼叫獲取文件的方法後再儲存到臨時文件去.

然而這是不必要的, 在 Repeater 中, 文件上傳接收到請求後就會將文件寫入到預先配置的臨時目錄中. 後續通過 Request 獲取到的已經是儲存後的文件資訊.

本身文件上傳就應該是 Web 伺服器應該做的事情, 而上游的應用服務應該專注與業務邏輯. 且在 Servlet 中也有一些限制, 文件上傳的表單, 必須要先獲取文件後才能獲取到其他引數, 否則會導致無法獲取文件的問題, 在 Repeater 的統一結構下, 避免了這個問題, 上游可以隨意順序呼叫, 因為文件上傳已經被 Repeater 處理過.


## cos

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>repeater-cos</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

為了彌補各伺服器的文件上傳的支援不同, 而引入的第三方上傳元件, COS 元件來自於 [oreilly cos](http://www.servlets.com/cos/) 是一個高效能的文件上傳元件, cos 上傳元件是基於 Servlet 實現的, 這裡將 cos 元件拿過來剔除了 Servlet 依賴, 且刪除了和文件上傳無關的類.

各伺服器對 cos 上傳元件的支援


| SERVER         | SUPPORT |
| -------------- | ------- |
| FastCGI        | YES     |
| Jetty          | YES     |
| NanoHttpd      | YES     |
| Netty          | YES     |
| tio-httpserver | NO      |
| Tomcat         | YES     |

- NanoHttpd
  cos 上傳也是 Repeater 中 NanoHttpd 唯一支援上傳的方式

- tio-httpserver
  tio 因其特殊性, 無法支援 cos, cos 上傳通過 InputStream 進行寫入, tio 若要相容就需要將 byte 陣列轉化為 InputStream, 這樣完全是浪費.

對於本身已支援文件上傳的提供者, 預設走的就是提供者自己的上傳方式, 若要切換為 cos 上傳, 在 Repeater 啟動時提供配置告知即可, 下列表格是相應的伺服器告知鍵.

| SERVER         | KEY                           | VALUE | DESCRIPTION                         |
| -------------- | ----------------------------- | ----- | ----------------------------------- |
| FastCGI        |                               |       | 無需提供, FastCGI 文件上傳必須使用 cos   |
| NanoHttpd      |                               |       | 無需提供, NanoHttpd 文件上傳必須使用 cos |
| tio-httpserver |                               |       | 不支援                                |
| Jetty          | provider.jetty.upload.vendor  | cos   | -                                    |
| Netty          | provider.netty.upload.vendor  | cos   | -                                    |
| Tomcat         | provider.tomcat.upload.vendor | cos   | -                                    |

## 使用案例

cos 的使用很簡單, 首先確認所使用的 Web 伺服器支援 cos 元件, 引入 cos pom, 然後在啟動 Repeater 時配置相應的值即可.

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

無需設定, NanoHttpd 預設使用 cos 元件, 因此引入 cos pom 即可.

### FastCGI

無需設定, FastCGI 預設使用 cos 元件, 因此引入 cos pom 即可.

