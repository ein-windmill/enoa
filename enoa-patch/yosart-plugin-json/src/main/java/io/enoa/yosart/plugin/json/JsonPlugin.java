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
package io.enoa.yosart.plugin.json;

import io.enoa.json.EnoaJsonMgr;
import io.enoa.json.EoJsonFactory;
import io.enoa.json.provider.enoa.EoJsonProvider;
import io.enoa.toolkit.text.TextKit;
import io.enoa.yosart.YoPlugin;
import io.enoa.yosart.thr.OyPluginException;

public class JsonPlugin implements YoPlugin {

  private EoJsonFactory factory;
  private String datePattern;

  public JsonPlugin() {
    this(new EoJsonProvider(), null);
  }

  public JsonPlugin(EoJsonFactory factory) {
    this(factory, null);
  }

  public JsonPlugin(EoJsonFactory factory, String datePattern) {
    this.factory = factory;
    this.datePattern = datePattern;
  }

  @Override
  public String name() {
    return "JsonPlugin";
  }

  @Override
  public String version() {
    return "1";
  }

  @Override
  public String description() {
    return "Yosart json support, include json render extension.";
  }

  @Override
  public boolean start() throws OyPluginException {
    EnoaJsonMgr.instance().defJsonFactory(this.factory);
    if (TextKit.notBlank(this.datePattern))
      EnoaJsonMgr.instance().defDatePattern(this.datePattern);
    return true;
  }

  @Override
  public boolean stop() throws OyPluginException {
    return true;
  }
}
