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
package io.enoa.docker;

import io.enoa.docker.dket.docker.run.EDRun;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.container.DQPContainerCreate;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.async.docker.eo.*;
import io.enoa.docker.async.docker.generic.EAsyncGenericDocker;
import io.enoa.docker.async.docker.origin.EAsyncOriginDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.dockerinfo.EDockerInfo;
import io.enoa.docker.stream.DStream;

public class AsyncDocker {

  private String name;

  AsyncDocker(String name) {
    this.name = name;
  }

  public EAsyncOriginDocker origin() {
    return Docker.epm().asyncorigin(this.name);
  }

  public EAsyncGenericDocker generic() {
    return Docker.epm().asyncgeneric(this.name);
  }

  public EAsyncEnoaDocker use() {
    return Docker.epm().asynceo(this.name);
  }

  public EnqueueAssetDocker<DRet<EDockerInfo>> info() {
    return use().info();
  }

  public EAsyncEnoaDockerConfig config() {
    return use().config();
  }

  public EAsyncEnoaDockerContainer container() {
    return use().container();
  }

  public EAsyncEnoaDockerDistribution distribution() {
    return use().distribution();
  }

  public EAsyncEnoaDockerExec exec() {
    return use().exec();
  }

  public EAsyncEnoaDockerImage image() {
    return use().image();
  }

  public EAsyncEnoaDockerNetwork network() {
    return use().network();
  }

  public EAsyncEnoaDockerNode node() {
    return use().node();
  }

  public EAsyncEnoaDockerPlugin plugin() {
    return use().plugin();
  }

  public EAsyncEnoaDockerSecret secret() {
    return use().secret();
  }

  public EAsyncEnoaDockerSwarm swarm() {
    return use().swarm();
  }

  public EAsyncEnoaDockerSystem system() {
    return use().system();
  }

  public EAsyncEnoaDockerTask task() {
    return use().task();
  }

  public EAsyncEnoaDockerVolume volume() {
    return use().volume();
  }

  public EnqueueAssetDocker<DRet<EDRun>> run(String name, DQPContainerCreate create) {
    return use().run(name, create);
  }

  public EnqueueAssetDocker<DRet<EDRun>> run(String name, DQPContainerCreate create, DQPResize resize) {
    return use().run(name, create, resize);
  }

  public EnqueueAssetDocker<DRet<EDRun>> run(String name, DQPContainerCreate create, DStream<String> dstream) {
    return use().run(name, create, dstream);
  }

  public EnqueueAssetDocker<DRet<EDRun>> run(String name, DQPContainerCreate create, DStream<String> dstream, DQPResize resize) {
    return use().run(name, create, dstream, resize);
  }
}
