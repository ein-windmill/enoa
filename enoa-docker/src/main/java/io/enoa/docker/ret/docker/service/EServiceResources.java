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

import io.enoa.docker.ret.AbstractDockerRet;
import io.enoa.docker.ret.docker.common.EResources;

public class EServiceResources extends AbstractDockerRet {

  private EResources limits;
  private EResources reservation;

  public EServiceResources(Builder builder) {
    this.limits = builder.limits;
    this.reservation = builder.reservation;
  }

  public EResources limits() {
    return limits;
  }

  public EResources reservation() {
    return reservation;
  }

  public static class Builder {
    private EResources limits;
    private EResources reservation;

    public EServiceResources build() {
      return new EServiceResources(this);
    }

    public Builder limits(EResources limits) {
      this.limits = limits;
      return this;
    }

    public Builder reservation(EResources reservation) {
      this.reservation = reservation;
      return this;
    }
  }

}
