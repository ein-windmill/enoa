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
package io.enoa.docker.ret.docker.dockerinfo;

import io.enoa.docker.ret.AbstractDockerRet;
import io.enoa.toolkit.map.Kv;

public class ERuntimes extends AbstractDockerRet {

  private final Kv runc;
  private final Kv runcmaster;
  private final Kv custom;

  private ERuntimes(Builder builder) {
    this.runc = builder.runc;
    this.runcmaster = builder.runcmaster;
    this.custom = builder.custom;
  }

  public Kv runc() {
    return this.runc;
  }

  public Kv runcmaster() {
    return this.runcmaster;
  }

  public Kv custom() {
    return this.custom;
  }

  public static class Builder {

    private Kv runc;
    private Kv runcmaster;
    private Kv custom;


    public ERuntimes build() {
      return new ERuntimes(this);
    }

    public Builder runc(Kv runc) {
      this.runc = runc;
      return this;
    }

    public Builder runcmaster(Kv runcmaster) {
      this.runcmaster = runcmaster;
      return this;
    }

    public Builder custom(Kv custom) {
      this.custom = custom;
      return this;
    }
  }
}
