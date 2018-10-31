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
import io.enoa.docker.command.docker.origin.EOriginDockerTask;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dket.docker.DRet;

import java.util.List;

public class EGeneicDockerTask {

  private OriginDocker docker;
  private DockerConfig config;
  private EOriginDockerTask tasks;

  EGeneicDockerTask(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._dockerconfig();
    this.tasks = docker.task();
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser) {
    return this.list(parser, null);
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser, DQPFilter dqp) {
    DResp resp = this.tasks.list(dqp);
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> inspect(DIParser<T> parser, String id) {
    DResp resp = this.tasks.inspect(id);
    return parser.parse(this.config, resp);
  }

}
