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
package io.enoa.docker.async.hub.origin;

import io.enoa.docker.DockerhubConfig;
import io.enoa.docker.command.hub._DockerhubConfigSupport;
import io.enoa.docker.command.hub.origin.OriginDockerhub;
import io.enoa.docker.dket.registry.RResp;
import io.enoa.docker.dqp.common.DQPPage;
import io.enoa.docker.dqp.dockerhub.DQPSearch;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.enqueue.EnqueueDoneargDocker;

public class EAsyncOriginDockerhub implements _DockerhubConfigSupport {

  private OriginDockerhub dockerhub;

  public EAsyncOriginDockerhub(OriginDockerhub dockerhub) {
    this.dockerhub = dockerhub;
  }

  @Override
  public DockerhubConfig _dockerhubconfig() {
    return null;
  }

  public EnqueueDoneargDocker<RResp> explore() {
    return EnqueueDocker.donearg(this._dockerhubconfig().executor(), () -> this.dockerhub.explore());
  }

  public EnqueueDoneargDocker<RResp> explore(DQPPage dqp) {
    return EnqueueDocker.donearg(this._dockerhubconfig().executor(), () -> this.dockerhub.explore(dqp));
  }

  public EnqueueDoneargDocker<RResp> search(String q) {
    return EnqueueDocker.donearg(this._dockerhubconfig().executor(), () -> this.dockerhub.search(q));
  }

  public EnqueueDoneargDocker<RResp> search(DQPSearch dqp) {
    return EnqueueDocker.donearg(this._dockerhubconfig().executor(), () -> this.dockerhub.search(dqp));
  }

  public EnqueueDoneargDocker<RResp> inspect(String repository) {
    return EnqueueDocker.donearg(this._dockerhubconfig().executor(), () -> this.dockerhub.inspect(repository));
  }

  public EnqueueDoneargDocker<RResp> tags(String repository) {
    return EnqueueDocker.donearg(this._dockerhubconfig().executor(), () -> this.dockerhub.tags(repository));
  }

  public EnqueueDoneargDocker<RResp> tags(String repository, DQPPage dqp) {
    return EnqueueDocker.donearg(this._dockerhubconfig().executor(), () -> this.dockerhub.tags(repository, dqp));
  }

  public EnqueueDoneargDocker<RResp> dockerfile(String repository) {
    return EnqueueDocker.donearg(this._dockerhubconfig().executor(), () -> this.dockerhub.dockerfile(repository));
  }

  public EnqueueDoneargDocker<RResp> autobuild(String repository) {
    return EnqueueDocker.donearg(this._dockerhubconfig().executor(), () -> this.dockerhub.autobuild(repository));
  }

  public EnqueueDoneargDocker<RResp> history(String repository) {
    return EnqueueDocker.donearg(this._dockerhubconfig().executor(), () -> this.dockerhub.history(repository));
  }

  public EnqueueDoneargDocker<RResp> history(String repository, DQPPage dqp) {
    return EnqueueDocker.donearg(this._dockerhubconfig().executor(), () -> this.dockerhub.history(repository, dqp));
  }
}
