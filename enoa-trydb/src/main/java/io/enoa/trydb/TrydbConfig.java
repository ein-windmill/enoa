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
package io.enoa.trydb;

import io.enoa.firetpl.Firetpl;
import io.enoa.toolkit.factory.ListFactory;
import io.enoa.toolkit.factory.MapFactory;
import io.enoa.toolkit.namecase.INameCase;
import io.enoa.toolkit.namecase.NamecaseKit;
import io.enoa.toolkit.namecase.NamecaseType;
import io.enoa.trydb.dialect.IDialect;
import io.enoa.trydb.tx.TxLevel;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;

public class TrydbConfig {


  private final String name;
  private final boolean debug;
  private final DataSource ds;
  private final IDialect dialect;
  private final TxLevel level;
  private final ISqlReporter reporter;
  private final INameCase namecase;
  private final Firetpl sqltemplate;
  private final boolean ignorecase;
  private final ListFactory lister;
  private final MapFactory maper;


  private TrydbConfig(Builder builder) {
    this.name = builder.name;
    this.debug = builder.debug;
    this.ds = builder.ds;
    this.dialect = builder.dialect;
    this.level = builder.level;
    this.reporter = builder.report;
    this.namecase = builder.namecase;
    this.sqltemplate = builder.sqltemplate;
    this.ignorecase = builder.ignorecase;
    this.lister = builder.lister;
    this.maper = builder.maper;
  }

  public ListFactory lister() {
    return this.lister;
  }

  public MapFactory maper() {
    return this.maper;
  }

  public boolean ignorecase() {
    return this.ignorecase;
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

  public Firetpl template() {
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
    private Firetpl sqltemplate;
    private boolean ignorecase;
    private ListFactory lister;
    private MapFactory maper;

    public Builder() {
      this.name = "main";
      this.level = TxLevel.REPEATABLE_READ;
      this.debug = Boolean.FALSE;
      this.namecase = NamecaseKit.namecase(NamecaseType.CASE_UNDERLINE);
      this.ignorecase = Boolean.FALSE;
      this.lister = LinkedList::new;
      this.maper = HashMap::new;
    }

    public TrydbConfig build() {
      return new TrydbConfig(this);
    }

    public Builder lister(ListFactory lister) {
      this.lister = lister;
      return this;
    }

    public Builder maper(MapFactory maper) {
      this.maper = maper;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder ignorecase() {
      return this.ignorecase(Boolean.TRUE);
    }

    public Builder ignorecase(boolean ignorecase) {
      this.ignorecase = ignorecase;
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

    public Builder template(Firetpl template) {
      this.sqltemplate = template;
      return this;
    }
  }

}
