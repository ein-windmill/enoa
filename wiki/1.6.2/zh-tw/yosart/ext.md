


# ext

Yosart 擴充套件用於進行高階功能擴充套件, Yosart 整個框架是完全基於擴充套件所實現的, 包括路由, 渲染等等.

Yosart 支援以下型別擴充套件

- BOOT_HOOK
  啟動鉤子, 將會在 Yosart 呼叫 Repeater 之前呼叫; 允許多個擴充套件
- ROUTER
  路由擴充套件, Yosart 所接收到的請求會全部分發到註冊的路由擴充套件上; 只允許一個擴充套件.
- ASSETS
  靜態資原始檔擴充套件; 只允許一個
- RENDER
  渲染擴充套件; 允許多個
- SESSION
  Session 擴充套件; 只允許一個

在 Yosart 中, 如果是隻允許一個的擴充套件, 多次新增以最後一個為標準. 例如:

```java
.ext(new AnostExt())
.ext(new AnostExt())
```

將會已第二個 AnostExt 的例項, 第一次新增的將會被拋棄.

## Anost

Anost 是 Yosart 預設搭載的路由擴充套件. 無需做任何配置即可使用. Anost 支援 Yosart 的多種路由形式, 包括 Action, Control 以及 Router. 支援 RESTful 風格 Api. 並且提供 Hook 進行請求過濾, 攔截, 資料驗證等功能.
如果有些特殊的需求, 例如自定義異常渲染, 全域性 Hook, 你可以主動建立一個 Anost 擴充套件, 加入到 Yosart 替換預設的 Anost 擴充套件.

Anost 的路由實現完全遵照 Yosart 標準, 因此 Yosart 標準提供的所有語法都可正常執行. 那麼這裡就詳細探討一下 Anost 提供的另外的功能, Anost 的配置, Hook 以及 Para 註解.

### Para

Yosart 提供的 Request 已經非常方便, 可以簡單快速的獲取請求內容. Anost 只是對其進行了擴充套件, 提供了 Paras 以及 Para 註解, 用於 Control 中, 可以通過註解獲取請求引數.

我們先從比較複雜的案例來理解

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

先不著急看程式碼, 來看看一下 Para 註解提供的方法

- index
  描述引數對應索引位置
- value
  引數名
- def
  預設值
- format
  時間格式化
- summary
  欄位描述

`value` 以及 `def` 很容易理解, `value` 是請求引數名, `def` 如果不存在該引數填充的預設值(空字串也算不存在).

`format` 作用於時間型別的引數, 當提供 format 時 Anost 則會將引數看作是時間型別, 就會根據提供的 format 進行時間轉換. 例如:

```text
?date=2001-02-01
@Para(value="date", format="yyyy-MM-dd")
```

`format` 使用什麼格式取決於請求傳遞的引數格式.

`summary` 描述欄位作用, 暫時不具備任何作用.

`index` 相對而言是比較容易混亂的設定, 來對照上面的程式碼來看, 方法簽名上提供了 3 個 Para 註解, 但是方法確有 4 個引數. 關於這個問題, 首先要知道的是, Para 不處理的幾個資料型別

- Request
- Renderer
- UFile
- File

當識別到方法引數是上面的型別時, Para 是不做處理的, 因此無需為其設定 Para 註解.
另外一個概念是, Para 的順序和方法引數的順序是一一對應的, 都是從 0 開始.
那麼結合上面兩個概念, `index` 的作用就是打破第二個概念, 使其不一一對應. `index` 去指定其應該對應哪個引數; 另外一點就是如果沒有指定 index 的 Para 註解仍然按照順序取索引.

再來看這段程式碼:

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

第一個 index 是 3, 那麼對應的就是 `Timestamp ts`, 第二個 index 是 1, 對應的就是 `String where`, 第三個沒有指定 index, 那麼取的索引就是 2, 也就對應著 `int zone`

相信你已經理解了.

可以看到還有 Paras 註解, 這個就處理多個引數的情況, 如果說請求只有一個引數時是可以省略的:

