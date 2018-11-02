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

import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.command.docker.generic.EGenericDockerVolume;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.volume.DQPVolumeCreate;
import io.enoa.docker.dqp.docker.volume.DQPVolumeList;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.toolkit.value.Void;

public class EAsyncGenericDockerVolume {


  private GenericDocker docker;
  private EGenericDockerVolume volume;

  EAsyncGenericDockerVolume(GenericDocker docker) {
    this.volume = docker.volume();
    this.docker = docker;
  }

  public <T> EnqueueAssetDocker<DRet<T>> list(DIParser<T> parser) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.volume.list(parser));
  }

  public <T> EnqueueAssetDocker<DRet<T>> list(DIParser<T> parser, DQPVolumeList dqp) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.volume.list(parser, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> create(DIParser<T> parser, String body) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.volume.create(parser, body));
  }

  public <T> EnqueueAssetDocker<DRet<T>> create(DIParser<T> parser, DQPVolumeCreate dqp) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.volume.create(parser, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> inspect(DIParser<T> parser, String id) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.volume.inspect(parser, id));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.volume.remove(id));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id, Boolean force) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.volume.remove(id, force));
  }

  public <T> EnqueueAssetDocker<DRet<T>> prune(DIParser<T> parser) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.volume.prune(parser));
  }

  public <T> EnqueueAssetDocker<DRet<T>> prune(DIParser<T> parser, DQPFilter dqp) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.volume.prune(parser, dqp));
  }
}
