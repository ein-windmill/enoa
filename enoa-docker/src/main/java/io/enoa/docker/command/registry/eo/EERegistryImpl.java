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
package io.enoa.docker.command.registry.eo;

import io.enoa.docker.RegistryConfig;
import io.enoa.docker.command.registry.generic.GenericRegistry;
import io.enoa.docker.parser.registry.RIParser;
import io.enoa.docker.dket.registry.RRet;
import io.enoa.docker.dket.registry.catalog.ECatalog;
import io.enoa.docker.dket.registry.tag.EITag;

public class EERegistryImpl implements EoRegistry {

  private GenericRegistry registry;

  public EERegistryImpl(GenericRegistry registry) {
    this.registry = registry;
  }

  @Override
  public RegistryConfig _registryconfig() {
    return this.registry._registryconfig();
  }

  @Override
  public RRet<ECatalog> _catalog(Integer n, String last) {
    return this.registry._catalog(RIParser.catalog(), n, last);
  }

  @Override
  public RRet<EITag> tags(String repository) {
    return this.registry.tags(RIParser.tag(), repository);
  }

  @Override
  public EERegistryManifests manifests() {
    return new EERegistryManifests(this.registry);
  }

  @Override
  public EERegistryBlob blob() {
    return new EERegistryBlob(this.registry);
  }

  @Override
  public EERegistryUpload upload() {
    return new EERegistryUpload(this.registry);
  }


}
