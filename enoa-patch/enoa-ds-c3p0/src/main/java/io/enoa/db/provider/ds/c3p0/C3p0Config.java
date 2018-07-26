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
package io.enoa.db.provider.ds.c3p0;

import io.enoa.db.EoDsConfig;

public class C3p0Config implements EoDsConfig {


  private final String driver;
  private final String name;
  private final String url;
  private final String user;
  private final String passwd;
  private final int maxSize;
  private final int minSize;
  private final int initSize;
  private final int maxIdle;

  private final int acquireIncrement;

  private C3p0Config(Builder builder) {
    this.driver = builder.driver;
    this.name = builder.name;
    this.url = builder.url;
    this.user = builder.user;
    this.passwd = builder.passwd;
    this.maxSize = builder.maxSize;
    this.minSize = builder.minSize;
    this.initSize = builder.initSize;
    this.maxIdle = builder.maxIdle;
    this.acquireIncrement = builder.acquireIncrement;
  }

  C3p0Config(EoDsConfig config) {
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

  public int maxIdle() {
    return this.maxIdle;
  }

  public int acquireIncrement() {
    return this.acquireIncrement;
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
    private int maxIdle;
    private int acquireIncrement;

    public Builder() {
      this.initDef();
    }

    private Builder(EoDsConfig config) {
      if (config instanceof C3p0Config) {
        this.initByC3p0((C3p0Config) config);
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
      this.maxSize = 100;
      this.minSize = 10;
      this.initSize = 10;
      this.maxIdle = 20;
      this.acquireIncrement = 1;
    }

    private void initByC3p0(C3p0Config config) {
      this.name = config.name();
      this.driver = config.driver();
      this.url = config.url();
      this.user = config.user();
      this.passwd = config.passwd();
      this.maxSize = config.maxSize();
      this.minSize = config.minSize();
      this.initSize = config.initSize();
      this.maxSize = config.maxSize();

      this.acquireIncrement = config.acquireIncrement();
    }

    public C3p0Config build() {
      return new C3p0Config(this);
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

    public Builder maxIdle(int maxIdle) {
      this.maxIdle = maxIdle;
      return this;
    }

    public Builder acquireIncrement(int acquireIncrement) {
      this.acquireIncrement = acquireIncrement;
      return this;
    }
  }
}
