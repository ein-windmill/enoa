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
package io.enoa.toolkit.ethernet;

import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.mark.IMarkIx;
import io.enoa.toolkit.text.TextReader;

public class IP {

  private final String text;
  private final String eth;
  private final String mask;
  private final Version version;

  private IP(Builder builder) {
    this.text = builder.text;
    this.eth = builder.eth;
    this.mask = builder.mask;
    this.version = builder.version;
  }


  public static IP create(Version version, String text) {
    if (Is.not().truthy(text))
      return null;
    TextReader reader = new TextReader(text);
    StringBuilder ipb = new StringBuilder(text.length()),
      ethb = new StringBuilder(),
      maskb = new StringBuilder();
    while (reader.hasNext()) {
      char now = reader.next();
      if (now == '%') {
        while (reader.hasNext()) {
          ethb.append(reader.next());
        }
      }
      if (now == '/') {
        while (reader.hasNext()) {
          maskb.append(reader.next());
        }
      }
      ipb.append(now);
    }
    Builder builder = new Builder()
      .text(ipb.toString())
      .eth(ethb.toString())
      .mask(maskb.toString())
      .version(version);
    ipb.delete(0, ipb.length());
    ethb.delete(0, ethb.length());
    maskb.delete(0, maskb.length());
    return builder.build();
  }

  public String string() {
    return text;
  }

  public Version version() {
    return version;
  }

  public String eth() {
    return eth;
  }

  public String mask() {
    return mask;
  }

  @Override
  public String toString() {
    return this.text;
  }

  private static class Builder {

    private String text;
    private String eth;
    private String mask;
    private Version version;

    public IP build() {
      return new IP(this);
    }

    public Builder version(Version version) {
      this.version = version;
      return this;
    }

    public Builder text(String text) {
      this.text = text;
      return this;
    }

    public Builder eth(String eth) {
      this.eth = eth;
      return this;
    }

    public Builder mask(String mask) {
      this.mask = mask;
      return this;
    }
  }


  public enum Version implements IMarkIx {
    FOUR(4),
    SIX(6),
    //
    ;
    private final int ix;

    Version(int ix) {
      this.ix = ix;
    }

    @Override
    public int ix() {
      return ix;
    }
  }

}
