package io.enoa.mq.rabbitmq;

public class ERabbitMQException extends RuntimeException {

  public ERabbitMQException() {
    super();
  }

  public ERabbitMQException(String message) {
    super(message);
  }

  public ERabbitMQException(String message, Throwable cause) {
    super(message, cause);
  }

  public ERabbitMQException(Throwable cause) {
    super(cause);
  }

  protected ERabbitMQException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
