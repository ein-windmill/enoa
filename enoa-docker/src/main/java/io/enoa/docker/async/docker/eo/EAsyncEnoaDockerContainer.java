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
import io.enoa.docker.command.docker.eo.EnoaDockerContainer;
import io.enoa.docker.command.docker.eo.EoDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.common.ECreatedWithWarning;
import io.enoa.docker.dket.docker.container.*;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.container.*;
import io.enoa.docker.stream.DStream;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EAsyncEnoaDockerContainer {

  private EoDocker docker;
  private EnoaDockerContainer container;

  EAsyncEnoaDockerContainer(EoDocker docker) {
    this.container = docker.container();
    this.docker = docker;
  }


  public EnqueueAssetDocker<DRet<List<EContainer>>> list() {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.list());
  }

  public EnqueueAssetDocker<DRet<List<EContainer>>> list(DQPContainerList dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.list(dqp));
  }

  public EnqueueAssetDocker<DRet<ECreatedWithWarning>> create(String name, DQPContainerCreate dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.create(name, dqp));
  }

  public EnqueueAssetDocker<DRet<ECreatedWithWarning>> create(String name, String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.create(name, body));
  }

  public EnqueueAssetDocker<DRet<ECInspect>> inspect(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.inspect(id));
  }

  public EnqueueAssetDocker<DRet<ECInspect>> inspect(String id, Boolean size) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.inspect(id, size));
  }

  public EnqueueAssetDocker<DRet<EProcesses>> top(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.top(id));
  }

  public EnqueueAssetDocker<DRet<EProcesses>> top(String id, String para) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.top(id, para));
  }

  public EnqueueAssetDocker<DRet<String>> logs(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.logs(id));
  }

  public EnqueueAssetDocker<DRet<String>> logs(String id, DQPContainerLogs dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.logs(id, dqp));
  }

  public EnqueueAssetDocker<DRet<List<EChange>>> changes(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.changes(id));
  }

  public EnqueueAssetDocker<DRet<Void>> export(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.export(id));
  }

  public EnqueueAssetDocker<DRet<EStatistics>> statistics(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.statistics(id));
  }

  public EnqueueAssetDocker<DRet<EStatistics>> statistics(String id, DStream<DRet<EStatistics>> dstream) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.statistics(id, dstream));
  }

  public EnqueueAssetDocker<DRet<Void>> resize(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.resize(id));
  }

  public EnqueueAssetDocker<DRet<Void>> resize(String id, DQPResize dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.resize(id, dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> start(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.start(id));
  }

  public EnqueueAssetDocker<DRet<Void>> start(String id, DQPContainerStart dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.start(id, dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> stop(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.stop(id));
  }

  public EnqueueAssetDocker<DRet<Void>> stop(String id, DQPContainerTime dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.stop(id, dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> restart(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.restart(id));
  }

  public EnqueueAssetDocker<DRet<Void>> restart(String id, DQPContainerTime dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.restart(id, dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> kill(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.kill(id));
  }

  public EnqueueAssetDocker<DRet<Void>> kill(String id, DQPContainerKill dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.kill(id, dqp));
  }

  public EnqueueAssetDocker<DRet<EUpdate>> update(String id, DQPContainerUpdate dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.update(id, dqp));
  }

  public EnqueueAssetDocker<DRet<EUpdate>> update(String id, String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.update(id, body));
  }

  public EnqueueAssetDocker<DRet<Void>> rename(String id, String name) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.rename(id, name));
  }

  public EnqueueAssetDocker<DRet<Void>> pause(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.pause(id));
  }

  public EnqueueAssetDocker<DRet<Void>> unpause(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.unpause(id));
  }

  public EnqueueAssetDocker<DRet<String>> attach(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.attach(id));
  }

  public EnqueueAssetDocker<DRet<String>> attach(String id, DQPContainerAttach dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.attach(id, dqp));
  }

  @Deprecated
  public EnqueueAssetDocker<DRet<Void>> ws(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.ws(id));
  }

  public EnqueueAssetDocker<DRet<ECWait>> wait(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.wait(id));
  }

  public EnqueueAssetDocker<DRet<ECWait>> wait(String id, String condition) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.wait(id, condition));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.remove(id));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id, DQPContainerRemove dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.remove(id, dqp));
  }

  public EnqueueAssetDocker<DRet<ECPrune>> prune() {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.prune());
  }

  public EnqueueAssetDocker<DRet<ECPrune>> prune(DQPFilter dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.prune(dqp));
  }
}
