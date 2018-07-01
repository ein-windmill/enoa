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
package io.enoa.tryjson.converter;

import io.enoa.toolkit.EoConst;

public class ConvConf {

  private final String dateFormat;

  private ConvConf(Builder builder) {
    this.dateFormat = builder.dateFormat;
  }

  public String dateFormat() {
    return this.dateFormat;
  }

  public static class Builder {
    private String dateFormat;

    public Builder() {
      this.dateFormat = EoConst.DEF_FORMAT_DATE;
    }

    public ConvConf build() {
      return new ConvConf(this);
    }

    public Builder dateFormat(String dateFormat) {
      this.dateFormat = dateFormat;
      return this;
    }
  }

}
