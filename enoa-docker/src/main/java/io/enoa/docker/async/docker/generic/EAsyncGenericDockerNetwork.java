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
import io.enoa.docker.command.docker.generic.EGenericDockerNetwork;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.network.DQPNetworkInspect;
import io.enoa.docker.dqp.docker.network.DQPNetworkList;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EAsyncGenericDockerNetwork {

  private GenericDocker docker;
  private EGenericDockerNetwork network;

  EAsyncGenericDockerNetwork(GenericDocker docker) {
    this.docker = docker;
    this.network = docker.network();
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> list(DIParser<List<T>> parser) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.network.list(parser));
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> list(DIParser<List<T>> parser, DQPNetworkList dqp) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.network.list(parser, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> inspect(DIParser<T> parser, String id) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.network.inspect(parser, id));
  }

  public <T> EnqueueAssetDocker<DRet<T>> inspect(DIParser<T> parser, String id, DQPNetworkInspect dqp) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.network.inspect(parser, id, dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.network.remove(id));
  }

  public <T> EnqueueAssetDocker<DRet<T>> create(DIParser<T> parser, String body) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.network.create(parser, body));
  }

  public EnqueueAssetDocker<DRet<Void>> connect(String id, String body) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.network.connect(id, body));
  }

  public EnqueueAssetDocker<DRet<Void>> disconnect(String id, String body) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.network.disconnect(id, body));
  }

  public <T> EnqueueAssetDocker<DRet<T>> prune(DIParser<T> parser) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.network.prune(parser));
  }

  public <T> EnqueueAssetDocker<DRet<T>> prune(DIParser<T> parser, DQPFilter dqp) {
    return EnqueueDocker.asseter(this.docker._dockerconfig().executor(), () -> this.network.prune(parser, dqp));
  }
}
