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

public class EBridge extends AbstractDRet {

  private final Object ipamconfig;
  private final Object links;
  private final Object aliases;
  private final String networkid;
  private final String endpointid;
  private final String gateway;
  private final String ipaddress;
  private final Integer ipprefixlen;
  private final String ipv6gateway;
  private final String globalipv6address;
  private final Integer globalipv6prefixlen;
  private final String macaddress;
  private final Object driveropts;

  public EBridge(Builder builder) {
    this.ipamconfig = builder.ipamconfig;
    this.links = builder.links;
    this.aliases = builder.aliases;
    this.networkid = builder.networkid;
    this.endpointid = builder.endpointid;
    this.gateway = builder.gateway;
    this.ipaddress = builder.ipaddress;
    this.ipprefixlen = builder.ipprefixlen;
    this.ipv6gateway = builder.ipv6gateway;
    this.globalipv6address = builder.globalipv6address;
    this.globalipv6prefixlen = builder.globalipv6prefixlen;
    this.macaddress = builder.macaddress;
    this.driveropts = builder.driveropts;
  }

  public Object ipamconfig() {
    return this.ipamconfig;
  }

  public Object links() {
    return this.links;
  }

  public Object aliases() {
    return this.aliases;
  }

  public String networkid() {
    return this.networkid;
  }

  public String endpointid() {
    return this.endpointid;
  }

  public String gateway() {
    return this.gateway;
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

  public String globalipv6address() {
    return this.globalipv6address;
  }

  public Integer globalipv6prefixlen() {
    return this.globalipv6prefixlen;
  }

  public String macaddress() {
    return this.macaddress;
  }

  public Object driveropts() {
    return this.driveropts;
  }

  public static class Builder {

    private Object ipamconfig;
    private Object links;
    private Object aliases;
    private String networkid;
    private String endpointid;
    private String gateway;
    private String ipaddress;
    private Integer ipprefixlen;
    private String ipv6gateway;
    private String globalipv6address;
    private Integer globalipv6prefixlen;
    private String macaddress;
    private Object driveropts;

    public EBridge build() {
      return new EBridge(this);
    }

    public Builder ipamconfig(Object ipamconfig) {
      this.ipamconfig = ipamconfig;
      return this;
    }

    public Builder links(Object links) {
      this.links = links;
      return this;
    }

    public Builder aliases(Object aliases) {
      this.aliases = aliases;
      return this;
    }

    public Builder networkid(String networkid) {
      this.networkid = networkid;
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

    public Builder globalipv6address(String globalipv6address) {
      this.globalipv6address = globalipv6address;
      return this;
    }

    public Builder globalipv6prefixlen(Integer globalipv6prefixlen) {
      this.globalipv6prefixlen = globalipv6prefixlen;
      return this;
    }

    public Builder macaddress(String macaddress) {
      this.macaddress = macaddress;
      return this;
    }

    public Builder driveropts(Object driveropts) {
      this.driveropts = driveropts;
      return this;
    }
  }

}
