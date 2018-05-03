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
package io.enoa.yosart.ext.anost.valid;

import io.enoa.yosart.ext.anost.valid.ivalid.ISpecialValid;

public class AoExistsValid {

  private Valid valid;
  private String name;

  AoExistsValid(Valid valid, String name) {
    this.valid = valid;
    this.name = name;
  }

  public AoObjectValid object() {
    return this.valid.object(this.name);
  }

  public AoStringValid string() {
    return this.valid.string(this.name);
  }

  public AoNumberValid number() {
    return this.valid.number(this.name);
  }

  public AoBooleanValid bool() {
    return this.valid.bool(this.name);
  }

  public AoFieldValid field() {
    return this.valid.field(this.name);
  }

  public AoArrayValid array() {
    return this.valid.array(this.name);
  }

  public AoExistsValid special(ISpecialValid valid) throws ValidException {
    this.valid.special(valid);
    return this;
  }
}
