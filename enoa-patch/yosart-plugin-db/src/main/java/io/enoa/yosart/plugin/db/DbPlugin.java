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
package io.enoa.yosart.plugin.db;

import io.enoa.db.EPMDb;
import io.enoa.db.EoDbConfig;
import io.enoa.db.EoDbFactory;
import io.enoa.log.Log;
import io.enoa.yosart.YoPlugin;
import io.enoa.yosart.thr.OyPluginException;

public class DbPlugin implements YoPlugin {

  private EoDbFactory db;
  private EoDbConfig config;

  public DbPlugin(EoDbFactory db, EoDbConfig config) {
    this.db = db;
    this.config = config;
  }

  @Override
  public String name() {
    return "DbPlugin";
  }

  @Override
  public String version() {
    return "1";
  }

  @Override
  public String description() {
    return "Database plugin, connection database.";
  }

  @Override
  public boolean start() throws OyPluginException {
    try {
      EPMDb.install(this.db, this.config);
      return true;
    } catch (Exception e) {
      Log.error(e.getMessage(), e);
      return false;
    }
  }

  @Override
  public boolean stop() throws OyPluginException {
    EPMDb.uninstall(this.config);
    return true;
  }
}
