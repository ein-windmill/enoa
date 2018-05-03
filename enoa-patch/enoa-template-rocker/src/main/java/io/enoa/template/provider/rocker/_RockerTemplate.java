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
package io.enoa.template.provider.rocker;

import com.fizzed.rocker.BindableRockerModel;
import com.fizzed.rocker.Rocker;
import io.enoa.template.EnoaTemplate;
import io.enoa.template.EoEngineConfig;

import java.util.Map;

class _RockerTemplate extends EnoaTemplate {

  private EoEngineConfig config;
  private String viewName;

  _RockerTemplate(EoEngineConfig config) {
    this.config = config;
  }

  @Override
  protected void viewName(String viewName) {
    this.viewName = viewName;
  }

  @Override
  public String render() {
    return this.render(null);
  }

  @Override
  public String render(Map<?, ?> data) {
    String viewPath = super.fillView(this.viewName, this.config.suffix());
    // todo
    BindableRockerModel template = Rocker.template(viewPath);
    if (data != null) {
      data.forEach((k, v) -> template.bind(k.toString(), v));
    }
    String tpl = template.render().toString();

    if (this.config.compress())
      return super.compress(this.config.compressor(), tpl);

    return tpl;
  }
}
