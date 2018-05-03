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
package io.enoa.template.provider.beetl;

import io.enoa.template.EoEngineConfig;
import io.enoa.template.compressor.EoCompressorFactory;
import io.enoa.toolkit.EoConst;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

public class BeetlConfig implements EoEngineConfig {


  private final String name;
  private final String viewPath;
  private final boolean debug;
  private final String datePattern;
  private final String suffix;
  private final Charset charset;
  private final boolean compress;
  private final EoCompressorFactory compressor;


  private final String placeholderStart;
  private final String placeholderEnd;
  private final String statementStart;
  private final String statementEnd;
  private final String htmlTagFlag;
  private final boolean isHtmlTagSupport;
  private final boolean nativeCall;
  private final boolean directByteOutput;
  private final boolean isStrict;
  private final boolean isIgnoreClientIOError;
  private final String errorHandlerClass;
  private final String htmlTagStart;
  private final String htmlTagEnd;
  private final String htmlTagBindingAttribute;
  private final Set<String> pkgList;
  private final String webAppExt;
  private final boolean hasFunctionLimiter;
  private final String functionLimiterStart;
  private final String functionLimiterEnd;
  private final String engine;
  private final String nativeSecurity;
  private final String resourceLoader;


  private BeetlConfig(Builder builder) {
    this.name = builder.name;
    this.viewPath = builder.viewPath;
    this.debug = builder.debug;
    this.datePattern = builder.datePattern;
    this.suffix = builder.suffix;
    this.charset = builder.charset;
    this.compressor = builder.compressor;
    this.compress = builder.compress;

    this.placeholderStart = builder.placeholderStart;
    this.placeholderEnd = builder.placeholderEnd;
    this.statementStart = builder.statementStart;
    this.statementEnd = builder.statementEnd;
    this.htmlTagFlag = builder.htmlTagFlag;
    this.isHtmlTagSupport = builder.isHtmlTagSupport;
    this.nativeCall = builder.nativeCall;
    this.directByteOutput = builder.directByteOutput;
    this.isStrict = builder.isStrict;
    this.isIgnoreClientIOError = builder.isIgnoreClientIOError;
    this.errorHandlerClass = builder.errorHandlerClass;
    this.htmlTagStart = builder.htmlTagStart;
    this.htmlTagEnd = builder.htmlTagEnd;
    this.htmlTagBindingAttribute = builder.htmlTagBindingAttribute;
    this.pkgList = builder.pkgList;
    this.webAppExt = builder.webAppExt;
    this.hasFunctionLimiter = builder.hasFunctionLimiter;
    this.functionLimiterStart = builder.functionLimiterStart;
    this.functionLimiterEnd = builder.functionLimiterEnd;
    this.engine = builder.engine;
    this.nativeSecurity = builder.nativeSecurity;
    this.resourceLoader = builder.resourceLoader;

  }

