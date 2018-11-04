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

import io.enoa.docker.DockerConfig;
import io.enoa.docker.command.docker._DockerConfigSupport;
import io.enoa.docker.command.docker.eo.EoDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.dockerinfo.EDockerInfo;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.container.DQPContainerCreate;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.stream.DStream;

public class EAsyncEnoaDocker implements _DockerConfigSupport {


  private EoDocker docker;

  private EAsyncEnoaDockerConfig config;
  private EAsyncEnoaDockerContainer container;
  private EAsyncEnoaDockerDistribution distribution;
  private EAsyncEnoaDockerExec exec;
  private EAsyncEnoaDockerImage image;
  private EAsyncEnoaDockerNetwork network;
  private EAsyncEnoaDockerNode node;
  private EAsyncEnoaDockerPlugin plugin;
  private EAsyncEnoaDockerSecret secret;
  private EAsyncEnoaDockerSwarm swarm;
  private EAsyncEnoaDockerSystem system;
  private EAsyncEnoaDockerTask task;
  private EAsyncEnoaDockerVolume volume;

  public EAsyncEnoaDocker(EoDocker docker) {
    this.docker = docker;
    this.config = new EAsyncEnoaDockerConfig(this.docker);
    this.container = new EAsyncEnoaDockerContainer(this.docker);
    this.distribution = new EAsyncEnoaDockerDistribution(this.docker);
    this.exec = new EAsyncEnoaDockerExec(this.docker);
    this.image = new EAsyncEnoaDockerImage(this.docker);
    this.network = new EAsyncEnoaDockerNetwork(this.docker);
    this.node = new EAsyncEnoaDockerNode(this.docker);
    this.plugin = new EAsyncEnoaDockerPlugin(this.docker);
    this.secret = new EAsyncEnoaDockerSecret(this.docker);
    this.swarm = new EAsyncEnoaDockerSwarm(this.docker);
    this.system = new EAsyncEnoaDockerSystem(this.docker);
    this.task = new EAsyncEnoaDockerTask(this.docker);
    this.volume = new EAsyncEnoaDockerVolume(this.docker);
  }

  @Override
  public DockerConfig _dockerconfig() {
    return this.docker._dockerconfig();
  }

  public EnqueueAssetDocker<DRet<EDockerInfo>> info() {
    return EnqueueDocker.asseterdocker(this._dockerconfig().executor(), () -> this.docker.info());
  }

  public EAsyncEnoaDockerConfig config() {
    return this.config;
  }

  public EAsyncEnoaDockerContainer container() {
    return this.container;
  }

  public EAsyncEnoaDockerDistribution distribution() {
    return this.distribution;
  }

  public EAsyncEnoaDockerExec exec() {
    return this.exec;
  }

  public EAsyncEnoaDockerImage image() {
    return this.image;
  }

  public EAsyncEnoaDockerNetwork network() {
    return this.network;
  }

  public EAsyncEnoaDockerNode node() {
    return this.node;
  }

  public EAsyncEnoaDockerPlugin plugin() {
    return this.plugin;
  }

  public EAsyncEnoaDockerSecret secret() {
    return this.secret;
  }

  public EAsyncEnoaDockerSwarm swarm() {
    return this.swarm;
  }

  public EAsyncEnoaDockerSystem system() {
    return this.system;
  }

  public EAsyncEnoaDockerTask task() {
    return this.task;
  }

  public EAsyncEnoaDockerVolume volume() {
    return this.volume;
  }

  public EnqueueAssetDocker<DRet<String>> run(String name, DQPContainerCreate create) {
    return EnqueueDocker.asseterdocker(this._dockerconfig().executor(), () -> this.docker.run(name, create));
  }

  public EnqueueAssetDocker<DRet<String>> run(String name, DQPContainerCreate create, DQPResize resize) {
    return EnqueueDocker.asseterdocker(this._dockerconfig().executor(), () -> this.docker.run(name, create, resize));
  }

  public EnqueueAssetDocker<DRet<String>> run(String name, DQPContainerCreate create, DStream<String> dstream) {
    return EnqueueDocker.asseterdocker(this._dockerconfig().executor(), () -> this.docker.run(name, create, dstream));
  }

  public EnqueueAssetDocker<DRet<String>> run(String name, DQPContainerCreate create, DStream<String> dstream, DQPResize resize) {
    return EnqueueDocker.asseterdocker(this._dockerconfig().executor(), () -> this.docker.run(name, create, dstream, resize));
  }

}
