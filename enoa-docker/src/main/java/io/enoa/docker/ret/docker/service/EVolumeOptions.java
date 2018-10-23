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
package io.enoa.docker.ret.docker.service;

import io.enoa.docker.ret.AbstractDRRet;
import io.enoa.toolkit.map.Kv;

public class EVolumeOptions extends AbstractDRRet {

  private Boolean nocopy;
  private Kv labels;
  private EDriverConfig driverconfig;

  public EVolumeOptions(Builder builder) {
    this.nocopy = builder.nocopy;
    this.labels = builder.labels;
    this.driverconfig = builder.driverconfig;
  }

  public Boolean nocopy() {
    return nocopy;
  }

  public Kv labels() {
    return labels;
  }

  public EDriverConfig driverconfig() {
    return driverconfig;
  }

  public static class Builder {
    private Boolean nocopy;
    private Kv labels;
    private EDriverConfig driverconfig;

    public EVolumeOptions build() {
      return new EVolumeOptions(this);
    }

    public Builder nocopy(Boolean nocopy) {
      this.nocopy = nocopy;
      return this;
    }

    public Builder labels(Kv labels) {
      this.labels = labels;
      return this;
    }

    public Builder driverconfig(EDriverConfig driverconfig) {
      this.driverconfig = driverconfig;
      return this;
    }
  }


}
