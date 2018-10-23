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

public class ELogConfig extends AbstractDockerRet {

  private final String type;
  private final Object config;

  public ELogConfig(Builder builder) {
    this.type = builder.type;
    this.config = builder.config;
  }

  public String type() {
    return type;
  }

  public Object config() {
    return config;
  }

  public static class Builder {
    private String type;
    private Object config;

    public ELogConfig build() {
      return new ELogConfig(this);
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder config(Object config) {
      this.config = config;
      return this;
    }
  }

}
