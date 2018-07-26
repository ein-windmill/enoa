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
import io.enoa.toolkit.text.TextKit;

import java.io.Serializable;

public class Sort implements Serializable {

  private String field;
  private OrderBy order;

  public Sort(String field, OrderBy order) {
    if (TextKit.blanky(field))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.solr.cqp_sort_field_null"));
    if (order == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.solr.cqp_sort_order_null"));

    this.field = field;
    this.order = order;
  }

  public static Sort create(String field, OrderBy order) {
    return new Sort(field, order);
  }

  public String field() {
    return this.field;
  }

  public OrderBy order() {
    return this.order;
  }

  public String string() {
    return TextKit.union(this.field, " ", this.order.val());
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;

    Sort sort = (Sort) object;

    if (field != null ? !field.equals(sort.field) : sort.field != null) return false;
    return order == sort.order;
  }

  @Override
  public int hashCode() {
    int result = field != null ? field.hashCode() : 0;
    result = 31 * result + (order != null ? order.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return this.string();
  }
}
