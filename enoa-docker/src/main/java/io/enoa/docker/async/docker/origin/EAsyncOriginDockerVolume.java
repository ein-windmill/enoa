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

import io.enoa.docker.async.docker.EnqueueDocker;
import io.enoa.docker.async.docker.EnqueueDoneargDocker;
import io.enoa.docker.command.docker.origin.EOriginDockerVolume;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.volume.DQPVolumeCreate;
import io.enoa.docker.dqp.docker.volume.DQPVolumeList;

public class EAsyncOriginDockerVolume {


  private OriginDocker docker;
  private EOriginDockerVolume volume;

  EAsyncOriginDockerVolume(OriginDocker docker) {
    this.docker = docker;
    this.volume = docker.volume();
  }


  public EnqueueDoneargDocker<DResp> list() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.volume.list());
  }

  public EnqueueDoneargDocker<DResp> list(DQPVolumeList dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.volume.list(dqp));
  }

  public EnqueueDoneargDocker<DResp> create(String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.volume.create(body));
  }

  public EnqueueDoneargDocker<DResp> create(DQPVolumeCreate dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.volume.create(dqp));
  }

  public EnqueueDoneargDocker<DResp> inspect(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.volume.inspect(id));
  }

  public EnqueueDoneargDocker<DResp> remove(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.volume.remove(id));
  }

  public EnqueueDoneargDocker<DResp> remove(String id, Boolean force) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.volume.remove(id, force));
  }

  public EnqueueDoneargDocker<DResp> prune() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.volume.prune());
  }

  public EnqueueDoneargDocker<DResp> prune(DQPFilter dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.volume.prune(dqp));
  }
}
