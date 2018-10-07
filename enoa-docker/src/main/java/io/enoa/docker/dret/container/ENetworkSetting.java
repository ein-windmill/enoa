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
package io.enoa.docker.dret.container;

import io.enoa.docker.dret.AbstractDRet;

public class ENetworkSetting extends AbstractDRet {

  private String bridge;
  private String sandboxid;
  private Boolean hairpinmode;
  private String linklocalipv6address;
  private Integer linklocalipv6prefixlen;
  private String sandboxkey;
  private String endpointid;
  private String gateway;
  private String globalipv6address;
  private Integer globalipv6prefixlen;
  private String ipaddress;
  private Integer ipprefixlen;
  private String ipv6gateway;
  private String macaddress;
  private ENetwork network;

  public ENetworkSetting(Builder builder) {
    this.bridge = builder.bridge;
    this.sandboxid = builder.sandboxid;
    this.hairpinmode = builder.hairpinmode;
    this.linklocalipv6address = builder.linklocalipv6address;
    this.linklocalipv6prefixlen = builder.linklocalipv6prefixlen;
    this.sandboxkey = builder.sandboxkey;
    this.endpointid = builder.endpointid;
    this.gateway = builder.gateway;
    this.globalipv6address = builder.globalipv6address;
    this.globalipv6prefixlen = builder.globalipv6prefixlen;
    this.ipaddress = builder.ipaddress;
    this.ipprefixlen = builder.ipprefixlen;
    this.ipv6gateway = builder.ipv6gateway;
    this.macaddress = builder.macaddress;
    this.network = builder.network;
  }

  public String bridge() {
    return this.bridge;
  }

  public String sandboxid() {
    return this.sandboxid;
  }

  public Boolean hairpinmode() {
    return this.hairpinmode;
  }

  public String linklocalipv6address() {
    return this.linklocalipv6address;
  }

  public Integer linklocalipv6prefixlen() {
    return this.linklocalipv6prefixlen;
  }

  public String sandboxkey() {
    return this.sandboxkey;
  }

  public String endpointid() {
    return this.endpointid;
  }

  public String gateway() {
    return this.gateway;
  }

  public String globalipv6address() {
    return this.globalipv6address;
  }

  public Integer globalipv6prefixlen() {
    return this.globalipv6prefixlen;
  }

  public String ipaddress() {
    return this.ipaddress;
  }

  public Integer ipprefixlen() {
    return this.ipprefixlen;
  }

  public String ipv6gateway() {
    return this.ipv6gateway;
  }

  public String macaddress() {
    return this.macaddress;
  }

  public ENetwork network() {
    return this.network;
  }

  public static class Builder {

    private String bridge;
    private String sandboxid;
    private Boolean hairpinmode;
    private String linklocalipv6address;
    private Integer linklocalipv6prefixlen;
    private String sandboxkey;
    private String endpointid;
    private String gateway;
    private String globalipv6address;
    private Integer globalipv6prefixlen;
    private String ipaddress;
    private Integer ipprefixlen;
    private String ipv6gateway;
    private String macaddress;
    private ENetwork network;

    public ENetworkSetting build() {
      return new ENetworkSetting(this);
    }

    public Builder bridge(String bridge) {
      this.bridge = bridge;
      return this;
    }

    public Builder sandboxid(String sandboxid) {
      this.sandboxid = sandboxid;
      return this;
    }

    public Builder hairpinmode(Boolean hairpinmode) {
      this.hairpinmode = hairpinmode;
      return this;
    }

    public Builder linklocalipv6address(String linklocalipv6address) {
      this.linklocalipv6address = linklocalipv6address;
      return this;
    }

    public Builder linklocalipv6prefixlen(Integer linklocalipv6prefixlen) {
      this.linklocalipv6prefixlen = linklocalipv6prefixlen;
      return this;
    }

    public Builder sandboxkey(String sandboxkey) {
      this.sandboxkey = sandboxkey;
      return this;
    }

    public Builder endpointid(String endpointid) {
      this.endpointid = endpointid;
      return this;
    }

    public Builder gateway(String gateway) {
      this.gateway = gateway;
      return this;
    }

    public Builder globalipv6address(String globalipv6address) {
      this.globalipv6address = globalipv6address;
      return this;
    }

    public Builder globalipv6prefixlen(Integer globalipv6prefixlen) {
      this.globalipv6prefixlen = globalipv6prefixlen;
      return this;
    }

    public Builder ipaddress(String ipaddress) {
      this.ipaddress = ipaddress;
      return this;
    }

    public Builder ipprefixlen(Integer ipprefixlen) {
      this.ipprefixlen = ipprefixlen;
      return this;
    }

    public Builder ipv6gateway(String ipv6gateway) {
      this.ipv6gateway = ipv6gateway;
      return this;
    }

    public Builder macaddress(String macaddress) {
      this.macaddress = macaddress;
      return this;
    }

    public Builder network(ENetwork network) {
      this.network = network;
      return this;
    }
  }

}
