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
package io.enoa.template.provider.enjoy;

import com.jfinal.template.IOutputDirectiveFactory;
import com.jfinal.template.source.ISource;
import com.jfinal.template.source.ISourceFactory;
import io.enoa.template.EoEngineConfig;
import io.enoa.template.compressor.EoCompressorFactory;
import io.enoa.toolkit.EoConst;

import java.nio.charset.Charset;
import java.util.List;

public class EnjoyConfig implements EoEngineConfig {


  private final String name;
  private final String viewPath;
  private final boolean debug;
  private final String datePattern;
  private final String suffix;
  private final Charset charset;
  private final boolean compress;
  private final EoCompressorFactory compressor;


  private final IOutputDirectiveFactory outputDirectiveFactory;
  private final boolean reloadModifiedSharedFunctionInDevMode;
  private final ISourceFactory sourceFactory;
  private final int bufferSize;
  private final List<ISource> sharedFunctionSources;
  private final String[] sharedFunctionString;
  private final EnjoyDirectiveConfig[] directives;
  private final Class<?>[] sharedMethods;
  private final EnjoySharedObjectConfig[] sharedObjects;
  private final Class<?>[] sharedStaticMethods;


  private EnjoyConfig(Builder builder) {
    this.name = builder.name;
    this.viewPath = builder.viewPath;
    this.debug = builder.debug;
    this.datePattern = builder.datePattern;
    this.suffix = builder.suffix;
    this.charset = builder.charset;
    this.compressor = builder.compressor;
    this.compress = builder.compress;

    this.outputDirectiveFactory = builder.outputDirectiveFactory;
    this.reloadModifiedSharedFunctionInDevMode = builder.reloadModifiedSharedFunctionInDevMode;
    this.sourceFactory = builder.sourceFactory;
    this.bufferSize = builder.bufferSize;
    this.sharedFunctionSources = builder.sharedFunctionSources;
    this.sharedFunctionString = builder.sharedFunctionString;
    this.directives = builder.directives;
    this.sharedMethods = builder.sharedMethods;
    this.sharedObjects = builder.sharedObjects;
    this.sharedStaticMethods = builder.sharedStaticMethods;
  }

  EnjoyConfig(EoEngineConfig config) {
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


  public IOutputDirectiveFactory outputDirectiveFactory() {
    return outputDirectiveFactory;
  }

  public boolean reloadModifiedSharedFunctionInDevMode() {
    return reloadModifiedSharedFunctionInDevMode;
  }

  public ISourceFactory sourceFactory() {
    return sourceFactory;
  }

  public int bufferSize() {
    return bufferSize;
  }

  public List<ISource> sharedFunctionSources() {
    return sharedFunctionSources;
  }

  public String[] sharedFunctionString() {
    return sharedFunctionString;
  }

  public EnjoyDirectiveConfig[] directives() {
    return directives;
  }

  public Class<?>[] sharedMethods() {
    return sharedMethods;
  }

  public EnjoySharedObjectConfig[] sharedObjects() {
    return sharedObjects;
  }

  public Class<?>[] sharedStaticMethods() {
    return sharedStaticMethods;
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


    private IOutputDirectiveFactory outputDirectiveFactory;
    private boolean reloadModifiedSharedFunctionInDevMode;
    private ISourceFactory sourceFactory;
    private int bufferSize;
    private List<ISource> sharedFunctionSources;
    private String[] sharedFunctionString;
    private EnjoyDirectiveConfig[] directives;
    private Class<?>[] sharedMethods;
    private EnjoySharedObjectConfig[] sharedObjects;
    private Class<?>[] sharedStaticMethods;


    public Builder() {
      this.name = "main";
      this.debug = false;
      this.charset = EoConst.CHARSET;
      this.datePattern = EoConst.DEF_FORMAT_DATE;
      this.compress = false;
      this.initDef();
    }

    private Builder(EoEngineConfig config) {
      this.initDef();
      this.name = config.name();
      this.debug = config.debug();
      this.charset = config.charset();
      this.datePattern = config.datePattern();
      this.compress = config.compress();
      this.viewPath = config.viewPath();
      this.suffix = config.suffix();
    }

    private void initDef() {
      this.reloadModifiedSharedFunctionInDevMode = true;
      this.bufferSize = 2048;
    }

    public EnjoyConfig build() {
      return new EnjoyConfig(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder viewPath(String viewPath) {
      this.viewPath = viewPath;
      return this;
    }

    public Builder debug() {
      return this.debug(true);
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


    public Builder outputDirectiveFactory(IOutputDirectiveFactory outputDirectiveFactory) {
      this.outputDirectiveFactory = outputDirectiveFactory;
      return this;
    }

    public Builder reloadModifiedSharedFunctionInDevMode(boolean reloadModifiedSharedFunctionInDevMode) {
      this.reloadModifiedSharedFunctionInDevMode = reloadModifiedSharedFunctionInDevMode;
      return this;
    }

    public Builder sourceFactory(ISourceFactory sourceFactory) {
      this.sourceFactory = sourceFactory;
      return this;
    }

    public Builder bufferSize(int bufferSize) {
      this.bufferSize = bufferSize;
      return this;
    }

    public Builder sharedFunction(List<ISource> sharedFunctionSources) {
      this.sharedFunctionSources = sharedFunctionSources;
      return this;
    }

    public Builder sharedFunction(String... sharedFunctionString) {
      this.sharedFunctionString = sharedFunctionString;
      return this;
    }

    public Builder directives(EnjoyDirectiveConfig... directives) {
      this.directives = directives;
      return this;
    }

    public Builder sharedMethods(Class<?>... sharedMethod) {
      this.sharedMethods = sharedMethods;
      return this;
    }

    public Builder sharedObjects(EnjoySharedObjectConfig... sharedObjects) {
      this.sharedObjects = sharedObjects;
      return this;
    }

    public Builder sharedStaticMethods(Class<?>... sharedStaticMethods) {
      this.sharedStaticMethods = sharedStaticMethods;
      return this;
    }
  }

}
