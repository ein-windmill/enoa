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
package io.enoa.docker.dret.dockerinfo;

import io.enoa.docker.dret.AbstractDRet;

public class EPlugin extends AbstractDRet {

  private final String[] volume;
  private final String[] network;
  private final Object authorization;
  private final String[] log;

  private EPlugin(Builder builder) {
    this.volume = builder.volume;
    this.network = builder.network;
    this.authorization = builder.authorization;
    this.log = builder.log;
  }

  public String[] volume() {
    return this.volume;
  }

  public String[] network() {
    return this.network;
  }

  public Object authorization() {
    return this.authorization;
  }

  public String[] log() {
    return this.log;
  }

  public static class Builder {
    private String[] volume;
    private String[] network;
    private Object authorization;
    private String[] log;

    public Builder() {
    }

    public EPlugin build() {
      return new EPlugin(this);
    }

    public Builder volume(String[] volume) {
      this.volume = volume;
      return this;
    }

    public Builder network(String[] network) {
      this.network = network;
      return this;
    }

    public Builder authorization(Object authorization) {
      this.authorization = authorization;
      return this;
    }

    public Builder log(String[] log) {
      this.log = log;
      return this;
    }
  }

}
