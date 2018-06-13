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

import io.enoa.toolkit.namecase.INameCase;
import io.enoa.toolkit.namecase.NamecaseKit;
import io.enoa.toolkit.namecase.NamecaseType;
import io.enoa.trydb.dialect.IDialect;
import io.enoa.trydb.tsql.template.TSqlTemplate;
import io.enoa.trydb.tx.TxLevel;

import javax.sql.DataSource;

public class TrydbConfig {


  private final String name;
  private final boolean debug;
  private final DataSource ds;
  private final IDialect dialect;
  private final TxLevel level;
  private final ISqlReporter reporter;
  private final INameCase namecase;
  private final TSqlTemplate sqltemplate;


  private TrydbConfig(Builder builder) {
    this.name = builder.name;
    this.debug = builder.debug;
    this.ds = builder.ds;
    this.dialect = builder.dialect;
    this.level = builder.level;
    this.reporter = builder.report;
    this.namecase = builder.namecase;
    this.sqltemplate = builder.sqltemplate;
  }

  public String name() {
    return this.name;
  }

  public boolean debug() {
    return this.debug;
  }

  public DataSource ds() {
    return this.ds;
  }

  public IDialect dialect() {
    return this.dialect;
  }

  public TxLevel txlevel() {
    return level;
  }

  public ISqlReporter reporter() {
    return this.reporter;
  }

  public INameCase namecase() {
    return namecase;
  }

  public TSqlTemplate template() {
    return this.sqltemplate;
  }

  public static class Builder {
    private String name;
    private boolean debug;
    private DataSource ds;
    private IDialect dialect;
    private TxLevel level;
    private ISqlReporter report;
    private INameCase namecase;
    private TSqlTemplate sqltemplate;

    public Builder() {
      this.name = "main";
      this.level = TxLevel.REPEATABLE_READ;
      this.debug = false;
      this.namecase = NamecaseKit.namecase(NamecaseType.CASE_UNDERLINE);
    }

    public TrydbConfig build() {
      return new TrydbConfig(this);
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

    public Builder ds(DataSource ds) {
      this.ds = ds;
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
      if (!showSql)
        return this;
      this.report = _TrydbSqlReporter.instance();
      return this;
    }

    public Builder reporter(ISqlReporter reporter) {
      this.report = reporter;
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
