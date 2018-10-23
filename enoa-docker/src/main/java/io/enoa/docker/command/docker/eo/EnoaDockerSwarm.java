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
package io.enoa.docker.command.docker.eo;

import io.enoa.docker.command.docker.generic.EGeneicDockerSwarm;
import io.enoa.docker.command.docker.generic.GeneicDocker;
import io.enoa.docker.dqp.swarm.DQPSwarmJoin;
import io.enoa.docker.dqp.swarm.DQPSwarmUnlock;
import io.enoa.docker.dqp.swarm.DQPSwarmUpdate;
import io.enoa.docker.ret.docker.DRet;
import io.enoa.docker.ret.docker.swarm.ESwarmInspect;
import io.enoa.docker.ret.docker.swarm.ESwarmUnlockKey;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.toolkit.value.Void;

public class EnoaDockerSwarm {

  private GeneicDocker docker;
  private EGeneicDockerSwarm swarm;

  EnoaDockerSwarm(GeneicDocker docker) {
    this.docker = docker;
    this.swarm = docker.swarm();
  }

  public DRet<ESwarmInspect> inspect() {
    return this.swarm.inspect(DIParser.swarminspect());
  }

  public DRet<String> init(String body) {
    return this.swarm.init(DIParser.string(), body);
  }

  public DRet<Void> join(String body) {
    return this.swarm.join(body);
  }

  public DRet<Void> join(DQPSwarmJoin dqp) {
    return this.swarm.join(dqp);
  }

  public DRet<Void> leave() {
    return this.swarm.leave();
  }

  public DRet<Void> leave(boolean force) {
    return this.swarm.leave(force);
  }

  public DRet<Void> update(DQPSwarmUpdate dqp, String body) {
    return this.swarm.update(dqp, body);
  }

  public DRet<ESwarmUnlockKey> unlockkey() {
    return this.swarm.unlockkey(DIParser.swarmunlockkey());
  }

  public DRet<Void> unlock(String body) {
    return this.swarm.unlock(body);
  }

  public DRet<Void> unlock(DQPSwarmUnlock dqp) {
    return this.swarm.unlock(dqp);
  }

}
