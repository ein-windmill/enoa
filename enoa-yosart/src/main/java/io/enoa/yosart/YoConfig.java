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
package io.enoa.yosart;

import io.enoa.repeater.config.ssl.EoxSSLConfig;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.sys.EnvKit;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class YoConfig {


  private final boolean debug;
  private final EoxSSLConfig ssl;
  /**
   * 是否打印 repeater 信息
   */
  private final boolean info;
  /**
   * 使用 log 组件打印 repeater 信息
   */
  private final boolean infoUseLog;
  private final int maxUploadSize;
  private final Path tmp;
  private final Charset charset;
  private final Kv other;


  private YoConfig(Builder builder) {
    this.debug = builder.debug;
    this.ssl = builder.ssl;
    this.info = builder.info;
    this.infoUseLog = builder.infoUseLog;
    this.charset = builder.charset;
    this.other = builder.other;
    this.maxUploadSize = builder.maxUploadSize;
    this.tmp = builder.tmp;
  }

  public static YoConfig def() {
    return new Builder()
      .build();
  }

  Builder newBuilder() {
    return new Builder(this);
  }

  public boolean debug() {
    return this.debug;
  }

  public EoxSSLConfig ssl() {
    return this.ssl;
  }

  public boolean info() {
    return this.info;
  }

  public boolean infoUseLog() {
    return this.infoUseLog;
  }

  public Charset charset() {
    return this.charset;
  }

  public Kv other() {
    return this.other;
  }

  public int maxUploadSize() {
    return this.maxUploadSize;
  }

  public Path tmp() {
    return this.tmp;
  }

  public static class Builder {
    private boolean debug;
    private EoxSSLConfig ssl;
    private boolean info;
    private boolean infoUseLog;
    private int maxUploadSize;
    private Path tmp;
    private Charset charset;
    private Kv other;

    public Builder() {
      this.debug = true;
      this.info = true;
      this.infoUseLog = false;
      this.charset = EoConst.CHARSET;
      this.other = Kv.create();
      this.maxUploadSize = 2; // MB
      this.tmp = Paths.get(EnvKit.string("java.io.tmpdir"));
    }

    public Builder(YoConfig config) {
      this.debug = config.debug;
      this.ssl = config.ssl;
      this.info = config.info;
      this.infoUseLog = config.infoUseLog;
      this.charset = config.charset;
      this.other = config.other;
      this.maxUploadSize = config.maxUploadSize;
      this.tmp = config.tmp;
    }

    public YoConfig build() {
      return new YoConfig(this);
    }

    public Builder debug() {
      return this.debug(true);
    }

    public Builder debug(boolean debug) {
      this.debug = debug;
      return this;
    }

    public Builder ssl(EoxSSLConfig ssl) {
      this.ssl = ssl;
      return this;
    }

    public Builder other(Kv other) {
      this.other = other;
      return this;
    }

    public Builder charset(Charset charset) {
      this.charset = charset;
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

    public Builder maxUploadSize(int maxUploadSize) {
      this.maxUploadSize = maxUploadSize;
      return this;
    }

    public Builder tmp(Path tmp) {
      this.tmp = tmp;
      return this;
    }

  }
}
