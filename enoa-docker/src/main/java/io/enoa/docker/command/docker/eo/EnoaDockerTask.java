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

import io.enoa.docker.command.docker.generic.EGeneicDockerTask;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dqp.docker.common.DQPFilter;
import io.enoa.docker.ret.docker.DRet;
import io.enoa.docker.parser.docker.DIParser;

import java.util.List;

public class EnoaDockerTask {


  private GenericDocker docker;
  private EGeneicDockerTask tasks;

  EnoaDockerTask(GenericDocker docker) {
    this.docker = docker;
    this.tasks = docker.task();
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser) {
    return this.tasks.list(parser);
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser, DQPFilter dqp) {
    return this.tasks.list(parser, dqp);
  }

  public <T> DRet<T> inspect(DIParser<T> parser, String id) {
    return this.tasks.inspect(parser, id);
  }


}
