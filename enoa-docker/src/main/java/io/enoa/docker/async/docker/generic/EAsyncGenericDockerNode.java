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
package io.enoa.docker.async.docker.generic;

import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.command.docker.generic.EGenericDockerNode;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EAsyncGenericDockerNode {

  private GenericDocker docker;
  private EGenericDockerNode node;

  EAsyncGenericDockerNode(GenericDocker docker) {
    this.docker = docker;
    this.node = docker.node();
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> nodes(DIParser<List<T>> parser, DQPFilter dqp) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.node.nodes(parser, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> inspect(DIParser<T> parser, String id) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.node.inspect(parser, id));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.node.remove(id));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id, boolean force) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.node.remove(id, force));
  }

  public EnqueueAssetDocker<DRet<Void>> update(String id, long version, String body) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.node.update(id, version, body));
  }
}
