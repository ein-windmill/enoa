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
package io.enoa.template.provider.velocity;

import io.enoa.template.EnoaEngine;
import io.enoa.template.EnoaTemplate;
import io.enoa.template.EoEngineConfig;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * vtom - io.enoa.template.provider.velocity
 */
class _VelocityEngine extends EnoaEngine {

  private VelocityEngine engine;
  private _VelocityTemplate template;

  _VelocityEngine(EoEngineConfig config) {
    this.engine = new VelocityEngine();
    this.init(config);
  }

  private void init(EoEngineConfig config) {
    VelocityConfig cfg = config instanceof VelocityConfig ? (VelocityConfig) config : new VelocityConfig(config);
    this.engine.setProperty("resource.loader", "file");
    this.engine.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
    this.engine.setProperty(Velocity.INPUT_ENCODING, cfg.charset().name());
    this.engine.setProperty(Velocity.OUTPUT_ENCODING, cfg.charset().name());
    this.engine.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, cfg.viewPath());
    this.template = new _VelocityTemplate(this.engine, config);
  }

  @Override
  public EnoaTemplate template(String viewName) {
    this.template.viewName(viewName);
    return this.template;
  }

  @Override
  public Object originEngine() {
    return this.engine;
  }
}
