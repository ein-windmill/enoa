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
package io.enoa.docker.ret.docker.node;

import io.enoa.docker.ret.AbstractDockerRet;
import io.enoa.docker.ret.docker.common.EResources;
import io.enoa.docker.ret.docker.common.ETLSInfo;

public class ENodeDescription extends AbstractDockerRet {

  private String hostname;
  private EPlatform platform;
  private EResources resources;
  private ENodeEngine engine;
  private ETLSInfo tlsinfo;

  public ENodeDescription(Builder builder) {
    this.hostname = builder.hostname;
    this.platform = builder.platform;
    this.resources = builder.resources;
    this.engine = builder.engine;
    this.tlsinfo = builder.tlsinfo;
  }

  public String hostname() {
    return this.hostname;
  }

  public EPlatform platform() {
    return this.platform;
  }

  public EResources resources() {
    return this.resources;
  }

  public ENodeEngine engine() {
    return this.engine;
  }

  public ETLSInfo tlsinfo() {
    return this.tlsinfo;
  }

  public static class Builder {
    private String hostname;
    private EPlatform platform;
    private EResources resources;
    private ENodeEngine engine;
    private ETLSInfo tlsinfo;

    public ENodeDescription build() {
      return new ENodeDescription(this);
    }

    public Builder hostname(String hostname) {
      this.hostname = hostname;
      return this;
    }

    public Builder platform(EPlatform platform) {
      this.platform = platform;
      return this;
    }

    public Builder resources(EResources resources) {
      this.resources = resources;
      return this;
    }

    public Builder engine(ENodeEngine engine) {
      this.engine = engine;
      return this;
    }

    public Builder tlsinfo(ETLSInfo tlsinfo) {
      this.tlsinfo = tlsinfo;
      return this;
    }
  }


}
