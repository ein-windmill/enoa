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

public class AoValueValid {

  private Valid valid;
  private String value;

  AoValueValid(Valid valid, String value) {
    this.valid = valid;
    this.value = value;
  }

  public AoObjectValid object() {
    return this.valid.object(this.value);
  }

  public AoStringValid string() {
    return this.valid.string(this.value);
  }

  public AoNumberValid number() {
    return this.valid.number(this.value);
  }

  public AoBooleanValid bool() {
    return this.valid.bool(this.value);
  }

  public AoFieldValid field() {
    return this.valid.field(this.value);
  }

  public AoExistsValid exists() {
    return this.valid.exists(this.value);
  }

}
