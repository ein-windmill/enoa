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

import io.enoa.docker.command.docker.origin.EOriginDockerSystem;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dqp.docker.system.DQPMonitor;
import io.enoa.docker.dqp.docker.system.DQPSystemAuth;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.enqueue.EnqueueDoneargDocker;

public class EAsyncOriginDockerSystem {

  private OriginDocker docker;
  private EOriginDockerSystem system;

  EAsyncOriginDockerSystem(OriginDocker docker) {
    this.docker = docker;
    this.system = docker.system();
  }

  public EnqueueDoneargDocker<DResp> auth(DQPSystemAuth dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.system.auth(dqp));
  }

  public EnqueueDoneargDocker<DResp> info() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.system.info());
  }

  public EnqueueDoneargDocker<DResp> version() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.system.version());
  }

  public EnqueueDoneargDocker<DResp> ping() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.system.ping());
  }

  public EnqueueDoneargDocker<DResp> monitor() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.system.monitor());
  }

  public EnqueueDoneargDocker<DResp> monitor(DQPMonitor dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.system.monitor(dqp));
  }

  public EnqueueDoneargDocker<DResp> df() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.system.df());
  }
}
