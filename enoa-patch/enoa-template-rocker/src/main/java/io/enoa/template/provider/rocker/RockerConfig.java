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
package io.enoa.template.provider.rocker;

import io.enoa.template.EoEngineConfig;
import io.enoa.template.compressor.EoCompressorFactory;
import io.enoa.toolkit.EoConst;

import java.nio.charset.Charset;

public class RockerConfig implements EoEngineConfig {


  private final String name;
  private final String viewPath;
  private final boolean debug;
  private final String datePattern;
  private final String suffix;
  private final Charset charset;
  private final boolean compress;
  private final EoCompressorFactory compressor;

  private RockerConfig(Builder builder) {
    this.name = builder.name;
    this.viewPath = builder.viewPath;
    this.debug = builder.debug;
    this.datePattern = builder.datePattern;
    this.suffix = builder.suffix;
    this.charset = builder.charset;
    this.compressor = builder.compressor;
    this.compress = builder.compress;
  }

  RockerConfig(EoEngineConfig config) {
    this(new Builder(config));
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public String viewPath() {
    return this.viewPath;
  }

  @Override
  public boolean debug() {
    return this.debug;
  }

  @Override
  public String datePattern() {
    return this.datePattern;
  }

  @Override
  public String suffix() {
    return this.suffix;
  }

  @Override
  public Charset charset() {
    return this.charset;
  }

  @Override
  public boolean compress() {
    return this.compress;
  }

  @Override
  public EoCompressorFactory compressor() {
    return this.compressor;
  }


  public static class Builder {

    private String name;
    private String viewPath;
    private boolean debug;
    private String datePattern;
    private String suffix;
    private Charset charset;
    private boolean compress;
    private EoCompressorFactory compressor;


    public Builder() {
      this.name = "main";
      this.debug = false;
      this.charset = EoConst.CHARSET;
      this.datePattern = EoConst.DEF_FORMAT_DATE;
      this.compress = false;
    }

    private Builder(EoEngineConfig config) {
      this.name = config.name();
      this.debug = config.debug();
      this.charset = config.charset();
      this.datePattern = config.datePattern();
      this.compress = config.compress();
      this.viewPath = config.viewPath();
      this.suffix = config.suffix();
    }

    public RockerConfig build() {
      return new RockerConfig(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder viewPath(String viewPath) {
      this.viewPath = viewPath;
      return this;
    }

    public Builder debug(boolean debug) {
      this.debug = debug;
      return this;
    }

    public Builder datePattern(String datePattern) {
      this.datePattern = datePattern;
      return this;
    }

    public Builder suffix(String suffix) {
      this.suffix = suffix;
      return this;
    }

    public Builder charset(Charset charset) {
      this.charset = charset;
      return this;
    }

    public Builder compress() {
      this.compress = true;
      return this;
    }

    public Builder compressor(EoCompressorFactory compressor) {
      this.compressor = compressor;
      return this;
    }

  }

}
