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
package io.enoa.db.provider.ds.hikaricp;

import io.enoa.db.EoDsConfig;

public class HikariCpConfig implements EoDsConfig {


  private final String driver;
  private final String name;
  private final String url;
  private final String user;
  private final String passwd;
  private final int maxSize;
  private final int minSize;
  private final int initSize;

  private final boolean autoCommit;
  private final long connectionTimeout;
  private final long idleTimeout;
  private final long maxLifetime;
  private final String connectionTestQuery;
  private final boolean readOnly;
  private final String catalog;
  private final String connectionInitSql;
  private final String transactionIsolation;
  private final long validationTimeout;
  private final long leakDetectionThreshold;

  private HikariCpConfig(Builder builder) {
    this.driver = builder.driver;
    this.name = builder.name;
    this.url = builder.url;
    this.user = builder.user;
    this.passwd = builder.passwd;
    this.maxSize = builder.maxSize;
    this.minSize = builder.minSize;
    this.initSize = builder.initSize;
    this.autoCommit = builder.autoCommit;
    this.connectionTimeout = builder.connectionTimeout;
    this.idleTimeout = builder.idleTimeout;
    this.maxLifetime = builder.maxLifetime;
    this.connectionTestQuery = builder.connectionTestQuery;
    this.readOnly = builder.readOnly;
    this.catalog = builder.catalog;
    this.connectionInitSql = builder.connectionInitSql;
    this.transactionIsolation = builder.transactionIsolation;
    this.validationTimeout = builder.validationTimeout;
    this.leakDetectionThreshold = builder.leakDetectionThreshold;
  }

  HikariCpConfig(EoDsConfig config) {
    this(new Builder(config));
  }

  @Override
  public String driver() {
    return this.driver;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public String url() {
    return this.url;
  }

  @Override
  public String user() {
    return this.user;
  }

  @Override
  public String passwd() {
    return this.passwd;
  }

  @Override
  public int maxSize() {
    return this.maxSize;
  }

  @Override
  public int minSize() {
    return this.minSize;
  }

  @Override
  public int initSize() {
    return this.initSize;
  }


  public boolean autoCommit() {
    return this.autoCommit;
  }

  public long connectionTimeout() {
    return this.connectionTimeout;
  }

  public long idleTimeout() {
    return this.idleTimeout;
  }

  public long maxLifetime() {
    return this.maxLifetime;
  }

  public String connectionTestQuery() {
    return this.connectionTestQuery;
  }

  public boolean readOnly() {
    return this.readOnly;
  }

  public String catalog() {
    return this.catalog;
  }

  public String connectionInitSql() {
    return this.connectionInitSql;
  }

  public String transactionIsolation() {
    return this.transactionIsolation;
  }

  public long validationTimeout() {
    return this.validationTimeout;
  }

  public long leakDetectionThreshold() {
    return this.leakDetectionThreshold;
  }

  public static class Builder {

    private String driver;
    private String name;
    private String url;
    private String user;
    private String passwd;
    private int maxSize;
    private int minSize;
    private int initSize;

    private boolean autoCommit;
    private long connectionTimeout;
    private long idleTimeout;
    private long maxLifetime;
    private String connectionTestQuery;
    private boolean readOnly;
    private String catalog;
    private String connectionInitSql;
    private String transactionIsolation;
    private long validationTimeout;
    private long leakDetectionThreshold;

    public Builder() {
      this.initDef();
    }

    private Builder(EoDsConfig config) {
      if (config instanceof HikariCpConfig) {
        this.initByHikariCp((HikariCpConfig) config);
        return;
      }
      this.initDef();
      this.name = config.name();
      this.driver = config.driver();
      this.url = config.url();
      this.user = config.user();
      this.passwd = config.passwd();
      this.maxSize = config.maxSize();
      this.minSize = config.minSize();
      this.initSize = config.initSize();
    }

    private void initDef() {
      this.autoCommit = true;
      this.connectionTimeout = 30000;
      this.idleTimeout = 600000;
      this.maxLifetime = 1800000;
      this.maxSize = 10;
      this.readOnly = false;
      this.validationTimeout = 5000;
      this.leakDetectionThreshold = 0;
    }

    private void initByHikariCp(HikariCpConfig config) {
      this.name = config.name();
      this.driver = config.driver();
      this.url = config.url();
      this.user = config.user();
      this.passwd = config.passwd();
      this.maxSize = config.maxSize();
      this.minSize = config.minSize();
      this.initSize = config.initSize();
      this.maxSize = config.maxSize();


      this.autoCommit = config.autoCommit();
      this.connectionTimeout = config.connectionTimeout();
      this.idleTimeout = config.idleTimeout();
      this.maxLifetime = config.maxLifetime();
      this.connectionTestQuery = config.connectionTestQuery();
      this.readOnly = config.readOnly();
      this.catalog = config.catalog();
      this.connectionInitSql = config.connectionInitSql();
      this.transactionIsolation = config.transactionIsolation();
      this.validationTimeout = config.validationTimeout();
      this.leakDetectionThreshold = config.leakDetectionThreshold();
    }

    public HikariCpConfig build() {
      return new HikariCpConfig(this);
    }

    public Builder driver(String driver) {
      this.driver = driver;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder user(String user) {
      this.user = user;
      return this;
    }

    public Builder passwd(String passwd) {
      this.passwd = passwd;
      return this;
    }

    public Builder maxSize(int maxSize) {
      this.maxSize = maxSize;
      return this;
    }

    public Builder minSize(int minSize) {
      this.minSize = minSize;
      return this;
    }

    public Builder initSize(int initSize) {
      this.initSize = initSize;
      return this;
    }


    public Builder autoCommit(boolean autoCommit) {
      this.autoCommit = autoCommit;
      return this;
    }

    public Builder connectionTimeout(long connectionTimeout) {
      this.connectionTimeout = connectionTimeout;
      return this;
    }

    public Builder idleTimeout(long idleTimeout) {
      this.idleTimeout = idleTimeout;
      return this;
    }

    public Builder maxLifetime(long maxLifetime) {
      this.maxLifetime = maxLifetime;
      return this;
    }

    public Builder connectionTestQuery(String connectionTestQuery) {
      this.connectionTestQuery = connectionTestQuery;
      return this;
    }

    public Builder readOnly(boolean readOnly) {
      this.readOnly = readOnly;
      return this;
    }

    public Builder catalog(String catalog) {
      this.catalog = catalog;
      return this;
    }

    public Builder connectionInitSql(String connectionInitSql) {
      this.connectionInitSql = connectionInitSql;
      return this;
    }

    public Builder transactionIsolation(String transactionIsolation) {
      this.transactionIsolation = transactionIsolation;
      return this;
    }

    public Builder validationTimeout(long validationTimeout) {
      this.validationTimeout = validationTimeout;
      return this;
    }

    public Builder leakDetectionThreshold(long leakDetectionThreshold) {
      this.leakDetectionThreshold = leakDetectionThreshold;
      return this;
    }
  }
}