```java
@Para("name")
public String func(String name) {
  return "ok";
}
```

### hook

Anost 提供 Hook 功能, 用於在請求到達路由之前可以進行部分處理. 支援使用 Hook 註解方式追加到路由控制器中.

Hook 按功能區分有兩種, 一種是全域性的, 另外就是區域性使用.

#### 全域性

全域性 Hook 需要在 AnostExt 物件中獲取 AnostHookMgr 進行新增:

```java
AnostExt anost = new AnostExt();
AnostHookMgr mgr = anost.hookMgr();
mgr.load(new GlobalHook())
  .load("/manager", new ManagerHook())
  .load("/manager/pre_*", new PreHook(), AnostHookMgr.Mode.REGEX)
```

load 有兩種模式, 限定 Api 和不限定; 當限定 Api 時, 需要指定匹配 Api 的模式, 預設是全匹配.


```java
mgr.load(new GlobalHook())
```

不限定 Api 的 Hook 將會作用於所有的介面.

```java
mgr.load("/manager", new ManagerHook());
mgr.load("/manager/pre_*", new PreHook(), AnostHookMgr.Mode.REGEX);
```

限定 Api 的 Hook 僅會作用於匹配到的介面, 匹配模式有三種

- FULL
  Api 全匹配
- PREFIX
  字首匹配
- REGEX
  正則匹配

預設全匹配模式


#### 區域性

除了全域性 Hook 之後, 也可以針對單個路由指定 Hook. 使用 `@Hook` 註解新增即可.

```java
@Hook({ValidHook.class})
public String index(){
  return "ok";
}
```

#### 消除

Hook 的消除同樣使用 AnostHookMgr 來完成, Hook 消除完全依據規則進行消除, 可以消除為某個介面設定的 Hook, 只要規則能匹配上即可

```java
AnostExt anost = new AnostExt();
AnostHookMgr mgr = anost.hookMgr();
mgr.unload(GlobalHook.class)
  .unload(ManagerHook.class, "/manager")
  .unload(PreHook.class, AnostHookMgr.Mode.REGEX, "/manager/pre_*");
```

於裝載全域性 Hook 相同, 消除 Hook 也是兩種模式, 作用完全於裝載全域性 Hook 相同. 並且支援消除使用 `@Hook` 註解新增的 Hook.


### IHook

Hook 物件的編寫很簡單, 實現 IHook 介面即可.

e.g.

```java
public class IndexHook implements IHook {
  @Override
  public void hook(YoRequest request, Resp resp) throws HookException {

  }
}

```

在 hook 方法中即可以在進入到 Control 之前進行部分操作.

:::info
hook 方法中會傳遞一個 Resp 物件, 該物件是 Anost 收到請求後就會建立的一個響應物件, 因此在這裡使用 resp 新增的資料最終都會寫入到 Response 中.
:::

