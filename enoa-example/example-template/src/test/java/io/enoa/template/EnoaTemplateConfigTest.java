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
import io.enoa.template.compressor.provider.htmlcompressor.HtmlCompressorProvider;
import io.enoa.template.provider.enjoy.EnjoyConfig;
import io.enoa.template.provider.enjoy.EnjoyProvider;
import io.enoa.toolkit.path.PathKit;
import org.junit.Test;

import java.nio.charset.Charset;

public class EnoaTemplateConfigTest {

  @Test
  public void testConfi() {
    EoEngineConfig config = new EoEngineConfig() {
      @Override
      public String name() {
        return "main";
      }

      @Override
      public String viewPath() {
//        return PathKit.path().concat("/src/test/beetl");
        return PathKit.path("beetl").toString();
      }

      @Override
      public boolean debug() {
        return false;
      }

      @Override
      public String datePattern() {
        return null;
      }

      @Override
      public String suffix() {
        return "html";
      }

      @Override
      public Charset charset() {
        return Charset.forName("UTF-8");
      }

      @Override
      public boolean compress() {
        return false;
      }

      @Override
      public EoCompressorFactory compressor() {
        return new HtmlCompressorProvider();
      }
    };
  }

  @Test
  public void startEnjoy() {
    EnoaEngine engine = new EnjoyProvider().engine(new EnjoyConfig.Builder()
      .viewPath(PathKit.path("enjoy").toString())
      .build());
  }

  @Test
  public void testMutl() {
    EnjoyConfig config = new EnjoyConfig.Builder()
//      .viewPath(PathKit.path().concat("/src/test/tpl/enjoy"))
      .viewPath(PathKit.path("enjoy").toString())
      .build();
    EnoaEngine engine = new EnjoyProvider().engine(config);
  }

}
