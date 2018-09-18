package io.enoa.mq.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import io.enoa.toolkit.text.TextKit;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

@Ignore
public class RabbitTest {

  private String queue = "test";

  @Before
  public void before() {
    Rabbit.epm().install(new RabbitConfig.Builder()
      .address("localhost", 5672)
      .name("main")
      .user("tmp")
      .passwd("passwd")
      .virtualHost("/")
      .build());

    Rabbit.queueDeclare(this.queue, false, false, false, null);

  }

  @Test
  public void testSend() {
    String message = "Hello World!";
    Rabbit.basicPublish("", this.queue, null, message.getBytes(Charset.forName("UTF-8")));
    System.out.println(TextKit.format("Sent '{0}'", message));
    Rabbit.close();
  }

  @Test
  public void testConsume() {
    Rabbit.basicConsume(this.queue, true,
      new DefaultConsumer(Rabbit.channel()) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
          String message = new String(body, Charset.forName("UTF-8"));
          System.out.println(TextKit.format("Received '{0}'", message));
        }
      });
    try {
      TimeUnit.DAYS.sleep(1L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
