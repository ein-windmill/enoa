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

import io.enoa.docker.ret.AbstractDRRet;

public class EIPAMConfig extends AbstractDRRet {

  private String subnet;
  private String gateway;

  public EIPAMConfig(Builder builder) {
    this.subnet = builder.subnet;
    this.gateway = builder.gateway;
  }

  public String subnet() {
    return subnet;
  }

  public String gateway() {
    return gateway;
  }

  public static class Builder {

    private String subnet;
    private String gateway;

    public EIPAMConfig build() {
      return new EIPAMConfig(this);
    }

    public Builder subnet(String subnet) {
      this.subnet = subnet;
      return this;
    }

    public Builder gateway(String gateway) {
      this.gateway = gateway;
      return this;
    }
  }
}
