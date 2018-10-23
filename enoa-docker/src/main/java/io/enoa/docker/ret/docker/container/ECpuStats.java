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
package io.enoa.docker.ret.docker.container;

import io.enoa.docker.ret.AbstractDockerRet;

public class ECpuStats extends AbstractDockerRet {

  private final ECpuUseage cpuusage;
  private final EThrottlingData throttlingdata;

  public ECpuStats(Builder builder) {
    this.cpuusage = builder.cpuusage;
    this.throttlingdata = builder.throttlingdata;
  }

  public ECpuUseage cpuusage() {
    return cpuusage;
  }

  public EThrottlingData throttlingdata() {
    return throttlingdata;
  }

  public static class Builder {

    private ECpuUseage cpuusage;
    private EThrottlingData throttlingdata;

    public ECpuStats build() {
      return new ECpuStats(this);
    }

    public Builder cpuusage(ECpuUseage cpuusage) {
      this.cpuusage = cpuusage;
      return this;
    }

    public Builder throttlingdata(EThrottlingData throttlingdata) {
      this.throttlingdata = throttlingdata;
      return this;
    }
  }

}
