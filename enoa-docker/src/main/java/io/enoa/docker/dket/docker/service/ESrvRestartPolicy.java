package io.enoa.docker.dket.docker.service;

import io.enoa.docker.dket.AbstractDRRet;

public class ESrvRestartPolicy extends AbstractDRRet {

  private String condition;
  private Long delay;
  private Long maxattempts;
  private Long window;

  public ESrvRestartPolicy(Builder builder) {
    this.condition = builder.condition;
    this.delay = builder.delay;
    this.maxattempts = builder.maxattempts;
    this.window = builder.window;
  }

  public String condition() {
    return condition;
  }

  public Long delay() {
    return delay;
  }

  public Long maxattempts() {
    return maxattempts;
  }

  public Long window() {
    return window;
  }

  public static class Builder {

    private String condition;
    private Long delay;
    private Long maxattempts;
    private Long window;

    public ESrvRestartPolicy build() {
      return new ESrvRestartPolicy(this);
    }

    public Builder condition(String condition) {
      this.condition = condition;
      return this;
    }

    public Builder delay(Long delay) {
      this.delay = delay;
      return this;
    }

    public Builder maxattempts(Long maxattempts) {
      this.maxattempts = maxattempts;
      return this;
    }

    public Builder window(Long window) {
      this.window = window;
      return this;
    }
  }

}
