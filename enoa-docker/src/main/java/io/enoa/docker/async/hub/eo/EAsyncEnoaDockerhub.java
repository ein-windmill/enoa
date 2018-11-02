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
package io.enoa.docker.async.hub.eo;

import io.enoa.docker.DockerhubConfig;
import io.enoa.docker.command.hub._DockerhubConfigSupport;
import io.enoa.docker.command.hub.eo.EnoaDockerhub;
import io.enoa.docker.dket.dockerhub.HRet;
import io.enoa.docker.dket.dockerhub.build.EHAutobuild;
import io.enoa.docker.dket.dockerhub.build.EHBuildHistory;
import io.enoa.docker.dket.dockerhub.explore.EHExplore;
import io.enoa.docker.dket.dockerhub.inspece.EHRepository;
import io.enoa.docker.dket.dockerhub.search.EHSearch;
import io.enoa.docker.dket.dockerhub.tag.EHTag;
import io.enoa.docker.dqp.common.DQPPage;
import io.enoa.docker.dqp.dockerhub.DQPSearch;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;

public class EAsyncEnoaDockerhub implements _DockerhubConfigSupport {

  private EnoaDockerhub dockerhub;

  public EAsyncEnoaDockerhub(EnoaDockerhub dockerhub) {
    this.dockerhub = dockerhub;
  }

  @Override
  public DockerhubConfig _dockerhubconfig() {
    return this.dockerhub._dockerhubconfig();
  }

  public EnqueueAssetDocker<HRet<EHExplore>> explore() {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.explore());
  }

  public EnqueueAssetDocker<HRet<EHExplore>> explore(DQPPage dqp) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.explore(dqp));
  }

  public EnqueueAssetDocker<HRet<EHSearch>> search(String q) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.search(q));
  }

  public EnqueueAssetDocker<HRet<EHSearch>> search(DQPSearch dqp) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.search(dqp));
  }

  public EnqueueAssetDocker<HRet<EHRepository>> inspect(String repository) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.inspect(repository));
  }

  public EnqueueAssetDocker<HRet<EHTag>> tags(String repository) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.tags(repository));
  }

  public EnqueueAssetDocker<HRet<EHTag>> tags(String repository, DQPPage dqp) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.tags(repository, dqp));
  }

  public EnqueueAssetDocker<HRet<String>> dockerfile(String repository) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.dockerfile(repository));
  }

  public EnqueueAssetDocker<HRet<EHAutobuild>> autobuild(String repository) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.autobuild(repository));
  }

  public EnqueueAssetDocker<HRet<EHBuildHistory>> history(String repository) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.history(repository));
  }

  public EnqueueAssetDocker<HRet<EHBuildHistory>> history(String repository, DQPPage dqp) {
    return EnqueueDocker.asseterhub(this.dockerhub._dockerhubconfig().executor(), () -> this.dockerhub.history(repository, dqp));
  }
}
