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

import io.enoa.docker.command.docker.generic.EGeneicDockerService;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.service.DQPServiceCreate;
import io.enoa.docker.dqp.docker.service.DQPServiceUpdate;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.service.DQPServiceLogs;
import io.enoa.docker.dket.docker.service.EServiceUpdate;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EnoaDockerService {


  private GenericDocker docker;
  private EGeneicDockerService services;

  EnoaDockerService(GenericDocker docker) {
    this.docker = docker;
    this.services = docker.service();
  }


  public <T> DRet<List<T>> list(DIParser<List<T>> parser) {
    return this.services.list(parser);
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser, DQPFilter dqp) {
    return this.services.list(parser, dqp);
  }

  public <T> DRet<T> create(DIParser<T> parser, String body) {
    return this.services.create(parser, body);
  }

  public <T> DRet<T> create(DIParser<T> parser, String body, DQPServiceCreate dqp) {
    return this.services.create(parser, body, dqp);
  }

  public <T> DRet<T> inspect(DIParser<T> parser, String id) {
    return this.services.inspect(parser, id);
  }

  public <T> DRet<T> inspect(DIParser<T> parser, String id, boolean insertDefaults) {
    return this.services.inspect(parser, id, insertDefaults);
  }

  public DRet<Void> remove(String id) {
    return this.services.remove(id);
  }

  public DRet<EServiceUpdate> update(String id, String body, DQPServiceUpdate dqp) {
    return this.services.update(DIParser.serviceupdate(), id, body, dqp);
  }

  public DRet<String> logs(String id) {
    return this.services.logs(DIParser.string(), id);
  }

  public DRet<String> logs(String id, DQPServiceLogs dqp) {
    return this.services.logs(DIParser.string(), id, dqp);
  }


}
