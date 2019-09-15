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
package io.enoa.docker.async.docker.origin;

import io.enoa.docker.command.docker.origin.EOriginDockerNetwork;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.network.DQPNetworkInspect;
import io.enoa.docker.dqp.docker.network.DQPNetworkList;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.enqueue.EnqueueDoneargDocker;

public class EAsyncOriginDockerNetwork {

  private OriginDocker docker;
  private EOriginDockerNetwork network;

  EAsyncOriginDockerNetwork(OriginDocker docker) {
    this.docker = docker;
    this.network = docker.network();
  }

  public EnqueueDoneargDocker<DResp> list() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.network.list());
  }

  public EnqueueDoneargDocker<DResp> list(DQPNetworkList dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.network.list(dqp));
  }

  public EnqueueDoneargDocker<DResp> inspect(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.network.inspect(id));
  }

  public EnqueueDoneargDocker<DResp> inspect(String id, DQPNetworkInspect dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.network.inspect(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> remove(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.network.remove(id));
  }

  public EnqueueDoneargDocker<DResp> create(String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.network.create(body));
  }

  public EnqueueDoneargDocker<DResp> connect(String id, String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.network.connect(id, body));
  }

  public EnqueueDoneargDocker<DResp> disconnect(String id, String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.network.disconnect(id, body));
  }

  public EnqueueDoneargDocker<DResp> prune() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.network.prune());
  }

  public EnqueueDoneargDocker<DResp> prune(DQPFilter dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.network.prune(dqp));
  }
}
