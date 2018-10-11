package io.enoa.docker.dret.node;

import io.enoa.docker.dret.AbstractDRet;

public class EPlatform extends AbstractDRet {

  private final String architecture;
  private final String os;

  public EPlatform(Builder builder) {
    this.architecture = builder.architecture;
    this.os = builder.os;
  }

  public String architecture() {
    return this.architecture;
  }

  public String os() {
    return this.os;
  }

  public static class Builder {
    private String architecture;
    private String os;

    public EPlatform build() {
      return new EPlatform(this);
    }

    public Builder architecture(String architecture) {
      this.architecture = architecture;
      return this;
    }

    public Builder os(String os) {
      this.os = os;
      return this;
    }
  }

}
