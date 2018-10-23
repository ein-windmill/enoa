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
import io.enoa.docker.command.origin.EOriginNode;
import io.enoa.docker.command.origin.OriginDocker;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.ret.docker.DRet;
import io.enoa.docker.parser.DIParser;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EGeneicDockerNode {

  private OriginDocker docker;
  private DockerConfig config;
  private EOriginNode nodes;

  EGeneicDockerNode(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._dockerconfig();
    this.nodes = docker.node();
  }

  public <T> DRet<List<T>> nodes(DIParser<List<T>> parser, DQPFilter dqp) {
    DResp resp = this.nodes.nodes(dqp);
    return parser.parse(this.config, resp);
  }

  public <T> DRet<T> inspect(DIParser<T> parser, String id) {
    DResp resp = this.nodes.inspect(id);
    return parser.parse(this.config, resp);
  }

  public DRet<Void> remove(String id) {
    DResp resp = this.nodes.remove(id);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> remove(String id, boolean force) {
    DResp resp = this.nodes.remove(id, force);
    return DIParser.voidx().parse(this.config, resp);
  }

  public DRet<Void> update(String id, long version, String body) {
    DResp resp = this.nodes.update(id, version, body);
    return DIParser.voidx().parse(this.config, resp);
  }
}
