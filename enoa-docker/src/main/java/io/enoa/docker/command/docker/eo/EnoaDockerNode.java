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

import io.enoa.docker.command.docker.generic.EGeneicDockerNode;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.node.ENode;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EnoaDockerNode {

  private GenericDocker docker;
  private EGeneicDockerNode nodes;

  EnoaDockerNode(GenericDocker docker) {
    this.docker = docker;
    this.nodes = docker.node();
  }


  public DRet<List<ENode>> nodes(DQPFilter dqp) {
    return this.nodes.nodes(DIParser.nodelist(), dqp);
  }

  public DRet<ENode> inspect(String id) {
    return this.nodes.inspect(DIParser.node(), id);
  }

  public DRet<Void> remove(String id) {
    return this.nodes.remove(id);
  }

  public DRet<Void> remove(String id, boolean force) {
    return this.nodes.remove(id, force);
  }

  public DRet<Void> update(String id, long version, String body) {
    return this.nodes.update(id, version, body);
  }
}
