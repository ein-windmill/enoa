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
package io.enoa.toolkit.ethernet;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class EthInfo {

  private final String name;
  private final IP ip;
  private final MAC mac;
  private final NetworkInterface nif;
  private final InetAddress address;

  private EthInfo(Builder builder) {
    this.name = builder.name;
    this.ip = builder.ip;
    this.nif = builder.nif;
    this.address = builder.address;
    this.mac = builder.mac;
  }

  Builder builder() {
    return new Builder(this);
  }

  public String name() {
    return name;
  }

  public IP ip() {
    return ip;
  }


  public MAC mac() {
    return mac;
  }

  public NetworkInterface nif() {
    return nif;
  }

  public InetAddress address() {
    return address;
  }

  public boolean loopback() {
    return this.usaferun(this.nif::isLoopback);
  }

  public boolean virtual() {
    return this.usaferun(this.nif::isVirtual);
  }

  public boolean up() {
    return this.usaferun(this.nif::isUp);
  }


  private <T> T usaferun(Caller<T> caller) {
    try {
      return caller.call();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }


  @Override
  public String toString() {
    return "EthInfo{" +
      "name='" + name + '\'' +
      ", ip=" + ip +
      ", mac=" + mac +
      '}';
  }

  private interface Caller<T> {
    T call() throws Exception;
  }

  protected static class Builder {

    private String name;
    private IP ip;
    private NetworkInterface nif;
    private InetAddress address;
    private MAC mac;

    protected Builder() {

    }

    private Builder(EthInfo inf) {
      this.name = inf.name;
      this.ip = inf.ip;
      this.nif = inf.nif;
      this.address = inf.address;
      this.mac = inf.mac;
    }

    public EthInfo build() {
      return new EthInfo(this);
    }

    public Builder mac(MAC mac) {
      this.mac = mac;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder ip(IP ip) {
      this.ip = ip;
      return this;
    }

    public Builder nif(NetworkInterface nif) {
      this.nif = nif;
      return this;
    }

    public Builder address(InetAddress address) {
      this.address = address;
      return this;
    }
  }
}
