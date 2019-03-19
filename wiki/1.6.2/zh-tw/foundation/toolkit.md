

# Toolkit

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-toolkit</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

toolkit 被多數 enoa 其他專案所依賴的一個模組, 其中包含很多實用的工具類. 此處舉出部分案例.





# [TextKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/text/TextKit.java "TextKit.java")

> 字元操作

包含常見的字元操作方法

- blanky
  鑑定字串是否是空字串或者 null
- blankn
  非空字串或非 null
- isBlank
  於 blanky 相同, 屬常見方法命名, 在 enoa 中的命名風格中會盡量減少駝峰命名, 因為常用, 所以保留
- nospace
  去除字串中的所有空格, 第二個引數表明是否激進模式, 激進模式將會連帶 \r \n \t 等符號一同去除

更多方法參見原始碼.



# [TextReader](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/text/TextReader.java "TextReader.java")

> 文字讀取

文字讀取類主要用於針對一段字元進行分析的場景使用. 類中幾個關鍵的欄位 `len`, `position`, `line`, `cursor`, 以及重要的方法 `next`, `peek`, `back`.

**欄位**

- len
  文字總長度
- position
  字元總遊標
- line
  當前行數
- cursor
  行遊標

**方法**

- next
  獲取下一個字元, 並且遊標 +1
- peek
  獲取當前遊標所在字元, 遊標不變
- back
  獲取前一個遊標處所在字元, 遊標 -1





# [BeanKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/bean/BeanKit.java) <sup>Beta</sup>

> 物件工具

提供物件於 Map 之間的互轉, 目前互轉的實現為資料的淺層 Copy, 無法做到深層資料互轉.



# [EnoaBinary](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/binary/EnoaBinary.java)

> 針對 byte\[] 的快速 API

EnoaBinary 可以快速的處理 byte\[], 將 byte\[] 轉換為目標型別資料.

e.g.

```java
Charset charset = Charset.forName("UTF-8");
String text = "Hello world";
byte[] bytes = text.getBytes(charset);
EnoaBinary binary = EnoaBinary.create(bytes, charset);

byte[] b0 = binary.bytes(); // binary to bytes[]
String t0 = binary.string(); // binary to string
ByteBuffer buffer = binary.bytebuffer(); // binary to bytebuffer
```

# [CollectionKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/collection/CollectionKit.java)

> 集合操作

- isEmpty
  是否空集合
- notEmpty
  是否非空集合
- distinct
  陣列去重
- merge
  合併陣列
- split
  等份切割, 按每份指定數量
- parts
  等份切割, 按指定份數

更多參見原始碼

# [ConvertKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/convert/ConvertKit.java "ConvertKit.java")

> 不同資料型別之間轉換

- string
  將目標型別轉換為 String
- longer
  將目標型別轉換為 Longer
- bigdecimal
  將目標型別轉換為 BigDecimal

特別注意:

- to
- supportConvert
- install

`to` 方法, 實際上是通過實現已註冊過的型別才可進行資料的轉換. 可通過 `install` 方法進行註冊, 預設已實現的註冊可見 [TypeConverter](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/convert/TypeConverter.java "TypeConverter.java") 檢視, 相同型別將會被覆蓋. `supportConvert` 檢視當前是否已支援此型別轉換.

更多參見原始碼


# [IConvert](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/convert/IConverter.java "IConvert.java")

> 型別轉換功能介面

介面用在不同的資料型別之間轉換, 提供轉換的實現方案. 提供兩個泛型引數 <R, P>,  R 返回的型別, P 傳入的引數型別.

e.g.

```java
IConverter<Integer, String> converter = origin -> Integer.valueOf(origin);
Integer num = converter.convert("2"); // return 2
```


# [BaseKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/digest/base/BaseKit.java "BaseKit")

> Base64, Base62 等演算法工具類

- ebase64
  base64 編碼
- debase64
  base64 解碼
- ebase64url
  base64 url 編碼
- debase64url
  base64 url 解碼
