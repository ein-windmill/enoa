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

import io.enoa.docker.command.docker.eo.EnoaDockerDistribution;
import io.enoa.docker.command.docker.eo.EoDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.distribution.EDistribution;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;

public class EAsyncEnoaDockerDistribution {

  private EoDocker docker;
  private EnoaDockerDistribution distribution;

  EAsyncEnoaDockerDistribution(EoDocker docker) {
    this.distribution = docker.distribution();
    this.docker = docker;
  }


  public EnqueueAssetDocker<DRet<EDistribution>> distribution(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.distribution.distribution(id));
  }
}
