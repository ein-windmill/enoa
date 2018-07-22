package io.enoa.example.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.MessageProperties;
import io.enoa.mq.rabbitmq.Rabbit;
import io.enoa.mq.rabbitmq.RabbitConfig;
import io.enoa.toolkit.text.TextKit;

import java.nio.charset.Charset;

public class RabbitMQExample {

  public void start() {
    this.before();
    this.declare();

    this.push();
    this.consume();
  }


  private void before() {
    Rabbit.epm().install(new RabbitConfig.Builder()
      .address("localhost", 5672)
      .name("main")
      .user("tmp")
      .passwd("passwd")
      .virtualHost("/")
      .build());
  }

  private void declare() {
    Rabbit.queueDelete("test");
    Rabbit.queueDeclare("test", true, false, false, null);
  }


  private void push() {
    String message = "Hello World!";
    Rabbit.basicPublish("", "test", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(Charset.forName("UTF-8")));
    System.out.println(TextKit.format("Sent '{0}'", message));
//    Rabbit.close();
  }

  private void consume() {
    Rabbit.basicConsume("test", true,
      new DefaultConsumer(Rabbit.channel()) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
          String message = new String(body, Charset.forName("UTF-8"));
          System.out.println(TextKit.format("Received '{0}'", message));
        }
      });
  }

}
