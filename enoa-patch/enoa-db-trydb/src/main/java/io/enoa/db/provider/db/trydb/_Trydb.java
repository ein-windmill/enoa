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
package io.enoa.db.provider.db.trydb;

import io.enoa.db.EnoaDb;
import io.enoa.db.EnoaDs;
import io.enoa.trydb.Trydb;
import io.enoa.trydb.TrydbConfig;

class _Trydb extends EnoaDb {

  private EoTrydbConfig config;
  private EnoaDs ds;

  _Trydb(EoTrydbConfig config) {
    this.config = config;
    this.ds = this.config.ds().dataSource(this.config.dsConfig());
  }

  @Override
  public void start() {
    TrydbConfig.Builder tconf = new TrydbConfig.Builder()
      .name(this.config.name())
      .debug(this.config.debug())
      .ds(this.ds.open())
      .dialect(this.config.dialect())
      .txlevel(this.config.txlevel())
      .namecase(this.config.namecase())
      .template(this.config.template());

    if (this.config.reporter() != null)
      tconf.reporter(this.config.reporter());

    if (this.config.reporter() == null && this.config.showSql())
      tconf.showSql();

    Trydb.epm().install(tconf.build());
  }

  @Override
  public void stop() {
    this.ds.close();
  }

}
