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
package io.enoa.docker.command.docker.generic;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.command.docker.origin.EOriginDistribution;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.ret.docker.DRet;
import io.enoa.docker.parser.docker.DIParser;

public class EGeneicDockerDistribution {

  private OriginDocker docker;
  private DockerConfig config;
  private EOriginDistribution distribution;

  EGeneicDockerDistribution(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._dockerconfig();
    this.distribution = docker.distribution();
  }

  public <T> DRet<T> distribution(DIParser<T> parser, String id) {
    DResp resp = this.distribution.distribution(id);
    return parser.parse(this.config, resp);
  }

}
