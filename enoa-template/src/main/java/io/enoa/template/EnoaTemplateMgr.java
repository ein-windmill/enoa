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
package io.enoa.template;

import io.enoa.template.compressor.EoCompressorFactory;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.path.PathKit;

import java.nio.charset.Charset;

/**
 * vtom - io.enoa.template
 */
public class EnoaTemplateMgr {

  private static class Holder {
    private static EnoaTemplateMgr INSTANCE = new EnoaTemplateMgr();
  }

  public static EnoaTemplateMgr instance() {
    return Holder.INSTANCE;
  }

  private static EoTemplateFactory factory;
  private static EoEngineConfig config;

  public EnoaTemplateMgr defTemplateFactory(EoTemplateFactory factory) {
    EnoaTemplateMgr.factory = factory;
    return this;
  }

  public EnoaTemplateMgr defConfig(EoEngineConfig config) {
    EnoaTemplateMgr.config = config;
    return this;
  }

  public EnoaEngine defEngine() {
    EoEngineConfig cfg = config;
    if (cfg == null) {
      cfg = new EoEngineConfig() {
        @Override
        public String name() {
          return "main";
        }

        @Override
        public String viewPath() {
          return PathKit.path().toString();
        }

        @Override
        public boolean debug() {
          return false;
        }

        @Override
        public String datePattern() {
          return EoConst.DEF_FORMAT_DATE;
        }

        @Override
        public String suffix() {
          return null;
        }

        @Override
        public Charset charset() {
          return EoConst.CHARSET;
        }

        @Override
        public boolean compress() {
          return false;
        }

        @Override
        public EoCompressorFactory compressor() {
          return null;
        }
      };
    }
    return factory.engine(cfg);
  }
}
