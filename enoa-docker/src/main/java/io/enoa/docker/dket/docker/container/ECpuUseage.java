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
package io.enoa.docker.dket.docker.container;

import io.enoa.docker.dket.AbstractDRRet;

public class ECpuUseage extends AbstractDRRet {

  private final Integer totalusage;
  private final Integer usageinkernelmode;
  private final Integer usageinusermode;

  public ECpuUseage(Builder builder) {
    this.totalusage = builder.totalusage;
    this.usageinkernelmode = builder.usageinkernelmode;
    this.usageinusermode = builder.usageinusermode;
  }

  public Integer totalusage() {
    return totalusage;
  }

  public Integer usageinkernelmode() {
    return usageinkernelmode;
  }

  public Integer usageinusermode() {
    return usageinusermode;
  }

  public static class Builder {

    private Integer totalusage;
    private Integer usageinkernelmode;
    private Integer usageinusermode;

    public ECpuUseage build() {
      return new ECpuUseage(this);
    }

    public Builder totalusage(Integer totalusage) {
      this.totalusage = totalusage;
      return this;
    }

    public Builder usageinkernelmode(Integer usageinkernelmode) {
      this.usageinkernelmode = usageinkernelmode;
      return this;
    }

    public Builder usageinusermode(Integer usageinusermode) {
      this.usageinusermode = usageinusermode;
      return this;
    }
  }

}
