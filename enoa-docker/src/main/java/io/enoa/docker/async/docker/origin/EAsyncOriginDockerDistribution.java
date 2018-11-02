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
import io.enoa.docker.command.docker.origin.EOriginDockerDistribution;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dket.docker.DResp;

public class EAsyncOriginDockerDistribution {

  private OriginDocker docker;
  private EOriginDockerDistribution distribution;

  EAsyncOriginDockerDistribution(OriginDocker docker) {
    this.docker = docker;
    this.distribution = docker.distribution();
  }

  public EnqueueDoneargDocker<DResp> distribution(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.distribution.distribution(id));
  }

}
