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
package io.enoa.db.provider.ds.hikaricp;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.enoa.db.EnoaDs;

import javax.sql.DataSource;

class _HikariCp extends EnoaDs {

  private HikariCpConfig config;
  private HikariDataSource ds;

  _HikariCp(HikariCpConfig config) {
    this.config = config;
  }

  @Override
  public DataSource open() {
    this.init();
    return this.ds;
  }

  @Override
  public void close() {
    this.ds.close();
  }

  private boolean notBlank(String text) {
    return text != null && !"".equals(text.replace(" ", "").replace("\n", "").replace("\t", ""));
  }

  private void init() {

    HikariConfig config = new HikariConfig();
    //设定基本参数
    config.setJdbcUrl(this.config.url());
    config.setUsername(this.config.user());
    config.setPassword(this.config.passwd());

    //设定额外参数
    config.setAutoCommit(this.config.autoCommit());
    config.setReadOnly(this.config.readOnly());

    config.setConnectionTimeout(this.config.connectionTimeout());
    config.setIdleTimeout(this.config.idleTimeout());
    config.setMaxLifetime(this.config.maxLifetime());
    config.setMaximumPoolSize(this.config.maxSize());
    config.setValidationTimeout(this.config.validationTimeout());

    if (this.notBlank(this.config.driver())) {
      config.setDriverClassName(this.config.driver());
    }

    if (this.notBlank(this.config.transactionIsolation())) {
      config.setTransactionIsolation(this.config.transactionIsolation());
    }

    if (this.config.leakDetectionThreshold() != 0) {
      config.setLeakDetectionThreshold(this.config.leakDetectionThreshold());
    }

    if (this.notBlank(this.config.catalog())) {
      config.setCatalog(this.config.catalog());
    }

    if (this.notBlank(this.config.connectionTestQuery())) {
      config.setConnectionTestQuery(this.config.connectionTestQuery());
    }

    if (this.notBlank(this.config.name())) {
      config.setPoolName(this.config.name());
    }

    if (this.notBlank(this.config.connectionInitSql())) {
      config.setConnectionInitSql(this.config.connectionInitSql());
    }

    if (this.config.url().toLowerCase().contains(":mysql:")) {
      config.addDataSourceProperty("cachePrepStmts", "true");
      config.addDataSourceProperty("useServerPrepStmts", "true");
      config.addDataSourceProperty("prepStmtCacheSize", "256");
      config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    }
    if (this.config.url().toLowerCase().contains(":postgresql:")) {
      if (this.config.readOnly()) {
        config.addDataSourceProperty("readOnly", "true");
      }
      config.setConnectionTimeout(0);
      config.addDataSourceProperty("prepareThreshold", "3");
      config.addDataSourceProperty("preparedStatementCacheQueries", "128");
      config.addDataSourceProperty("preparedStatementCacheSizeMiB", "4");
    }

    this.ds = new HikariDataSource(config);
  }
}
