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
import io.enoa.docker.dret.DResp;
import io.enoa.docker.stream.DStream;
import io.enoa.toolkit.binary.EnoaBinary;

public class EUNIXSOCKETDockerContainer implements EOriginDockerContainer {

  private EnoaUNIXSOCKETDocker docker;

  EUNIXSOCKETDockerContainer(EnoaUNIXSOCKETDocker docker) {
    this.docker = docker;
  }

  @Override
  public DResp list(DQPListContainer dqp) {
    return null;
  }

  @Override
  public DResp create(String name, String body) {
    return null;
  }

  @Override
  public DResp inspect(String id, Boolean size) {
    return null;
  }

  @Override
  public DResp top(String id, String para) {
    return null;
  }

  @Override
  public DResp logs(String id, DQPLogs dqp) {
    return null;
  }

  @Override
  public DResp changes(String id) {
    return null;
  }

  @Override
  public DResp export(String id) {
    return null;
  }

  @Override
  public DResp statistics(String id) {
    return null;
  }

  @Override
  public DResp statistics(String id, DStream<String> dstream) {
    return null;
  }

  @Override
  public DResp resize(String id, DQPResize dqp) {
    return null;
  }

  @Override
  public DResp start(String id, DQPStart dqp) {
    return null;
  }

  @Override
  public DResp stop(String id, DQPTime dqp) {
    return null;
  }

  @Override
  public DResp restart(String id, DQPTime dqp) {
    return null;
  }

  @Override
  public DResp kill(String id, DQPKill dqp) {
    return null;
  }

  @Override
  public DResp update(String id, String body) {
    return null;
  }

  @Override
  public DResp rename(String id, String name) {
    return null;
  }

  @Override
  public DResp pause(String id) {
    return null;
  }

  @Override
  public DResp unpause(String id) {
    return null;
  }

  @Override
  public DResp attach(String id, DQPAttch dqp) {
    return null;
  }

  @Override
  public DResp wait(String id, String condition) {
    return null;
  }

  @Override
  public DResp remove(String id, DQPRemove dqp) {
    return null;
  }

  @Override
  public DResp archive(String id, String path) {
    return null;
  }

  @Override
  public DResp prune(DQPPruneContainer dqp) {
    return null;
  }
}
