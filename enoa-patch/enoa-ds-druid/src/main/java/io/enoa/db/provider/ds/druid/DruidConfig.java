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
package io.enoa.db.provider.ds.druid;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import io.enoa.db.EoDsConfig;

import java.util.List;

public class DruidConfig implements EoDsConfig {

  private final String driver;
  private final String name;
  private final String url;
  private final String user;
  private final String passwd;
  private final int maxSize;
  private final int minSize;
  private final int initSize;
  private final int minIdle;
  private final int maxActive;
  private final String pubKey;
  private final long maxWait;
  private final long timeBetweenEvictionRunsMillis;
  private final long minEvictableIdleTimeMillis;
  private final long timeBetweenConnectErrorMillis;
  private final String validationQuery;
  private final String connectionInitSql;
  private final String connectionProperties;
  private final boolean testWhileIdle;
  private final boolean testOnBorrow;
  private final boolean testOnReturn;
  private final boolean removeAbandoned;
  private final long removeAbandonedTimeoutMillis;
  private final boolean logAbandoned;
  private final int maxPoolPreparedStatementPerConnectionSize;
  private final String filters;
  private final List<Filter> filterList;

  private DruidConfig(Builder builder) {
    this.driver = builder.driver;
    this.name = builder.name;
    this.url = builder.url;
    this.user = builder.user;
    this.passwd = builder.passwd;
    this.maxSize = builder.maxSize;
    this.minSize = builder.minSize;
    this.initSize = builder.initSize;
    this.minIdle = builder.minIdle;
    this.maxActive = builder.maxActive;
    this.pubKey = builder.pubKey;
    this.maxWait = builder.maxWait;
    this.timeBetweenEvictionRunsMillis = builder.timeBetweenEvictionRunsMillis;
    this.minEvictableIdleTimeMillis = builder.minEvictableIdleTimeMillis;
    this.timeBetweenConnectErrorMillis = builder.timeBetweenConnectErrorMillis;
    this.validationQuery = builder.validationQuery;
    this.connectionInitSql = builder.connectionInitSql;
    this.connectionProperties = builder.connectionProperties;
    this.testWhileIdle = builder.testWhileIdle;
    this.testOnBorrow = builder.testOnBorrow;
    this.testOnReturn = builder.testOnReturn;
    this.removeAbandoned = builder.removeAbandoned;
    this.removeAbandonedTimeoutMillis = builder.removeAbandonedTimeoutMillis;
    this.logAbandoned = builder.logAbandoned;
    this.maxPoolPreparedStatementPerConnectionSize = builder.maxPoolPreparedStatementPerConnectionSize;
    this.filters = builder.filters;
    this.filterList = builder.filterList;
  }

  DruidConfig(EoDsConfig config) {
    this(new Builder(config));
  }

