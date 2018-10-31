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
package io.enoa.docker.dket.docker.plugin;

import io.enoa.docker.dket.AbstractDRRet;
import io.enoa.docker.dket.docker.common.EUser;

import java.util.List;

public class EPluginConfig extends AbstractDRRet {

  private final String dockerversion;
  private final String description;
  private final String documentation;
  private final List<String> entrypoint;
  private final String workdir;
  private final String propagatedmount;
  private final Boolean ipchost;
  private final Boolean pidhost;
  private final List<EPluginMount> mounts;
  private final EPluginInterface interfacex;
  private final EUser user;
  private final EPluginNetwork network;
  private final EPluginLinux linux;
  private final List<EPluginEnv> env;
  private final List<EPluginArg> arg;
  private final EPluginRootfs rootfs;

  public EPluginConfig(Builder builder) {
    this.dockerversion = builder.dockerversion;
    this.description = builder.description;
    this.documentation = builder.documentation;
    this.entrypoint = builder.entrypoint;
    this.workdir = builder.workdir;
    this.propagatedmount = builder.propagatedmount;
    this.ipchost = builder.ipchost;
    this.pidhost = builder.pidhost;
    this.mounts = builder.mounts;
    this.interfacex = builder.interfacex;
    this.user = builder.user;
    this.network = builder.network;
    this.linux = builder.linux;
    this.env = builder.env;
    this.arg = builder.arg;
    this.rootfs = builder.rootfs;
  }

  public String dockerversion() {
    return dockerversion;
  }

  public String description() {
    return description;
  }

  public String documentation() {
    return documentation;
  }

  public List<String> entrypoint() {
    return entrypoint;
  }

  public String workdir() {
    return workdir;
  }

  public String propagatedmount() {
    return propagatedmount;
  }

  public Boolean ipchost() {
    return ipchost;
  }

  public Boolean pidhost() {
    return pidhost;
  }

  public List<EPluginMount> mounts() {
    return mounts;
  }

  public EPluginInterface interfacex() {
    return interfacex;
  }

  public EUser user() {
    return user;
  }

  public EPluginNetwork network() {
    return network;
  }

  public EPluginLinux linux() {
    return linux;
  }

  public List<EPluginEnv> env() {
    return env;
  }

  public List<EPluginArg> arg() {
    return arg;
  }

  public EPluginRootfs rootfs() {
    return rootfs;
  }

  public static class Builder {
    private String dockerversion;
    private String description;
    private String documentation;
    private List<String> entrypoint;
    private String workdir;
    private String propagatedmount;
    private Boolean ipchost;
    private Boolean pidhost;
    private List<EPluginMount> mounts;
    private EPluginInterface interfacex;
    private EUser user;
    private EPluginNetwork network;
    private EPluginLinux linux;
    private List<EPluginEnv> env;
    private List<EPluginArg> arg;
    private EPluginRootfs rootfs;

    public EPluginConfig build() {
      return new EPluginConfig(this);
    }

    public Builder dockerversion(String dockerversion) {
      this.dockerversion = dockerversion;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder documentation(String documentation) {
      this.documentation = documentation;
      return this;
    }

    public Builder entrypoint(List<String> entrypoint) {
      this.entrypoint = entrypoint;
      return this;
    }

    public Builder workdir(String workdir) {
      this.workdir = workdir;
      return this;
    }

    public Builder propagatedmount(String propagatedmount) {
      this.propagatedmount = propagatedmount;
      return this;
    }

    public Builder ipchost(Boolean ipchost) {
      this.ipchost = ipchost;
      return this;
    }

    public Builder pidhost(Boolean pidhost) {
      this.pidhost = pidhost;
      return this;
    }

    public Builder mounts(List<EPluginMount> mounts) {
      this.mounts = mounts;
      return this;
    }

    public Builder interfacex(EPluginInterface interfacex) {
      this.interfacex = interfacex;
      return this;
    }

    public Builder user(EUser user) {
      this.user = user;
      return this;
    }

    public Builder network(EPluginNetwork network) {
      this.network = network;
      return this;
    }

    public Builder linux(EPluginLinux linux) {
      this.linux = linux;
      return this;
    }

    public Builder env(List<EPluginEnv> env) {
      this.env = env;
      return this;
    }

    public Builder arg(List<EPluginArg> arg) {
      this.arg = arg;
      return this;
    }

    public Builder rootfs(EPluginRootfs rootfs) {
      this.rootfs = rootfs;
      return this;
    }
  }


}
