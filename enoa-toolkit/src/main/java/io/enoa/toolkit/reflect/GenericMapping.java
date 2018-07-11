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
package io.enoa.toolkit.reflect;

import io.enoa.toolkit.text.TextKit;

public class GenericMapping {

  private String name;
  private Integer ix;
  private RefType reftype;

  private GenericMapping(Builder builder) {
    this.name = builder.name;
    this.ix = builder.ix;
    this.reftype = builder.reftype;
  }

  public String name() {
    return name;
  }

  public Integer ix() {
    return ix;
  }

  public RefType reftype() {
    return reftype;
  }

  @Override
  public String toString() {
    return TextKit.union("(", this.ix, ") -> ", this.name, " => ", this.reftype);
  }

  public static class Builder {

    private String name;
    private Integer ix;
    private RefType reftype;

    public GenericMapping build() {
      return new GenericMapping(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder ix(Integer ix) {
      this.ix = ix;
      return this;
    }

    public Builder reftype(RefType reftype) {
      this.reftype = reftype;
      return this;
    }
  }
}
