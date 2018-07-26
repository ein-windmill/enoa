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
package io.enoa.db.provider.db.activerecord;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import io.enoa.db.EnoaDb;
import io.enoa.db.EnoaDs;

class _ActiveRecord extends EnoaDb {

  private ActiveRecordConfig config;
  private EnoaDs ds;

  _ActiveRecord(ActiveRecordConfig config) {
    this.config = config;
    this.ds = this.config.ds().dataSource(this.config.dsConfig());
  }

  @Override
  public void start() {

    ActiveRecordPlugin plugin = new ActiveRecordPlugin(this.config.name(), this.ds.open());
    if (this.config.mappings().length > 0) {
      for (ActiveRecordMapping mapping : this.config.mappings()) {
        if (mapping.primary() != null)
          plugin.addMapping(mapping.table(), mapping.primary(), mapping.modelClass());
        else
          plugin.addMapping(mapping.table(), mapping.modelClass());
      }
    }
    if (this.config.sqlTemplate() != null)
      plugin.addSqlTemplate(this.config.sqlTemplate());
    if (this.config.dbProFactory() != null)
      plugin.setDbProFactory(this.config.dbProFactory());
    if (this.config.baseSqlTemplatePath() != null)
      plugin.setBaseSqlTemplatePath(this.config.baseSqlTemplatePath());
    if (this.config.cache() != null)
      plugin.setCache(this.config.cache());
    if (this.config.containerFactory() != null)
      plugin.setContainerFactory(this.config.containerFactory());

    plugin.setDevMode(this.config.devMode());
    if (this.config.dialect() != null)
      plugin.setDialect(this.config.dialect());
    plugin.setShowSql(this.config.showSql());
    if (this.config.transactionLevel() != null)
      plugin.setTransactionLevel(this.config.transactionLevel());

    plugin.start();
  }

  @Override
  public void stop() {
    this.ds.close();
  }
}
