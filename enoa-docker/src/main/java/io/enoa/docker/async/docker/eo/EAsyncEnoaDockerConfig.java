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

import io.enoa.docker.command.docker.eo.EnoaDockerConfig;
import io.enoa.docker.command.docker.eo.EoDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.common.ECreated;
import io.enoa.docker.dket.docker.config.EConfig;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EAsyncEnoaDockerConfig {

  private EoDocker docker;
  private EnoaDockerConfig config;

  EAsyncEnoaDockerConfig(EoDocker docker) {
    this.config = docker.config();
    this.docker = docker;
  }


  public EnqueueAssetDocker<DRet<List<EConfig>>> list() {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.config.list());
  }

  public EnqueueAssetDocker<DRet<List<EConfig>>> list(DQPFilter dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.config.list(dqp));
  }

  public EnqueueAssetDocker<DRet<ECreated>> create(String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.config.create(body));
  }

  public EnqueueAssetDocker<DRet<EConfig>> inspect(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.config.inspect(id));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.config.remove(id));
  }

  public EnqueueAssetDocker<DRet<Void>> update(String id, long version, String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.config.update(id, version, body));
  }
}
