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
package io.enoa.docker.dket.docker.node;

import io.enoa.docker.dket.AbstractDRRet;
import io.enoa.docker.dket.docker.swarm.EVersion;

import java.util.Date;

public class ENode extends AbstractDRRet {

  private String id;
  private EVersion version;
  private Date createdat;
  private Date updatedat;
  private ENodeSpec spec;
  private ENodeDescription description;
  private ENodeStatus status;
  private ENodeStatus managerstatus;

  public ENode(Builder builder) {
    this.id = builder.id;
    this.version = builder.version;
    this.createdat = builder.createdat;
    this.updatedat = builder.updatedat;
    this.spec = builder.spec;
    this.description = builder.description;
    this.status = builder.status;
    this.managerstatus = builder.managerstatus;
  }

  public String id() {
    return this.id;
  }

  public EVersion version() {
    return this.version;
  }

  public Date createdat() {
    return this.createdat;
  }

  public Date updatedat() {
    return this.updatedat;
  }

  public ENodeSpec spec() {
    return this.spec;
  }

  public ENodeDescription description() {
    return this.description;
  }

  public ENodeStatus status() {
    return this.status;
  }

  public ENodeStatus managerstatus() {
    return this.managerstatus;
  }

  public static class Builder {
    private String id;
    private EVersion version;
    private Date createdat;
    private Date updatedat;
    private ENodeSpec spec;
    private ENodeDescription description;
    private ENodeStatus status;
    private ENodeStatus managerstatus;

    public ENode build() {
      return new ENode(this);
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

    public Builder spec(ENodeSpec spec) {
      this.spec = spec;
      return this;
    }

    public Builder description(ENodeDescription description) {
      this.description = description;
      return this;
    }

    public Builder status(ENodeStatus status) {
      this.status = status;
      return this;
    }

    public Builder managerstatus(ENodeStatus managerstatus) {
      this.managerstatus = managerstatus;
      return this;
    }
  }

}
