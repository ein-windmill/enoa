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
package io.enoa.docker.async.registry.eo;

import io.enoa.docker.RegistryConfig;
import io.enoa.docker.command.registry._RegistryConfigSupport;
import io.enoa.docker.command.registry.eo.EoRegistry;
import io.enoa.docker.dket.registry.RRet;
import io.enoa.docker.dket.registry.catalog.ECatalog;
import io.enoa.docker.dket.registry.tag.EITag;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;

public class EAsyncEnoaRegistry implements _RegistryConfigSupport {

  private EoRegistry registry;

  private EAsyncEnoaBlob blob;
  private EAsyncEnoaManifests manifests;
  private EAsyncEnoaUpload upload;

  public EAsyncEnoaRegistry(EoRegistry registry) {
    this.registry = registry;
    this.blob = new EAsyncEnoaBlob(this.registry);
    this.manifests = new EAsyncEnoaManifests(this.registry);
    this.upload = new EAsyncEnoaUpload(this.registry);
  }

  @Override
  public RegistryConfig _registryconfig() {
    return null;
  }

  public EnqueueAssetDocker<RRet<ECatalog>> _catalog() {
    return EnqueueDocker.asseterregistry(this._registryconfig().executor(), () -> this.registry._catalog());
  }

  public EnqueueAssetDocker<RRet<ECatalog>> _catalog(Integer n, String last) {
    return EnqueueDocker.asseterregistry(this._registryconfig().executor(), () -> this.registry._catalog(n, last));
  }

  public EnqueueAssetDocker<RRet<EITag>> tags(String repository) {
    return EnqueueDocker.asseterregistry(this._registryconfig().executor(), () -> this.registry.tags(repository));
  }

  public EAsyncEnoaManifests manifests() {
    return this.manifests;
  }

  public EAsyncEnoaBlob blob() {
    return this.blob;
  }

  public EAsyncEnoaUpload upload() {
    return this.upload;
  }
}
