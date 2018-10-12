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
package io.enoa.docker.dret.config;

import io.enoa.docker.dret.AbstractDRet;
import io.enoa.docker.dret.swarm.EVersion;

import java.util.Date;

public class EConfig extends AbstractDRet {

  private String id;
  private EVersion version;
  private Date createdat;
  private Date updatedat;
  private EConfigSpec spec;

  public EConfig(Builder builder) {
    this.id = builder.id;
    this.version = builder.version;
    this.createdat = builder.createdat;
    this.updatedat = builder.updatedat;
    this.spec = builder.spec;
  }

  public String id() {
    return id;
  }

  public EVersion version() {
    return version;
  }

  public Date createdat() {
    return createdat;
  }

  public Date updatedat() {
    return updatedat;
  }

  public EConfigSpec spec() {
    return spec;
  }

  public static class Builder {

    private String id;
    private EVersion version;
    private Date createdat;
    private Date updatedat;
    private EConfigSpec spec;

    public EConfig build() {
      return new EConfig(this);
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder version(EVersion version) {
      this.version = version;
      return this;
    }

    public Builder createdat(Date createdat) {
      this.createdat = createdat;
      return this;
    }

    public Builder updatedat(Date updatedat) {
      this.updatedat = updatedat;
      return this;
    }

    public Builder spec(EConfigSpec spec) {
      this.spec = spec;
      return this;
    }
  }

}
