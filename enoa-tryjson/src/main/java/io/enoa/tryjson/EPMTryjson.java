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
package io.enoa.tryjson;

import io.enoa.toolkit.EoConst;

public class EPMTryjson {

  private static class Holder {
    private static final EPMTryjson INSTANCE = new EPMTryjson();
  }

  public static EPMTryjson instance() {
    return Holder.INSTANCE;
  }

  private Esonfig config;

  private EPMTryjson() {
    this.config = new Esonfig.Builder()
      .debug(Boolean.FALSE)
      .dateFormat(EoConst.DEF_FORMAT_DATE)
      .build();
  }

  public EPMTryjson install(Esonfig config) {
    this.config = config;
    return this;
  }

  public Esonfig config() {
    return this.config;
  }
}
