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

public enum Wt implements IMarkVal {

  JSON("json"),

  XML("xml"),

  PTHON("python"),

  RUBY("ruby"),

  PHP("php"),

  CSV("csv"),
  //
  ;

  private String val;

  Wt(String val) {
    this.val = val;
  }

  @Override
  public String val() {
    return this.val;
  }

  public static Wt of(String wt) {
    if (wt == null)
      return null;
    for (Wt _w : Wt.values()) {
      if (_w.val.equalsIgnoreCase(wt))
        return _w;
    }
    return null;
  }
}
