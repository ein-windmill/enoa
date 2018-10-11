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
package io.enoa.docker.dqp.swarm;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

public class DQPSwarmUpdate implements DQP {

  private Long version;
  private Boolean rotateworkertoken;
  private Boolean rotatemanagertoken;
  private Boolean rotatemanagerunlockkey;


  public static DQPSwarmJoin create() {
    return new DQPSwarmJoin();
  }

  public DQPSwarmUpdate() {
    this.rotatemanagerunlockkey = Boolean.FALSE;
    this.rotatemanagertoken = Boolean.FALSE;
    this.rotateworkertoken = Boolean.FALSE;
  }

  public DQPSwarmUpdate version(Long version) {
    this.version = version;
    return this;
  }

  public DQPSwarmUpdate rotateworkertoken() {
    return this.rotateworkertoken(Boolean.TRUE);
  }

  public DQPSwarmUpdate rotateworkertoken(Boolean rotateworkertoken) {
    this.rotateworkertoken = rotateworkertoken;
    return this;
  }

  public DQPSwarmUpdate rotatemanagertoken() {
    return this.rotatemanagertoken(Boolean.TRUE);
  }

  public DQPSwarmUpdate rotatemanagertoken(Boolean rotatemanagertoken) {
    this.rotatemanagertoken = rotatemanagertoken;
    return this;
  }

  public DQPSwarmUpdate rotatemanagerunlockkey() {
    return this.rotatemanagerunlockkey(Boolean.TRUE);
  }

  public DQPSwarmUpdate rotatemanagerunlockkey(Boolean rotatemanagerunlockkey) {
    this.rotatemanagerunlockkey = rotatemanagerunlockkey;
    return this;
  }

  @Override
  public DQR dqr() {
    return DQR.create()
      .putIf("version", this.version)
      .putIf("rotateWorkerToken", this.rotateworkertoken)
      .putIf("rotateManagerToken", this.rotatemanagertoken)
      .putIf("rotateManagerUnlockKey", this.rotatemanagerunlockkey);
  }
}
