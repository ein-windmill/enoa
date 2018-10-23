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

import io.enoa.docker.dqp.docker.swarm.DQPSwarmUpdate;
import io.enoa.docker.ret.docker.DResp;

public class EUNIXSOCKETDockerDockerSwarm implements EOriginDockerSwarm {

  private EnoaUNIXSOCKETDocker docker;

  EUNIXSOCKETDockerDockerSwarm(EnoaUNIXSOCKETDocker docker) {
    this.docker = docker;
  }

  @Override
  public DResp inspect() {
    return null;
  }

  @Override
  public DResp init(String body) {
    return null;
  }

  @Override
  public DResp join(String body) {
    return null;
  }

  @Override
  public DResp leave(boolean force) {
    return null;
  }

  @Override
  public DResp update(DQPSwarmUpdate dqp, String body) {
    return null;
  }

  @Override
  public DResp unlockkey() {
    return null;
  }

  @Override
  public DResp unlock(String body) {
    return null;
  }
}
