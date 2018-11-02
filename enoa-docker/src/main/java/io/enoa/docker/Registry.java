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

import io.enoa.docker.command.registry.eo.EERegistryBlob;
import io.enoa.docker.command.registry.eo.EERegistryManifests;
import io.enoa.docker.command.registry.eo.EERegistryUpload;
import io.enoa.docker.command.registry.eo.EoRegistry;
import io.enoa.docker.command.registry.generic.GenericRegistry;
import io.enoa.docker.command.registry.origin.OriginRegistry;
import io.enoa.docker.dket.registry.RRet;
import io.enoa.docker.dket.registry.catalog.ECatalog;
import io.enoa.docker.dket.registry.tag.EITag;

public class Registry {

  public static EPMRegistry epm() {
    return EPMRegistry.instance();
  }

  public static EoRegistry use(String name) {
    return epm().registry(name);
  }

  public static EoRegistry use() {
    return epm().registry();
  }

  public static OriginRegistry origin(String name) {
    return epm().origin(name);
  }

  public static OriginRegistry origin() {
    return epm().origin();
  }

  public GenericRegistry generic(String name) {
    return epm().generic(name);
  }

  public GenericRegistry generic() {
    return epm().generic();
  }

  public AsyncRegistry async(String name) {
    return epm().async(name);
  }

  public AsyncRegistry async() {
    return epm().async();
  }

  public static RRet<ECatalog> _catalog() {
    return use()._catalog();
  }

  public static RRet<ECatalog> _catalog(Integer n, String last) {
    return use()._catalog(n, last);
  }

  public static RRet<EITag> tags(String repository) {
    return use().tags(repository);
  }

  public static EERegistryManifests manifests() {
    return use().manifests();
  }

  public static EERegistryBlob blob() {
    return use().blob();
  }

  public static EERegistryUpload upload() {
    return use().upload();
  }

}
