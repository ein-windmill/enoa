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
package io.enoa.provider.db.beetlsql;

import io.enoa.db.EoDbConfig;
import io.enoa.db.EoDsConfig;
import io.enoa.db.EoDsFactory;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.NameConversion;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.DBStyle;

public class BeetlSQLConfig implements EoDbConfig {

  private final String name;
  private final EoDsFactory ds;
  private final EoDsConfig dsConfig;
  private final DBStyle style;
  private final String load;
  private final NameConversion nameConversion;
  private final Interceptor[] interceptors;

  private BeetlSQLConfig(Builder builder) {
    this.name = builder.name;
    this.ds = builder.ds;
    this.dsConfig = builder.dsConfig;
    this.style = builder.style;
    this.load = builder.load;
    this.nameConversion = builder.nameConversion;
    this.interceptors = builder.interceptors;
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

  public DBStyle style() {
    return this.style;
  }

  public String load() {
    return this.load;
  }

  public NameConversion nameConversion() {
    return this.nameConversion;
  }

  public Interceptor[] interceptors() {
    return this.interceptors;
  }

  public static class Builder {

    private String name;
    private EoDsFactory ds;
    private EoDsConfig dsConfig;
    private DBStyle style;
    private String load;
    private NameConversion nameConversion;
    private Interceptor[] interceptors;

    public Builder() {
      this.name = "main";
      this.load = "/sql";
      this.nameConversion = new UnderlinedNameConversion();
      this.interceptors = new Interceptor[0];
    }

    public BeetlSQLConfig build() {
      return new BeetlSQLConfig(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder ds(EoDsFactory ds, EoDsConfig config) {
      this.ds = ds;
      this.dsConfig = config;
      return this;
    }

    public Builder style(DBStyle style) {
      this.style = style;
      return this;
    }

    public Builder load(String load) {
      this.load = load;
      return this;
    }

    public Builder nameConversion(NameConversion nameConversion) {
      this.nameConversion = nameConversion;
      return this;
    }

    public Builder interceptors(Interceptor[] interceptors) {
      this.interceptors = interceptors;
      return this;
    }

  }
}
