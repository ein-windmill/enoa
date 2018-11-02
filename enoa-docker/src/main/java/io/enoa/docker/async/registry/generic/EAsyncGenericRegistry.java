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
package io.enoa.docker.async.registry.generic;

import io.enoa.docker.RegistryConfig;
import io.enoa.docker.command.registry._RegistryConfigSupport;
import io.enoa.docker.command.registry.generic.GenericRegistry;
import io.enoa.docker.dket.registry.RRet;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.parser.registry.RIParser;

public class EAsyncGenericRegistry implements _RegistryConfigSupport {

  private GenericRegistry registry;

  private EAsyncGenericManifests manifests;
  private EAsyncGenericBlob blob;
  private EAsyncGenericUpload upload;

  public EAsyncGenericRegistry(GenericRegistry registry) {
    this.registry = registry;

    this.manifests = new EAsyncGenericManifests(this.registry);
    this.blob = new EAsyncGenericBlob(this.registry);
    this.upload = new EAsyncGenericUpload(this.registry);
  }

  @Override
  public RegistryConfig _registryconfig() {
    return this.registry._registryconfig();
  }

  public <T> EnqueueAssetDocker<RRet<T>> _catalog(RIParser<T> parser) {
    return EnqueueDocker.asseterregistry(this.registry._registryconfig().executor(), () -> this.registry._catalog(parser));
  }

  public <T> EnqueueAssetDocker<RRet<T>> _catalog(RIParser<T> parser, Integer n, String last) {
    return EnqueueDocker.asseterregistry(this.registry._registryconfig().executor(), () -> this.registry._catalog(parser, n, last));
  }

  public <T> EnqueueAssetDocker<RRet<T>> tags(RIParser<T> parser, String repository) {
    return EnqueueDocker.asseterregistry(this.registry._registryconfig().executor(), () -> this.registry.tags(parser, repository));
  }

  public EAsyncGenericManifests manifests() {
    return this.manifests;
  }

  public EAsyncGenericBlob blob() {
    return this.blob;
  }

  public EAsyncGenericUpload upload() {
    return this.upload;
  }

}
