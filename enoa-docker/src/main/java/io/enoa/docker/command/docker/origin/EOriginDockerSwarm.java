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
package io.enoa.docker.command.docker.origin;

import io.enoa.docker.dqp.docker.swarm.DQPSwarmJoin;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmUnlock;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmUpdate;
import io.enoa.docker.dket.docker.DResp;

public interface EOriginDockerSwarm {


  /**
   * Inspect swarm
   *
   * @return DResp
   */
  DResp inspect();

  /**
   * Initialize a new swarm
   *
   * @param body body
   * @return DResp
   */
  DResp init(String body);

  /**
   * Join an existing swarm
   *
   * @param body body
   * @return DResp
   */
  DResp join(String body);

  default DResp join(DQPSwarmJoin dqp) {
    return this.join(dqp.dqr().json());
  }

  default DResp leave() {
    return this.leave(Boolean.FALSE);
  }

  /**
   * Leave a swarm
   *
   * @param force boolean
   *              default false
   *              <p>
   *              Force leave swarm, even if this is the last manager or that it will break the cluster.
   * @return DResp
   */
  DResp leave(boolean force);

  /**
   * Update a swarm
   *
   * @param dqp  dqp
   * @param body body
   * @return DResp
   */
  DResp update(DQPSwarmUpdate dqp, String body);

  /**
   * Get the unlock key
   *
   * @return DResp
   */
  DResp unlockkey();

  /**
   * Unlock a locked manager
   *
   * @param body body
   * @return DResp
   */
  DResp unlock(String body);

  default DResp unlock(DQPSwarmUnlock dqp) {
    return this.unlock(dqp.dqr().json());
  }


}
