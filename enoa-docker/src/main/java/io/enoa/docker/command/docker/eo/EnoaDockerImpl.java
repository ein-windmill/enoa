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
package io.enoa.docker.command.docker.eo;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.ret.docker.DRet;
import io.enoa.docker.ret.docker.dockerinfo.EDockerInfo;
import io.enoa.docker.parser.docker.DIParser;

public class EnoaDockerImpl implements EoDocker {

  private GenericDocker docker;

  public EnoaDockerImpl(GenericDocker docker) {
    this.docker = docker;
  }

  @Override
  public DockerConfig _dockerconfig() {
    return this.docker._dockerconfig();
  }

  @Override
  public DRet<EDockerInfo> info() {
    return this.docker.info(DIParser.dockerinfo());
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
  public EnoaDockerNetwork network() {
    return new EnoaDockerNetwork(this.docker);
  }

  @Override
  public EnoaDockerVolume volume() {
    return new EnoaDockerVolume(this.docker);
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
  public EnoaDockerNode node() {
    return new EnoaDockerNode(this.docker);
  }

  @Override
  public EnoaDockerService service() {
    return new EnoaDockerService(this.docker);
  }

  @Override
  public EnoaDockerTask task() {
    return new EnoaDockerTask(this.docker);
  }

  @Override
  public EnoaDockerSecret secret() {
    return new EnoaDockerSecret(this.docker);
  }

  @Override
  public EnoaDockerConfig config() {
    return new EnoaDockerConfig(this.docker);
  }

  @Override
  public EnoaDockerPlugin plugin() {
    return new EnoaDockerPlugin(this.docker);
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
