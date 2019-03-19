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
package io.enoa.docker.async.registry.origin;

import io.enoa.docker.command.registry.origin.OriginManifests;
import io.enoa.docker.command.registry.origin.OriginRegistry;
import io.enoa.docker.dket.registry.RResp;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.enqueue.EnqueueDoneargDocker;

public class EAsyncOriginRegistryManifests {


  private OriginRegistry registry;
  private OriginManifests manifests;

  EAsyncOriginRegistryManifests(OriginRegistry registry) {
    this.manifests = registry.manifests();
    this.registry = registry;
  }

  public EnqueueDoneargDocker<RResp> find(String repository, String reference) {
    return EnqueueDocker.donearg(this.registry._registryconfig().executor(), () -> this.manifests.find(repository, reference));
  }

  public EnqueueDoneargDocker<RResp> update(String repository, String reference) {
    return EnqueueDocker.donearg(this.registry._registryconfig().executor(), () -> this.manifests.update(repository, reference));
  }

  public EnqueueDoneargDocker<RResp> delete(String repository, String reference) {
    return EnqueueDocker.donearg(this.registry._registryconfig().executor(), () -> this.manifests.delete(repository, reference));
  }
}
