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
package io.enoa.template.provider.enjoy;

import com.jfinal.template.Engine;
import io.enoa.template.EnoaTemplate;
import io.enoa.template.EoEngineConfig;

import java.util.Map;

/**
 * vtom - io.enoa.template.provider.enjoy
 */
class _EnjoyTemplate extends EnoaTemplate {

  private String viewName;
  private EoEngineConfig config;
  private Engine engine;

  _EnjoyTemplate(Engine engine, EoEngineConfig config) {
    this.engine = engine;
    this.config = config;
  }

  @Override
  public void viewName(String viewName) {
    this.viewName = viewName;
  }

  @Override
  public String render() {
    return this.render(null);
  }

  @Override
  public String render(Map<?, ?> data) {
    String ret = this.engine.getTemplate(super.fillView(this.viewName, this.config.suffix())).renderToString(data);
    if (this.config.compress())
      return super.compress(this.config.compressor(), ret);
    return ret;
  }
}
