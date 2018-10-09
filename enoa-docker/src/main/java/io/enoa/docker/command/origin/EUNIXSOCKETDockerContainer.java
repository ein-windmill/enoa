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

import io.enoa.docker.dqp.container.*;
import io.enoa.toolkit.binary.EnoaBinary;

public class EUNIXSOCKETDockerContainer implements EOriginDockerContainer {

  private EnoaUNIXSOCKETDocker docker;

  EUNIXSOCKETDockerContainer(EnoaUNIXSOCKETDocker docker) {
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

  @Override
  public String export(String id) {
    return null;
  }

  @Override
  public String statistics(String id, Boolean stream) {
    return null;
  }

  @Override
  public String resize(String id, DQPResize dqp) {
    return null;
  }

  @Override
  public String start(String id, DQPStart dqp) {
    return null;
  }

  @Override
  public String stop(String id, DQPTime dqp) {
    return null;
  }

  @Override
  public String restart(String id, DQPTime dqp) {
    return null;
  }

  @Override
  public String kill(String id, DQPKill dqp) {
    return null;
  }

  @Override
  public String update(String id, String body) {
    return null;
  }

  @Override
  public String rename(String id, String name) {
    return null;
  }

  @Override
  public String pause(String id) {
    return null;
  }

  @Override
  public String unpause(String id) {
    return null;
  }

  @Override
  public String attach(String id, DQPAttch dqp) {
    return null;
  }

  @Override
  public String wait(String id, String condition) {
    return null;
  }

  @Override
  public String remove(String id, DQPRemove dqp) {
    return null;
  }

  @Override
  public EnoaBinary archive(String id, String path) {
    return null;
  }

  @Override
  public String prune(DQPPrune dqp) {
    return null;
  }
}
