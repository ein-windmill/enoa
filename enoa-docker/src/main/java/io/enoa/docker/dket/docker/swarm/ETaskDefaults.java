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
package io.enoa.docker.dket.docker.swarm;

import io.enoa.docker.dket.AbstractDRRet;
import io.enoa.docker.dket.docker.common.EDriver;

public class ETaskDefaults extends AbstractDRRet {


  private final EDriver logdriver;

  public ETaskDefaults(Builder builder) {
    this.logdriver = builder.logdriver;
  }

  public EDriver logdriver() {
    return this.logdriver;
  }

  public static class Builder {
    private EDriver logdriver;

    public ETaskDefaults build() {
      return new ETaskDefaults(this);
    }

    public Builder logdriver(EDriver logdriver) {
      this.logdriver = logdriver;
      return this;
    }
  }

}
