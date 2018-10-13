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
package io.enoa.docker.dret.network;

import io.enoa.docker.dret.AbstractDRet;

public class ENetworkContainer extends AbstractDRet {

  private String name;
  private String endpointid;
  private String macaddress;
  private String ipv4address;
  private String ipv6address;

  public ENetworkContainer(Builder builder) {
    this.name = builder.name;
    this.endpointid = builder.endpointid;
    this.macaddress = builder.macaddress;
    this.ipv4address = builder.ipv4address;
    this.ipv6address = builder.ipv6address;
  }

  public String name() {
    return name;
  }

  public String endpointid() {
    return endpointid;
  }

  public String macaddress() {
    return macaddress;
  }

  public String ipv4address() {
    return ipv4address;
  }

  public String ipv6address() {
    return ipv6address;
  }

  public static class Builder {

    private String name;
    private String endpointid;
    private String macaddress;
    private String ipv4address;
    private String ipv6address;

    public ENetworkContainer build() {
      return new ENetworkContainer(this);
    }


    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder endpointid(String endpointid) {
      this.endpointid = endpointid;
      return this;
    }

    public Builder macaddress(String macaddress) {
      this.macaddress = macaddress;
      return this;
    }

    public Builder ipv4address(String ipv4address) {
      this.ipv4address = ipv4address;
      return this;
    }

    public Builder ipv6address(String ipv6address) {
      this.ipv6address = ipv6address;
      return this;
    }
  }
}
