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

import java.util.List;

public class EPluginSetting extends AbstractDRRet {

  private List<String> env;
  private List<String> args;
  private List<EPluginMount> mounts;
  private List<EPluginDevice> devices;

  public EPluginSetting(Builder builder) {
    this.env = builder.env;
    this.args = builder.args;
    this.mounts = builder.mounts;
    this.devices = builder.devices;
  }

  public List<String> env() {
    return env;
  }

  public List<String> args() {
    return args;
  }

  public List<EPluginMount> mounts() {
    return mounts;
  }

  public List<EPluginDevice> devices() {
    return devices;
  }

  public static class Builder {


    private List<String> env;
    private List<String> args;
    private List<EPluginMount> mounts;
    private List<EPluginDevice> devices;

    public EPluginSetting build() {
      return new EPluginSetting(this);
    }

    public Builder env(List<String> env) {
      this.env = env;
      return this;
    }

    public Builder args(List<String> args) {
      this.args = args;
      return this;
    }

    public Builder mounts(List<EPluginMount> mounts) {
      this.mounts = mounts;
      return this;
    }

    public Builder devices(List<EPluginDevice> devices) {
      this.devices = devices;
      return this;
    }
  }


}
