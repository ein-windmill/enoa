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
package io.enoa.template;

import io.enoa.template.compressor.EoCompressorFactory;

import java.nio.charset.Charset;

/**
 * vtom - io.enoa.template
 */
public interface EoEngineConfig {

  default String name() {
    return "main";
  }

  String viewPath();

  boolean debug();

  String datePattern();

  String suffix();

  Charset charset();

  boolean compress();

  EoCompressorFactory compressor();

//  private final String name;
//  private final String viewPath;
//  private final boolean debug;
//  private final String datePattern;
//  private final String suffix;
//  private final Charset charset;
//  private final boolean compress;
//  private final EoCompressorFactory compressor;
//
//  private EoEngineConfig(Builder builder) {
//    this.name = builder.name;
//    this.viewPath = builder.viewPath;
//    this.debug = builder.debug;
//    this.datePattern = builder.datePattern;
//    this.suffix = builder.suffix;
//    this.charset = builder.charset;
//    this.compressor = builder.compressor;
//    this.compress = builder.compress;
//  }
//
//  public String name() {
//    return this.name;
//  }
//
//  public String viewPath() {
//    return this.viewPath;
//  }
//
//  public boolean debug() {
//    return this.debug;
//  }
//
//  public String datePattern() {
//    return this.datePattern;
//  }
//
//  public String suffix() {
//    return this.suffix;
//  }
//
//  public Charset charset() {
//    return this.charset;
//  }
//
//  public boolean compress() {
//    return this.compress;
//  }
//
//  public EoCompressorFactory compressor() {
//    return this.compressor;
//  }
//
//  public static class Builder {
//
//    private String name;
//    private String viewPath;
//    private boolean debug;
//    private String datePattern;
//    private String suffix;
//    private Charset charset;
//    private boolean compress;
//    private EoCompressorFactory compressor;
//
//    public Builder() {
//      this.name = "main";
//      this.debug = false;
//      this.charset = EoConst.CHARSET;
//      this.datePattern = EoConst.DEF_FORMAT_DATE;
//      this.compress = false;
//    }
//
//    public EoEngineConfig build() {
//      return new EoEngineConfig(this);
//    }
//
//    public Builder name(String name) {
//      this.name = name;
//      return this;
//    }
//
//    public Builder viewPath(String viewPath) {
//      this.viewPath = viewPath;
//      return this;
//    }
//
//    public Builder debug(boolean debug) {
//      this.debug = debug;
//      return this;
//    }
//
//    public Builder datePattern(String datePattern) {
//      this.datePattern = datePattern;
//      return this;
//    }
//
//    public Builder suffix(String suffix) {
//      this.suffix = suffix;
//      return this;
//    }
//
//    public Builder charset(Charset charset) {
//      this.charset = charset;
//      return this;
//    }
//
//    public Builder compress(boolean compress) {
//      this.compress = compress;
//      return this;
//    }
//
//    public Builder compressor(EoCompressorFactory compressor) {
//      this.compressor = compressor;
//      return this;
//    }
//
//  }

}
