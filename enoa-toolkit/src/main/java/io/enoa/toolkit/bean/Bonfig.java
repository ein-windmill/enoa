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
package io.enoa.toolkit.bean;

import io.enoa.toolkit.bean.bory.IBmaco;
import io.enoa.toolkit.bean.bory.PriorityStrategy;
import io.enoa.toolkit.convert.IConverter;
import io.enoa.toolkit.namecase.INameCase;
import io.enoa.toolkit.namecase.NamecaseKit;
import io.enoa.toolkit.namecase.NamecaseType;

import java.util.Collection;
import java.util.Map;

public class Bonfig {

  /**
   * map key 命名规则转换 默认不转换
   */
  private final INameCase namecase;
  /**
   * bean 转换 map 创建的 map 实例来源 默认 HashMap
   */
  private final IBmaco<Map<String, Object>> bmap;
  /**
   * collection 实例 默认 ArrayList
   */
  private final IBmaco<Collection<Object>> bcollection;
  /**
   * 是否跳过转换错误 默认不跳过
   */
  private final boolean skipError;
  /**
   * null 键所使用的键名 默认 null 字符串
   */
  private final String nullKey;
  /**
   * bean 转换 map 会考虑该类的 public 字段以及 public 方法, 如果当字段于方法重复时, 使用何种方式.
   * 默认 方法优先
   */
  private final PriorityStrategy priority;
  /**
   * 枚举字段值转换方式, 默认返回枚举原始类
   */
  private final IConverter<Object, Enum> enumer;

  private Bonfig(Builder builder) {
    this.bmap = builder.bmap;
    this.skipError = builder.skipError;
    this.namecase = builder.namecase;
    this.nullKey = builder.nullKey;
    this.priority = builder.priority;
    this.bcollection = builder.bcollection;
    this.enumer = builder.enumer;
  }

  public INameCase namecase() {
    return this.namecase;
  }

  public IBmaco<Map<String, Object>> bmap() {
    return this.bmap;
  }

  public boolean skipError() {
    return this.skipError;
  }

  public String nullKey() {
    return this.nullKey;
  }

  public PriorityStrategy priority() {
    return this.priority;
  }

  public IBmaco<Collection<Object>> bcollection() {
    return this.bcollection;
  }

  public IConverter<Object, Enum> enumer() {
    return enumer;
  }

  public Builder builder() {
    return new Builder(this);
  }

  public static class Builder {
    private INameCase namecase;
    private boolean skipError;
    private String nullKey;
    private PriorityStrategy priority;
    private IBmaco<Map<String, Object>> bmap;
    private IBmaco<Collection<Object>> bcollection;
    private IConverter<Object, Enum> enumer;

    public Builder() {
      this.bmap = IBmaco.map();
      this.bcollection = IBmaco.collection();
      this.skipError = Boolean.FALSE;
      this.namecase = NamecaseKit.namecase(NamecaseType.CASE_NONE);
      this.nullKey = "null";
      this.priority = PriorityStrategy.METHOD;
      this.enumer = $Bonfig$EnumOriginConverter.instance();
    }

    public Builder(Bonfig config) {
      this();
      this.bmap = config.bmap;
      this.namecase = config.namecase;
      this.skipError = config.skipError;
      this.nullKey = config.nullKey;
      this.priority = config.priority;
      this.bcollection = config.bcollection;
      this.enumer = config.enumer;
    }

    public Bonfig build() {
      return new Bonfig(this);
    }

    public Builder nullKey(String nullKey) {
      this.nullKey = nullKey;
      return this;
    }

    public Builder namecase(INameCase namecase) {
      this.namecase = namecase;
      return this;
    }

    public Builder skipError(boolean skipError) {
      this.skipError = skipError;
      return this;
    }

    public Builder bmap(IBmaco<Map<String, Object>> bmap) {
      this.bmap = bmap;
      return this;
    }

    public Builder priority(PriorityStrategy priority) {
      this.priority = priority;
      return this;
    }

    public Builder bcollection(IBmaco<Collection<Object>> bcollection) {
      this.bcollection = bcollection;
      return this;
    }

    public Builder enumer(IConverter<Object, Enum> enumer) {
      this.enumer = enumer;
      return this;
    }
  }

}
