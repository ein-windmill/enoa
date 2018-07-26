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
package io.enoa.example.tryjson.entity;

import io.enoa.toolkit.mark.IMarkIx;

public enum BTypeIx implements IMarkIx {
  TYPE_A(0),
  TYPE_B(1),
  //
  ;

  private final int ix;

  BTypeIx(int ix) {
    this.ix = ix;
  }

  @Override
  public int ix() {
    return this.ix;
  }
}