- ebase62
  base62 編碼
- debase62
  base62 解碼


# [DigestKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/digest/DigestKit.java "DigestKit.java")

> Hash 演算法

- md5
- sha1
- sha256
- hex

特別注意:

hash 方法, hash 方法由使用者決定使用何種演算法, 提供 java 支援的演算法標識即可.

e.g.

```java
DigestKit.hash("SHA-256", "Hello world");
```

# [PasswdKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/digest/PasswdKit.java "PasswdKit.java")

> 密碼工具類

- encrypt
- checkpwd

## 注意

> toolkit 包堅持無第三方依賴, `PasswdKit` 進行密碼加密需要依賴 `BCrypt`, 因此如果需要使用, 請單獨引入.
> 
> maven 座標: [jbcrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt)

# [ChecksumKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/file/ChecksumKit.java "ChecksumKit")

> 計算檔案校驗碼

e.g.

```java
String checksum = ChecksumKit.md5(Paths.get("/tmp/hello.txt"));
```

# [FileKit](#https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/file/FileKit.java "FileKit.java")

> 檔案處理工具類

檔案處理都是已 `java.nio.file.Path` 來獲取檔案資訊, 很少會用 `java.io.File`.

- delete
- exists
- mkdirs
- extension
- write
- read
- move
- probeContentType

## 注意

> 特別注意: 檔案處理工具類提供的 `write` 以及 `read` 不具備大檔案處理能力, 如果有大檔案, 不建議使用此方法.

# [UrlKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/http/UrlKit.java "UrlKit.java")

> Url 處理


- encode

  Url 編碼, 修復 java 連帶引數編碼問題

- decode
- correct
- analysis

  分析 URL, 返回 String[], 第一個是 host 第二個是剩餘的部分


# [SnowflakeKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/id/snowflake/SnowflakeKit.java "SnowflakeKit.java")

> 生成 ID 演算法

- init
- next
- longer
- string

使用前, 需要呼叫 init 方法進行初始化, ID 生成演算法通過 snowflake 演算法實現.