  public Builder newBuilder() {
    return new Builder(this);
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

  public int minIdle() {
    return this.minIdle;
  }


  public int maxActive() {
    return maxActive;
  }

  public String pubKey() {
    return pubKey;
  }

  public long maxWait() {
    return maxWait;
  }

  public long timeBetweenEvictionRunsMillis() {
    return timeBetweenEvictionRunsMillis;
  }

  public long minEvictableIdleTimeMillis() {
    return minEvictableIdleTimeMillis;
  }

  public long timeBetweenConnectErrorMillis() {
    return timeBetweenConnectErrorMillis;
  }

  public String validationQuery() {
    return validationQuery;
  }

  public String connectionInitSql() {
    return connectionInitSql;
  }

  public String connectionProperties() {
    return connectionProperties;
  }

  public boolean testWhileIdle() {
    return testWhileIdle;
  }

  public boolean testOnBorrow() {
    return testOnBorrow;
  }

  public boolean testOnReturn() {
    return testOnReturn;
  }

  public boolean removeAbandoned() {
    return removeAbandoned;
  }

  public long removeAbandonedTimeoutMillis() {
    return removeAbandonedTimeoutMillis;
  }

  public boolean logAbandoned() {
    return logAbandoned;
  }

  public int maxPoolPreparedStatementPerConnectionSize() {
    return maxPoolPreparedStatementPerConnectionSize;
  }

  public String filters() {
    return filters;
  }

  public List<Filter> filterList() {
    return filterList;
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
    private int minIdle;
    private String pubKey;
    private long maxWait;
    private int maxActive;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private long timeBetweenConnectErrorMillis;
    private String validationQuery;
    private String connectionInitSql;
    private String connectionProperties;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean removeAbandoned;
    private long removeAbandonedTimeoutMillis;
    private boolean logAbandoned;
    private int maxPoolPreparedStatementPerConnectionSize;
    private String filters;
    private List<Filter> filterList;

    public Builder() {
      this.initDef();
    }

    private Builder(EoDsConfig config) {
      if (config instanceof DruidConfig) {
        this.initByDruidConfig((DruidConfig) config);
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

    private Builder(DruidConfig config) {
      this.initByDruidConfig(config);
    }

    private void initByDruidConfig(DruidConfig config) {
      this.name = config.name();
      this.driver = config.driver();
      this.url = config.url();
      this.user = config.user();
      this.passwd = config.passwd();
      this.maxSize = config.maxSize();
      this.minSize = config.minSize();
      this.initSize = config.initSize();
      this.minIdle = config.minIdle();

      this.pubKey = config.pubKey();
      this.maxWait = config.maxWait();
      this.maxActive = config.maxActive();
      this.timeBetweenEvictionRunsMillis = config.timeBetweenEvictionRunsMillis();
      this.minEvictableIdleTimeMillis = config.minEvictableIdleTimeMillis();
      this.timeBetweenConnectErrorMillis = config.timeBetweenConnectErrorMillis();
      this.validationQuery = config.validationQuery();
      this.connectionInitSql = config.connectionInitSql();
      this.connectionProperties = config.connectionProperties();
      this.testWhileIdle = config.testWhileIdle();
      this.testOnBorrow = config.testOnBorrow();
      this.testOnReturn = config.testOnReturn();
      this.removeAbandoned = config.removeAbandoned();
      this.removeAbandonedTimeoutMillis = config.removeAbandonedTimeoutMillis();
      this.logAbandoned = config.logAbandoned();
      this.maxPoolPreparedStatementPerConnectionSize = config.maxPoolPreparedStatementPerConnectionSize();
      this.filters = config.filters();
      this.filterList = config.filterList();
    }

    private void initDef() {
      this.initSize = 10;
      this.minIdle = 10;
      this.maxActive = 100;
      this.maxWait = DruidDataSource.DEFAULT_MAX_WAIT;
      this.timeBetweenEvictionRunsMillis = DruidDataSource.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
      this.minEvictableIdleTimeMillis = DruidDataSource.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
      this.timeBetweenConnectErrorMillis = DruidDataSource.DEFAULT_TIME_BETWEEN_CONNECT_ERROR_MILLIS;
      this.validationQuery = "select 1";
      this.connectionInitSql = null;
      this.connectionProperties = null;
      this.testWhileIdle = true;
      this.testOnBorrow = false;
      this.testOnReturn = false;
      this.removeAbandoned = false;
      this.removeAbandonedTimeoutMillis = 300 * 1000;
      this.logAbandoned = false;
      this.maxPoolPreparedStatementPerConnectionSize = -1;
    }

    public DruidConfig build() {
      return new DruidConfig(this);
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

    public Builder minIdle(int minIdle) {
      this.minIdle = minIdle;
      return this;
    }

    public Builder maxActive(int maxActive) {
      this.maxActive = maxActive;
      return this;
    }

    public Builder pubKey(String pubKey) {
      this.pubKey = pubKey;
      return this;
    }

    public Builder maxWait(long maxWait) {
      this.maxWait = maxWait;
      return this;
    }

    public Builder timeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
      this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
      return this;
    }

    public Builder minEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
      this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
      return this;
    }

    public Builder timeBetweenConnectErrorMillis(long timeBetweenConnectErrorMillis) {
      this.timeBetweenConnectErrorMillis = timeBetweenConnectErrorMillis;
      return this;
    }

    public Builder validationQuery(String validationQuery) {
      this.validationQuery = validationQuery;
      return this;
    }

    public Builder connectionInitSql(String connectionInitSql) {
      this.connectionInitSql = connectionInitSql;
      return this;
    }

    public Builder connectionProperties(String connectionProperties) {
      this.connectionProperties = connectionProperties;
      return this;
    }

    public Builder testWhileIdle(boolean testWhileIdle) {
      this.testWhileIdle = testWhileIdle;
      return this;
    }

    public Builder testOnBorrow(boolean testOnBorrow) {
      this.testOnBorrow = testOnBorrow;
      return this;
    }

    public Builder testOnReturn(boolean testOnReturn) {
      this.testOnReturn = testOnReturn;
      return this;
    }

    public Builder removeAbandoned(boolean removeAbandoned) {
      this.removeAbandoned = removeAbandoned;
      return this;
    }

    public Builder removeAbandonedTimeoutMillis(long removeAbandonedTimeoutMillis) {
      this.removeAbandonedTimeoutMillis = removeAbandonedTimeoutMillis;
      return this;
    }

    public Builder logAbandoned(boolean logAbandoned) {
      this.logAbandoned = logAbandoned;
      return this;
    }

    public Builder maxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
      this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
      return this;
    }

    public Builder filters(String filters) {
      this.filters = filters;
      return this;
    }

    public Builder filterList(List<Filter> filterList) {
      this.filterList = filterList;
      return this;
    }
  }
}
