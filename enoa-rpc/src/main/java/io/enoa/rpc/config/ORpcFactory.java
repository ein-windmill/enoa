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
package io.enoa.rpc.config;

import io.enoa.json.EnoaJson;
import io.enoa.json.EoJsonFactory;
import io.enoa.log.EnoaLog;
import io.enoa.log.EoLogFactory;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.thr.EoException;

public class ORpcFactory {

  private static class Holder {
    private static final ORpcFactory INSTANCE = new ORpcFactory();
  }

  private ORpcFactory() {

  }

  static ORpcFactory instance() {
    return Holder.INSTANCE;
  }

  private EoJsonFactory json;
  private EoLogFactory log;

  public ORpcFactory json(EoJsonFactory json) {
    this.json = json;
    return this;
  }

  public EnoaJson json() {
    if (this.json == null)
      throw new EoException(EnoaTipKit.message("eo.tip.rpc.handler_json_provider_null"));
    return this.json.json();
  }

  public ORpcFactory log(EoLogFactory log) {
    this.log = log;
    return this;
  }

  public EnoaLog log(String name) {
    return this.log.logger(name);
  }

  public EnoaLog log(Class<?> clazz) {
    return this.log.logger(clazz);
  }

}
