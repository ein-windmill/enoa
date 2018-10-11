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

import io.enoa.docker.command.geneic.EGeneicDockerContainer;
import io.enoa.docker.command.geneic.GeneicDocker;
import io.enoa.docker.dqp.container.*;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.dret.container.*;
import io.enoa.docker.parser.DIParser;
import io.enoa.docker.stream.DStream;
import io.enoa.docker.thr.DockerException;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EnoaDockerContainer {

  private GeneicDocker docker;
  private EGeneicDockerContainer container;

  EnoaDockerContainer(GeneicDocker docker) {
    this.docker = docker;
    this.container = this.docker.container();
  }

  public DRet<List<EContainer>> list() {
    return this.list(null);
  }

  public DRet<List<EContainer>> list(DQPContainerList dqp) {
    return this.container.list(DIParser.listcontainer(), dqp);
  }

  public DRet<EContainerCreated> create(String name, String body) {
    return this.container.create(DIParser.created(), name, body);
  }

  public DRet<ECInspect> inspect(String id) {
    return this.inspect(id, Boolean.FALSE);
  }

  public DRet<ECInspect> inspect(String id, Boolean size) {
    return this.container.inspect(DIParser.containerinspect(), id, size);
  }

  public DRet<EProcesses> top(String id) {
    return this.top(id, null);
  }

  public DRet<EProcesses> top(String id, String para) {
    return this.container.top(DIParser.top(), id, para);
  }

  public DRet<String> logs(String id) {
    return this.logs(id, null);
  }

  public DRet<String> logs(String id, DQPContainerLogs dqp) {
    return this.container.logs(DIParser.logs(), id, dqp);
  }

  public DRet<List<EChange>> changes(String id) {
    return this.container.changes(DIParser.changes(), id);
  }

  public DRet<Void> export(String id) {
    return this.container.export(id);
  }

  public DRet<EStatistics> statistics(String id) {
    return this.container.statistics(DIParser.statistics(), id);
  }

  public DRet<EStatistics> statistics(String id, DStream<DRet<EStatistics>> dstream) {
    return this.container.statistics(DIParser.statistics(), id, dstream);
  }

  public DRet<Void> resize(String id) {
    return this.resize(id, null);
  }

  public DRet<Void> resize(String id, DQPContainerResize dqp) {
    return this.container.resize(id, dqp);
  }

  public DRet<Void> start(String id) {
    return this.container.start(id);
  }

  public DRet<Void> start(String id, DQPContainerStart dqp) {
    return this.container.start(id, dqp);
  }

  public DRet<Void> stop(String id) {
    return this.container.stop(id);
  }

  public DRet<Void> stop(String id, DQPContainerTime dqp) {
    return this.container.stop(id, dqp);
  }

  public DRet<Void> restart(String id) {
    return this.container.restart(id);
  }

  public DRet<Void> restart(String id, DQPContainerTime dqp) {
    return this.container.restart(id, dqp);
  }

  public DRet<Void> kill(String id) {
    return this.container.kill(id);
  }

  public DRet<Void> kill(String id, DQPContainerKill dqp) {
    return this.container.kill(id, dqp);
  }

  public DRet<EUpdate> update(String id, DQPContainerUpdate dqp) {
    return this.container.update(DIParser.update(), id, dqp);
  }

  public DRet<EUpdate> update(String id, String body) {
    return this.container.update(DIParser.update(), id, body);
  }

  public DRet<Void> rename(String id, String name) {
    return this.container.rename(id, name);
  }

  public DRet<Void> pause(String id) {
    return this.container.pause(id);
  }

  public DRet<Void> unpause(String id) {
    return this.container.unpause(id);
  }

  public DRet<Void> attach(String id) {
    return this.container.attach(id);
  }

  public DRet<Void> attach(String id, DQPAttch dqp) {
    return this.container.attach(id, dqp);
  }

  @Deprecated
  public Void ws(String id) {
    throw new DockerException(EnoaTipKit.message("eo.tip.docker.notsupport"));
  }

  public DRet<ECWait> wait(String id) {
    return this.container.wait(DIParser.waitx(), id);
  }

  public DRet<ECWait> wait(String id, String condition) {
    return this.container.wait(DIParser.waitx(), id, condition);
  }

  public DRet<Void> remove(String id) {
    return this.container.remove(id);
  }

  public DRet<Void> remove(String id, DQPContainerRemove dqp) {
    return this.container.remove(id, dqp);
  }

//  public DRet<Void> archive(String id, String path) {
//    return this.container.archive(id, path);
//  }

  public DRet<ECPrune> prune() {
    return this.prune(null);
  }

  public DRet<ECPrune> prune(DQPContainerPrune dqp) {
    return this.container.prune(DIParser.containerprune(), dqp);
  }

}
