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
package io.enoa.toolkit.bean.bory;

import io.enoa.toolkit.mark.IMarkIx;

/**
 * 當一個實體類同時存在 public field 以及該 field 的 public getter 方法時
 * 優先考慮何種方式的策略
 */
public enum PriorityStrategy implements IMarkIx {

  FIELD(0),

  METHOD(1),

  //
  ;

  private final int ix;

  PriorityStrategy(int ix) {
    this.ix = ix;
  }

  @Override
  public int ix() {
    return this.ix;
  }
}