  BeetlConfig(EoEngineConfig config) {
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


  public String placeholderStart() {
    return placeholderStart;
  }

  public String placeholderEnd() {
    return placeholderEnd;
  }

  public String statementStart() {
    return statementStart;
  }

  public String statementEnd() {
    return statementEnd;
  }

  public String htmlTagFlag() {
    return htmlTagFlag;
  }

  public boolean isHtmlTagSupport() {
    return isHtmlTagSupport;
  }

  public boolean isNativeCall() {
    return nativeCall;
  }

  public boolean isDirectByteOutput() {
    return directByteOutput;
  }

  public boolean isStrict() {
    return isStrict;
  }

  public boolean isIgnoreClientIOError() {
    return isIgnoreClientIOError;
  }

  public String errorHandlerClass() {
    return errorHandlerClass;
  }

  public String htmlTagStart() {
    return htmlTagStart;
  }

  public String htmlTagEnd() {
    return htmlTagEnd;
  }

  public String htmlTagBindingAttribute() {
    return htmlTagBindingAttribute;
  }

  public Set<String> pkgList() {
    return pkgList;
  }

  public String webAppExt() {
    return webAppExt;
  }

  public boolean isHasFunctionLimiter() {
    return hasFunctionLimiter;
  }

  public String functionLimiterStart() {
    return functionLimiterStart;
  }

  public String functionLimiterEnd() {
    return functionLimiterEnd;
  }

  public String engine() {
    return engine;
  }

  public String nativeSecurity() {
    return nativeSecurity;
  }

  public String resourceLoader() {
    return resourceLoader;
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


    private String placeholderStart;
    private String placeholderEnd;
    private String statementStart;
    private String statementEnd;
    private String htmlTagFlag;
    private boolean isHtmlTagSupport;
    private boolean nativeCall;
    private boolean directByteOutput;
    private boolean isStrict;
    private boolean isIgnoreClientIOError;
    private String errorHandlerClass;
    private String htmlTagStart;
    private String htmlTagEnd;
    private String htmlTagBindingAttribute;
    private Set<String> pkgList;
    private String webAppExt;
    private boolean hasFunctionLimiter;
    private String functionLimiterStart;
    private String functionLimiterEnd;
    private String engine;
    private String nativeSecurity;
    private String resourceLoader;


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
      this.compressor = config.compressor();
      this.compress = config.compress();
      this.viewPath = config.viewPath();
      this.suffix = config.suffix();
    }

    private void initDef() {

      this.placeholderStart = "${";
      this.placeholderEnd = "}";
      this.statementStart = "<%";
      this.statementEnd = "%>";
      this.htmlTagFlag = "#";
      this.isHtmlTagSupport = false;
      this.nativeCall = false;
      this.directByteOutput = false;
      this.isStrict = false;
      this.isIgnoreClientIOError = true;
      this.errorHandlerClass = "org.beetl.core.ConsoleErrorHandler";
      this.htmlTagStart = "<" + htmlTagFlag;
      this.htmlTagEnd = "</" + htmlTagFlag;
      this.htmlTagBindingAttribute = "var";
      this.pkgList = new HashSet<>();
      this.webAppExt = null;
      this.hasFunctionLimiter = false;
      this.functionLimiterStart = null;
      this.functionLimiterEnd = null;
      this.engine = "org.beetl.core.engine.FastRuntimeEngine";
      this.nativeSecurity = "org.beetl.core.DefaultNativeSecurityManager";
      this.resourceLoader = "org.beetl.core.resource.ClasspathResourceLoader";
    }

    public BeetlConfig build() {
      return new BeetlConfig(this);
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


    public Builder placeholderStart(String placeholderStart) {
      this.placeholderStart = placeholderStart;
      return this;
    }

    public Builder placeholderEnd(String placeholderEnd) {
      this.placeholderEnd = placeholderEnd;
      return this;
    }

    public Builder statementStart(String statementStart) {
      this.statementStart = statementStart;
      return this;
    }

    public Builder statementEnd(String statementEnd) {
      this.statementEnd = statementEnd;
      return this;
    }

    public Builder htmlTagFlag(String htmlTagFlag) {
      this.htmlTagFlag = htmlTagFlag;
      return this;
    }

    public Builder htmlTagSupport(boolean htmlTagSupport) {
      isHtmlTagSupport = htmlTagSupport;
      return this;
    }

    public Builder nativeCall(boolean nativeCall) {
      this.nativeCall = nativeCall;
      return this;
    }

    public Builder directByteOutput(boolean directByteOutput) {
      this.directByteOutput = directByteOutput;
      return this;
    }

    public Builder strict(boolean strict) {
      isStrict = strict;
      return this;
    }

    public Builder ignoreClientIOError(boolean ignoreClientIOError) {
      isIgnoreClientIOError = ignoreClientIOError;
      return this;
    }

    public Builder errorHandlerClass(String errorHandlerClass) {
      this.errorHandlerClass = errorHandlerClass;
      return this;
    }

    public Builder htmlTagStart(String htmlTagStart) {
      this.htmlTagStart = htmlTagStart;
      return this;
    }

    public Builder htmlTagEnd(String htmlTagEnd) {
      this.htmlTagEnd = htmlTagEnd;
      return this;
    }

    public Builder htmlTagBindingAttribute(String htmlTagBindingAttribute) {
      this.htmlTagBindingAttribute = htmlTagBindingAttribute;
      return this;
    }

    public Builder pkgList(Set<String> pkgList) {
      this.pkgList = pkgList;
      return this;
    }

    public Builder webAppExt(String webAppExt) {
      this.webAppExt = webAppExt;
      return this;
    }

    public Builder hasFunctionLimiter(boolean hasFunctionLimiter) {
      this.hasFunctionLimiter = hasFunctionLimiter;
      return this;
    }

    public Builder functionLimiterStart(String functionLimiterStart) {
      this.functionLimiterStart = functionLimiterStart;
      return this;
    }

    public Builder functionLimiterEnd(String functionLimiterEnd) {
      this.functionLimiterEnd = functionLimiterEnd;
      return this;
    }

    public Builder engine(String engine) {
      this.engine = engine;
      return this;
    }

    public Builder nativeSecurity(String nativeSecurity) {
      this.nativeSecurity = nativeSecurity;
      return this;
    }

    public Builder resourceLoader(String resourceLoader) {
      this.resourceLoader = resourceLoader;
      return this;
    }
  }

}
