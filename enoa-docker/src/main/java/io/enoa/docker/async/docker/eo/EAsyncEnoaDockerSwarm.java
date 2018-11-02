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
package io.enoa.docker.async.docker.eo;

import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.command.docker.eo.EnoaDockerSwarm;
import io.enoa.docker.command.docker.eo.EoDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.swarm.ESwarmInspect;
import io.enoa.docker.dket.docker.swarm.ESwarmUnlockKey;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmJoin;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmUnlock;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmUpdate;
import io.enoa.toolkit.value.Void;

public class EAsyncEnoaDockerSwarm {

  private EoDocker docker;
  private EnoaDockerSwarm swarm;

  EAsyncEnoaDockerSwarm(EoDocker docker) {
    this.swarm = docker.swarm();
    this.docker = docker;
  }


  public EnqueueAssetDocker<DRet<ESwarmInspect>> inspect() {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.swarm.inspect());
  }

  public EnqueueAssetDocker<DRet<String>> init(String body) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.swarm.init(body));
  }

  public EnqueueAssetDocker<DRet<Void>> join(String body) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.swarm.join(body));
  }

  public EnqueueAssetDocker<DRet<Void>> join(DQPSwarmJoin dqp) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.swarm.join(dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> leave() {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.swarm.leave());
  }

  public EnqueueAssetDocker<DRet<Void>> leave(boolean force) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.swarm.leave(force));
  }

  public EnqueueAssetDocker<DRet<Void>> update(DQPSwarmUpdate dqp, String body) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.swarm.update(dqp, body));
  }

  public EnqueueAssetDocker<DRet<ESwarmUnlockKey>> unlockkey() {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.swarm.unlockkey());
  }

  public EnqueueAssetDocker<DRet<Void>> unlock(String body) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.swarm.unlock(body));
  }

  public EnqueueAssetDocker<DRet<Void>> unlock(DQPSwarmUnlock dqp) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.swarm.unlock(dqp));
  }
}
