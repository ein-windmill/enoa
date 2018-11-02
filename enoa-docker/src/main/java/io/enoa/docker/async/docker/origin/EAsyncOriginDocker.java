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

import io.enoa.docker.DockerConfig;
import io.enoa.docker.async.docker.EnqueueDocker;
import io.enoa.docker.async.docker.EnqueueDoneargDocker;
import io.enoa.docker.command.docker._DockerConfigSupport;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dket.docker.DResp;

public class EAsyncOriginDocker implements _DockerConfigSupport {

  private DockerConfig dockerconfig;
  private OriginDocker docker;

  private EAsyncOriginDockerConfig config;
  private EAsyncOriginDockerContainer container;
  private EAsyncOriginDockerDistribution distribution;
  private EAsyncOriginDockerExec exec;
  private EAsyncOriginDockerImage image;
  private EAsyncOriginDockerNetwork network;
  private EAsyncOriginDockerNode node;
  private EAsyncOriginDockerPlugin plugin;
  private EAsyncOriginDockerSecret secret;
  private EAsyncOriginDockerSwarm swarm;
  private EAsyncOriginDockerSystem system;
  private EAsyncOriginDockerTask task;
  private EAsyncOriginDockerVolume volume;

  public EAsyncOriginDocker(OriginDocker docker) {
    this.docker = docker;
    this.dockerconfig = this.docker._dockerconfig();

    this.config = new EAsyncOriginDockerConfig(this.docker);
    this.container = new EAsyncOriginDockerContainer(this.docker);
    this.distribution = new EAsyncOriginDockerDistribution(this.docker);
    this.exec = new EAsyncOriginDockerExec(this.docker);
    this.image = new EAsyncOriginDockerImage(this.docker);
    this.network = new EAsyncOriginDockerNetwork(this.docker);
    this.node = new EAsyncOriginDockerNode(this.docker);
    this.plugin = new EAsyncOriginDockerPlugin(this.docker);
    this.secret = new EAsyncOriginDockerSecret(this.docker);
    this.swarm = new EAsyncOriginDockerSwarm(this.docker);
    this.system = new EAsyncOriginDockerSystem(this.docker);
    this.task = new EAsyncOriginDockerTask(this.docker);
    this.volume = new EAsyncOriginDockerVolume(this.docker);
  }

  @Override
  public DockerConfig _dockerconfig() {
    return this.docker._dockerconfig();
  }

  public EnqueueDoneargDocker<DResp> info() {
    return EnqueueDocker.donearg(this._dockerconfig().executor(), () -> this.docker.info());
  }

  public EAsyncOriginDockerConfig config() {
    return this.config;
  }

  public EAsyncOriginDockerContainer container() {
    return this.container;
  }

  public EAsyncOriginDockerDistribution distribution() {
    return this.distribution;
  }

  public EAsyncOriginDockerExec exec() {
    return this.exec;
  }

  public EAsyncOriginDockerImage image() {
    return this.image;
  }

  public EAsyncOriginDockerNetwork network() {
    return this.network;
  }

  public EAsyncOriginDockerNode node() {
    return this.node;
  }

  public EAsyncOriginDockerPlugin plugin() {
    return this.plugin;
  }

  public EAsyncOriginDockerSecret secret() {
    return this.secret;
  }

  public EAsyncOriginDockerSwarm swarm() {
    return this.swarm;
  }

  public EAsyncOriginDockerSystem system() {
    return this.system;
  }

  public EAsyncOriginDockerTask task() {
    return this.task;
  }

  public EAsyncOriginDockerVolume volume() {
    return this.volume;
  }

}
