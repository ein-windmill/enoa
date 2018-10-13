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
package io.enoa.docker.dret.service;

import io.enoa.docker.dret.AbstractDRet;
import io.enoa.docker.dret.plugin.EPluginPrivilege;

import java.util.List;

public class EPluginSpec extends AbstractDRet {

  private String name;
  private String remote;
  private Boolean disabled;
  private List<EPluginPrivilege> pluginprivilege;

  public EPluginSpec(Builder builder) {
    this.name = builder.name;
    this.remote = builder.remote;
    this.disabled = builder.disabled;
    this.pluginprivilege = builder.pluginprivilege;
  }

  public String name() {
    return name;
  }

  public String remote() {
    return remote;
  }

  public Boolean disabled() {
    return disabled;
  }

  public List<EPluginPrivilege> pluginprivilege() {
    return pluginprivilege;
  }

  public static class Builder {

    private String name;
    private String remote;
    private Boolean disabled;
    private List<EPluginPrivilege> pluginprivilege;

    public EPluginSpec build() {
      return new EPluginSpec(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder remote(String remote) {
      this.remote = remote;
      return this;
    }

    public Builder disabled(Boolean disabled) {
      this.disabled = disabled;
      return this;
    }

    public Builder pluginprivilege(List<EPluginPrivilege> pluginprivilege) {
      this.pluginprivilege = pluginprivilege;
      return this;
    }
  }
}
