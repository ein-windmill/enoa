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
package io.enoa.docker.command.origin;

import io.enoa.docker.dqp.system.DQPAuth;
import io.enoa.docker.dqp.system.DQPMonitor;
import io.enoa.docker.dret.DResp;

public class EUNIXSOCKETDockerSystem implements EOriginSystem {

  private EnoaUNIXSOCKETDocker docker;

  EUNIXSOCKETDockerSystem(EnoaUNIXSOCKETDocker docker) {
    this.docker = docker;
  }

  @Override
  public DResp auth(DQPAuth dqp) {
    return null;
  }

  @Override
  public DResp info() {
    return null;
  }

  @Override
  public DResp version() {
    return null;
  }

  @Override
  public DResp ping() {
    return null;
  }

  @Override
  public DResp monitor(DQPMonitor dqp) {
    return null;
  }

  @Override
  public DResp df() {
    return null;
  }
}
