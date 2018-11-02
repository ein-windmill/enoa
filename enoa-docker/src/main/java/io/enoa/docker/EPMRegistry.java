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

import io.enoa.docker.async.registry.eo.EAsyncEnoaRegistry;
import io.enoa.docker.async.registry.generic.EAsyncGenericRegistry;
import io.enoa.docker.async.registry.origin.EAsyncOriginRegistry;
import io.enoa.docker.command.registry.eo.EERegistryImpl;
import io.enoa.docker.command.registry.eo.EoRegistry;
import io.enoa.docker.command.registry.generic.EGenericRegistryImpl;
import io.enoa.docker.command.registry.generic.GenericRegistry;
import io.enoa.docker.command.registry.origin.EDockerhubImpl;
import io.enoa.docker.command.registry.origin.EOriginRegistryImpl;
import io.enoa.docker.command.registry.origin.OriginRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EPMRegistry {

  private static class Holder {
    private static final EPMRegistry INSTANCE = new EPMRegistry();
  }

  static EPMRegistry instance() {
    return Holder.INSTANCE;
  }

  private Map<String, OriginRegistry> origin;
  private Map<String, GenericRegistry> generic;
  private Map<String, EoRegistry> eo;

  private Map<String, AsyncRegistry> async;
  private Map<String, EAsyncOriginRegistry> asyncorigin;
  private Map<String, EAsyncGenericRegistry> asyncgeneric;
  private Map<String, EAsyncEnoaRegistry> asynceo;

  private EPMRegistry() {
    this.origin = new ConcurrentHashMap<>();
    this.generic = new ConcurrentHashMap<>();
    this.eo = new ConcurrentHashMap<>();
    this.async = new ConcurrentHashMap<>();
    this.asynceo = new ConcurrentHashMap<>();
    this.asyncorigin = new ConcurrentHashMap<>();
    this.asyncgeneric = new ConcurrentHashMap<>();
  }

  public void install(String name, RegistryConfig config) {
    if (config.dockerhub()) {
      this.origin.put(name, new EDockerhubImpl(config));
    } else {
      this.origin.put(name, new EOriginRegistryImpl(config));
    }
  }

  public void install(RegistryConfig config) {
    this.install("main", config);
  }

  public void uninstall(String name) {
    this.origin.remove(name);
    this.generic.remove(name);
    this.eo.remove(name);
  }

  public void uninstall() {
    this.uninstall("main");
  }

  public OriginRegistry origin(String name) {
    OriginRegistry registry = this.origin.get(name);
    return registry;
  }

  public OriginRegistry origin() {
    return this.origin("main");
  }

  public GenericRegistry generic(String name) {
    GenericRegistry geneic = this.generic.get(name);
    if (geneic != null)
      return geneic;
    OriginRegistry origin = this.origin(name);
    if (origin == null)
      return null;
    geneic = new EGenericRegistryImpl(origin);
    this.generic.put(name, geneic);
    return geneic;
  }

  public GenericRegistry generic() {
    return this.generic("main");
  }

  public EoRegistry registry(String name) {
    EoRegistry registry = this.eo.get(name);
    if (registry != null)
      return registry;
    GenericRegistry generic = this.generic(name);
    if (generic == null)
      return null;
    registry = new EERegistryImpl(generic);
    this.eo.put(name, registry);
    return registry;
  }

  public EoRegistry registry() {
    return this.registry("main");
  }


  public AsyncRegistry async(String name) {
    AsyncRegistry registry = this.async.get(name);
    if (registry != null)
      return registry;
    registry = new AsyncRegistry(name);
    this.async.put(name, registry);
    return registry;
  }

  public AsyncRegistry async() {
    return this.async("main");
  }


  EAsyncOriginRegistry asyncorigin(String name) {
    EAsyncOriginRegistry registry = this.asyncorigin.get(name);
    if (registry != null)
      return registry;
    registry = new EAsyncOriginRegistry(this.origin(name));
    this.asyncorigin.put(name, registry);
    return registry;
  }

  EAsyncGenericRegistry asyncgeneric(String name) {
    EAsyncGenericRegistry registry = this.asyncgeneric.get(name);
    if (registry != null)
      return registry;
    registry = new EAsyncGenericRegistry(this.generic(name));
    this.asyncgeneric.put(name, registry);
    return registry;
  }

  EAsyncEnoaRegistry asynceo(String name) {
    EAsyncEnoaRegistry registry = this.asynceo.get(name);
    if (registry != null)
      return registry;
    registry = new EAsyncEnoaRegistry(this.registry(name));
    this.asynceo.put(name, registry);
    return registry;
  }

}
