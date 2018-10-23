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
package io.enoa.docker.command.registry.generic;

import io.enoa.docker.RegistryConfig;
import io.enoa.docker.command.registry.origin.OriginRegistry;
import io.enoa.docker.parser.registry.RIParser;
import io.enoa.docker.ret.registry.RResp;
import io.enoa.docker.ret.registry.RRet;

public class EGenericRegistryImpl implements GenericRegistry {

  private OriginRegistry registry;

  public EGenericRegistryImpl(OriginRegistry registry) {
    this.registry = registry;
  }

  @Override
  public RegistryConfig _registryconfig() {
    return this.registry._registryconfig();
  }

  @Override
  public <T> RRet<T> _catalog(RIParser<T> parser, Integer n, String last) {
    RResp resp = this.registry._catalog(n, last);
    return parser.parse(this._registryconfig(), resp);
  }

  @Override
  public <T> RRet<T> tags(RIParser<T> parser, String repository) {
    return null;
  }

  @Override
  public GeneicManifests manifests() {
    return new GeneicManifests(this.registry);
  }

  @Override
  public GeneicBlob blob() {
    return new GeneicBlob(this.registry);
  }

  @Override
  public GeneicUpload upload() {
    return new GeneicUpload(this.registry);
  }

}
