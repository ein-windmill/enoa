package io.enoa.docker.dket.common;

import io.enoa.toolkit.mark.IMarkVal;

public enum NetProtocol implements IMarkVal {

  TCP("tcp"),
  UDP("udp"),
  //
  ;

  private final String val;

  NetProtocol(String val) {
    this.val = val;
  }

  @Override
  public String val() {
    return val;
  }

  public static NetProtocol of(String val) {
    if (val == null)
      return null;
    for (NetProtocol protocol : NetProtocol.values()) {
      if (protocol.val.equalsIgnoreCase(val))
        return protocol;
    }
    return null;
  }

}
