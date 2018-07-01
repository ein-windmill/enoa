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
import io.enoa.tryjson.mark.DateFormatStrategy;

public class Esonfig {

  private final boolean debug;
  private final DateFormatStrategy dateFormatStrategy;
  private final String dateFormat;

  private Esonfig(Builder builder) {
    this.debug = builder.debug;
    this.dateFormat = builder.dateFormat;
    this.dateFormatStrategy = builder.dateFormatStrategy;
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

  public Builder builder() {
    return new Builder(this);
  }

  public static class Builder {
    private boolean debug;
    private DateFormatStrategy dateFormatStrategy;
    private String dateFormat;

    public Builder() {
      this.debug = Boolean.FALSE;
      this.dateFormat = EoConst.DEF_FORMAT_DATE;
      this.dateFormatStrategy = DateFormatStrategy.STRING;
    }

    public Builder(Esonfig esonfig) {
      this.debug = esonfig.debug;
      this.dateFormat = esonfig.dateFormat;
      this.dateFormatStrategy = esonfig.dateFormatStrategy;
    }

    public Esonfig build() {
      return new Esonfig(this);
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
  }
}
