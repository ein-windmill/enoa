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
package io.enoa.repeater;

import io.enoa.repeater.config.ssl.EoxSSLConfig;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.sys.EnvKit;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EoxConfig {

  /**
   * 开发模式
   */
  private final boolean debug;
  private final String context;
  private final Charset charset;
  /**
   * 是否打印 repeater 信息
   */
  private final boolean info;
  /**
   * 使用 log 组件打印 repeater 细腻
   */
  private final boolean infoUseLog;
  private final int soTimeout;
  private final Path tmp;
  private final int maxUploadSize;
  private final EoxSSLConfig ssl;
  private final Kv other;
  /**
   * 保持文件上传后在内存不持久化到硬盘
   */
  private final boolean holdFile;

  private static EoxConfig current;

  private EoxConfig(Builder builder) {
    this.debug = builder.debug;
    this.context = builder.context;
    this.charset = builder.charset;
    this.info = builder.info;
    this.infoUseLog = builder.infoUseLog;
    this.soTimeout = builder.soTimeout;
    this.tmp = builder.tmp;
    this.maxUploadSize = builder.maxUploadSize;
    this.ssl = builder.ssl;
    this.other = builder.other;
    this.holdFile = builder.holdFile;
  }

  public static EoxConfig def() {
    return new Builder()
      .ssl(EoxSSLConfig.def())
      .build();
  }

  static EoxConfig current() {
    return current;
  }

  static void current(EoxConfig config) {
    current = config;
  }

  public boolean debug() {
    return this.debug;
  }

  public String context() {
    return this.context;
  }

  public Charset charset() {
    return this.charset;
  }

  public boolean info() {
    return this.info;
  }

  public boolean infoUseLog() {
    return this.infoUseLog;
  }

  public int soTimeout() {
    return this.soTimeout;
  }

  public Path tmp() {
    return this.tmp;
  }

  public int maxUploadSize() {
    return this.maxUploadSize;
  }

  public EoxSSLConfig ssl() {
    return this.ssl;
  }

  public Kv other() {
    return this.other;
  }

  public boolean holdFile() {
    return this.holdFile;
  }

  public Builder newBuilder() {
    return new Builder(this);
  }

  /**
   * ConfigBuilder
   */
  public static class Builder {
    private boolean debug;
    private String context;
    private Charset charset;
    private boolean info;
    private boolean infoUseLog;
    private int soTimeout;
    private Path tmp;
    private int maxUploadSize;
    private EoxSSLConfig ssl;
    private Kv other;
    private boolean holdFile;

    public Builder() {
      this.debug = false;
      this.context = "/";
      this.charset = EoConst.CHARSET;
      this.info = true;
      this.infoUseLog = false;
      this.soTimeout = 5000;
      this.tmp = Paths.get(EnvKit.string("java.io.tmpdir"));
      this.maxUploadSize = 2; // MB
      this.holdFile = false;
    }

    public Builder(EoxConfig config) {
      this();
      this.debug = config.debug;
      this.context = config.context;
      this.charset = config.charset;
      this.info = config.info;
      this.infoUseLog = config.infoUseLog;
      this.soTimeout = config.soTimeout;
      this.tmp = config.tmp;
      this.maxUploadSize = config.maxUploadSize;
      this.ssl = config.ssl;
      this.other = config.other;
      this.holdFile = config.holdFile;
    }

    public EoxConfig build() {
      return new EoxConfig(this);
    }

    public Builder debug() {
      return this.debug(true);
    }

    public Builder debug(boolean debug) {
      this.debug = debug;
      return this;
    }

    public Builder context(String context) {
      this.context = context;
      return this;
    }

    public Builder charset(Charset charset) {
      this.charset = charset;
      return this;
    }

    public Builder tmp(Path tmp) {
      this.tmp = tmp;
      return this;
    }

    public Builder maxUploadSize(int maxUploadSize) {
      this.maxUploadSize = maxUploadSize;
      return this;
    }

    public Builder ssl(EoxSSLConfig ssl) {
      this.ssl = ssl;
      return this;
    }

    public Builder info(boolean info) {
      this.info = info;
      return this;
    }

    public Builder infoUseLog(boolean infoUseLog) {
      this.infoUseLog = infoUseLog;
      return this;
    }

    public Builder soTimeout(int soTimeout) {
      this.soTimeout = soTimeout;
      return this;
    }

    public Builder holdFile() {
      return this.holdFile(true);
    }

    public Builder holdFile(boolean holdFile) {
      this.holdFile = holdFile;
      return this;
    }

    public Builder other(Kv other) {
      this.other = other;
      return this;
    }

  }
}
