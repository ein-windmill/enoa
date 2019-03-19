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

import io.enoa.docker.RegistryConfig;
import io.enoa.docker.command.registry._RegistryConfigSupport;
import io.enoa.docker.command.registry.origin.OriginRegistry;
import io.enoa.docker.dket.registry.RResp;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.enqueue.EnqueueDoneargDocker;

public class EAsyncOriginRegistry implements _RegistryConfigSupport {

  private OriginRegistry registry;
  private EAsyncOriginRegistryBlob blob;
  private EAsyncOriginRegistryManifests manifests;
  private EAsyncOriginRegistryUpload upload;

  public EAsyncOriginRegistry(OriginRegistry registry) {
    this.registry = registry;

    this.blob = new EAsyncOriginRegistryBlob(this.registry);
    this.manifests = new EAsyncOriginRegistryManifests(this.registry);
    this.upload = new EAsyncOriginRegistryUpload(this.registry);
  }

  @Override
  public RegistryConfig _registryconfig() {
    return this.registry._registryconfig();
  }

  public EnqueueDoneargDocker<RResp> _catalog() {
    return EnqueueDocker.donearg(this._registryconfig().executor(), () -> this.registry._catalog());
  }

  public EnqueueDoneargDocker<RResp> _catalog(Integer n, String last) {
    return EnqueueDocker.donearg(this._registryconfig().executor(), () -> this.registry._catalog(n, last));
  }

  public EnqueueDoneargDocker<RResp> tags(String repository) {
    return EnqueueDocker.donearg(this._registryconfig().executor(), () -> this.registry.tags(repository));
  }

  public EAsyncOriginRegistryManifests manifests() {
    return this.manifests;
  }

  public EAsyncOriginRegistryBlob blob() {
    return this.blob;
  }

  public EAsyncOriginRegistryUpload upload() {
    return this.upload;
  }
}
