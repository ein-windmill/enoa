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

import io.enoa.chunk.stream.ChunkStream;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.container.*;

public class EUNIXSOCKETDockerContainer implements EOriginDockerContainer {

  private EnoaUNIXSOCKETDocker docker;

  EUNIXSOCKETDockerContainer(EnoaUNIXSOCKETDocker docker) {
    this.docker = docker;
  }

  @Override
  public DResp list(DQPContainerList dqp) {
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
  public DResp logs(String id, DQPContainerLogs dqp) {
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
  public DResp statistics(String id, ChunkStream stream) {
    return null;
  }

  @Override
  public DResp resize(String id, DQPResize dqp) {
    return null;
  }

  @Override
  public DResp start(String id, DQPContainerStart dqp) {
    return null;
  }

  @Override
  public DResp stop(String id, DQPContainerTime dqp) {
    return null;
  }

  @Override
  public DResp restart(String id, DQPContainerTime dqp) {
    return null;
  }

  @Override
  public DResp kill(String id, DQPContainerKill dqp) {
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
  public DResp attach(String id, DQPContainerAttach dqp, ChunkStream stream) {
    return null;
  }

  @Override
  public DResp wait(String id, String condition) {
    return null;
  }

  @Override
  public DResp remove(String id, DQPContainerRemove dqp) {
    return null;
  }

  @Override
  public DResp archive(String id, String path) {
    return null;
  }

  @Override
  public DResp prune(DQPFilter dqp) {
    return null;
  }
}
