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

import io.enoa.toolkit.mark.IMarkVal;

public enum ORDER implements IMarkVal {

  ASC("asc"),

  DESC("desc"),
  //
  ;

  private final String val;

  ORDER(String val) {
    this.val = val;
  }

  @Override
  public String val() {
    return this.val;
  }

  public static ORDER of(String val) {
    if (val == null)
      return null;
    for (ORDER order : ORDER.values()) {
      if (order.val.equalsIgnoreCase(val))
        return order;
    }
    return null;
  }

}
