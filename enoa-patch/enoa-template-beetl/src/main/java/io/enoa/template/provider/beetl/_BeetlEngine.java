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
package io.enoa.template.provider.beetl;

import io.enoa.template.EnoaEngine;
import io.enoa.template.EnoaTemplate;
import io.enoa.template.EoEngineConfig;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.FileResourceLoader;

import java.io.IOException;

/**
 * vtom - io.enoa.template.provider.beetl
 */
class _BeetlEngine extends EnoaEngine {

  private GroupTemplate gt;
  private _BeetlTemplate template;

  _BeetlEngine(EoEngineConfig config) {
    this.init(config);
  }

  private void init(EoEngineConfig config) {
    try {
      BeetlConfig bcfg = config instanceof BeetlConfig ? (BeetlConfig) config : new BeetlConfig(config);
      Configuration cfg = Configuration.defaultConfiguration();
      cfg.setCharset(bcfg.charset().name());
      cfg.setPlaceholderStart(bcfg.placeholderStart());
      cfg.setPlaceholderEnd(bcfg.placeholderEnd());
      cfg.setStatementStart(bcfg.statementStart());
      cfg.setStatementEnd(bcfg.statementEnd());
      cfg.setHtmlTagFlag(bcfg.htmlTagFlag());
      cfg.setHtmlTagSupport(bcfg.isHtmlTagSupport());
      cfg.setNativeCall(bcfg.isNativeCall());
      cfg.setDirectByteOutput(bcfg.isDirectByteOutput());
      cfg.setStrict(bcfg.isStrict());
      cfg.setIgnoreClientIOError(bcfg.isIgnoreClientIOError());
      cfg.setErrorHandlerClass(bcfg.errorHandlerClass());
      cfg.setHtmlTagStart(bcfg.htmlTagStart());
      cfg.setHtmlTagEnd(bcfg.htmlTagEnd());
      cfg.setHtmlTagBindingAttribute(bcfg.htmlTagBindingAttribute());
      cfg.setPkgList(bcfg.pkgList());
      cfg.setWebAppExt(bcfg.webAppExt());
      cfg.setHasFunctionLimiter(bcfg.isHasFunctionLimiter());
      cfg.setFunctionLimiterStart(bcfg.functionLimiterStart());
      cfg.setFunctionLimiterEnd(bcfg.functionLimiterEnd());
      cfg.setEngine(bcfg.engine());
      cfg.setNativeSecurity(bcfg.nativeSecurity());
      cfg.setResourceLoader(bcfg.resourceLoader());

      FileResourceLoader resourceLoader = new FileResourceLoader(bcfg.viewPath(), bcfg.charset().name());
      this.gt = new GroupTemplate(resourceLoader, cfg);
      this.template = new _BeetlTemplate(this.gt, bcfg);
    } catch (IOException e) {
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
    return this.gt;
  }
}
