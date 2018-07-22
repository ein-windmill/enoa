/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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
package io.enoa.mq.rabbitmq;

import com.rabbitmq.client.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class RabbitConfig {

  private final List<Address> addresses;
  private final String user;
  private final String passwd;
  private final String virtualHost;
  private final ExecutorService executor;
  private final String name;
  private final Integer channelNumber;

  private RabbitConfig(Builder builder) {
    this.addresses = builder.addresses;
    this.user = builder.user;
    this.passwd = builder.passwd;
    this.virtualHost = builder.virtualHost;
    this.executor = builder.executor;
    this.name = builder.name;
    this.channelNumber = builder.channelNumber;
  }

  public List<Address> addresses() {
    return this.addresses;
  }

  public String user() {
    return this.user;
  }

  public String passwd() {
    return this.passwd;
  }

  public String virtualHost() {
    return this.virtualHost;
  }

  public ExecutorService executor() {
    return this.executor;
  }

  public String name() {
    return this.name;
  }

  public Integer channelNumber() {
    return this.channelNumber;
  }

  public static class Builder {
    private List<Address> addresses;
    private String user;
    private String passwd;
    private String virtualHost;
    private ExecutorService executor;
    private String name;
    private Integer channelNumber;

    public Builder() {
      this.name = "main";
    }

    public RabbitConfig build() {
      return new RabbitConfig(this);
    }

    public Builder address(String host, Integer port) {
      if (this.addresses == null)
        this.addresses = new ArrayList<>();
      this.addresses.add(new Address(host, port));
      return this;
    }

    public Builder addresses(List<Address> addresses) {
      addresses.forEach(address -> this.address(address.getHost(), address.getPort()));
      return this;
    }

    public Builder user(String user) {
      this.user = user;
      return this;
    }

    public Builder passwd(String passwd) {
      this.passwd = passwd;
      return this;
    }

    public Builder virtualHost(String virtualHost) {
      this.virtualHost = virtualHost;
      return this;
    }

    public Builder executor(ExecutorService executor) {
      this.executor = executor;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder channelNumber(Integer channelNumber) {
      this.channelNumber = channelNumber;
      return this;
    }
  }

}
