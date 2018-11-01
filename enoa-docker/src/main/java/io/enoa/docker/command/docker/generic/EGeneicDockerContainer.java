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
import io.enoa.docker.command.docker.origin.EOriginDockerContainer;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.container.*;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.docker.stream.DStream;
import io.enoa.docker.stream.IDStreamRunner;
import io.enoa.docker.thr.DockerException;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EGeneicDockerContainer {

  private OriginDocker docker;
  private DockerConfig config;
  private EOriginDockerContainer container;

  EGeneicDockerContainer(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._dockerconfig();
    this.container = docker.container();
    this.container = docker.container();
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser) {
    return this.list(parser, null);
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser, DQPContainerList dqp) {
    DResp origin = this.container.list(dqp);
    return parser.parse(this.config, origin);
  }

  public <T> DRet<T> create(DIParser<T> parser, String name, DQPContainerCreate dqp) {
    return this.create(parser, name, dqp == null ? null : dqp.dqr().json());
  }

  public <T> DRet<T> create(DIParser<T> parser, String name, String body) {
    DResp origin = this.container.create(name, body);
    return parser.parse(this.config, origin);
  }

  public <T> DRet<T> inspect(DIParser<T> parser, String id) {
    return this.inspect(parser, id, Boolean.FALSE);
  }

  public <T> DRet<T> inspect(DIParser<T> parser, String id, Boolean size) {
    DResp origin = this.container.inspect(id, size);
    return parser.parse(this.config, origin);
  }

  public <T> DRet<T> top(DIParser<T> parser, String id) {
    return this.top(parser, id, null);
  }

  public <T> DRet<T> top(DIParser<T> parser, String id, String para) {
    DResp origin = this.container.top(id, para);
    return parser.parse(this.config, origin);
  }

  public <T> DRet<T> logs(DIParser<T> parser, String id) {
    return this.logs(parser, id, null);
  }

  public <T> DRet<T> logs(DIParser<T> parser, String id, DQPContainerLogs dqp) {
    DResp origin = this.container.logs(id, dqp);
    return parser.parse(this.config, origin);
  }

  public <T> DRet<List<T>> changes(DIParser<List<T>> parser, String id) {
    DResp origin = this.container.changes(id);
    return parser.parse(this.config, origin);
  }

  public DRet<Void> export(String id) {
    DResp origin = this.container.export(id);
    return DIParser.voidx().parse(this.config, origin);
  }

  public <T> DRet<T> statistics(DIParser<T> parser, String id) {
    DResp origin = this.container.statistics(id);
    return parser.parse(this.config, origin);
  }

  public <T> DRet<T> statistics(DIParser<T> parser, String id, DStream<DRet<T>> dstream) {
    DStream<String> dst0 = DStream.builder((IDStreamRunner<String>) data -> dstream.runner().run(parser.parse(this.config, data)))
      .stopper(dstream.stopper())
      .build();
    DResp origin = this.container.statistics(id, dst0);
    return parser.parse(this.config, origin);
  }

  public DRet<Void> resize(String id) {
    return this.resize(id, null);
  }

  public DRet<Void> resize(String id, DQPResize dqp) {
    DResp origin = this.container.resize(id, dqp);
    return DIParser.voidx().parse(this.config, origin);
  }

  public DRet<Void> start(String id) {
    return this.start(id, null);
  }

  public DRet<Void> start(String id, DQPContainerStart dqp) {
    DResp origin = this.container.start(id, dqp);
    return DIParser.voidx().parse(this.config, origin);
  }

  public DRet<Void> stop(String id) {
    return this.stop(id, null);
  }

  public DRet<Void> stop(String id, DQPContainerTime dqp) {
    DResp origin = this.container.stop(id, dqp);
    return DIParser.voidx().parse(this.config, origin);
  }

  public DRet<Void> restart(String id) {
    return this.restart(id, null);
  }

  public DRet<Void> restart(String id, DQPContainerTime dqp) {
    DResp origin = this.container.restart(id, dqp);
    return DIParser.voidx().parse(this.config, origin);
  }

  public DRet<Void> kill(String id) {
    return this.kill(id, null);
  }

  public DRet<Void> kill(String id, DQPContainerKill dqp) {
    DResp origin = this.container.kill(id, dqp);
    return DIParser.voidx().parse(this.config, origin);
  }

  public <T> DRet<T> update(DIParser<T> parser, String id, DQPContainerUpdate dqp) {
    return this.update(parser, id, dqp.dqr().json());
  }

  public <T> DRet<T> update(DIParser<T> parser, String id, String body) {
    DResp origin = this.container.update(id, body);
    return parser.parse(this.config, origin);
  }

  public DRet<Void> rename(String id, String name) {
    DResp origin = this.container.rename(id, name);
    return DIParser.voidx().parse(this.config, origin);
  }

  public DRet<Void> pause(String id) {
    DResp origin = this.container.pause(id);
    return DIParser.voidx().parse(this.config, origin);
  }

  public DRet<Void> unpause(String id) {
    DResp origin = this.container.unpause(id);
    return DIParser.voidx().parse(this.config, origin);
  }

  public DRet<Void> attach(String id) {
    return this.attach(id, null);
  }

  public DRet<Void> attach(String id, DQPContainerAttch dqp) {
    DResp origin = this.container.attach(id, dqp);
    return DIParser.voidx().parse(this.config, origin);
  }

  @Deprecated
  public Void ws(String id) {
    throw new DockerException(EnoaTipKit.message("eo.tip.docker.notsupport"));
  }

  public <T> DRet<T> wait(DIParser<T> parser, String id) {
    DResp origin = this.container.wait(id);
    return parser.parse(this.config, origin);
  }

  public <T> DRet<T> wait(DIParser<T> parser, String id, String condition) {
    DResp origin = this.container.wait(id, condition);
    return parser.parse(this.config, origin);
  }

  public DRet<Void> remove(String id) {
    return this.remove(id, null);
  }

  public DRet<Void> remove(String id, DQPContainerRemove dqp) {
    DResp origin = this.container.remove(id, dqp);
    return DIParser.voidx().parse(this.config, origin);
  }

//  public DRet<EnoaBinary> archive(String id, String path) {
////    DResp origin = this.container.archive(id, path);
////    return DIParser.voidx().parse(this.config, origin);
////    EnoaBinary archive = this.container.archive(id, path);
////    return
//  }

  public <T> DRet<T> prune(DIParser<T> parser) {
    return this.prune(parser, null);
  }

  public <T> DRet<T> prune(DIParser<T> parser, DQPFilter dqp) {
    DResp origin = this.container.prune(dqp);
    return parser.parse(this.config, origin);
  }

}
