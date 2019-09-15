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

import io.enoa.docker.command.docker.origin.EOriginDockerExec;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.exec.DQPExecCreate;
import io.enoa.docker.dqp.docker.exec.DQPExecStart;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.enqueue.EnqueueDoneargDocker;

public class EAsyncOriginDockerExec {

  private OriginDocker docker;
  private EOriginDockerExec exec;

  EAsyncOriginDockerExec(OriginDocker docker) {
    this.docker = docker;
    this.exec = docker.exec();
  }


  public EnqueueDoneargDocker<DResp> exec(String id, String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.exec.exec(id, body));
  }

  public EnqueueDoneargDocker<DResp> exec(String id, DQPExecCreate dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.exec.exec(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> start(String id, String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.exec.start(id, body));
  }

  public EnqueueDoneargDocker<DResp> start(String id, DQPExecStart dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.exec.start(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> resize(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.exec.resize(id));
  }

  public EnqueueDoneargDocker<DResp> resize(String id, DQPResize dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.exec.resize(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> inspect(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.exec.inspect(id));
  }
}
