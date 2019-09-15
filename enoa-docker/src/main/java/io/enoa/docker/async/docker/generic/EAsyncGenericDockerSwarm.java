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
package io.enoa.docker.async.docker.generic;

import io.enoa.docker.command.docker.generic.EGenericDockerSwarm;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmJoin;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmUnlock;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmUpdate;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.toolkit.value.Void;

public class EAsyncGenericDockerSwarm {


  private GenericDocker docker;
  private EGenericDockerSwarm swarm;

  EAsyncGenericDockerSwarm(GenericDocker docker) {
    this.swarm = docker.swarm();
    this.docker = docker;
  }


  public <T> EnqueueAssetDocker<DRet<T>> inspect(DIParser<T> parser) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.swarm.inspect(parser));
  }

  public <T> EnqueueAssetDocker<DRet<T>> init(DIParser<T> parser, String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.swarm.init(parser, body));
  }

  public EnqueueAssetDocker<DRet<Void>> join(String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.swarm.join(body));
  }

  public EnqueueAssetDocker<DRet<Void>> join(DQPSwarmJoin dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.swarm.join(dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> leave() {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.swarm.leave());
  }

  public EnqueueAssetDocker<DRet<Void>> leave(boolean force) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.swarm.leave(force));
  }

  public EnqueueAssetDocker<DRet<Void>> update(DQPSwarmUpdate dqp, String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.swarm.update(dqp, body));
  }

  public <T> EnqueueAssetDocker<DRet<T>> unlockkey(DIParser<T> parser) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.swarm.unlockkey(parser));
  }

  public EnqueueAssetDocker<DRet<Void>> unlock(String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.swarm.unlock(body));
  }

  public EnqueueAssetDocker<DRet<Void>> unlock(DQPSwarmUnlock dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.swarm.unlock(dqp));
  }
}
