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

import io.enoa.db.EoDbConfig;
import io.enoa.db.EoDsConfig;
import io.enoa.db.EoDsFactory;
import io.enoa.toolkit.namecase.INameCase;
import io.enoa.toolkit.namecase.NamecaseKit;
import io.enoa.toolkit.namecase.NamecaseType;
import io.enoa.trydb.ISqlReport;
import io.enoa.trydb.dialect.IDialect;
import io.enoa.trydb.tsql.template.TSqlTemplate;
import io.enoa.trydb.tx.TxLevel;

public class EoTrydbConfig implements EoDbConfig {

  private final String name;
  private final boolean debug;
  private final EoDsFactory ds;
  private final EoDsConfig dsConfig;
  private final IDialect dialect;
  private final TxLevel level;
  private final ISqlReport report;
  private final INameCase namecase;
  private final boolean showSql;
  private final TSqlTemplate sqltemplate;

  private EoTrydbConfig(Builder builder) {
    this.name = builder.name;
    this.debug = builder.debug;
    this.ds = builder.ds;
    this.dsConfig = builder.dsConfig;
    this.dialect = builder.dialect;
    this.level = builder.level;
    this.report = builder.report;
    this.namecase = builder.namecase;
    this.showSql = builder.showSql;
    this.sqltemplate = builder.sqltemplate;
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

  public boolean debug() {
    return this.debug;
  }

  public IDialect dialect() {
    return this.dialect;
  }

  public TxLevel txlevel() {
    return level;
  }

  public ISqlReport report() {
    return this.report;
  }

  public INameCase namecase() {
    return namecase;
  }

  public boolean showSql() {
    return this.showSql;
  }

  public TSqlTemplate template() {
    return this.sqltemplate;
  }

  public static class Builder {
    private String name;
    private boolean debug;
    private EoDsFactory ds;
    private EoDsConfig dsConfig;
    private IDialect dialect;
    private TxLevel level;
    private ISqlReport report;
    private INameCase namecase;
    private boolean showSql;
    private TSqlTemplate sqltemplate;


    public Builder() {
      this.name = "main";
      this.level = TxLevel.REPEATABLE_READ;
      this.debug = false;
      this.namecase = NamecaseKit.namecase(NamecaseType.CASE_UNDERLINE);
    }

    public EoTrydbConfig build() {
      return new EoTrydbConfig(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder debug() {
      return this.debug(true);
    }

    public Builder debug(boolean debug) {
      this.debug = debug;
      return this;
    }

    public Builder ds(EoDsFactory ds, EoDsConfig config) {
      this.ds = ds;
      this.dsConfig = config;
      return this;
    }

    public Builder dialect(IDialect dialect) {
      this.dialect = dialect;
      return this;
    }

    public Builder txlevel(TxLevel level) {
      this.level = level;
      return this;
    }

    public Builder showSql() {
      return this.showSql(true);
    }

    public Builder showSql(boolean showSql) {
      this.showSql = showSql;
      return this;
    }

    public Builder report(ISqlReport report) {
      this.report = report;
      return this;
    }

    public Builder namecase(INameCase namecase) {
      this.namecase = namecase;
      return this;
    }

    public Builder template(TSqlTemplate template) {
      this.sqltemplate = template;
      return this;
    }
  }
}
