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
package io.enoa.docker.command.docker.geneic;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.ret.docker.DRet;
import io.enoa.docker.parser.docker.DIParser;

public class EnoaGenericDocker implements GeneicDocker {

  private DockerConfig config;
  private OriginDocker docker;

  public EnoaGenericDocker(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._dockerconfig();
  }

  @Override
  public DockerConfig _dockerconfig() {
    return this.docker._dockerconfig();
  }

  @Override
  public <T> DRet<T> info(DIParser<T> parser) {
    DResp origin = this.docker.info();
    return parser.parse(this.config, origin);
  }

  @Override
  public EGeneicDockerContainer container() {
    return new EGeneicDockerContainer(this.docker);
  }

  @Override
  public EGeneicDockerImage image() {
    return new EGeneicDockerImage(this.docker);
  }

  @Override
  public EGeneicDockerNetwork network() {
    return new EGeneicDockerNetwork(this.docker);
  }

  @Override
  public EGeneicDockerVolume volume() {
    return new EGeneicDockerVolume(this.docker);
  }

  @Override
  public EGeneicDockerExec exec() {
    return new EGeneicDockerExec(this.docker);
  }

  @Override
  public EGeneicDockerSwarm swarm() {
    return new EGeneicDockerSwarm(this.docker);
  }

  @Override
  public EGeneicDockerNode node() {
    return new EGeneicDockerNode(this.docker);
  }

  @Override
  public EGeneicDockerService service() {
    return new EGeneicDockerService(this.docker);
  }

  @Override
  public EGeneicDockerTask task() {
    return new EGeneicDockerTask(this.docker);
  }

  @Override
  public EGeneicDockerSecret secret() {
    return new EGeneicDockerSecret(this.docker);
  }

  @Override
  public EGeneicDockerConfig config() {
    return new EGeneicDockerConfig(this.docker);
  }

  @Override
  public EGeneicDockerPlugin plugin() {
    return new EGeneicDockerPlugin(this.docker);
  }

  @Override
  public EGeneicDockerSystem system() {
    return new EGeneicDockerSystem(this.docker);
  }

  @Override
  public EGeneicDockerDistribution distribution() {
    return new EGeneicDockerDistribution(this.docker);
  }


}
