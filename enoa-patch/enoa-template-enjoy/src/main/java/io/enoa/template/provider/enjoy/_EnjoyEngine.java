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
package io.enoa.template.provider.enjoy;

import com.jfinal.template.Engine;
import io.enoa.template.EnoaEngine;
import io.enoa.template.EnoaTemplate;
import io.enoa.template.EoEngineConfig;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.text.TextKit;

import java.util.stream.Stream;

/**
 * vtom - io.enoa.template.provider.enjoy
 */
class _EnjoyEngine extends EnoaEngine {

  private Engine engine;
  private _EnjoyTemplate template;

  _EnjoyEngine(EoEngineConfig config) {
    EnjoyConfig cfg = config instanceof EnjoyConfig ? (EnjoyConfig) config : new EnjoyConfig(config);

    this.engine = Engine.use(cfg.name());
    if (this.engine == null)
      this.engine = Engine.create(cfg.name());

    this.engine.setBaseTemplatePath(cfg.viewPath());
    this.engine.setDevMode(cfg.debug());
    if (TextKit.notBlank(cfg.datePattern()))
      this.engine.setDatePattern(cfg.datePattern());

    this.engine.setEncoding(cfg.charset().name());

    if (cfg.outputDirectiveFactory() != null)
      this.engine.setOutputDirectiveFactory(cfg.outputDirectiveFactory());

    this.engine.setReloadModifiedSharedFunctionInDevMode(cfg.reloadModifiedSharedFunctionInDevMode());
    if (cfg.sourceFactory() != null)
      this.engine.setSourceFactory(cfg.sourceFactory());


    this.engine.setWriterBufferSize(cfg.bufferSize());
    if (CollectionKit.notEmpty(cfg.sharedFunctionSources()))
      cfg.sharedFunctionSources().forEach(f -> this.engine.addSharedFunction(f));

    if (CollectionKit.notEmpty(cfg.sharedFunctionString()))
      Stream.of(cfg.sharedFunctionString()).forEach(f -> this.engine.addSharedFunction(f));

    if (CollectionKit.notEmpty(cfg.directives()))
      Stream.of(cfg.directives()).forEach(d -> this.engine.addDirective(d.name(), d.clazz()));

    if (CollectionKit.notEmpty(cfg.sharedMethods()))
      Stream.of(cfg.sharedMethods()).forEach(s -> this.engine.addSharedMethod(s));

    if (CollectionKit.notEmpty(cfg.sharedObjects()))
      Stream.of(cfg.sharedObjects()).forEach(s -> this.engine.addSharedObject(s.name(), s.value()));
//    this.engine.addSharedFunctionByString();
//
//    if (cfg.sharedObjectName() != null && cfg.sharedObjectValue() != null)
//    this.engine.addSharedObject(cfg.sharedObjectName(), config);

    if (CollectionKit.notEmpty(cfg.sharedStaticMethods()))
      Stream.of(cfg.sharedStaticMethods()).forEach(s -> this.engine.addSharedStaticMethod(s));

    this.template = new _EnjoyTemplate(this.engine, cfg);
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
