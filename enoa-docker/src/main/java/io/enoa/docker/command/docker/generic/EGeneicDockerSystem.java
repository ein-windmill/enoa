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
import io.enoa.docker.command.docker.origin.EOriginDockerSystem;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dqp.docker.system.DQPMonitor;
import io.enoa.docker.dqp.docker.system.DQPSystemAuth;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.ret.docker.DRet;

public class EGeneicDockerSystem {

  private OriginDocker docker;
  private DockerConfig config;
  private EOriginDockerSystem system;

  EGeneicDockerSystem(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._dockerconfig();
    this.system = docker.system();
  }

  public <T> DRet<T> auth(DIParser<T> parser, DQPSystemAuth dqp) {
    DResp resp = this.system.auth(dqp);
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> info(DIParser<T> parser) {
    DResp resp = this.system.info();
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> version(DIParser<T> parser) {
    DResp resp = this.system.version();
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> ping(DIParser<T> parser) {
    DResp resp = this.system.ping();
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> monitor(DIParser<T> parser) {
    return this.monitor(parser, null);
  }

  public <T> DRet<T> monitor(DIParser<T> parser, DQPMonitor dqp) {
    DResp resp = this.system.monitor(dqp);
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> df(DIParser<T> parser) {
    DResp resp = this.system.df();
    return parser.parse(this.config, resp);
  }
}
