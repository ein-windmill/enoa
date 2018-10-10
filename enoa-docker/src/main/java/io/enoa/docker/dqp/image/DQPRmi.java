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
package io.enoa.docker.dqp.image;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

public class DQPRmi implements DQP {

  private Boolean force;
  private Boolean noprune;

  public static DQPRmi create() {
    return new DQPRmi();
  }

  public DQPRmi() {
    this.force = Boolean.FALSE;
    this.noprune = Boolean.FALSE;
  }

  public DQPRmi force(Boolean force) {
    this.force = force;
    return this;
  }

  public DQPRmi noprune(Boolean noprune) {
    this.noprune = noprune;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create();
    if (this.force)
      dqr.put("force", this.force);
    if (this.noprune)
      dqr.put("noprune", this.noprune);
    return dqr;
  }
}
