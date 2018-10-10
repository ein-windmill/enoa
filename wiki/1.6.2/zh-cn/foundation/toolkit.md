

# Toolkit

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-toolkit</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

toolkit 被多数 enoa 其他项目所依赖的一个模块, 其中包含很多实用的工具类. 此处举出部分案例.





# [TextKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/text/TextKit.java "TextKit.java")

> 字符操作

包含常见的字符操作方法

- blanky
  鉴定字符串是否是空字符串或者 null
- blankn
  非空字符串或非 null
- isBlank
  于 blanky 相同, 属常见方法命名, 在 enoa 中的命名风格中会尽量减少驼峰命名, 因为常用, 所以保留
- nospace
  去除字符串中的所有空格, 第二个参数表明是否激进模式, 激进模式将会连带 \r \n \t 等符号一同去除

更多方法参见源码.



# [TextReader](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/text/TextReader.java "TextReader.java")

> 文字读取

文字读取类主要用于针对一段字符进行分析的场景使用. 类中几个关键的字段 `len`, `position`, `line`, `cursor`, 以及重要的方法 `next`, `peek`, `back`.

**字段**

- len
  文字总长度
- position
  字符总游标
- line
  当前行数
- cursor
  行游标

**方法**

- next
  获取下一个字符, 并且游标 +1
- peek
  获取当前游标所在字符, 游标不变
- back
  获取前一个游标处所在字符, 游标 -1





# [BeanKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/bean/BeanKit.java) <sup>Beta</sup>

> 对象工具

提供对象于 Map 之间的互转, 目前互转的实现为数据的浅层 Copy, 无法做到深层数据互转.



# [EnoaBinary](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/binary/EnoaBinary.java)

> 针对 byte\[] 的快速 API

EnoaBinary 可以快速的处理 byte\[], 将 byte\[] 转换为目标类型数据.

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
  数组去重
- merge
  合并数组
- split
  等份切割, 按每份指定数量
- parts
  等份切割, 按指定份数

更多参见源码

# [ConvertKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/convert/ConvertKit.java "ConvertKit.java")

> 不同数据类型之间转换

- string
  将目标类型转换为 String
- longer
  将目标类型转换为 Longer
- bigdecimal
  将目标类型转换为 BigDecimal

特别注意:

- to
- supportConvert
- install

`to` 方法, 实际上是通过实现已注册过的类型才可进行数据的转换. 可通过 `install` 方法进行注册, 默认已实现的注册可见 [TypeConverter](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/convert/TypeConverter.java "TypeConverter.java") 查看, 相同类型将会被覆盖. `supportConvert` 查看当前是否已支持此类型转换.

更多参见源码


# [IConvert](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/convert/IConverter.java "IConvert.java")

> 类型转换功能接口

接口用在不同的数据类型之间转换, 提供转换的实现方案. 提供两个泛型参数 <R, P>,  R 返回的类型, P 传入的参数类型.

e.g.

```java
IConverter<Integer, String> converter = origin -> Integer.valueOf(origin);
Integer num = converter.convert("2"); // return 2
```


# [BaseKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/digest/base/BaseKit.java "BaseKit")

> Base64, Base62 等算法工具类

- ebase64
  base64 编码
- debase64
  base64 解码
- ebase64url
  base64 url 编码
- debase64url
  base64 url 解码
- ebase62
  base62 编码
- debase62
  base62 解码


# [DigestKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/digest/DigestKit.java "DigestKit.java")

> Hash 算法

- md5
- sha1
- sha256
- hex

特别注意:

hash 方法, hash 方法由使用者决定使用何种算法, 提供 java 支持的算法标识即可.

e.g.

```java
DigestKit.hash("SHA-256", "Hello world");
```

# [PasswdKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/digest/PasswdKit.java "PasswdKit.java")

> 密码工具类

- encrypt
- checkpwd

## 注意

> toolkit 包坚持无第三方依赖, `PasswdKit` 进行密码加密需要依赖 `BCrypt`, 因此如果需要使用, 请单独引入.
> 
> maven 座标: [jbcrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt)

# [ChecksumKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/file/ChecksumKit.java "ChecksumKit")

> 计算文件校验码

e.g.

```java
String checksum = ChecksumKit.md5(Paths.get("/tmp/hello.txt"));
```

# [FileKit](#https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/file/FileKit.java "FileKit.java")

> 文件处理工具类

文件处理都是已 `java.nio.file.Path` 来获取文件信息, 很少会用 `java.io.File`.

- delete
- exists
- mkdirs
- extension
- write
- read
- move
- probeContentType

## 注意

> 特别注意: 文件处理工具类提供的 `write` 以及 `read` 不具备大文件处理能力, 如果有大文件, 不建议使用此方法.

# [UrlKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/http/UrlKit.java "UrlKit.java")

> Url 处理


- encode

  Url 编码, 修复 java 连带参数编码问题

