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
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.parser.docker.DIParser;

public class EnoaGenericDocker implements GenericDocker {

  private DockerConfig dockerconfig;
  private OriginDocker docker;
  private EGenericDockerContainer container;
  private EGenericDockerImage image;
  private EGenericDockerNetwork network;
  private EGenericDockerVolume volume;
  private EGenericDockerExec exec;
  private EGenericDockerSwarm swarm;
  private EGenericDockerNode node;
  private EGenericDockerService service;
  private EGenericDockerTask task;
  private EGenericDockerSecret secret;
  private EGenericDockerConfig config;
  private EGenericDockerPlugin plugin;
  private EGenericDockerSystem system;
  private EGenericDockerDistribution distribution;


  public EnoaGenericDocker(OriginDocker docker) {
    this.docker = docker;
    this.dockerconfig = docker._dockerconfig();

    this.container = new EGenericDockerContainer(this.docker);
    this.image = new EGenericDockerImage(this.docker);
    this.network = new EGenericDockerNetwork(this.docker);
    this.volume = new EGenericDockerVolume(this.docker);
    this.exec = new EGenericDockerExec(this.docker);
    this.swarm = new EGenericDockerSwarm(this.docker);
    this.node = new EGenericDockerNode(this.docker);
    this.service = new EGenericDockerService(this.docker);
    this.task = new EGenericDockerTask(this.docker);
    this.secret = new EGenericDockerSecret(this.docker);
    this.config = new EGenericDockerConfig(this.docker);
    this.plugin = new EGenericDockerPlugin(this.docker);
    this.system = new EGenericDockerSystem(this.docker);
    this.distribution = new EGenericDockerDistribution(this.docker);
  }

  @Override
  public DockerConfig _dockerconfig() {
    return this.docker._dockerconfig();
  }

  @Override
  public <T> DRet<T> info(DIParser<T> parser) {
    DResp origin = this.docker.info();
    return parser.parse(this._dockerconfig(), origin);
  }

  @Override
  public EGenericDockerContainer container() {
    return this.container;
  }

  @Override
  public EGenericDockerImage image() {
    return this.image;
  }

  @Override
  public EGenericDockerNetwork network() {
    return this.network;
  }

  @Override
  public EGenericDockerVolume volume() {
    return this.volume;
  }

  @Override
  public EGenericDockerExec exec() {
    return this.exec;
  }

  @Override
  public EGenericDockerSwarm swarm() {
    return this.swarm;
  }

  @Override
  public EGenericDockerNode node() {
    return this.node;
  }

  @Override
  public EGenericDockerService service() {
    return this.service;
  }

  @Override
  public EGenericDockerTask task() {
    return this.task;
  }

  @Override
  public EGenericDockerSecret secret() {
    return this.secret;
  }

  @Override
  public EGenericDockerConfig config() {
    return this.config;
  }

  @Override
  public EGenericDockerPlugin plugin() {
    return this.plugin;
  }

  @Override
  public EGenericDockerSystem system() {
    return this.system;
  }

  @Override
  public EGenericDockerDistribution distribution() {
    return this.distribution;
  }


}
