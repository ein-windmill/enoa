

# EoxAccessor

EoxAccessor 是 Repeater 的重點, Repeater 中提供了一個預設的 EoxAccessor, [EnoaRepeaterAccessorImpl](https://github.com/fewensa/enoa/blob/master/enoa-repeater/src/main/java/io/enoa/repeater/EnoaRepeaterAccessorImpl.java "EnoaRepeaterAccessorImpl.java"), 預設的實現僅僅只是簡單的顯示 `It works!`, 告訴你 Repeater 正在工作.

但是這不妨礙我們來說明 EoxAccessor, 當建立一個 Repeater 伺服器之後, 所有的請求最終都會進入到 EoxAccessor 中, 因此我們可以在 EoxAccessor 中書寫我們的程式碼來執行, 來看下 EoxAccessor 的原始碼.

```java
public interface EoxAccessor {
  Response access(Request request);
}
```

僅僅只是簡單的一個介面, EoxAccessor 介面提供 access 方法來獲取請求, 會給出 Request 物件, 並且要求返回 Response 物件.

也來看下 Repeater 的預設實現程式碼:

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

這裡我們能看到一些倪端, 並且看到了 Response 物件是如何建立的, Response 物件更詳細的資訊, 將會在後面說明.

相信你應該能看懂預設實現的程式碼原理. 僅僅是返回 Response 並且在 Header 設定 Content-Type 為 text/html, 然後響應體是 "It works!".

那麼, 你可以點選這個[連結](https://github.com/fewensa/enoa/blob/master/enoa-example/example-repeater/src/main/java/io/enoa/example/repeater/ExampleRepeaterAccessorImpl.java "ExampleRepeaterAccessorImpl.java"), 來看一個相對較複雜的案例, 該案例返回的 Response 將會展示出請求的所有資訊.


## [Request](https://github.com/fewensa/enoa/blob/master/enoa-repeater/src/main/java/io/enoa/repeater/http/Request.java)

Request 是 EoxAccessor 介面中獲取到的請求資訊, 內部方法相對較多. 不過大致可以分為下面幾類:

1. 請求資訊
2. 請求引數
3. Header 引數
4. attr

此外還有一個特殊的方式是 `originRequest` 該方法是用來獲取原始 Request 型別的, 例如你使用的是 Jetty 伺服器, 那麼 `originRequest` 方法實際返回的物件是 `HttpServletRequest`.

### 請求資訊

請求資訊的方法包括

- method
  請求型別 GET POST PATCH DELET 等等
- context
  上下文連結
- uri
  URI
- url
  URL
- body
  請求體, 當請求以 post 形式發起是, 請求資料可以通過 body 方法獲取.

### 請求引數

請求引數方法較多, 但是大部分都是為了方便使用而提供的方法, 例如 `paraToInt` 是將獲取到的引數轉換為 Integer 型別, 同類型的還包括 `paraToLong` 等等.

下面列出一些比較不同的方法

- para
  獲取引數值
- paraMap
  獲取所有引數
- paraNames
  獲取所有引數名
- paraValues
  獲取某個引數名下的所有值, 返回陣列
- files
  上傳的所有檔案列表, 同名方法有個帶引數, 區別是, 不帶引數獲取的所有檔案, 帶引數獲取該名稱下的所有檔案
- file
  獲取單個檔案

```java
String name = request.para("name");
Map<String, String[]> map = request.paraMap();
String[] names = request.paraNames();
String[] items = request.paraValues("item");
UFile[] ufiles = request.files();
UFile[] ufs = request.files("file");
UFile uf = request.file("file");
```

如果請求是以 post 形式傳送, 並且 Content-Type 表明是表單請求, para 都是可以獲取到值的, 包括檔案上傳型別請求(form-data). 但是如果是其他型別, 則無法獲取(例如 Json 請求), 需要通過 body 方法.


### Header 引數

Header 引數方法相對較少, 但是要注意的是, Request 中也提供獲取 Cookie 的相關方法, 此類方法也歸檔於 Header 引數中

- header
  獲取單個 Header 值
- headerNames
  獲取所有 Header 名稱
- cookie
  獲取單個 Cookie 值
- cookieObject
  獲取單個 Cookie 物件
- cookies
  獲取所有 Cookie


```java
String[] names = request.headerNames();
String contentType = request.header("content-type");
String sessid = request.cookie("sessid");
Integer some = request.cookieToInt("some");
Cookie sessobj = request.cookie("sessid");
Cookie[] cookies = request.cookies();
```

:::success
header 方法引數名不區分大小寫.
:::

### attr

attr 是一個特別的存在, attr 並不屬於原始請求所攜帶的資料, 而是當請求到達時建立的一個物件, 生命週期僅在當次請求中. 並且 Request 提供設定 attr 的方法.

- attrNames
  所有屬性名
- attr
  獲取或者設定屬性
- rmAttr
  刪除屬性

```java
String[] names = request.attrNames();
String value = request.attr("key"); // get attr
request.attr("name", "Jack")
  .attr("id", 1); // set attr
request.rmAttr("id");
```

## [Response](https://github.com/fewensa/enoa/blob/master/enoa-repeater/src/main/java/io/enoa/repeater/http/Response.java)

Response 物件用於表述, 當次請求需要返回資料給請求者. 相對於 Request, Response 就簡單很多

- status
  響應狀態碼
- contentType
  響應資料型別
- contentLength
  響應體長度
- cookie
  響應 Cookie
- header
  響應 Header
- body
  響應體
- charset
  字元編碼

首先, 狀態碼可以參考 [HttpStatus](https://github.com/fewensa/enoa/blob/master/enoa-repeater/src/main/java/io/enoa/repeater/http/HttpStatus.java "HttpStatus.java"), 提供響應的狀態碼即可; 通常 200(OK) 表示成功, 30x 表示授權失敗, 40x 表示資源不存在, 50x 表示伺服器錯誤.

contentType 響應資料型別, 常見的包括 `text/html`, `application/json`.

contentLength 此資料通常不需要主動設定, Response 會自動設定, 但是要注意, 如果你需要主動設定必須要在呼叫 body 方法之後, 因為 body 方法會重新計算一次內容長度.

cookie 用於設定響應的資料設定 Cookie 記錄.

header 設定響應頭, 這裡需要特別注意的是, 不建議在 header 中設定 `set-cookie` 屬性, 這樣你會得到一個警告, 告訴你應該使用 cookie 方法, 因為 cookie 是可以新增多個的, 如果一段程式碼中你同時使用了 cookie 方法以及 header 中設定 `set-cookie`, 會容易造成混亂, 因此建議統一使用 cookie 方法. 雖然 `set-cookie` 也可以正常工作.

body 要求提供一個 ResponseBody 物件, 響應的訊息體, 該物件的建立很簡單, 呼叫 create 方法即可.

charset 響應資料編碼.


```java
new Response.Builder()
  .header("Content-Type", contentType)
  .body(ResponseBody.create("Hello World"))
  .build();
```

