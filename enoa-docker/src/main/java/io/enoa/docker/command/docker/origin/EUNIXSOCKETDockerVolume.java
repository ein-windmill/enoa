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
package io.enoa.docker.command.docker.origin;

import io.enoa.docker.dqp.docker.common.DQPFilter;
import io.enoa.docker.dqp.docker.volume.DQPVolumeList;
import io.enoa.docker.ret.docker.DResp;

public class EUNIXSOCKETDockerVolume implements EOriginDockerVolume {

  private EnoaUNIXSOCKETDocker docker;

  public EUNIXSOCKETDockerVolume(EnoaUNIXSOCKETDocker docker) {
    this.docker = docker;
  }

  @Override
  public DResp list(DQPVolumeList dqp) {
    return null;
  }

  @Override
  public DResp create(String body) {
    return null;
  }

  @Override
  public DResp inspect(String id) {
    return null;
  }

  @Override
  public DResp remove(String id, Boolean force) {
    return null;
  }

  @Override
  public DResp prune(DQPFilter dqp) {
    return null;
  }
}
