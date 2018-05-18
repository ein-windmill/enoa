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
package io.enoa.yosart.ext.render.template;

import io.enoa.log.kit.LogKit;
import io.enoa.repeater.http.Request;
import io.enoa.template.EnoaEngine;
import io.enoa.template.EoEngineConfig;
import io.enoa.template.EoTemplateFactory;
import io.enoa.template.compressor.EoCompressorFactory;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.path.PathKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.kernel.ext.YmRenderExt;
import io.enoa.yosart.kernel.render.YoRender;
import io.enoa.yosart.thr.OyExtException;

import java.nio.charset.Charset;

public class TemplateRenderExt implements YmRenderExt {

  private String basePath;
  private boolean compress;
  private EoCompressorFactory compressorFactory;
  private EoTemplateFactory factory;
  private String suffix;
  private EoEngineConfig config;

  private EnoaEngine engine;

  public TemplateRenderExt(EoTemplateFactory factory, String basePath) {
    this(factory, basePath, null);
  }

  public TemplateRenderExt(EoTemplateFactory factory, String basePath, String suffix) {
    if (factory == null)
      throw new OyExtException(EnoaTipKit.message("eo.tip.ext.render.template_factory_null"));
    if (TextKit.isBlank(basePath))
      LogKit.warn(EnoaTipKit.message("eo.tip.ext.render.template_base_path_null"));
    this.basePath = TextKit.isBlank(basePath) ? PathKit.path().toString() : basePath;
    this.compress = false;
    this.factory = factory;
    this.suffix = suffix;
  }

  public TemplateRenderExt(EoTemplateFactory factory, String basePath, String suffix, EoCompressorFactory compressorFactory) {
    if (factory == null)
      throw new OyExtException(EnoaTipKit.message("eo.tip.ext.render.template_factory_null"));
    if (compressorFactory == null)
      throw new OyExtException(EnoaTipKit.message("eo.tip.ext.render.template_compressor_null"));
    if (TextKit.isBlank(basePath))
      LogKit.warn(EnoaTipKit.message("eo.tip.ext.render.template_base_path_null"));
    this.factory = factory;
    this.basePath = TextKit.isBlank(basePath) ? PathKit.path().toString() : basePath;
    this.compressorFactory = compressorFactory;
    this.compress = true;
    this.suffix = suffix;
  }

  public TemplateRenderExt(EoTemplateFactory factory, EoEngineConfig config) {
    this.factory = factory;
    this.config = config;
  }

  private EnoaEngine engine() {
    if (this.engine != null)
      return this.engine;
    this.engine = this.factory.engine(this.config == null ? this.defConfig() : this.config);
//    EnoaTemplateMgr.defEngine(this.factory, this.config == null ? this.defConfig() : this.config);
//    return EnoaTemplateMgr.defEngine();
    return this.engine;
  }


  private EoEngineConfig defConfig() {
    return new EoEngineConfig() {
      @Override
      public String name() {
        return "main";
      }

      @Override
      public String viewPath() {
        return basePath;
      }

      @Override
      public boolean debug() {
        return Oysart.config().debug();
      }

      @Override
      public String datePattern() {
        return null;
      }

      @Override
      public String suffix() {
        return suffix;
      }

      @Override
      public Charset charset() {
        return Oysart.config().charset();
      }

      @Override
      public boolean compress() {
        return compress;
      }

      @Override
      public EoCompressorFactory compressor() {
        return compressorFactory;
      }
    };
  }

  @Override
  public String renderType() {
    return YoRender.Type.TEMPLATE.name();
  }

  @Override
  public YoRender renderer(Request req, Kv attr, Object... args) throws Exception {
    return new TemplateRender(req, attr, args)
      .engine(this.engine());
  }

  @Override
  public String name() {
    return "TemplateRender";
  }

  @Override
  public String version() {
    return "1";
  }
}
