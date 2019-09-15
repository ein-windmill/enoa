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

import io.enoa.docker.command.docker.eo.EnoaDockerVolume;
import io.enoa.docker.command.docker.eo.EoDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.volume.EVolume;
import io.enoa.docker.dket.docker.volume.EVolumeLs;
import io.enoa.docker.dket.docker.volume.EVolumePrune;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.volume.DQPVolumeCreate;
import io.enoa.docker.dqp.docker.volume.DQPVolumeList;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.toolkit.value.Void;

public class EAsyncEnoaDockerVolume {

  private EoDocker docker;
  private EnoaDockerVolume volume;

  EAsyncEnoaDockerVolume(EoDocker docker) {
    this.volume = docker.volume();
    this.docker = docker;
  }


  public EnqueueAssetDocker<DRet<EVolumeLs>> list() {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.volume.list());
  }

  public EnqueueAssetDocker<DRet<EVolumeLs>> list(DQPVolumeList dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.volume.list(dqp));
  }

  public EnqueueAssetDocker<DRet<EVolume>> create(String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.volume.create(body));
  }

  public EnqueueAssetDocker<DRet<EVolume>> create(DQPVolumeCreate dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.volume.create(dqp));
  }

  public EnqueueAssetDocker<DRet<EVolume>> inspect(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.volume.inspect(id));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.volume.remove(id));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id, Boolean force) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.volume.remove(id, force));
  }

  public EnqueueAssetDocker<DRet<EVolumePrune>> prune() {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.volume.prune());
  }

  public EnqueueAssetDocker<DRet<EVolumePrune>> prune(DQPFilter dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.volume.prune(dqp));
  }
}
