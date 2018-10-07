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
import io.enoa.docker.command.origin.OriginDocker;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.parser.DIParser;

public class EnoaGenericDocker implements GeneicDocker {

  private DockerConfig config;
  private OriginDocker docker;

  public EnoaGenericDocker(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._config();
  }

  @Override
  public DockerConfig _config() {
    return this.docker._config();
  }

  @Override
  public <T> DRet<T> info(DIParser<T> parser) {
    String origin = this.docker.info();
    return parser.parse(this.config, origin);
  }

  @Override
  public EGeneicDockerContainer container() {
    return new EGeneicDockerContainer(this.docker);
  }


}
