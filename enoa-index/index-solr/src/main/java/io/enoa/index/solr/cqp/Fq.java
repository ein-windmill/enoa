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
package io.enoa.index.solr.cqp;

import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.value.EnoaValue;

import java.io.Serializable;

public class Fq implements Serializable {

  private String field;
  private Object value;

  public Fq(String field, Object value) {
    if (Is.not().truthy(field))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.solr.cqp_fq_field_null"));
    if (value == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.solr.cqp_fq_value_null"));

    this.field = field;
    this.value = value;
  }

  public static Fq create(String field, Object value) {
    return new Fq(field, value);
  }

  public String field() {
    return this.field;
  }

  public EnoaValue value() {
    return EnoaValue.with(this.value);
  }

  public String string() {
    return TextKit.union(this.field, ":", this.value);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;

    Fq fq = (Fq) object;

    if (field != null ? !field.equals(fq.field) : fq.field != null) return false;
    return value != null ? value.equals(fq.value) : fq.value == null;
  }

  @Override
  public int hashCode() {
    int result = field != null ? field.hashCode() : 0;
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return this.string();
  }

}
