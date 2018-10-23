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

import io.enoa.docker.ret.AbstractDRRet;

import java.util.List;

public class EContainer extends AbstractDRRet {

  private final String id;
  private final List<String> names;
  private final String image;
  private final String imageid;
  private final String command;
  private final Long created;
  private final List<EPort> ports;
  private final Object labels;
  private final String state;
  private final String status;
  private final EHostConfig hostconfig;
  private final ENetworkSetting networksetting;
  private final List<EMount> mounts;


  public EContainer(Builder builder) {
    this.id = builder.id;
    this.names = builder.names;
    this.image = builder.image;
    this.imageid = builder.imageid;
    this.command = builder.command;
    this.created = builder.created;
    this.ports = builder.ports;
    this.labels = builder.labels;
    this.state = builder.state;
    this.status = builder.status;
    this.hostconfig = builder.hostconfig;
    this.networksetting = builder.networksetting;
    this.mounts = builder.mounts;
  }

  public String id() {
    return this.id;
  }

  public List<String> names() {
    return this.names;
  }

  public String image() {
    return this.image;
  }

  public String imageid() {
    return this.imageid;
  }

  public String command() {
    return this.command;
  }

  public Long created() {
    return this.created;
  }

  public List<EPort> ports() {
    return this.ports;
  }

  public Object labels() {
    return this.labels;
  }

  public String state() {
    return this.state;
  }

  public String status() {
    return this.status;
  }

  public EHostConfig hostconfig() {
    return this.hostconfig;
  }

  public ENetworkSetting networksetting() {
    return this.networksetting;
  }

  public List<EMount> mounts() {
    return this.mounts;
  }

  public static class Builder {

    private String id;
    private List<String> names;
    private String image;
    private String imageid;
    private String command;
    private Long created;
    private List<EPort> ports;
    private Object labels;
    private String state;
    private String status;
    private EHostConfig hostconfig;
    private ENetworkSetting networksetting;
    private List<EMount> mounts;

    public EContainer build() {
      return new EContainer(this);
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder names(List<String> names) {
      this.names = names;
      return this;
    }

    public Builder image(String image) {
      this.image = image;
      return this;
    }

    public Builder imageid(String imageid) {
      this.imageid = imageid;
      return this;
    }

    public Builder command(String command) {
      this.command = command;
      return this;
    }

    public Builder created(Long created) {
      this.created = created;
      return this;
    }

    public Builder ports(List<EPort> ports) {
      this.ports = ports;
      return this;
    }

    public Builder labels(Object labels) {
      this.labels = labels;
      return this;
    }

    public Builder state(String state) {
      this.state = state;
      return this;
    }

    public Builder status(String status) {
      this.status = status;
      return this;
    }

    public Builder hostconfig(EHostConfig hostconfig) {
      this.hostconfig = hostconfig;
      return this;
    }

    public Builder networksetting(ENetworkSetting networksetting) {
      this.networksetting = networksetting;
      return this;
    }

    public Builder mounts(List<EMount> mounts) {
      this.mounts = mounts;
      return this;
    }
  }


}
