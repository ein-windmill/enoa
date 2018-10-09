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
package io.enoa.docker.command.geneic;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.command.origin.EOriginDockerImage;
import io.enoa.docker.command.origin.OriginDocker;
import io.enoa.docker.dqp.image.DQPListImage;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.parser.DIParser;

import java.util.List;

public class EGeneicDockerImage {

  private OriginDocker docker;
  private DockerConfig config;
  private EOriginDockerImage image;

  EGeneicDockerImage(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._dockerconfig();
    this.image = docker.image();
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser) {
    return this.list(parser, null);
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser, DQPListImage dqp) {
    String origin = this.image.list(dqp);
    return parser.parse(this.config, origin);
  }

}
