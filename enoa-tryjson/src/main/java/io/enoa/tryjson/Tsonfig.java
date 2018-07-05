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
package io.enoa.tryjson;

import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.namecase.INameCase;
import io.enoa.tryjson.ext.detect.ITypeDetector;
import io.enoa.tryjson.ext.enumer.IEnumConverter;
import io.enoa.tryjson.ext.format.IJsonFormat;
import io.enoa.tryjson.mark.DateFormatStrategy;

public class Tsonfig {

  /**
   * 調試模式
   */
  private final boolean debug;
  /**
   * 時間格式化策略
   */
  private final DateFormatStrategy dateFormatStrategy;
  /**
   * 如果時間格式化策略爲字符串模式, 所選用的時間格式化格式
   */
  private final String dateFormat;
  /**
   * json key 命名轉換規則, 默認不轉換
   */
  private final INameCase namecase;
  /**
   * 枚舉轉換規則, 默認使用枚舉字段名
   */
  private final IEnumConverter enumConverter;
  /**
   * 對象轉換 json 後, 對 json 字符串進行格式化
   */
  private final IJsonFormat jsonFormat;
  /**
   * json 解析属性值类型侦测
   */
  private final ITypeDetector detector;
  /**
   * json 序列化时跳过 null 值
   */
  private final boolean skipNull;

  private Tsonfig(Builder builder) {
    this.debug = builder.debug;
    this.dateFormat = builder.dateFormat;
    this.dateFormatStrategy = builder.dateFormatStrategy;
    this.namecase = builder.namecase;
    this.enumConverter = builder.enumConverter;
    this.jsonFormat = builder.jsonFormat;
    this.detector = builder.detector;
    this.skipNull = builder.skipNull;
  }

  public static Tsonfig def() {
    return new Builder().build();
  }

  public boolean debug() {
    return this.debug;
  }

  public String dateFormat() {
    return this.dateFormat;
  }

  public DateFormatStrategy dateFormatStrategy() {
    return this.dateFormatStrategy;
  }

  public INameCase namecase() {
    return this.namecase;
  }

  public IEnumConverter enumConverter() {
    return this.enumConverter;
  }

  public IJsonFormat jsonFormat() {
    return this.jsonFormat;
  }

  public ITypeDetector detector() {
    return this.detector;
  }

  public boolean skipNull() {
    return this.skipNull;
  }

  public Builder builder() {
    return new Builder(this);
  }

  public static class Builder {
    private boolean debug;
    private DateFormatStrategy dateFormatStrategy;
    private String dateFormat;
    private INameCase namecase;
    private IEnumConverter enumConverter;
    private IJsonFormat jsonFormat;
    private ITypeDetector detector;
    private boolean skipNull;

    public Builder() {
      this.debug = Boolean.FALSE;
      this.dateFormat = EoConst.DEF_FORMAT_DATE;
      this.dateFormatStrategy = DateFormatStrategy.STRING;
      this.namecase = INameCase.def();
      this.enumConverter = IEnumConverter.def();
      this.jsonFormat = IJsonFormat.def();
      this.detector = ITypeDetector.def();
      this.skipNull = Boolean.TRUE;
    }

    private Builder(Tsonfig tsonfig) {
      this.debug = tsonfig.debug;
      this.dateFormat = tsonfig.dateFormat;
      this.dateFormatStrategy = tsonfig.dateFormatStrategy;
      this.namecase = tsonfig.namecase;
      this.enumConverter = tsonfig.enumConverter;
      this.jsonFormat = tsonfig.jsonFormat;
      this.detector = tsonfig.detector;
      this.skipNull = tsonfig.skipNull;
    }

    public Tsonfig build() {
      return new Tsonfig(this);
    }

    public Builder debug() {
      return this.debug(Boolean.TRUE);
    }

    public Builder debug(boolean debug) {
      this.debug = debug;
      return this;
    }

    public Builder dateFormatStrategy(DateFormatStrategy dateFormatStrategy) {
      this.dateFormatStrategy = dateFormatStrategy;
      return this;
    }

    public Builder dateFormat(String dateFormat) {
      this.dateFormat = dateFormat;
      return this;
    }

    public Builder namecase(INameCase namecase) {
      this.namecase = namecase;
      return this;
    }

    public Builder enumConverter(IEnumConverter enumConverter) {
      this.enumConverter = enumConverter;
      return this;
    }

    public Builder jsonFormat(IJsonFormat jsonFormat) {
      this.jsonFormat = jsonFormat;
      return this;
    }

    public Builder decector(ITypeDetector detector) {
      this.detector = detector;
      return this;
    }

    public Builder skipNull() {
      return this.skipNull(Boolean.TRUE);
    }

    public Builder skipNull(boolean skipNull) {
      this.skipNull = skipNull;
      return this;
    }
  }
}
