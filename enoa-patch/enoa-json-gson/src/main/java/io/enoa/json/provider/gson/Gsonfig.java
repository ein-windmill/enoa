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
package io.enoa.json.provider.gson;

public class Gsonfig {

  private final String dateFormat;
  private final boolean disableHtmlEscaping;
  private final boolean fixPrecision;

  public Gsonfig(Builder builder) {
    this.dateFormat = builder.dateFormat;
    this.disableHtmlEscaping = builder.disableHtmlEscaping;
    this.fixPrecision = builder.fixPrecision;
  }

  public boolean fixPrecision() {
    return this.fixPrecision;
  }

  public String dateFormat() {
    return dateFormat;
  }

  public boolean disableHtmlEscaping() {
    return disableHtmlEscaping;
  }

  public static class Builder {
    private String dateFormat;
    private boolean disableHtmlEscaping;
    private boolean fixPrecision;

    public Builder() {
      this.dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";
      this.disableHtmlEscaping = Boolean.TRUE;
      this.fixPrecision = Boolean.FALSE;
    }

    public Gsonfig build() {
      return new Gsonfig(this);
    }

    public Builder fixPrecision() {
      return this.fixPrecision(Boolean.TRUE);
    }

    public Builder fixPrecision(boolean fixPrecision) {
      this.fixPrecision = fixPrecision;
      return this;
    }

    public Builder dateFormat(String dateFormat) {
      this.dateFormat = dateFormat;
      return this;
    }

    public Builder disableHtmlEscaping() {
      return this.disableHtmlEscaping(Boolean.TRUE);
    }

    public Builder disableHtmlEscaping(boolean disableHtmlEscaping) {
      this.disableHtmlEscaping = disableHtmlEscaping;
      return this;
    }
  }

}
