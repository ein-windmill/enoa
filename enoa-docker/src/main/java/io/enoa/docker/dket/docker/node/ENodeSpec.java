package io.enoa.docker.dket.docker.node;

import io.enoa.docker.dket.AbstractDRRet;
import io.enoa.toolkit.map.Kv;

public class ENodeSpec extends AbstractDRRet {

  private final String availability;
  private final String name;
  private final String role;
  private final Kv labels;

  public ENodeSpec(Builder builder) {
    this.availability = builder.availability;
    this.name = builder.name;
    this.role = builder.role;
    this.labels = builder.labels;
  }

  public String availability() {
    return this.availability;
  }

  public String name() {
    return this.name;
  }

  public String role() {
    return this.role;
  }

  public Kv labels() {
    return this.labels;
  }

  public static class Builder {

    private String availability;
    private String name;
    private String role;
    private Kv labels;

    public ENodeSpec build() {
      return new ENodeSpec(this);
    }

    public Builder availability(String availability) {
      this.availability = availability;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder role(String role) {
      this.role = role;
      return this;
    }

    public Builder labels(Kv labels) {
      this.labels = labels;
      return this;
    }
  }

}
