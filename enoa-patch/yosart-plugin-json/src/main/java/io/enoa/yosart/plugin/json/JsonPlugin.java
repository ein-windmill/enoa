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
package io.enoa.yosart.plugin.json;

import io.enoa.json.EoJsonFactory;
import io.enoa.json.Json;
import io.enoa.json.provider.enoa.EoJsonProvider;
import io.enoa.yosart.YoPlugin;
import io.enoa.yosart.thr.OyPluginException;

public class JsonPlugin implements YoPlugin {

  private EoJsonFactory factory;

  public JsonPlugin() {
    this(new EoJsonProvider());
  }

  public JsonPlugin(EoJsonFactory factory) {
    this.factory = factory;
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
//    EMgrJson.defJsonFactory(this.factory);
//    if (TextKit.notBlank(this.datePattern))
//      EMgrJson.defDatePattern(this.datePattern);
    Json.epm().install(this.factory);
    return true;
  }

  @Override
  public boolean stop() throws OyPluginException {
    return true;
  }
}
