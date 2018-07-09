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

import io.enoa.toolkit.bean.bory.IBcollection;
import io.enoa.toolkit.bean.bory.IBmap;
import io.enoa.toolkit.bean.bory.PriorityStrategy;
import io.enoa.toolkit.namecase.INameCase;
import io.enoa.toolkit.namecase.NamecaseKit;
import io.enoa.toolkit.namecase.NamecaseType;

public class Bonfig {

  private final INameCase namecase;
  private final IBmap bmap;
  private final IBcollection bcollection;
  private final boolean skipError;
  private final String nullKey;
  private final PriorityStrategy priority;

  private Bonfig(Builder builder) {
    this.bmap = builder.bmap;
    this.skipError = builder.skipError;
    this.namecase = builder.namecase;
    this.nullKey = builder.nullKey;
    this.priority = builder.priority;
    this.bcollection = builder.bcollection;
  }

  public INameCase namecase() {
    return this.namecase;
  }

  public IBmap bmap() {
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

  public IBcollection bcollection() {
    return this.bcollection;
  }

  public static class Builder {
    private INameCase namecase;
    private IBmap bmap;
    private boolean skipError;
    private String nullKey;
    private PriorityStrategy priority;
    private IBcollection bcollection;

    public Builder() {
      this.bmap = IBmap.def();
      this.bcollection = IBcollection.def();
      this.skipError = Boolean.FALSE;
      this.namecase = NamecaseKit.namecase(NamecaseType.CASE_NONE);
      this.nullKey = "null";
      this.priority = PriorityStrategy.METHOD;
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

    public Builder bmap(IBmap bmap) {
      this.bmap = bmap;
      return this;
    }

    public Builder priority(PriorityStrategy priority) {
      this.priority = priority;
      return this;
    }

    public Builder bcollection(IBcollection bcollection) {
      this.bcollection = bcollection;
      return this;
    }
  }

}
