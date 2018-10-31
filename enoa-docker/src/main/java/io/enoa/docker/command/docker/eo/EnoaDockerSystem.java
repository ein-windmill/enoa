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

import io.enoa.docker.command.docker.generic.EGeneicDockerSystem;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dqp.docker.system.DQPMonitor;
import io.enoa.docker.dqp.docker.system.DQPSystemAuth;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.system.*;

public class EnoaDockerSystem {


  private GenericDocker docker;
  private EGeneicDockerSystem system;

  EnoaDockerSystem(GenericDocker docker) {
    this.docker = docker;
    this.system = docker.system();
  }


  public DRet<EAuth> auth(DQPSystemAuth dqp) {
    return this.system.auth(DIParser.auth(), dqp);
  }

  public DRet<EInfo> info() {
    return this.system.info(DIParser.info());
  }

  public DRet<EYVersion> version() {
    return this.system.version(DIParser.eyversion());
  }

  public DRet<String> ping() {
    return this.system.ping(DIParser.string());
  }

  public DRet<EMonitor> monitor() {
    return this.system.monitor(DIParser.monitor());
  }

  public DRet<EMonitor> monitor(DQPMonitor dqp) {
    return this.system.monitor(DIParser.monitor(), dqp);
  }

  public DRet<Edf> df() {
    return this.system.df(DIParser.edfparser());
  }

}
