/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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
package io.enoa.template;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EPMTemplate {

  private static class Holder {
    private static final EPMTemplate INSTANCE = new EPMTemplate();
  }

  static EPMTemplate instance() {
    return Holder.INSTANCE;
  }

  private EPMTemplate() {

  }

  private Map<String, EoTemplateFactory> factoryMap = new ConcurrentHashMap<>();
  private Map<String, EoEngineConfig> configMap = new ConcurrentHashMap<>();
  private Map<String, EnoaEngine> engineMap = new ConcurrentHashMap<>();

  public void install(EoTemplateFactory factory, EoEngineConfig config) {
    this.install("main", factory, config);
  }

  public void install(String name, EoTemplateFactory factory, EoEngineConfig config) {
    this.engineMap.put(name, factory.engine(config));
    this.factoryMap.put(name, factory);
    this.configMap.put(name, config);
  }

  public EoTemplateFactory factory(String name) {
    return this.factoryMap.get(name);
  }

  public EoTemplateFactory factory() {
    return this.factory("main");
  }

  public EoEngineConfig config(String name) {
    return this.configMap.get(name);
  }

  public EoEngineConfig config() {
    return this.config("main");
  }

  public EnoaEngine engine(String name) {
    return this.engineMap.get(name);
  }

  public EnoaEngine engine() {
    return this.engine("main");
  }

}