- decode
- correct
- analysis

  分析 URL, 返回 String[], 第一个是 host 第二个是剩余的部分


# [SnowflakeKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/id/snowflake/SnowflakeKit.java "SnowflakeKit.java")

> 生成 ID 算法

- init
- next
- longer
- string

使用前, 需要调用 init 方法进行初始化, ID 生成算法通过 snowflake 算法实现.

## 注意
> toolkit 包坚持无第三方依赖, `SnowflakeKit` 进行密码加密需要依赖 `snowflake`, 因此如果需要使用, 请单独引入.
>
> maven 座标: [snowflake](https://mvnrepository.com/artifact/xyz.downgoon/snowflake)

# [FastKv](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/map/FastKv.java "FastKv.java")

> Map 接口扩充

继承自 Map 接口, 为 Map 扩充更多实用接口.

- string
- longer
- integer
- bigdecimal

更多参见源码

Enoa 提供的实现: [Kv](#Kv), [OKv](#OKv)

# [Kv](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/map/Kv.java "Kv.java")

> 快速 Map 实现

Kv 实现 [FastKv](#FastKv) 接口, 并继承自 HashMap.

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

更多用法参见源码.

# [OKv](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/map/OKv.java "OKv.java")

功能于 [Kv](#Kv) 相同, 但是继承自 LinkedHashMap



# [NamecaseKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/namecase/NamecaseKit.java "NamecaseKit.java")

> 命名风格工具

- convert

  自定义风格

- nonecase

  不进行转换

- underline

  转换为下划线风格

- camelcaseupper

  大驼峰风格

- camelcaselower

  小驼峰风格
  
e.g.

```java
NamecaseKit.nonecase("HelloWorld"); // HelloWorld
NamecaseKit.underline("HelloWorld"); // hello_world
NamecaseKit.camelcaselower("HelloWorld"); // helloWorld
NamecaseKit.camelcaseupper("HelloWorld"); // HelloWorld
NamecaseKit.convert("HelloWorld", origin -> origin.replace("o", "e")); // HelleWerld
```

# [NumberKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/number/NumberKit.java "NumberKit.java")

> Number 互转

提供不同类型 Number 互转.

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

> 路径工具

- debugPath

  调试路径

- userHome

  当前系统账户路径

- resources

  工程环境变量路径

- path

  依据环境变量路径计算的路径

获取的路径均是 `java.nio.file.Path`


path 可用于生成环境, 依据于项目启动时提供的环境变量, 从环境变量中提取路径, 例如启动时 `-cp "/tmp:/tmp/conf:/tmp/resources"`, `PathKit.path("test")` 将会查找 `/tmp/test`, `/tmp/conf/test`, `/tmp/resources/test` 以第一个查找到的为准.

## 警告

> debugPath 仅仅用于调试, 且需要符合 maven 工程的目录约定, 不要用于生产环境

# [PropKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/prop/PropKit.java "PropKit.java")

> 配置文件读取

`PropKit` 会在启动的时候自动读取环境变量中目录的所有的 `properties` 文件, 因此无需手动设定读取文件的位置, 直接使用即可; 当然也是可以通过 `add` 方法进行手动设定配置文件路径. 

- add

  追加配置文件

- reload

  重新加载所有配置文件

- value

  获取配置值, 返回 [EnoaValue](#EnoaValue)

- string

  读取 String 值

更多方法参见源码.


# [Ret](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/ret/Ret.java "Ret.java")

> 返回数据接口


# [EoRet](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/ret/EoRet.java "EoRet.java")

> Ret 实现

实现自 Ret, 以及 FastKv, 实质上是一个 Map

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

`set` 以及 `setIf` 的区别是, `setIf` 只会在 `stat` 为 true 的状态下才写入到 EoRet 中. `resp` 方法, 将 EoRet 转化为 [EoResp](#EoResp).


# [EoResp](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/ret/EoResp.java "EoResp.java")

> 响应返回数据

响应返回固定模板. 数据格式:

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

更多用法参见源码.


# [StreamKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/stream/StreamKit.java "StreamKit.java")

> IO 流工具

- binary

  读取 InputStream 为 EnoaBinary

- bytes

  读取 InputStream 为 byte\[]

- close

  关闭流


# [EnvKit](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/sys/EnvKit.java "EnvKit.java")

> Java 配置读取

读取 Java `System.getenv()` 以及 `System.getProperties()` 中的配置.

e.g.

```java
// get system temp folder path
EnvKit.string("java.io.tmpdir");
```


# [EnoaValue](https://github.com/fewensa/enoa/blob/master/enoa-toolkit/src/main/java/io/enoa/toolkit/value/EnoaValue.java "EnoaValue.java")

> 通用值

通用值, 提供多种不同的数据类型转换方法.

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

文档中并没有列出所有的工具类, 以及释放方法, 可以通过参见源码进行了解.