IHook 介面, 允許丟擲 `HookException`, Anost 並不會對 HookException 進行任何處理, 只會冒泡的上游, 因此建議搭配 [exception-extension](#exception-extension) 擴充套件一同使用, 效果更加.

### 配置

Anost 路由預設是不需要任何配置就可以使用的, 考慮到開發的實際情況, 使用者可以考慮進行部分擴充套件, 替換掉預設的 Anost 路由機制.

擴充套件的方式就是建立一個新的 Anost 擴充套件, 並在 Yosart 中加入. 從建立的這個 Anost 中可以設定包括 Hook, Anost 序列化方式等等.

```java
AnostExt anost = new AnostExt();
Yosart.createServer()
  .ext(anost)
  .listen(9001)
```

關於 Hook 的設定, 上文已經介紹過, 這裡就不再說明, 來看一下 Anost 的序列化方法. Anost 提供一個介面 `AnostSerializer` 用於序列化. 這裡說的序列化並不是要進行物件的序列化, 準確的說是提供一種資料轉換規則而已.

Anost 支援 Control 方法返回任意一個物件, 那麼這個序列化要做的事情就是, 當 Control 中返回了一個物件後, 這個物件應該如何處理這個物件, 將物件序列化成最終的資料返回給請求者. Anost 的預設方案是呼叫物件的 `toString` 方法, 在大部分情況下, 者肯定是不滿足的, 因此可以提供一個你所需要的序列化方案.

序列化的設定, 通過建立 Anost 擴充套件的構造方法傳入.

```java
AnostExt anost = new AnostExt(new SomeSerializer());
Yosart.createServer()
  .ext(anost)
  .listen(9001)
```

一個簡單的序列化實現

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

這個簡單的實現就是用來將 Control 返回的物件轉為 Json 字串返回給請求者.

### Valid

Anost 提供了強大, 並且簡單易用的資料驗證工具. 通常 Valid 建議用在 Hook 中, 提前在進入 Control 之前進行驗證.

建立 Valid 驗證物件

```java
Valid valid = Valid.with(request);
```

Valid 提供多種不同型別的資料驗證, `string`, `number`, 等等, 下面將會都列出說明; Valid 驗證失敗會丟擲 `ValidException` 異常.

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

value 用於固定值提前驗證. 而非 Valid 自動獲取

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

只有存在 email 引數是才會進行驗證

## Session

Yosart 提供 Session 擴充套件, 並且支援使用檔案儲存或者 Redis 儲存兩種方案.

**檔案儲存**

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

檔案儲存需要提供四個引數

1. Session 鍵名
2. 檔案儲存路徑
3. 物件序列化方案
4. 重啟是否有效

**Redis 儲存**

```java
Yosart.createServer()
  .ext(new SessExt(
    new RedisSession("YOSESS",
      new RedisPlugin("SESS_REDIS","localhost", 6379, new JdkProvider()),
      true))
    )
  .listen(9001);
```

Redis 儲存需要提供三個引數

1. Session 鍵名
2. RedisPlugin
3. 重啟是否有效

## render

Yosart 提供渲染擴充套件, Yosart 自身沒有實現所有的渲染擴充套件, 因為部分渲染擴充套件需要依賴第三方庫進行實現, 因此沒有主動繼承, 由使用者自行決定, 或者使用者有自己的渲染方案, 同樣需要提供支援. 下面是 Yosart 所支援的渲染擴充套件.

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

標記為 NO 的渲染方式, Yosart 均未直接實現, 需要由第三方庫去實現, Yosart 提供了 template 以及 json 的第三方實現.

### render-json

POM 直接使用 Json Plugin 即可, Json Plugin 中提供了一個 Json Render. 使用的時候直接加入 Json 擴充套件即可.

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

:::warning
這個 POM 只是引入擴充套件, 具體選用什麼模板需要單獨引入. [Template](#Template)
:::

#### 使用

```java
Yosart.createServer()
  .ext(new TemplateRenderExt(new FreemarkerProvider(),
    PathKit.debugPath().resolve("src/main/tpl/"),
    "ftl"))
  .listen(9001);
```


### 自定義 Render


除 Yosart 提供的渲染介面之外, 也可以新增自定義渲染外掛. 自定義很簡單, 只需要實現渲染介面即可, 例如一個簡單的案例:

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

在 Yosart 啟動時加入該渲染擴充套件

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

Yosart 提供的擴充套件中, 提供一個 Exception 的擴充套件, 此擴充套件的作用是攔截 Yosart 中丟擲的所有異常, 並做出響應.

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

assets 方法用於處理資原始檔處理請求, assets 脫離路由表, Yosart 提供預設的實現, 使用者可通過擴充套件使用自己的實現.

```java
Yosart.createServer()
  .assets("/assets", Paths.get(EnvKit.string("java.io.tmpdir")), true)
  .listen(9001);
```

預設的實現提供三個引數, 第一個是請求的 URI, 第二個資原始檔路徑, 第三個是否顯示樹形的檔案目錄.

使用自己實現的靜態資源可以參照 [EyAssetsExt](https://github.com/fewensa/enoa/blob/master/enoa-yosart/src/main/java/io/enoa/yosart/ext/assets/EyAssetsExt.java "EyAssetsExt.java")

