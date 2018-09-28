

# RabbitMQ

## POM

```xml
<dependency>
  <groupId>io.enoa</groupId>
  <artifactId>enoa-rabbitmq</artifactId>
  <version>1.6-beta.2</version>
</dependency>
```

## EPM

Rabbit 元件使用 EPM 進行管理, 且支援多個例項.

```java
// install rabbit instance with name 'main'
Rabbit.epm().install(new RabbitConfig.Builder()
  .address("localhost", 5672)
  .name("main")
  .user("tmp")
  .passwd("passwd")
  .virtualHost("/")
  .build());
```

使用

```java
Rabbit.use(); // get rabbit instance with name 'main'
Rabbit.use("main"); // get rabbit instance with name 'main'
Rabbit.use("other"); // get rabbit instance with name 'other'
```


## 使用

### 宣告

```java
Rabbit.queueDeclare("test", false, false, false, null);
```


### 消費

```java
Rabbit.basicConsume("test", true, new DefaultConsumer(Rabbit.channel()) {
  @Override
  public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
    String message = new String(body, Charset.forName("UTF-8"));
    System.out.println(TextKit.format("Received '{0}'", message));
  }
});
```

### 傳送

```java
String message = "Hello World!";
Rabbit.basicPublish("", "test", null, message.getBytes(Charset.forName("UTF-8")));
System.out.println(TextKit.format("Sent '{0}'", message));
```

