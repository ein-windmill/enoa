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

import io.enoa.docker.DockerConfig;

public class EnoaUNIXSOCKETDocker implements OriginDocker {

  private DockerConfig config;

  public EnoaUNIXSOCKETDocker(DockerConfig config) {
    this.config = config;
  }

  @Override
  public DockerConfig _config() {
    return this.config;
  }

  @Override
  public String info() {
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
  public EOriginDockerNetworks networks() {
    return new EUNIXSOCKETDockerNetworks(this);
  }

  @Override
  public EOriginDockerVolumes volumes() {
    return new EUNIXSOCKETDockerVolumes(this);
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
  public EOriginNodes nodes() {
    return new EUNIXSOCKETDockerNodes(this);
  }

  @Override
  public EOriginServices services() {
    return new EUNIXSOCKETDockerServices(this);
  }

  @Override
  public EOriginTasks tasks() {
    return new EUNIXSOCKETDockerTasks(this);
  }

  @Override
  public EOriginSecrets secrets() {
    return new EUNIXSOCKETDockerSecrets(this);
  }

  @Override
  public EOriginConfigs configs() {
    return new EUNIXSOCKETDockerConfigs(this);
  }

  @Override
  public EOriginPlugins plugins() {
    return new EUNIXSOCKETDockerPlugins(this);
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
