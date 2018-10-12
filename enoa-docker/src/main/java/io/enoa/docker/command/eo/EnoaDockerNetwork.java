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
package io.enoa.docker.command.eo;

import io.enoa.docker.command.geneic.EGeneicDockerNetwork;
import io.enoa.docker.command.geneic.GeneicDocker;
import io.enoa.docker.dqp.network.DQPNetworkInspect;
import io.enoa.docker.dqp.network.DQPNetworkList;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.dret.network.ENetworPrune;
import io.enoa.docker.dret.network.ENetwork;
import io.enoa.docker.dret.common.ECreatedWithWarning;
import io.enoa.docker.parser.DIParser;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EnoaDockerNetwork {

  private GeneicDocker docker;
  private EGeneicDockerNetwork networks;

  EnoaDockerNetwork(GeneicDocker docker) {
    this.docker = docker;
    this.networks = docker.network();
  }

  public DRet<List<ENetwork>> list() {
    return this.networks.list(DIParser.networklist());
  }

  public DRet<List<ENetwork>> list(DQPNetworkList dqp) {
    return this.networks.list(DIParser.networklist(), dqp);
  }

  public DRet<ENetwork> inspect(String id) {
    return this.networks.inspect(DIParser.networkinspect(), id);
  }

  public DRet<ENetwork> inspect(String id, DQPNetworkInspect dqp) {
    return this.networks.inspect(DIParser.networkinspect(), id, dqp);
  }

  public DRet<Void> remove(String id) {
    return this.networks.remove(id);
  }

  public DRet<ECreatedWithWarning> create(String body) {
    return this.networks.create(DIParser.createdwithwarning(), body);
  }

  public DRet<Void> connect(String id, String body) {
    return this.networks.connect(id, body);
  }

  public DRet<Void> disconnect(String id, String body) {
    return this.networks.disconnect(id, body);
  }

  public DRet<ENetworPrune> prune() {
    return this.networks.prune(DIParser.networkprune());
  }

  public DRet<ENetworPrune> prune(DQPFilter dqp) {
    return this.networks.prune(DIParser.networkprune(), dqp);
  }

}
