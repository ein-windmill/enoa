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

import io.enoa.toolkit.collection.CollectionKit;

public class RefType {

  private final String type;
  private final RefType[] generics;

  private RefType(Builder builder) {
    this.type = builder.type;
    this.generics = builder.generics;
  }

  public String type() {
    return this.type;
  }

  public RefType[] generics() {
    return this.generics;
  }

  public String string() {
    StringBuilder ret = new StringBuilder(this.type);
    if (CollectionKit.isEmpty(this.generics))
      return ret.toString();
    ret.append('<');
    for (RefType generic : this.generics) {
      ret.append(generic.type);
      if (CollectionKit.notEmpty(generic.generics)) {
        ret.append('<');
        for (RefType gene1 : generic.generics) {
          ret.append(gene1.toString())
            .append(", ");
        }
        ret.delete(ret.length() - 2, ret.length());
        ret.append('>');
      }
      ret.append(", ");
    }
    ret.delete(ret.length() - 2, ret.length());
    ret.append('>');
    return ret.toString();
  }

  @Override
  public String toString() {
    return this.string();
  }

  public static class Builder {
    private String type;
    private RefType[] generics;


    public RefType build() {
      return new RefType(this);
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder generics(RefType... generics) {
      this.generics = generics;
      return this;
    }

    @Override
    public String toString() {
      if (this.type == null)
        return null;
      return this.build().toString();
    }
  }

}
