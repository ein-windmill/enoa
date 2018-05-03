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
package io.enoa.template.provider.beetl;

import io.enoa.template.EnoaTemplate;
import io.enoa.template.EoEngineConfig;
import io.enoa.toolkit.collection.CollectionKit;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;

import java.util.Map;

/**
 * vtom - io.enoa.template.provider.beetl
 */
class _BeetlTemplate extends EnoaTemplate {


  private GroupTemplate engine;
  private EoEngineConfig config;
  private String viewName;

  _BeetlTemplate(GroupTemplate engine, EoEngineConfig config) {
    this.engine = engine;
    this.config = config;
  }

  @Override
  public String render() {
    return this.render(null);
  }

  @Override
  public String render(Map<?, ?> data) {
    Template template = this.engine.getTemplate(super.fillView(this.viewName, this.config.suffix()));
    if (CollectionKit.notEmpty(data)) {
      for (Object key : data.keySet()) {
        template.binding(key.toString(), data.get(key));
      }
    }
    String ret = template.render();
    if (this.config.compress())
      ret = super.compress(this.config.compressor(), ret);

    return ret;
  }

  @Override
  protected void viewName(String viewName) {
    this.viewName = viewName;
  }
}
