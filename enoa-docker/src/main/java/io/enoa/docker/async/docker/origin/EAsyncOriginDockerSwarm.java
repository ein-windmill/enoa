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
package io.enoa.docker.async.docker.origin;

import io.enoa.docker.async.docker.EnqueueDocker;
import io.enoa.docker.async.docker.EnqueueDoneargDocker;
import io.enoa.docker.command.docker.origin.EOriginDockerSwarm;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmJoin;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmUnlock;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmUpdate;

public class EAsyncOriginDockerSwarm {

  private OriginDocker docker;

  private EOriginDockerSwarm swarm;

  EAsyncOriginDockerSwarm(OriginDocker docker) {
    this.docker = docker;
    this.swarm = docker.swarm();
  }


  public EnqueueDoneargDocker<DResp> inspect() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.swarm.inspect());
  }

  public EnqueueDoneargDocker<DResp> init(String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.swarm.init(body));
  }

  public EnqueueDoneargDocker<DResp> join(String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.swarm.join(body));
  }

  public EnqueueDoneargDocker<DResp> join(DQPSwarmJoin dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.swarm.join(dqp));
  }

  public EnqueueDoneargDocker<DResp> leave() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.swarm.leave());
  }

  public EnqueueDoneargDocker<DResp> leave(boolean force) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.swarm.leave(force));
  }

  public EnqueueDoneargDocker<DResp> update(DQPSwarmUpdate dqp, String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.swarm.update(dqp, body));
  }

  public EnqueueDoneargDocker<DResp> unlockkey() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.swarm.unlockkey());
  }

  public EnqueueDoneargDocker<DResp> unlock(String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.swarm.unlock(body));
  }

  public EnqueueDoneargDocker<DResp> unlock(DQPSwarmUnlock dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.swarm.unlock(dqp));
  }
}
