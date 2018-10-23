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

import io.enoa.docker.command.docker.generic.EGeneicDockerExec;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.exec.DQPExecCreate;
import io.enoa.docker.dqp.exec.DQPExecStart;
import io.enoa.docker.ret.docker.DRet;
import io.enoa.docker.ret.docker.common.ECreated;
import io.enoa.docker.ret.docker.exec.EExecInspect;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.toolkit.value.Void;

public class EnoaDockerExec {

  private GenericDocker docker;
  private EGeneicDockerExec exec;

  EnoaDockerExec(GenericDocker docker) {
    this.docker = docker;
    this.exec = docker.exec();
  }

  public DRet<ECreated> exec(String id, String body) {
    return this.exec.exec(DIParser.created(), id, body);
  }

  public DRet<ECreated> exec(String id, DQPExecCreate dqp) {
    return this.exec.exec(DIParser.created(), id, dqp);
  }

  public DRet<Void> start(String id, String body) {
    return this.exec.start(id, body);
  }

  public DRet<Void> start(String id, DQPExecStart dqp) {
    return this.exec.start(id, dqp);
  }

  public DRet<Void> resize(String id) {
    return this.exec.resize(id);
  }

  public DRet<Void> resize(String id, DQPResize dqp) {
    return this.exec.resize(id, dqp);
  }

  public DRet<EExecInspect> inspect(String id) {
    return this.exec.inspect(DIParser.execinspect(), id);
  }
}
