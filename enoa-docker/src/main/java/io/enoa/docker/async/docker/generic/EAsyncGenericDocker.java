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

import io.enoa.docker.DockerConfig;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.command.docker._DockerConfigSupport;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.parser.docker.DIParser;

public class EAsyncGenericDocker implements _DockerConfigSupport {

  private GenericDocker docker;

  private EAsyncGenericDockerConfig config;
  private EAsyncGenericDockerContainer container;
  private EAsyncGenericDockerDistribution distribution;
  private EAsyncGenericDockerExec exec;
  private EAsyncGenericDockerImage image;
  private EAsyncGenericDockerNetwork network;
  private EAsyncGenericDockerNode node;
  private EAsyncGenericDockerPlugin plugin;
  private EAsyncGenericDockerSecret secret;
  private EAsyncGenericDockerSwarm swarm;
  private EAsyncGenericDockerSystem system;
  private EAsyncGenericDockerTask task;
  private EAsyncGenericDockerVolume volume;

  public EAsyncGenericDocker(GenericDocker docker) {
    this.docker = docker;

    this.config = new EAsyncGenericDockerConfig(this.docker);
    this.container = new EAsyncGenericDockerContainer(this.docker);
    this.distribution = new EAsyncGenericDockerDistribution(this.docker);
    this.exec = new EAsyncGenericDockerExec(this.docker);
    this.image = new EAsyncGenericDockerImage(this.docker);
    this.network = new EAsyncGenericDockerNetwork(this.docker);
    this.node = new EAsyncGenericDockerNode(this.docker);
    this.plugin = new EAsyncGenericDockerPlugin(this.docker);
    this.secret = new EAsyncGenericDockerSecret(this.docker);
    this.swarm = new EAsyncGenericDockerSwarm(this.docker);
    this.system = new EAsyncGenericDockerSystem(this.docker);
    this.task = new EAsyncGenericDockerTask(this.docker);
    this.volume = new EAsyncGenericDockerVolume(this.docker);
  }


  @Override
  public DockerConfig _dockerconfig() {
    return this.docker._dockerconfig();
  }

  public <T> EnqueueAssetDocker<DRet<T>> info(DIParser<T> parser) {
    return EnqueueDocker.asseter(this._dockerconfig().executor(), () -> this.docker.info(parser));
  }

  public EAsyncGenericDockerConfig config() {
    return this.config;
  }

  public EAsyncGenericDockerContainer container() {
    return this.container;
  }

  public EAsyncGenericDockerDistribution distribution() {
    return this.distribution;
  }

  public EAsyncGenericDockerExec exec() {
    return this.exec;
  }

  public EAsyncGenericDockerImage image() {
    return this.image;
  }

  public EAsyncGenericDockerNetwork network() {
    return this.network;
  }

  public EAsyncGenericDockerNode node() {
    return this.node;
  }

  public EAsyncGenericDockerPlugin plugin() {
    return this.plugin;
  }

  public EAsyncGenericDockerSecret secret() {
    return this.secret;
  }

  public EAsyncGenericDockerSwarm swarm() {
    return this.swarm;
  }

  public EAsyncGenericDockerSystem system() {
    return this.system;
  }

  public EAsyncGenericDockerTask task() {
    return this.task;
  }

  public EAsyncGenericDockerVolume volume() {
    return this.volume;
  }
}
