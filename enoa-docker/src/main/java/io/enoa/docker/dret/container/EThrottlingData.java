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
package io.enoa.docker.dret.container;

import io.enoa.docker.dret.AbstractDRet;

public class EThrottlingData extends AbstractDRet {

  private final Integer periods;
  private final Integer throttledperiods;
  private final Integer throttledtime;

  public EThrottlingData(Builder builder) {
    this.periods = builder.periods;
    this.throttledperiods = builder.throttledperiods;
    this.throttledtime = builder.throttledtime;
  }

  public Integer periods() {
    return periods;
  }

  public Integer throttledperiods() {
    return throttledperiods;
  }

  public Integer throttledtime() {
    return throttledtime;
  }

  public static class Builder {

    private Integer periods;
    private Integer throttledperiods;
    private Integer throttledtime;

    public EThrottlingData build() {
      return new EThrottlingData(this);
    }

    public Builder periods(Integer periods) {
      this.periods = periods;
      return this;
    }

    public Builder throttledperiods(Integer throttledperiods) {
      this.throttledperiods = throttledperiods;
      return this;
    }

    public Builder throttledtime(Integer throttledtime) {
      this.throttledtime = throttledtime;
      return this;
    }
  }

}
