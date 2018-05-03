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
package io.enoa.yosart.ext.yemgr;

import com.jfinal.template.source.ClassPathSourceFactory;
import io.enoa.template.EnoaEngine;
import io.enoa.template.EoEngineConfig;
import io.enoa.template.compressor.provider.htmlcompressor.HtmlCompressorProvider;
import io.enoa.template.provider.enjoy.EnjoyConfig;
import io.enoa.template.provider.enjoy.EnjoyProvider;
import io.enoa.toolkit.EoConst;
import io.enoa.yosart.Oysart;
import io.enoa.yosart.Yesart;
import io.enoa.yosart.kernel.ext.YmBootHookExt;
import io.enoa.yosart.thr.OyExtException;

public class YemgrExt implements YmBootHookExt {

  private static EnoaEngine ENGINE;

  @Override
  public void hook(Yesart yesart) throws OyExtException {
    if (!Oysart.config().debug())
      return;
    yesart.handle(new Yemgr(this.engine()));
  }

  @Override
  public String name() {
    return "YemgrExt";
  }

  @Override
  public String version() {
    return "1";
  }


  private EnoaEngine engine() {
    if (ENGINE != null)
      return ENGINE;

    EoEngineConfig config = new EnjoyConfig.Builder()
      .name("YemgrExt")
      .compress()
      .compressor(new HtmlCompressorProvider())
      .debug(Oysart.config().debug())
      .charset(EoConst.CHARSET)
      .sourceFactory(new ClassPathSourceFactory())
      .viewPath(null)
      .sharedFunction("/layout/layout.html")
      .build();

    ENGINE = new EnjoyProvider().engine(config);
    return ENGINE;
  }

}
