

# Logger

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-log</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

Logger 组件, 用于整合日志框架, 提供统一的日志接口, 并且对其进行优化, 无需到处定义 `private static Logger log` 类似的代码, 全部通过 `Log.debug` 静态方法使用即可.

受支持的日志框架:

- JdkLog
- Slf4j
- Logback
- Log4j
- Log4j2


Enoa log 默认搭载 JdkLog 以及 Slf4j, 默认实现选用 Slf4j, 如果没有使用 JdkLog, 其他日志框架需要单独引入.

## EPM

Log epm 方法, 用于选取 Log 所使用的框架.

e.g.

```java
Log.epm().install(new JdkLogProvider());
Log.epm().install(new Slf4JLogProvider());

Log.use("name").debug("debug");
```

:::warning
于其他组件 EPM 不同的是, Log EPM 尽支持单个实例, 通常项目中不应该使用多种日志框架. 同时 use 方法也不是选取实例的意思, 而是相当于 `LoggerFactory.getLogger("name")`
:::

:::danger
大多数日志框架提供给 Marker 接口, 由于每个框架的 Marker 接口都不相同, 因此也不便于做转换, 因此 Enoa Log 中没有实现 Marker 相关接口, 如果你的工程中需要使用 Marker 请放弃 Enoa Log.
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

在任何你想打印日志的地方, 使用 Log 类提供的方法, 最终会调用安装的提供者进行实现.

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

