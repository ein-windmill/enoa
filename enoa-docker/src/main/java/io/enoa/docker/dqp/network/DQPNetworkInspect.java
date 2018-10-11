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
package io.enoa.docker.dqp.network;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

public class DQPNetworkInspect implements DQP {

  /**
   * boolean
   * default false
   * <p>
   * Detailed inspect output for troubleshooting
   */
  private Boolean verbose;
  /**
   * string
   * <p>
   * Filter the network by scope (swarm, global, or local)
   */
  private String scope;

  public static DQPNetworkInspect create() {
    return new DQPNetworkInspect();
  }

  public DQPNetworkInspect() {
    this.verbose = Boolean.FALSE;
  }

  public DQPNetworkInspect verbose(Boolean verbose) {
    this.verbose = verbose;
    return this;
  }

  public DQPNetworkInspect scope(String scope) {
    this.scope = scope;
    return this;
  }

  @Override
  public DQR dqr() {
    return DQR.create()
      .put("verbose", this.verbose)
      .putIf("scope", this.scope);
  }
}
