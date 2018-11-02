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
package io.enoa.docker.command.docker.generic;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.command.docker.origin.EOriginDockerSwarm;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmJoin;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmUnlock;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmUpdate;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.toolkit.value.Void;

public class EGenericDockerSwarm {

  private OriginDocker docker;
  private DockerConfig config;
  private EOriginDockerSwarm swarm;

  EGenericDockerSwarm(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._dockerconfig();
    this.swarm = docker.swarm();
  }

  public <T> DRet<T> inspect(DIParser<T> parser) {
    DResp resp = this.swarm.inspect();
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> init(DIParser<T> parser, String body) {
    DResp resp = this.swarm.init(body);
    return parser.parse(this.config, resp);
  }

  public DRet<Void> join(String body) {
    DResp resp = this.swarm.join(body);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> join(DQPSwarmJoin dqp) {
    DResp resp = this.swarm.join(dqp);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> leave() {
    return this.leave(Boolean.FALSE);
  }

  public DRet<Void> leave(boolean force) {
    DResp resp = this.swarm.leave(force);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> update(DQPSwarmUpdate dqp, String body) {
    DResp resp = this.swarm.update(dqp, body);
    return DIParser.voidx().parse(this.config, resp);
  }

  public <T> DRet<T> unlockkey(DIParser<T> parser) {
    DResp resp = this.swarm.unlockkey();
    return parser.parse(this.config, resp);
  }

  public DRet<Void> unlock(String body) {
    DResp resp = this.swarm.unlock(body);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> unlock(DQPSwarmUnlock dqp) {
    DResp resp = this.swarm.unlock(dqp);
    return DIParser.voidx().parse(this.config, resp);
  }
}
