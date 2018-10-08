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
import io.enoa.docker.dqp.container.DQPListContainer;
import io.enoa.docker.dqp.container.DQPLogs;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.parser.DIParser;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EGeneicDockerContainer {

  private OriginDocker docker;
  private DockerConfig config;

  EGeneicDockerContainer(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._config();
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser) {
    return this.list(parser, null);
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser, DQPListContainer dqp) {
    String origin = this.docker.container().list(dqp);
    return parser.parse(this.config, origin);
  }

  public <T> DRet<T> create(DIParser<T> parser, String name, String body) {
    String origin = this.docker.container().create(name, body);
    return parser.parse(this.config, origin);
  }

  public <T> DRet<T> inspect(DIParser<T> parser, String id) {
    return this.inspect(parser, id, Boolean.FALSE);
  }

  public <T> DRet<T> inspect(DIParser<T> parser, String id, Boolean size) {
    String origin = this.docker.container().inspect(id, size);
    return parser.parse(this.config, origin);
  }

  public <T> DRet<T> top(DIParser<T> parser, String id) {
    return this.top(parser, id, null);
  }

  public <T> DRet<T> top(DIParser<T> parser, String id, String para) {
    String origin = this.docker.container().top(id, para);
    return parser.parse(this.config, origin);
  }

  public <T> DRet<T> logs(DIParser<T> parser, String id) {
    return this.logs(parser, id, null);
  }

  public <T> DRet<T> logs(DIParser<T> parser, String id, DQPLogs dqp) {
    String origin = this.docker.container().logs(id, dqp);
    return parser.parse(this.config, origin);
  }

  public <T> DRet<List<T>> changes(DIParser<List<T>> parser, String id) {
    String origin = this.docker.container().changes(id);
    return parser.parse(this.config, origin);
  }

  public DRet<Void> export(String id) {
    String origin = this.docker.container().export(id);
    return DIParser.voidx().parse(this.config, origin);
  }

}
