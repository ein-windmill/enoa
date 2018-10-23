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

import io.enoa.docker.DockerConfig;
import io.enoa.docker.ret.docker.DResp;

public class EnoaUNIXSOCKETDocker implements OriginDocker {

  private DockerConfig config;

  public EnoaUNIXSOCKETDocker(DockerConfig config) {
    this.config = config;
  }

  @Override
  public DockerConfig _dockerconfig() {
    return this.config;
  }

  @Override
  public DResp info() {
    return null;
  }

  @Override
  public EOriginDockerContainer container() {
    return new EUNIXSOCKETDockerContainer(this);
  }

  @Override
  public EOriginDockerImage image() {
    return new EUNIXSOCKETDockerImage(this);
  }

  @Override
  public EOriginDockerNetwork network() {
    return new EUNIXSOCKETDockerNetwork(this);
  }

  @Override
  public EOriginDockerVolume volume() {
    return new EUNIXSOCKETDockerVolume(this);
  }

  @Override
  public EOriginDockerExec exec() {
    return new EUNIXSOCKETDockerExec(this);
  }

  @Override
  public EOriginSwarm swarm() {
    return new EUNIXSOCKETDockerSwarm(this);
  }

  @Override
  public EOriginNode node() {
    return new EUNIXSOCKETDockerNode(this);
  }

  @Override
  public EOriginService service() {
    return new EUNIXSOCKETDockerService(this);
  }

  @Override
  public EOriginTask task() {
    return new EUNIXSOCKETDockerTask(this);
  }

  @Override
  public EOriginSecret secret() {
    return new EUNIXSOCKETDockerSecret(this);
  }

  @Override
  public EOriginConfig config() {
    return new EUNIXSOCKETDockerConfig(this);
  }

  @Override
  public EOriginPlugin plugin() {
    return new EUNIXSOCKETDockerPlugin(this);
  }

  @Override
  public EOriginSystem system() {
    return new EUNIXSOCKETDockerSystem(this);
  }

  @Override
  public EOriginDistribution distribution() {
    return new EUNIXSOCKETDockerDistribution(this);
  }
}
