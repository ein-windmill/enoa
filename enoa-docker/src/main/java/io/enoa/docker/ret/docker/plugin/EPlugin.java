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
package io.enoa.docker.ret.docker.plugin;

import io.enoa.docker.ret.AbstractDockerRet;

public class EPlugin extends AbstractDockerRet {

  private final String id;
  private final String name;
  private final Boolean enabled;
  private final String reference;
  private final EPluginSetting setting;
  private final EPluginConfig config;

  public EPlugin(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.enabled = builder.enabled;
    this.reference = builder.reference;
    this.setting = builder.setting;
    this.config = builder.config;
  }

  public String id() {
    return id;
  }

  public String name() {
    return name;
  }

  public Boolean enabled() {
    return enabled;
  }

  public String reference() {
    return reference;
  }

  public EPluginSetting setting() {
    return setting;
  }

  public EPluginConfig config() {
    return config;
  }

  public static class Builder {
    private String id;
    private String name;
    private Boolean enabled;
    private String reference;
    private EPluginSetting setting;
    private EPluginConfig config;

    public EPlugin build() {
      return new EPlugin(this);
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder enabled(Boolean enabled) {
      this.enabled = enabled;
      return this;
    }

    public Builder reference(String reference) {
      this.reference = reference;
      return this;
    }

    public Builder setting(EPluginSetting setting) {
      this.setting = setting;
      return this;
    }

    public Builder config(EPluginConfig config) {
      this.config = config;
      return this;
    }
  }


}
