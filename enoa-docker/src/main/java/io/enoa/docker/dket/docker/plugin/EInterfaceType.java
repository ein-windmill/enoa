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

public class EInterfaceType extends AbstractDRRet {

  private String prefix;
  private String capability;
  private String version;

  public EInterfaceType(Builder builder) {
    this.prefix = builder.prefix;
    this.capability = builder.capability;
    this.version = builder.version;
  }

  public String prefix() {
    return prefix;
  }

  public String capability() {
    return capability;
  }

  public String version() {
    return version;
  }

  public static class Builder {

    private String prefix;
    private String capability;
    private String version;

    public EInterfaceType build() {
      return new EInterfaceType(this);
    }

    public Builder prefix(String prefix) {
      this.prefix = prefix;
      return this;
    }

    public Builder capability(String capability) {
      this.capability = capability;
      return this;
    }

    public Builder version(String version) {
      this.version = version;
      return this;
    }
  }

}
