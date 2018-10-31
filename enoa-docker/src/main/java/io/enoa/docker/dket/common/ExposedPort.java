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
package io.enoa.docker.dket.common;

import io.enoa.docker.dket.AbstractDRRet;

public class ExposedPort extends AbstractDRRet {


  public enum Type {
    TCP,
    UDP
  }

  private final Integer port;
  private final Type type;


  public ExposedPort(Builder builder) {
    this.type = builder.type;
    this.port = builder.port;
  }

  public Integer port() {
    return port;
  }

  public Type type() {
    return type;
  }

  public static class Builder {

    private Integer port;
    private Type type;

    public ExposedPort build() {
      return new ExposedPort(this);
    }

    public Builder port(Integer port) {
      this.port = port;
      return this;
    }

    public Builder type(Type type) {
      this.type = type;
      return this;
    }
  }

}
