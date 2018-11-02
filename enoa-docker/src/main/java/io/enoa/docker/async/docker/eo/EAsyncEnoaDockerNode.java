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
import io.enoa.docker.command.docker.eo.EnoaDockerNode;
import io.enoa.docker.command.docker.eo.EoDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.node.ENode;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EAsyncEnoaDockerNode  {

  private EoDocker docker;
  private EnoaDockerNode node;

  EAsyncEnoaDockerNode(EoDocker docker) {
    this.node = docker.node();
    this.docker = docker;
  }


  public EnqueueAssetDocker<DRet<List<ENode>>> nodes(DQPFilter dqp) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.node.nodes(dqp));
  }

  public EnqueueAssetDocker<DRet<ENode>> inspect(String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.node.inspect(id));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.node.remove(id));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id, boolean force) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.node.remove(id, force));
  }

  public EnqueueAssetDocker<DRet<Void>> update(String id, long version, String body) {
    return EnqueueDocker.asset(this.docker._dockerconfig().executor(), () -> this.node.update(id, version, body));
  }
}
