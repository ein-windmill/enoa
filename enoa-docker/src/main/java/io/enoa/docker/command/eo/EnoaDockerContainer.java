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
import io.enoa.docker.dqp.container.DQPLogs;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.dret.container.*;
import io.enoa.docker.parser.DIParser;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EnoaDockerContainer {

  private GeneicDocker docker;

  EnoaDockerContainer(GeneicDocker docker) {
    this.docker = docker;
  }

  public DRet<List<EContainer>> list() {
    return this.list(null);
  }

  public DRet<List<EContainer>> list(DQPListContainer dqp) {
    return this.docker.container().list(DIParser.listcontainer(), dqp);
  }

  public DRet<EContainerCreated> create(String name, String body) {
    return this.docker.container().create(DIParser.created(), name, body);
  }

  public DRet<EInspect> inspect(String id) {
    return this.inspect(id, Boolean.FALSE);
  }

  public DRet<EInspect> inspect(String id, Boolean size) {
    return this.docker.container().inspect(DIParser.inspect(), id, size);
  }

  public DRet<EProcesses> top(String id) {
    return this.top(id, null);
  }

  public DRet<EProcesses> top(String id, String para) {
    return this.docker.container().top(DIParser.top(), id, para);
  }

  public DRet<String> logs(String id) {
    return this.logs(id, null);
  }

  public DRet<String> logs(String id, DQPLogs dqp) {
    return this.docker.container().logs(DIParser.logs(), id, dqp);
  }

  public DRet<List<EChange>> changes(String id) {
    return this.docker.container().changes(DIParser.changes(), id);
  }

  public DRet<Void> export(String id) {
    return this.docker.container().export(id);
  }

}
