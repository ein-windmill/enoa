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
package io.enoa.docker.command.eo;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.command.geneic.GeneicDocker;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.dret.dockerinfo.EDockerInfo;
import io.enoa.docker.parser.DIParser;

public class EnoaDockerImpl implements EoDocker {

  private GeneicDocker docker;

  public EnoaDockerImpl(GeneicDocker docker) {
    this.docker = docker;
  }

  @Override
  public DockerConfig _config() {
    return this.docker._config();
  }

  @Override
  public DRet<EDockerInfo> info(DIParser<EDockerInfo> parser) {
    return this.docker.info(parser);
  }

  @Override
  public EnoaDockerContainer container() {
    return new EnoaDockerContainer(this.docker);
  }

  @Override
  public EnoaDockerImage image() {
    return new EnoaDockerImage(this.docker);
  }

  @Override
  public EnoaDockerNetworks networks() {
    return new EnoaDockerNetworks(this.docker);
  }

  @Override
  public EnoaDockerVolumes volumes() {
    return new EnoaDockerVolumes(this.docker);
  }

  @Override
  public EnoaDockerExec exec() {
    return new EnoaDockerExec(this.docker);
  }

  @Override
  public EnoaDockerSwarm swarm() {
    return new EnoaDockerSwarm(this.docker);
  }

  @Override
  public EnoaDockerNodes nodes() {
    return new EnoaDockerNodes(this.docker);
  }

  @Override
  public EnoaDockerServices services() {
    return new EnoaDockerServices(this.docker);
  }

  @Override
  public EnoaDockerTasks tasks() {
    return new EnoaDockerTasks(this.docker);
  }

  @Override
  public EnoaDockerSecrets secrets() {
    return new EnoaDockerSecrets(this.docker);
  }

  @Override
  public EnoaDockerConfigs configs() {
    return new EnoaDockerConfigs(this.docker);
  }

  @Override
  public EnoaDockerPlugins plugins() {
    return new EnoaDockerPlugins(this.docker);
  }

  @Override
  public EnoaDockerSystem system() {
    return new EnoaDockerSystem(this.docker);
  }

  @Override
  public EnoaDockerDistribution distribution() {
    return new EnoaDockerDistribution(this.docker);
  }


}
