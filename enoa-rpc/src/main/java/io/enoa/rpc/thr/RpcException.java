package io.enoa.rpc.thr;

import io.enoa.toolkit.thr.EoException;

public class RpcException extends EoException {

  public enum Type {
    HTTP,
    SCOKET
  }

  private Type type;

  public RpcException(Type type) {
    super();
    this.type = type;
  }

  public RpcException(Type type, String message, Object... format) {
    super(message, format);
    this.type = type;
  }

  public RpcException(Type type, String message, Throwable cause, Object... format) {
    super(message, cause, format);
    this.type = type;
  }

  public RpcException(Type type, Throwable cause) {
    super(cause);
    this.type = type;
  }

  public RpcException(Type type, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... format) {
    super(message, cause, enableSuppression, writableStackTrace, format);
    this.type = type;
  }

  public Type type() {
    return this.type;
  }
}
