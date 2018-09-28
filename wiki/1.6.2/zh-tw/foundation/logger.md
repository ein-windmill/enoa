

# Logger

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-log</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Logger 元件, 用於整合日誌框架, 提供統一的日誌介面, 並且對其進行優化, 無需到處定義 `private static Logger log` 類似的程式碼, 全部通過 `Log.debug` 靜態方法使用即可.

受支援的日誌框架:

- JdkLog
- Slf4j
- Logback
- Log4j
- Log4j2


Enoa log 預設搭載 JdkLog 以及 Slf4j, 預設實現選用 Slf4j, 如果沒有使用 JdkLog, 其他日誌框架需要單獨引入.

## EPM

Log epm 方法, 用於選取 Log 所使用的框架.

e.g.

```java
Log.epm().install(new JdkLogProvider());
Log.epm().install(new Slf4JLogProvider());

Log.use("name").debug("debug");
```

:::warning
於其他元件 EPM 不同的是, Log EPM 盡支援單個例項, 通常專案中不應該使用多種日誌框架. 同時 use 方法也不是選取例項的意思, 而是相當於 `LoggerFactory.getLogger("name")`
:::

:::danger
大多數日誌框架提供給 Marker 介面, 由於每個框架的 Marker 介面都不相同, 因此也不便於做轉換, 因此 Enoa Log 中沒有實現 Marker 相關介面, 如果你的工程中需要使用 Marker 請放棄 Enoa Log.
:::

## Slf4j

```xml
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-api</artifactId>
  <scope>1.7.25</scope>
</dependency>
```

```java
Log.epm().install(new Slf4JLogProvider());
```

## Logback

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-log-logback</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
Log.epm().install(new LogbackProvider());
```

## Log4j

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-log-log4j</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
Log.epm().install(new Log4JProvider());
```

## Log4j2

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-log-log4j2</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

```java
Log.epm().install(new Log4J2Provider());
```

## 使用

在任何你想列印日誌的地方, 使用 Log 類提供的方法, 最終會呼叫安裝的提供者進行實現.

```java
Log.debug("Debug log");
Log.info("Info log");
Log.warn("Warn log");
Log.error("Error log");
Log.debug(Log.name());
Log.use("TEST").debug("test");
Log.debug("============");
Log.debug("Debug log");
Log.info("Info log");
Log.warn("Warn log");
Log.error("Error log");
Log.debug(Log.name());
Log.use(LogTest.class).debug("Log test");
Log.debug("============");
Log.debug("Debug log");
Log.info("Info log");
Log.warn("Warn log");
Log.error("Error log");
Log.debug(Log.name());
```

