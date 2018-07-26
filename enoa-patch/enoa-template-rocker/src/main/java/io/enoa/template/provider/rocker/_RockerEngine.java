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
package io.enoa.template.provider.rocker;

import io.enoa.template.EnoaEngine;
import io.enoa.template.EnoaTemplate;
import io.enoa.template.EoEngineConfig;

public class _RockerEngine extends EnoaEngine {

  private _RockerTemplate template;

  _RockerEngine(EoEngineConfig config) {
    RockerConfig cfg = config instanceof RockerConfig ? (RockerConfig) config : new RockerConfig(config);
    this.template = new _RockerTemplate(config);
  }

  @Override
  public EnoaTemplate template(String viewName) {
    this.template.viewName(viewName);
    return this.template;
  }

  @Override
  public Object originEngine() {
    return null;
  }
}
