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

import java.util.List;

public class EPluginPrivilege extends AbstractDockerRet {

  private String name;
  private String description;
  private List<String> value;

  public EPluginPrivilege(Builder builder) {
    this.name = builder.name;
    this.description = builder.description;
    this.value = builder.value;
  }

  public String name() {
    return name;
  }

  public String description() {
    return description;
  }

  public List<String> value() {
    return value;
  }

  public static class Builder {

    private String name;
    private String description;
    private List<String> value;

    public EPluginPrivilege build() {
      return new EPluginPrivilege(this);
    }


    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder value(List<String> value) {
      this.value = value;
      return this;
    }
  }
}
