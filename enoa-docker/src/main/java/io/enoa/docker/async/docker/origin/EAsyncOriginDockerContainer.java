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

import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.enqueue.EnqueueDoneargDocker;
import io.enoa.docker.command.docker.origin.EOriginDockerContainer;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.container.*;
import io.enoa.docker.stream.DStream;

public class EAsyncOriginDockerContainer {

  private OriginDocker docker;
  private EOriginDockerContainer container;

  EAsyncOriginDockerContainer(OriginDocker docker) {
    this.docker = docker;
    this.container = docker.container();
  }


  public EnqueueDoneargDocker<DResp> list() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.list());
  }

  public EnqueueDoneargDocker<DResp> list(DQPContainerList dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.list(dqp));
  }

  public EnqueueDoneargDocker<DResp> create(String name, DQPContainerCreate dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.create(name, dqp));
  }

  public EnqueueDoneargDocker<DResp> create(String name, String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.create(name, body));
  }

  public EnqueueDoneargDocker<DResp> inspect(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.inspect(id));
  }

  public EnqueueDoneargDocker<DResp> inspect(String id, Boolean size) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.inspect(id, size));
  }

  public EnqueueDoneargDocker<DResp> top(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.top(id));
  }

  public EnqueueDoneargDocker<DResp> top(String id, String para) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.top(id, para));
  }

  public EnqueueDoneargDocker<DResp> logs(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.logs(id));
  }

  public EnqueueDoneargDocker<DResp> logs(String id, DQPContainerLogs dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.logs(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> changes(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.changes(id));
  }

  public EnqueueDoneargDocker<DResp> export(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.export(id));
  }

  public EnqueueDoneargDocker<DResp> statistics(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.statistics(id));
  }

  public EnqueueDoneargDocker<DResp> statistics(String id, DStream<String> dstream) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.statistics(id, dstream));
  }

  public EnqueueDoneargDocker<DResp> resize(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.resize(id));
  }

  public EnqueueDoneargDocker<DResp> resize(String id, DQPResize dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.resize(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> start(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.start(id));
  }

  public EnqueueDoneargDocker<DResp> start(String id, DQPContainerStart dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.start(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> stop(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.stop(id));
  }

  public EnqueueDoneargDocker<DResp> stop(String id, DQPContainerTime dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.stop(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> restart(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.restart(id));
  }

  public EnqueueDoneargDocker<DResp> restart(String id, DQPContainerTime dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.restart(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> kill(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.kill(id));
  }

  public EnqueueDoneargDocker<DResp> kill(String id, DQPContainerKill dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.kill(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> update(String id, DQPContainerUpdate dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.update(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> update(String id, String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.update(id, body));
  }

  public EnqueueDoneargDocker<DResp> rename(String id, String name) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.rename(id, name));
  }

  public EnqueueDoneargDocker<DResp> pause(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.pause(id));
  }

  public EnqueueDoneargDocker<DResp> unpause(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.unpause(id));
  }

  public EnqueueDoneargDocker<DResp> attach(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.attach(id));
  }

  public EnqueueDoneargDocker<DResp> attach(String id, DQPContainerAttach dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.attach(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> attach(String id, DQPContainerAttach dqp, DStream<String> dstream) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.attach(id, dqp, dstream));
  }

  @Deprecated
  public EnqueueDoneargDocker<Void> ws(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.ws(id));
  }

  public EnqueueDoneargDocker<DResp> wait(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.wait(id));
  }

  public EnqueueDoneargDocker<DResp> wait(String id, String condition) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.wait(id, condition));
  }

  public EnqueueDoneargDocker<DResp> remove(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.remove(id));
  }

  public EnqueueDoneargDocker<DResp> remove(String id, DQPContainerRemove dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.remove(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> archive(String id, String path) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.archive(id, path));
  }

  public EnqueueDoneargDocker<DResp> prune() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.prune());
  }

  public EnqueueDoneargDocker<DResp> prune(DQPFilter dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.container.prune(dqp));
  }
}
