package io.enoa.docker.dket.docker.node;

import io.enoa.docker.dket.AbstractDRRet;

public class EPlatform extends AbstractDRRet {

  private final String architecture;
  private final String os;
  private final String osversion;
  private final String[] osfeatures;
  private final String variant;
  private final String[] features;

  public EPlatform(Builder builder) {
    this.architecture = builder.architecture;
    this.os = builder.os;
    this.osversion = builder.osversion;
    this.osfeatures = builder.osfeatures;
    this.variant = builder.variant;
    this.features = builder.features;
  }

  public String architecture() {
    return this.architecture;
  }

  public String os() {
    return this.os;
  }

  public String osversion() {
    return this.osversion;
  }

  public String[] osfeatures() {
    return this.osfeatures;
  }

  public String variant() {
    return this.variant;
  }

  public String[] features() {
    return this.features;
  }

  public static class Builder {
    private String architecture;
    private String os;
    private String osversion;
    private String[] osfeatures;
    private String variant;
    private String[] features;

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

    public Builder osversion(String osversion) {
      this.osversion = osversion;
      return this;
    }

    public Builder osfeatures(String[] osfeatures) {
      this.osfeatures = osfeatures;
      return this;
    }

    public Builder variant(String variant) {
      this.variant = variant;
      return this;
    }

    public Builder features(String[] features) {
      this.features = features;
      return this;
    }
  }

}
