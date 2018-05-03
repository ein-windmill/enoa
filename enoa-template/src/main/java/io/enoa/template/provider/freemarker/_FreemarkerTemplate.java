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
package io.enoa.template.provider.freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import io.enoa.template.EnoaTemplate;
import io.enoa.template.EoEngineConfig;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * vtom - io.enoa.template.provider.freemarker
 */
class _FreemarkerTemplate extends EnoaTemplate {

  private EoEngineConfig config;
  private Configuration engine;
  private String viewName;

  _FreemarkerTemplate(EoEngineConfig config, Configuration engine) {
    this.engine = engine;
    this.config = config;
  }

  @Override
  public String render() {
    return this.render(null);
  }

  @Override
  public String render(Map<?, ?> data) {
    StringWriter out = new StringWriter();
    try {
      String viewPath = super.fillView(this.viewName, this.config.suffix());
      this.engine.getTemplate(viewPath).process(data, out);
    } catch (TemplateException | IOException e) {
      throw new RuntimeException(e);
    }
    out.flush();
    String tpl = out.getBuffer().toString();
    try {
      out.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    if (this.config.compress())
      return super.compress(this.config.compressor(), tpl);

    return tpl;
  }

  @Override
  protected void viewName(String viewName) {
    this.viewName = viewName;
  }
}
