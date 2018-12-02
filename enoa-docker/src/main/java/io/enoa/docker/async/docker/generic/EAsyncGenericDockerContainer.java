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

import io.enoa.chunk.stream.ChunkStream;
import io.enoa.docker.command.docker.generic.EGenericDockerContainer;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.container.*;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EAsyncGenericDockerContainer {

  private GenericDocker docker;
  private EGenericDockerContainer container;

  EAsyncGenericDockerContainer(GenericDocker docker) {
    this.docker = docker;
    this.container = docker.container();
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> list(DIParser<List<T>> parser) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.list(parser));
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> list(DIParser<List<T>> parser, DQPContainerList dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.list(parser, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> create(DIParser<T> parser, String name, DQPContainerCreate dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.create(parser, name, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> create(DIParser<T> parser, String name, String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.create(parser, name, body));
  }

  public <T> EnqueueAssetDocker<DRet<T>> inspect(DIParser<T> parser, String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.inspect(parser, id));
  }

  public <T> EnqueueAssetDocker<DRet<T>> inspect(DIParser<T> parser, String id, Boolean size) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.inspect(parser, id, size));
  }

  public <T> EnqueueAssetDocker<DRet<T>> top(DIParser<T> parser, String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.top(parser, id));
  }

  public <T> EnqueueAssetDocker<DRet<T>> top(DIParser<T> parser, String id, String para) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.top(parser, id, para));
  }

  public <T> EnqueueAssetDocker<DRet<T>> logs(DIParser<T> parser, String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.logs(parser, id));
  }

  public <T> EnqueueAssetDocker<DRet<T>> logs(DIParser<T> parser, String id, DQPContainerLogs dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.logs(parser, id, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> changes(DIParser<List<T>> parser, String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.changes(parser, id));
  }

  public EnqueueAssetDocker<DRet<Void>> export(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.export(id));
  }

  public <T> EnqueueAssetDocker<DRet<T>> statistics(DIParser<T> parser, String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.statistics(parser, id));
  }

  public <T> EnqueueAssetDocker<DRet<T>> statistics(DIParser<T> parser, String id, ChunkStream dstream) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.statistics(parser, id, dstream));
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

  public <T> EnqueueAssetDocker<DRet<T>> update(DIParser<T> parser, String id, DQPContainerUpdate dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.update(parser, id, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> update(DIParser<T> parser, String id, String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.update(parser, id, body));
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

  public <T> EnqueueAssetDocker<DRet<T>> attach(DIParser<T> parser, String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.attach(parser, id));
  }

  public <T> EnqueueAssetDocker<DRet<T>> attach(DIParser<T> parser, String id, DQPContainerAttach dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.attach(parser, id, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> attach(DIParser<T> parser, String id, DQPContainerAttach dqp, ChunkStream dstream) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.attach(parser, id, dqp, dstream));
  }

  @Deprecated
  public EnqueueAssetDocker<DRet<Void>> ws(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.ws(id));
  }

  public <T> EnqueueAssetDocker<DRet<T>> wait(DIParser<T> parser, String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.wait(parser, id));
  }

  public <T> EnqueueAssetDocker<DRet<T>> wait(DIParser<T> parser, String id, String condition) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.wait(parser, id, condition));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.remove(id));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id, DQPContainerRemove dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.remove(id, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> prune(DIParser<T> parser) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.prune(parser));
  }

  public <T> EnqueueAssetDocker<DRet<T>> prune(DIParser<T> parser, DQPFilter dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.container.prune(parser, dqp));
  }

}
