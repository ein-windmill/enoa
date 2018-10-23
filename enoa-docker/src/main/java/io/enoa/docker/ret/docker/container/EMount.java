/*
 * Copyright (c) 2018, enoa (fewensa@enoa.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.enoa.docker.ret.docker.container;

import io.enoa.docker.ret.AbstractDockerRet;

public class EMount extends AbstractDockerRet {

  private final String type;
  private final String name;
  private final String source;
  private final String destination;
  private final String driver;
  private final String mode;
  private final Boolean rw;
  private final String propagation;

  public EMount(Builder builder) {
    this.type = builder.type;
    this.name = builder.name;
    this.source = builder.source;
    this.destination = builder.destination;
    this.driver = builder.driver;
    this.mode = builder.mode;
    this.rw = builder.rw;
    this.propagation = builder.propagation;
  }

  public String type() {
    return this.type;
  }

  public String name() {
    return this.name;
  }

  public String source() {
    return this.source;
  }

  public String destination() {
    return this.destination;
  }

  public String driver() {
    return this.driver;
  }

  public String mode() {
    return this.mode;
  }

  public Boolean rw() {
    return this.rw;
  }

  public String propagation() {
    return this.propagation;
  }

  public static class Builder {

    private String type;
    private String name;
    private String source;
    private String destination;
    private String driver;
    private String mode;
    private Boolean rw;
    private String propagation;

    public EMount build() {
      return new EMount(this);
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder source(String source) {
      this.source = source;
      return this;
    }

    public Builder destination(String destination) {
      this.destination = destination;
      return this;
    }

    public Builder driver(String driver) {
      this.driver = driver;
      return this;
    }

    public Builder mode(String mode) {
      this.mode = mode;
      return this;
    }

    public Builder rw(Boolean rw) {
      this.rw = rw;
      return this;
    }

    public Builder propagation(String propagation) {
      this.propagation = propagation;
      return this;
    }
  }
}
