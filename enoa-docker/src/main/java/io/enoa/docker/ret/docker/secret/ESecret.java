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
package io.enoa.docker.ret.docker.secret;

import io.enoa.docker.ret.AbstractDockerRet;
import io.enoa.docker.ret.docker.swarm.EVersion;

import java.util.Date;

public class ESecret extends AbstractDockerRet {

  private final String id;
  private final EVersion version;
  private final Date createdat;
  private final Date updatedat;
  private final ESecretSpec spec;

  public ESecret(Builder builder) {
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

  public ESecretSpec spec() {
    return spec;
  }

  public static class Builder {

    private String id;
    private EVersion version;
    private Date createdat;
    private Date updatedat;
    private ESecretSpec spec;

    public ESecret build() {
      return new ESecret(this);
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

    public Builder spec(ESecretSpec spec) {
      this.spec = spec;
      return this;
    }
  }


}