## 注意
> toolkit 包堅持無第三方依賴, `SnowflakeKit` 進行密碼加密需要依賴 `snowflake`, 因此如果需要使用, 請單獨引入.
>
> maven 座標: [snowflake](https://mvnrepository.com/artifact/xyz.downgoon/snowflake)

# [FastKv](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/map/FastKv.java "FastKv.java")

> Map 介面擴充

繼承自 Map 介面, 為 Map 擴充更多實用介面.

- string
- longer
- integer
- bigdecimal

更多參見原始碼

Enoa 提供的實現: [Kv](#Kv), [OKv](#OKv)

# [Kv](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/map/Kv.java "Kv.java")

> 快速 Map 實現

Kv 實現 [FastKv](#FastKv) 介面, 並繼承自 HashMap.

e.g.

```java
// create a kv
Kv kv = Kv.create()
  .set("name", "Jack")
  .set("age", 20);

// another version
Kv kv = Kv.by("name", "Jack")
  .set("age", 20);

// get value
Integer age = kv.integer("age");
// default value
String job = kv.string("job", "Engineer");
```

更多用法參見原始碼.

# [OKv](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/map/OKv.java "OKv.java")

功能於 [Kv](#Kv) 相同, 但是繼承自 LinkedHashMap



# [NamecaseKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/namecase/NamecaseKit.java "NamecaseKit.java")

> 命名風格工具

- convert

  自定義風格

- nonecase

  不進行轉換

- underline

  轉換為下劃線風格

- camelcaseupper

  大駝峰風格

- camelcaselower

  小駝峰風格
  
e.g.

```java
NamecaseKit.nonecase("HelloWorld"); // HelloWorld
NamecaseKit.underline("HelloWorld"); // hello_world
NamecaseKit.camelcaselower("HelloWorld"); // helloWorld
NamecaseKit.camelcaseupper("HelloWorld"); // HelloWorld
NamecaseKit.convert("HelloWorld", origin -> origin.replace("o", "e")); // HelleWerld
```

# [NumberKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/number/NumberKit.java "NumberKit.java")

> Number 互轉

提供不同型別 Number 互轉.

e.g.

```java
BigDecimal bd0 = new BigDecimal(2.10001010);
Number c0 = NumberKit.to(bd0, Double.class);
Number c1 = NumberKit.to(c0, int.class);
Integer c2 = NumberKit.integer(bd0);
Long c3 = NumberKit.longer(bd0);
BigDecimal c4 = NumberKit.bigdecimal(310.19304);
```

# [PathKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/path/PathKit.java "PathKit.java")

> 路徑工具

- debugPath

  除錯路徑

- userHome

  當前系統賬戶路徑

- resources

  工程環境變數路徑

- path

  依據環境變數路徑計算的路徑

獲取的路徑均是 `java.nio.file.Path`


path 可用於生成環境, 依據於專案啟動時提供的環境變數, 從環境變數中提取路徑, 例如啟動時 `-cp "/tmp:/tmp/conf:/tmp/resources"`, `PathKit.path("test")` 將會查詢 `/tmp/test`, `/tmp/conf/test`, `/tmp/resources/test` 以第一個查詢到的為準.

## 警告

> debugPath 僅僅用於除錯, 且需要符合 maven 工程的目錄約定, 不要用於生產環境

# [PropKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/prop/PropKit.java "PropKit.java")

> 配置檔案讀取

`PropKit` 會在啟動的時候自動讀取環境變數中目錄的所有的 `properties` 檔案, 因此無需手動設定讀取檔案的位置, 直接使用即可; 當然也是可以通過 `add` 方法進行手動設定配置檔案路徑. 

- add

  追加配置檔案

- reload

  重新載入所有配置檔案

- value

  獲取配置值, 返回 [EnoaValue](#EnoaValue)

- string

  讀取 String 值

更多方法參見原始碼.


# [Ret](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/ret/Ret.java "Ret.java")

> 返回資料介面


# [EoRet](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/ret/EoRet.java "EoRet.java")

> Ret 實現

實現自 Ret, 以及 FastKv, 實質上是一個 Map

e.g.

```java
EoRet ret = EoRet.builder()
  .ok()
  .set("name", "Jack")
  .build();

EoRet ret = EoRet.builder()
  .stat(true)
  .setIf("id", "1")
  .build();

EoResp resp = ret.resp();
```

`set` 以及 `setIf` 的區別是, `setIf` 只會在 `stat` 為 true 的狀態下才寫入到 EoRet 中. `resp` 方法, 將 EoRet 轉化為 [EoResp](#EoResp).


# [EoResp](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/ret/EoResp.java "EoResp.java")

> 響應返回資料

響應返回固定模板. 資料格式:

```json
{
  "stat": 1,
  "message": "message",
  "data": object
}
```

e.g.

```java
EoResp.ok().message("ok").data(object);
EoResp.build(EoResp.Stat.OK, object);
```

更多用法參見原始碼.


# [StreamKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/stream/StreamKit.java "StreamKit.java")

> IO 流工具

- binary

  讀取 InputStream 為 EnoaBinary

- bytes

  讀取 InputStream 為 byte\[]

- close

  關閉流


# [EnvKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/sys/EnvKit.java "EnvKit.java")

> Java 配置讀取

讀取 Java `System.getenv()` 以及 `System.getProperties()` 中的配置.

e.g.

```java
// get system temp folder path
EnvKit.string("java.io.tmpdir");
```


# [EnoaValue](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/value/EnoaValue.java "EnoaValue.java")

> 通用值

通用值, 提供多種不同的資料型別轉換方法.

e.g.

```java
// convert string to integer
EnoaValue.with("1").integer();
// convert string to long
EnoaValue.with("1").longer();
// default value
EnoaValue.with(null).integer(1);
```


# 其他

文件中並沒有列出所有的工具類, 以及釋放方法, 可以通過參見原始碼進行了解.



