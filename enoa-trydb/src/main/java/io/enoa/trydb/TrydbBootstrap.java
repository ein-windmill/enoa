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
package io.enoa.trydb;

import io.enoa.toolkit.eo.tip.EnoaTipKit;

public class TrydbBootstrap {

  private TrydbConfig config;

  public TrydbBootstrap(TrydbConfig config) {
    this.config = config;
  }

  public void start() {
    if (Trydb.exists(this.config.name()))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.trydb.name_exists", this.config.name()));

    Trydb.reg(this.config.name(), new EnoaTrydb(this.config));
  }

}
