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

import io.enoa.docker.command.docker.origin.EOriginDockerNode;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.enqueue.EnqueueDoneargDocker;

public class EAsyncOriginDockerNode {

  private OriginDocker docker;

  private EOriginDockerNode node;

  EAsyncOriginDockerNode(OriginDocker docker) {
    this.docker = docker;
    this.node = docker.node();
  }


  public EnqueueDoneargDocker<DResp> nodes(DQPFilter dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.node.nodes(dqp));
  }

  public EnqueueDoneargDocker<DResp> inspect(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.node.inspect(id));
  }

  public EnqueueDoneargDocker<DResp> remove(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.node.remove(id));
  }

  public EnqueueDoneargDocker<DResp> remove(String id, boolean force) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.node.remove(id, force));
  }

  public EnqueueDoneargDocker<DResp> update(String id, long version, String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.node.update(id, version, body));
  }
}
