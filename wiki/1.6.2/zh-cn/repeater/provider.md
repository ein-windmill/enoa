

# Provider

Repeater 默认没有搭载任何 Web 服务器, 因此无论你要使用任何一个 Web 服务器让 Repeater 去中继, 你都需要提供该 Web 服务器的实现代码.

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

