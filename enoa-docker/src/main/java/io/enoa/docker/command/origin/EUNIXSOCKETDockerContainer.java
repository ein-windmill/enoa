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

import io.enoa.docker.dqp.container.DQPListContainer;
import io.enoa.docker.dqp.container.DQPLogs;

public class EUNIXSOCKETDockerContainer implements EOriginDockerContainer {

  private OriginDocker docker;

  EUNIXSOCKETDockerContainer(OriginDocker docker) {
    this.docker = docker;
  }

  @Override
  public String list(DQPListContainer dqp) {
    return null;
  }

  @Override
  public String create(String name, String body) {
    return null;
  }

  @Override
  public String inspect(String id, Boolean size) {
    return null;
  }

  @Override
  public String top(String id, String para) {
    return null;
  }

  @Override
  public String logs(String id, DQPLogs dqp) {
    return null;
  }

  @Override
  public String changes(String id) {
    return null;
  }
}
