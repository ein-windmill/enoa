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
package io.enoa.template.provider.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Version;
import io.enoa.template.EnoaEngine;
import io.enoa.template.EnoaTemplate;
import io.enoa.template.EoEngineConfig;

import java.io.File;

/**
 * vtom - io.enoa.template.provider.freemarker
 */
class _FreemarkerEngine extends EnoaEngine {

  private Configuration engine;
  private _FreemarkerTemplate template;

  _FreemarkerEngine(EoEngineConfig config) {
    this.init(config);
  }


  private void init(EoEngineConfig config) {
    try {
      FreemarkerConfig cfg = config instanceof FreemarkerConfig ? (FreemarkerConfig) config : new FreemarkerConfig(config);
      this.engine = new Configuration(new Version("2.3.0"));
      this.engine.setDefaultEncoding(cfg.charset().name());
      this.engine.setDirectoryForTemplateLoading(new File(cfg.viewPath()));


      if (cfg.autoImports() != null)
        this.engine.setAutoImports(cfg.autoImports());

      this.template = new _FreemarkerTemplate(config, this.engine);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
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
