package io.enoa.stove.api;

public class StoveException extends RuntimeException {
  public StoveException() {
    super();
  }

  public StoveException(String message) {
    super(message);
  }

  public StoveException(String message, Throwable cause) {
    super(message, cause);
  }

  public StoveException(Throwable cause) {
    super(cause);
  }
}
