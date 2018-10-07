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

import io.enoa.docker.command.geneic.GeneicDocker;
import io.enoa.docker.dqp.container.DQPListContainer;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.dret.container.EContainer;
import io.enoa.docker.dret.container.EContainerCreated;
import io.enoa.docker.parser.DIParser;

import java.util.List;

public class EnoaDockerContainer {

  private GeneicDocker docker;

  EnoaDockerContainer(GeneicDocker docker) {
    this.docker = docker;
  }

  public DRet<List<EContainer>> ps() {
    return this.ps(DQPListContainer.create());
  }

  public DRet<List<EContainer>> ps(DQPListContainer dqp) {
    return this.docker.container().ps(DIParser.ps(), dqp);
  }

  public DRet<EContainerCreated> create(String name, String body) {
    return this.docker.container().create(name, body);
  }

}
