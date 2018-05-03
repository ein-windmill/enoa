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

import io.enoa.template.EnoaTemplate;
import io.enoa.template.EoEngineConfig;
import io.enoa.toolkit.collection.CollectionKit;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * vtom - io.enoa.template.provider.velocity
 */
class _VelocityTemplate extends EnoaTemplate {

  private VelocityEngine engine;
  private EoEngineConfig config;
  private String viewName;

  _VelocityTemplate(VelocityEngine engine, EoEngineConfig config) {
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
    Context context = null;
    if (CollectionKit.notEmpty(data)) {
      context = new VelocityContext();
      for (Object key : data.keySet()) {
        context.put(key.toString(), data.get(key));
      }
    }
    this.engine.mergeTemplate(super.fillView(this.viewName, this.config.suffix()),
      this.config.charset().name(), context, out);

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
