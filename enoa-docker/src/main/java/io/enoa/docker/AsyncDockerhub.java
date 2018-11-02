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
package io.enoa.docker;

import io.enoa.docker.async.hub.eo.EAsyncEnoaDockerhub;
import io.enoa.docker.async.hub.generic.EAsyncGenericDockerhub;
import io.enoa.docker.async.hub.origin.EAsyncOriginDockerhub;
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

public class AsyncDockerhub {

  AsyncDockerhub() {
  }

  public EAsyncEnoaDockerhub use() {
    return Dockerhub.epm().asynceo();
  }

  public EAsyncOriginDockerhub origin() {
    return Dockerhub.epm().asyncorigin();
  }

  public EAsyncGenericDockerhub generic() {
    return Dockerhub.epm().asyncgeneric();
  }


  public EnqueueAssetDocker<HRet<EHExplore>> explore() {
    return use().explore();
  }

  public EnqueueAssetDocker<HRet<EHExplore>> explore(DQPPage dqp) {
    return use().explore(dqp);
  }

  public EnqueueAssetDocker<HRet<EHSearch>> search(String q) {
    return use().search(q);
  }

  public EnqueueAssetDocker<HRet<EHSearch>> search(DQPSearch dqp) {
    return use().search(dqp);
  }

  public EnqueueAssetDocker<HRet<EHRepository>> inspect(String repository) {
    return use().inspect(repository);
  }

  public EnqueueAssetDocker<HRet<EHTag>> tags(String repository) {
    return use().tags(repository);
  }

  public EnqueueAssetDocker<HRet<EHTag>> tags(String repository, DQPPage dqp) {
    return use().tags(repository, dqp);
  }

  public EnqueueAssetDocker<HRet<String>> dockerfile(String repository) {
    return use().dockerfile(repository);
  }

  public EnqueueAssetDocker<HRet<EHAutobuild>> autobuild(String repository) {
    return use().autobuild(repository);
  }

  public EnqueueAssetDocker<HRet<EHBuildHistory>> history(String repository) {
    return use().history(repository);
  }

  public EnqueueAssetDocker<HRet<EHBuildHistory>> history(String repository, DQPPage dqp) {
    return use().history(repository, dqp);
  }

}
