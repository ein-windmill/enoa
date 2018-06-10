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
package io.enoa.index.solr.cqp;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.text.TextKit;

import java.io.Serializable;

public class Fq implements Serializable {

  private String field;
  private String value;

  public Fq(String field, String value) {
    if (TextKit.isBlank(field))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.solr.cqp_fq_field_null"));
    if (value == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.solr.cqp_fq_value_null"));

    this.field = field;
    this.value = value;
  }

  public static Fq create(String field, String value) {
    return new Fq(field, value);
  }

  public String field() {
    return this.field;
  }

  public String value() {
    return this.value;
  }

  public String string() {
    return TextKit.union(this.field, ":", this.value);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;

    Fq fq = (Fq) object;

    if (!field.equals(fq.field)) return false;
    return value.equals(fq.value);
  }

  @Override
  public int hashCode() {
    int result = field.hashCode();
    result = 31 * result + value.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return this.string();
  }

}
