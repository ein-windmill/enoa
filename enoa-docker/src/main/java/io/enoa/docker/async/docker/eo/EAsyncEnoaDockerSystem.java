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
import io.enoa.docker.command.docker.eo.EnoaDockerSystem;
import io.enoa.docker.command.docker.eo.EoDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.dockerinfo.EDockerInfo;
import io.enoa.docker.dket.docker.system.EAuth;
import io.enoa.docker.dket.docker.system.EMonitor;
import io.enoa.docker.dket.docker.system.EYVersion;
import io.enoa.docker.dket.docker.system.Edf;
import io.enoa.docker.dqp.docker.system.DQPMonitor;
import io.enoa.docker.dqp.docker.system.DQPSystemAuth;

public class EAsyncEnoaDockerSystem {

  private EoDocker docker;
  private EnoaDockerSystem system;

  EAsyncEnoaDockerSystem(EoDocker docker) {
    this.system = docker.system();
    this.docker = docker;
  }


  public EnqueueAssetDocker<DRet<EAuth>> auth(DQPSystemAuth dqp) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.system.auth(dqp));
  }

  public EnqueueAssetDocker<DRet<EDockerInfo>> info() {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.system.info());
  }

  public EnqueueAssetDocker<DRet<EYVersion>> version() {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.system.version());
  }

  public EnqueueAssetDocker<DRet<String>> ping() {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.system.ping());
  }

  public EnqueueAssetDocker<DRet<EMonitor>> monitor() {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.system.monitor());
  }

  public EnqueueAssetDocker<DRet<EMonitor>> monitor(DQPMonitor dqp) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.system.monitor(dqp));
  }

  public EnqueueAssetDocker<DRet<Edf>> df() {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.system.df());
  }
}
