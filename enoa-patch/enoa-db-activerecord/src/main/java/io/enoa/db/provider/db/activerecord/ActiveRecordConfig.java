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
package io.enoa.db.provider.db.activerecord;

import com.jfinal.plugin.activerecord.IContainerFactory;
import com.jfinal.plugin.activerecord.IDbProFactory;
import com.jfinal.plugin.activerecord.cache.ICache;
import com.jfinal.plugin.activerecord.dialect.Dialect;
import io.enoa.db.EoDbConfig;
import io.enoa.db.EoDsConfig;
import io.enoa.db.EoDsFactory;

public class ActiveRecordConfig implements EoDbConfig {

  private final String name;
  private final EoDsFactory ds;
  private final EoDsConfig dsConfig;
  private final ActiveRecordMapping[] mappings;
  private final String sqlTemplate;
  private final IDbProFactory dbProFactory;
  private final String baseSqlTemplatePath;
  private final ICache cache;
  private final IContainerFactory containerFactory;
  private final boolean devMode;
  private final Dialect dialect;
  private final boolean showSql;
  private final Integer transactionLevel;


  private ActiveRecordConfig(Builder builder) {
    this.name = builder.name;
    this.ds = builder.ds;
    this.dsConfig = builder.dsConfig;
    this.mappings = builder.mappings;
    this.sqlTemplate = builder.sqlTemplate;
    this.dbProFactory = builder.dbProFactory;
    this.baseSqlTemplatePath = builder.baseSqlTemplatePath;
    this.cache = builder.cache;
    this.containerFactory = builder.containerFactory;
    this.devMode = builder.devMode;
    this.dialect = builder.dialect;
    this.showSql = builder.showSql;
    this.transactionLevel = builder.transactionLevel;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public EoDsFactory ds() {
    return this.ds;
  }

  @Override
  public EoDsConfig dsConfig() {
    return this.dsConfig;
  }

  public ActiveRecordMapping[] mappings() {
    return this.mappings;
  }

  public String sqlTemplate() {
    return this.sqlTemplate;
  }

  public IDbProFactory dbProFactory() {
    return this.dbProFactory;
  }

  public String baseSqlTemplatePath() {
    return this.baseSqlTemplatePath;
  }

  public ICache cache() {
    return this.cache;
  }

  public IContainerFactory containerFactory() {
    return this.containerFactory;
  }

  public boolean devMode() {
    return this.devMode;
  }

  public Dialect dialect() {
    return this.dialect;
  }

  public boolean showSql() {
    return this.showSql;
  }

  public Integer transactionLevel() {
    return this.transactionLevel;
  }

  public static class Builder {

    private String name;
    private EoDsFactory ds;
    private EoDsConfig dsConfig;
    private ActiveRecordMapping[] mappings;
    private String sqlTemplate;
    private IDbProFactory dbProFactory;
    private String baseSqlTemplatePath;
    private ICache cache;
    private IContainerFactory containerFactory;
    private boolean devMode;
    private Dialect dialect;
    private boolean showSql;
    private Integer transactionLevel;


    public Builder() {
      this.name = "main";
      this.mappings = new ActiveRecordMapping[0];
      this.devMode = false;
      this.showSql = true;
    }

    public ActiveRecordConfig build() {
      return new ActiveRecordConfig(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder ds(EoDsFactory ds, EoDsConfig dsConfig) {
      this.ds = ds;
      this.dsConfig = dsConfig;
      return this;
    }

    public Builder mappings(ActiveRecordMapping[] mappings) {
      this.mappings = mappings;
      return this;
    }

    public Builder sqlTemplate(String sqlTemplate) {
      this.sqlTemplate = sqlTemplate;
      return this;
    }

    public Builder dbProFactory(IDbProFactory dbProFactory) {
      this.dbProFactory = dbProFactory;
      return this;
    }

    public Builder baseSqlTemplatePath(String baseSqlTemplatePath) {
      this.baseSqlTemplatePath = baseSqlTemplatePath;
      return this;
    }

    public Builder cache(ICache cache) {
      this.cache = cache;
      return this;
    }

    public Builder containerFactory(IContainerFactory containerFactory) {
      this.containerFactory = containerFactory;
      return this;
    }

    public Builder devMode(boolean devMode) {
      this.devMode = devMode;
      return this;
    }

    public Builder dialect(Dialect dialect) {
      this.dialect = dialect;
      return this;
    }

    public Builder showSql(boolean showSql) {
      this.showSql = showSql;
      return this;
    }

    public Builder transactionLevel(Integer transactionLevel) {
      this.transactionLevel = transactionLevel;
      return this;
    }
  }
}
