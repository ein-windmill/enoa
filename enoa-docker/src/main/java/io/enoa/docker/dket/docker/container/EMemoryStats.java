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

public class EMemoryStats extends AbstractDRRet {

  private final Integer maxusage;
  private final Integer usage;
  private final Integer failcnt;
  private final Double limit;
  private final EMStats stats;

  public EMemoryStats(Builder builder) {
    this.maxusage = builder.maxusage;
    this.usage = builder.usage;
    this.failcnt = builder.failcnt;
    this.limit = builder.limit;
    this.stats = builder.stats;
  }

  public Integer maxusage() {
    return maxusage;
  }

  public Integer usage() {
    return usage;
  }

  public Integer failcnt() {
    return failcnt;
  }

  public Double limit() {
    return limit;
  }

  public EMStats stats() {
    return stats;
  }

  public static class Builder {

    private Integer maxusage;
    private Integer usage;
    private Integer failcnt;
    private Double limit;
    private EMStats stats;

    public EMemoryStats build() {
      return new EMemoryStats(this);
    }

    public Builder maxusage(Integer maxusage) {
      this.maxusage = maxusage;
      return this;
    }

    public Builder usage(Integer usage) {
      this.usage = usage;
      return this;
    }

    public Builder failcnt(Integer failcnt) {
      this.failcnt = failcnt;
      return this;
    }

    public Builder limit(Double limit) {
      this.limit = limit;
      return this;
    }

    public Builder stats(EMStats stats) {
      this.stats = stats;
      return this;
    }
  }
}
