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

import io.enoa.docker.command.docker.generic.EGenericDockerConfig;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EAsyncGenericDockerConfig {

  private GenericDocker docker;
  private EGenericDockerConfig config;

  EAsyncGenericDockerConfig(GenericDocker docker) {
    this.docker = docker;
    this.config = docker.config();
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> list(DIParser<List<T>> parser) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.config.list(parser));
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> list(DIParser<List<T>> parser, DQPFilter dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.config.list(parser, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> create(DIParser<T> parser, String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.config.create(parser, body));
  }

  public <T> EnqueueAssetDocker<DRet<T>> inspect(DIParser<T> parser, String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.config.inspect(parser, id));
  }

  public EnqueueAssetDocker<DRet<Void>> remove(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.config.remove(id));
  }

  public EnqueueAssetDocker<DRet<Void>> update(String id, long version, String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.config.update(id, version, body));
  }
}
