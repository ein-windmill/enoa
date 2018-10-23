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

import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.service.DQPServiceCreate;
import io.enoa.docker.dqp.docker.service.DQPServiceUpdate;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.ret.docker.service.DQPServiceLogs;

public class EUNIXSOCKETDockerDockerService implements EOriginDockerService {

  private EnoaUNIXSOCKETDocker docker;

  EUNIXSOCKETDockerDockerService(EnoaUNIXSOCKETDocker docker) {
    this.docker = docker;
  }

  @Override
  public DResp list(DQPFilter dqp) {
    return null;
  }

  @Override
  public DResp create(String body, DQPServiceCreate dqp) {
    return null;
  }

  @Override
  public DResp inspect(String id, boolean insertDefaults) {
    return null;
  }

  @Override
  public DResp remove(String id) {
    return null;
  }

  @Override
  public DResp update(String id, String body, DQPServiceUpdate dqp) {
    return null;
  }

  @Override
  public DResp logs(String id, DQPServiceLogs dqp) {
    return null;
  }
}
