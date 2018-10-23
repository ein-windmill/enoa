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

import java.util.Date;
import java.util.List;

public class ECInspect extends AbstractDRRet {

  private final String id;
  private final Date created;
  private final String path;
  private final List<String> args;
  private final ECState state;
  private final String image;
  private final String resolvconfpath;
  private final String hostnamepath;
  private final String hostspath;
  private final String logpath;
  private final String name;
  private final Integer restartcount;
  private final String driver;
  private final String platform;
  private final String mountlabel;
  private final String processlabel;
  private final String apparmorprofile;
  private final List<String> execids;
  private final EHostConfig hostconfig;
  private final EGraphDriver graphdriver;
  private final Integer sizerw;
  private final Integer sizerootfs;
  private final ECConfig config;
  private final List<EMount> mounts;
  private final ENetworkSetting networksetting;

  public ECInspect(Builder builder) {
    this.id = builder.id;
    this.created = builder.created;
    this.path = builder.path;
    this.args = builder.args;
    this.state = builder.state;
    this.image = builder.image;
    this.resolvconfpath = builder.resolvconfpath;
    this.hostnamepath = builder.hostnamepath;
    this.hostspath = builder.hostspath;
    this.logpath = builder.logpath;
    this.name = builder.name;
    this.restartcount = builder.restartcount;
    this.driver = builder.driver;
    this.platform = builder.platform;
    this.mountlabel = builder.mountlabel;
    this.processlabel = builder.processlabel;
    this.apparmorprofile = builder.apparmorprofile;
    this.execids = builder.execids;
    this.hostconfig = builder.hostconfig;
    this.graphdriver = builder.graphdriver;
    this.config = builder.config;
    this.mounts = builder.mounts;
    this.networksetting = builder.networksetting;
    this.sizerw = builder.sizerw;
    this.sizerootfs = builder.sizerootfs;
  }

  public Integer sizerw() {
    return sizerw;
  }

  public Integer sizerootfs() {
    return sizerootfs;
  }

  public String id() {
    return id;
  }

  public Date created() {
    return created;
  }

  public String path() {
    return path;
  }

  public List<String> args() {
    return args;
  }

  public ECState state() {
    return state;
  }

  public String image() {
    return image;
  }

  public String resolvconfpath() {
    return resolvconfpath;
  }

  public String hostnamepath() {
    return hostnamepath;
  }

  public String hostspath() {
    return hostspath;
  }

  public String logpath() {
    return logpath;
  }

  public String name() {
    return name;
  }

  public Integer restartcount() {
    return restartcount;
  }

  public String driver() {
    return driver;
  }

  public String platform() {
    return platform;
  }

  public String mountlabel() {
    return mountlabel;
  }

  public String processlabel() {
    return processlabel;
  }

  public String apparmorprofile() {
    return apparmorprofile;
  }

  public List<String> execids() {
    return execids;
  }

  public EHostConfig hostconfig() {
    return hostconfig;
  }

  public EGraphDriver graphdriver() {
    return graphdriver;
  }

  public ECConfig config() {
    return config;
  }

  public List<EMount> mounts() {
    return mounts;
  }

  public ENetworkSetting networksetting() {
    return networksetting;
  }

  public static class Builder {

    private String id;
    private Date created;
    private String path;
    private List<String> args;
    private ECState state;
    private String image;
    private String resolvconfpath;
    private String hostnamepath;
    private String hostspath;
    private String logpath;
    private String name;
    private Integer restartcount;
    private String driver;
    private String platform;
    private String mountlabel;
    private String processlabel;
    private String apparmorprofile;
    private List<String> execids;
    private EHostConfig hostconfig;
    private Integer sizerw;
    private Integer sizerootfs;
    private EGraphDriver graphdriver;
    private ECConfig config;
    private List<EMount> mounts;
    private ENetworkSetting networksetting;

    public ECInspect build() {
      return new ECInspect(this);
    }

    public Builder sizerw(Integer sizerw) {
      this.sizerw = sizerw;
      return this;
    }

    public Builder sizerootfs(Integer sizerootfs) {
      this.sizerootfs = sizerootfs;
      return this;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder created(Date created) {
      this.created = created;
      return this;
    }

    public Builder path(String path) {
      this.path = path;
      return this;
    }

    public Builder args(List<String> args) {
      this.args = args;
      return this;
    }

    public Builder state(ECState state) {
      this.state = state;
      return this;
    }

    public Builder image(String image) {
      this.image = image;
      return this;
    }

    public Builder resolvconfpath(String resolvconfpath) {
      this.resolvconfpath = resolvconfpath;
      return this;
    }

    public Builder hostnamepath(String hostnamepath) {
      this.hostnamepath = hostnamepath;
      return this;
    }

    public Builder hostspath(String hostspath) {
      this.hostspath = hostspath;
      return this;
    }

    public Builder logpath(String logpath) {
      this.logpath = logpath;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder restartcount(Integer restartcount) {
      this.restartcount = restartcount;
      return this;
    }

    public Builder driver(String driver) {
      this.driver = driver;
      return this;
    }

    public Builder platform(String platform) {
      this.platform = platform;
      return this;
    }

    public Builder mountlabel(String mountlabel) {
      this.mountlabel = mountlabel;
      return this;
    }

    public Builder processlabel(String processlabel) {
      this.processlabel = processlabel;
      return this;
    }

    public Builder apparmorprofile(String apparmorprofile) {
      this.apparmorprofile = apparmorprofile;
      return this;
    }

    public Builder execids(List<String> execids) {
      this.execids = execids;
      return this;
    }

    public Builder hostconfig(EHostConfig hostconfig) {
      this.hostconfig = hostconfig;
      return this;
    }

    public Builder graphdriver(EGraphDriver graphdriver) {
      this.graphdriver = graphdriver;
      return this;
    }

    public Builder config(ECConfig config) {
      this.config = config;
      return this;
    }

    public Builder mounts(List<EMount> mounts) {
      this.mounts = mounts;
      return this;
    }

    public Builder networksetting(ENetworkSetting networksetting) {
      this.networksetting = networksetting;
      return this;
    }
  }

}
