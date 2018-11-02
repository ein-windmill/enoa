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

import io.enoa.docker.async.docker.EnqueueAssetDocker;
import io.enoa.docker.async.docker.EnqueueDocker;
import io.enoa.docker.command.docker.eo.EnoaDockerExec;
import io.enoa.docker.command.docker.eo.EoDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.common.ECreated;
import io.enoa.docker.dket.docker.exec.EExecInspect;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.exec.DQPExecCreate;
import io.enoa.docker.dqp.docker.exec.DQPExecStart;
import io.enoa.toolkit.value.Void;

public class EAsyncEnoaDockerExec {

  private EoDocker docker;
  private EnoaDockerExec exec;

  EAsyncEnoaDockerExec(EoDocker docker) {
    this.exec = docker.exec();
    this.docker = docker;
  }

  public EnqueueAssetDocker<DRet<ECreated>> exec(String id, String body) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.exec.exec(id, body));
  }

  public EnqueueAssetDocker<DRet<ECreated>> exec(String id, DQPExecCreate dqp) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.exec.exec(id, dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> start(String id, String body) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.exec.start(id, body));
  }

  public EnqueueAssetDocker<DRet<Void>> start(String id, DQPExecStart dqp) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.exec.start(id, dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> resize(String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.exec.resize(id));
  }

  public EnqueueAssetDocker<DRet<Void>> resize(String id, DQPResize dqp) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.exec.resize(id, dqp));
  }

  public EnqueueAssetDocker<DRet<EExecInspect>> inspect(String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.exec.inspect(id));
  }
}
