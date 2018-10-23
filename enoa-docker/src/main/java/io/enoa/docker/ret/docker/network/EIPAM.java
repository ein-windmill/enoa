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
package io.enoa.docker.ret.docker.network;

import io.enoa.docker.ret.AbstractDockerRet;
import io.enoa.toolkit.map.Kv;

import java.util.List;

public class EIPAM extends AbstractDockerRet {

  private String driver;
  private List<EIPAMConfig> config;
  private List<Kv> options;

  public EIPAM(Builder builder) {
    this.driver = builder.driver;
    this.config = builder.config;
    this.options = builder.options;
  }

  public String driver() {
    return driver;
  }

  public List<EIPAMConfig> config() {
    return config;
  }

  public List<Kv> options() {
    return options;
  }

  public static class Builder {
    private String driver;
    private List<EIPAMConfig> config;
    private List<Kv> options;

    public EIPAM build() {
      return new EIPAM(this);
    }

    public Builder driver(String driver) {
      this.driver = driver;
      return this;
    }

    public Builder config(List<EIPAMConfig> config) {
      this.config = config;
      return this;
    }

    public Builder options(List<Kv> options) {
      this.options = options;
      return this;
    }
  }
}
