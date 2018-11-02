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
package io.enoa.docker.async.docker.eo;

import io.enoa.docker.async.docker.EnqueueAssetDocker;
import io.enoa.docker.async.docker.EnqueueDocker;
import io.enoa.docker.command.docker.eo.EnoaDockerNetwork;
import io.enoa.docker.command.docker.eo.EoDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.common.ECreatedWithWarning;
import io.enoa.docker.dket.docker.network.ENetworPrune;
import io.enoa.docker.dket.docker.network.ENetwork;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.network.DQPNetworkInspect;
import io.enoa.docker.dqp.docker.network.DQPNetworkList;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EAsyncEnoaDockerNetwork {

  private EnoaDockerNetwork network;

  private EoDocker docker;

  EAsyncEnoaDockerNetwork(EoDocker docker) {
    this.network = docker.network();
    this.docker = docker;
  }


  public EnqueueAssetDocker<DRet<List<ENetwork>>> list() {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.network.list());
  }

  public EnqueueAssetDocker<DRet<List<ENetwork>>> list(DQPNetworkList dqp) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.network.list(dqp));
  }

  public EnqueueAssetDocker<DRet<ENetwork>> inspect(String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.network.inspect(id));
  }

  public EnqueueAssetDocker<DRet<ENetwork>> inspect(String id, DQPNetworkInspect dqp) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.network.inspect(id, dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.network.remove(id));
  }

  public EnqueueAssetDocker<DRet<ECreatedWithWarning>> create(String body) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.network.create(body));
  }

  public EnqueueAssetDocker<DRet<Void>> connect(String id, String body) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.network.connect(id, body));
  }

  public EnqueueAssetDocker<DRet<Void>> disconnect(String id, String body) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.network.disconnect(id, body));
  }

  public EnqueueAssetDocker<DRet<ENetworPrune>> prune() {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.network.prune());
  }

  public EnqueueAssetDocker<DRet<ENetworPrune>> prune(DQPFilter dqp) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.network.prune(dqp));
  }
}
