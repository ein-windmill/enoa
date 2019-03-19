

# Provider

Repeater 預設沒有搭載任何 Web 伺服器, 因此無論你要使用任何一個 Web 伺服器讓 Repeater 去中繼, 你都需要提供該 Web 伺服器的實現程式碼.

## NanoHttpd

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>repeater-nanohttpd</artifactId>
</dependency>
```

```java
Repeater.createServer(new NanoHTTPDProvider())
  .listen(9001);
```

## Netty

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>repeater-netty</artifactId>
</dependency>
```

```java
Repeater.createServer(new NettyProvider())
  .listen(9001);
```

## Jetty

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>repeater-jetty</artifactId>
</dependency>
```

```java
Repeater.createServer(new JettyProvicer())
  .listen(9001);
```

## tio

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>repeater-tio</artifactId>
</dependency>
```

```java
Repeater.createServer(new TioProvider())
  .listen(9001);
```

## FastCGI

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>repeater-fastcgi</artifactId>
</dependency>
```

```java
Repeater.createServer(new FastCGIProvider())
  .listen(9001);
```

## Tomcat

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>repeater-tomcat</artifactId>
</dependency>
```

```java
Repeater.createServer(new TomcatProvider())
  .listen(9001);
```

