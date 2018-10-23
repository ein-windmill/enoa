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

public class EPluginInterface extends AbstractDockerRet {

  private String socket;
  private List<EInterfaceType> types;

  public EPluginInterface(Builder builder) {
    this.socket = builder.socket;
    this.types = builder.types;
  }

  public String socket() {
    return socket;
  }

  public List<EInterfaceType> types() {
    return types;
  }

  public static class Builder {

    private String socket;
    private List<EInterfaceType> types;

    public EPluginInterface build() {
      return new EPluginInterface(this);
    }

    public Builder socket(String socket) {
      this.socket = socket;
      return this;
    }

    public Builder types(List<EInterfaceType> types) {
      this.types = types;
      return this;
    }
  }

}
