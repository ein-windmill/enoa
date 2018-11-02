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
import io.enoa.docker.dket.docker.DResp;

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
  public EOriginDockerSwarm swarm() {
    return new EUNIXSOCKETDockerDockerSwarm(this);
  }

  @Override
  public EOriginDockerNode node() {
    return new EUNIXSOCKETDockerDockerNode(this);
  }

  @Override
  public EOriginDockerService service() {
    return new EUNIXSOCKETDockerDockerService(this);
  }

  @Override
  public EOriginDockerTask task() {
    return new EUNIXSOCKETDockerDockerTask(this);
  }

  @Override
  public EOriginDockerSecret secret() {
    return new EUNIXSOCKETDockerDockerSecret(this);
  }

  @Override
  public EOriginDockerConfig config() {
    return new EUNIXSOCKETDockerConfig(this);
  }

  @Override
  public EOriginDockerPlugin plugin() {
    return new EUNIXSOCKETDockerDockerPlugin(this);
  }

  @Override
  public EOriginDockerSystem system() {
    return new EUNIXSOCKETDockerDockerSystem(this);
  }

  @Override
  public EOriginDockerDistribution distribution() {
    return new EUNIXSOCKETDockerDistribution(this);
  }
}
