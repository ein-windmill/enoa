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
import io.enoa.docker.command.origin.EOriginDockerExec;
import io.enoa.docker.command.origin.OriginDocker;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.exec.DQPExecCreate;
import io.enoa.docker.dqp.exec.DQPExecStart;
import io.enoa.docker.dret.DResp;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.parser.DIParser;
import io.enoa.toolkit.value.Void;


public class EGeneicDockerExec {

  private OriginDocker docker;
  private DockerConfig config;
  private EOriginDockerExec exec;

  EGeneicDockerExec(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._dockerconfig();
    this.exec = docker.exec();
  }


  public <T> DRet<T> exec(DIParser<T> parser, String id, String body) {
    DResp resp = this.exec.exec(id, body);
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> exec(DIParser<T> parser, String id, DQPExecCreate dqp) {
    DResp resp = this.exec.exec(id, dqp);
    return parser.parse(this.config, resp);
  }

  public DRet<Void> start(String id, String body) {
    DResp resp = this.exec.start(id, body);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> start(String id, DQPExecStart dqp) {
    DResp resp = this.exec.start(id, dqp);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> resize(String id) {
    DResp resp = this.exec.resize(id);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> resize(String id, DQPResize dqp) {
    DResp resp = this.exec.resize(id, dqp);
    return DIParser.voidx().parse(this.config, resp);
  }

  public <T> DRet<T> inspect(DIParser<T> parser, String id) {
    DResp resp = this.exec.inspect(id);
    return parser.parse(this.config, resp);
  }
}
