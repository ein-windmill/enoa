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
package io.enoa.docker.async.hub.generic;

import io.enoa.docker.DockerhubConfig;
import io.enoa.docker.command.hub._DockerhubConfigSupport;
import io.enoa.docker.command.hub.generic.GenericDockerhub;
import io.enoa.docker.dket.dockerhub.HRet;
import io.enoa.docker.dqp.common.DQPPage;
import io.enoa.docker.dqp.dockerhub.DQPSearch;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.parser.dockerhub.HIParser;

public class EAsyncGenericDockerhub implements _DockerhubConfigSupport {

  private GenericDockerhub dockerhub;

  public EAsyncGenericDockerhub(GenericDockerhub dockerhub) {
    this.dockerhub = dockerhub;
  }

  public DockerhubConfig _dockerhubconfig() {
    return this.dockerhub._dockerhubconfig();
  }

  public <T> EnqueueAssetDocker<HRet<T>> explore(HIParser<T> parser) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.explore(parser));
  }

  public <T> EnqueueAssetDocker<HRet<T>> explore(HIParser<T> parser, DQPPage dqp) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.explore(parser, dqp));
  }

  public <T> EnqueueAssetDocker<HRet<T>> search(HIParser<T> parser, String q) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.search(parser, q));
  }

  public <T> EnqueueAssetDocker<HRet<T>> search(HIParser<T> parser, DQPSearch dqp) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.search(parser, dqp));
  }

  public <T> EnqueueAssetDocker<HRet<T>> inspect(HIParser<T> parser, String repository) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.inspect(parser, repository));
  }

  public <T> EnqueueAssetDocker<HRet<T>> tags(HIParser<T> parser, String repository) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.tags(parser, repository));
  }

  public <T> EnqueueAssetDocker<HRet<T>> tags(HIParser<T> parser, String repository, DQPPage dqp) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.tags(parser, repository, dqp));
  }

  public EnqueueAssetDocker<HRet<String>> dockerfile(String repository) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.dockerfile(repository));
  }

  public <T> EnqueueAssetDocker<HRet<T>> autobuild(HIParser<T> parser, String repository) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.autobuild(parser, repository));
  }

  public <T> EnqueueAssetDocker<HRet<T>> history(HIParser<T> parser, String repository) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.history(parser, repository));
  }

  public <T> EnqueueAssetDocker<HRet<T>> history(HIParser<T> parser, String repository, DQPPage dqp) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.history(parser, repository, dqp));
  }
}
