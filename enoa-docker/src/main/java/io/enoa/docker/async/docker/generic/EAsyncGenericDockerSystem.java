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

import io.enoa.docker.command.docker.generic.EGenericDockerSystem;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dqp.docker.system.DQPMonitor;
import io.enoa.docker.dqp.docker.system.DQPSystemAuth;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.parser.docker.DIParser;

public class EAsyncGenericDockerSystem {

  private GenericDocker docker;
  private EGenericDockerSystem system;

  EAsyncGenericDockerSystem(GenericDocker docker) {
    this.docker = docker;
    this.system = docker.system();
  }


  public <T> EnqueueAssetDocker<DRet<T>> auth(DIParser<T> parser, DQPSystemAuth dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.system.auth(parser, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> info(DIParser<T> parser) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.system.info(parser));
  }

  public <T> EnqueueAssetDocker<DRet<T>> version(DIParser<T> parser) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.system.version(parser));
  }

  public <T> EnqueueAssetDocker<DRet<T>> ping(DIParser<T> parser) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.system.ping(parser));
  }

  public <T> EnqueueAssetDocker<DRet<T>> monitor(DIParser<T> parser) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.system.monitor(parser));
  }

  public <T> EnqueueAssetDocker<DRet<T>> monitor(DIParser<T> parser, DQPMonitor dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.system.monitor(parser, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> df(DIParser<T> parser) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.system.df(parser));
  }
}
