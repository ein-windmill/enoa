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
import io.enoa.docker.command.docker.origin.EOriginDockerNetwork;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dqp.network.DQPNetworkInspect;
import io.enoa.docker.dqp.network.DQPNetworkList;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.ret.docker.DRet;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EGeneicDockerNetwork {


  private OriginDocker docker;
  private DockerConfig config;
  private EOriginDockerNetwork networks;

  EGeneicDockerNetwork(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._dockerconfig();
    this.networks = docker.network();
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser) {
    return this.list(parser, null);
  }

  public <T> DRet<List<T>> list(DIParser<List<T>> parser, DQPNetworkList dqp) {
    DResp resp = this.networks.list(dqp);
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> inspect(DIParser<T> parser, String id) {
    return this.inspect(parser, id, null);
  }

  public <T> DRet<T> inspect(DIParser<T> parser, String id, DQPNetworkInspect dqp) {
    DResp resp = this.networks.inspect(id, dqp);
    return parser.parse(this.config, resp);
  }

  public DRet<Void> remove(String id) {
    DResp resp = this.networks.remove(id);
    return DIParser.voidx().parse(this.config, resp);
  }

  public <T> DRet<T> create(DIParser<T> parser, String body) {
    DResp resp = this.networks.create(body);
    return parser.parse(this.config, resp);
  }

  public DRet<Void> connect(String id, String body) {
    DResp resp = this.networks.connect(id, body);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> disconnect(String id, String body) {
    DResp resp = this.networks.disconnect(id, body);
    return DIParser.voidx().parse(this.config, resp);
  }

  public <T> DRet<T> prune(DIParser<T> parser) {
    return this.prune(parser, null);
  }

  public <T> DRet<T> prune(DIParser<T> parser, DQPFilter dqp) {
    DResp resp = this.networks.prune(dqp);
    return parser.parse(this.config, resp);
  }

}
