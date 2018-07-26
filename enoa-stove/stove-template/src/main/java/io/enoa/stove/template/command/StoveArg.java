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
package io.enoa.stove.template.command;

import io.enoa.toolkit.value.EnoaValue;

public class StoveArg {

  private String name;
  private String value;

  public StoveArg(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String name() {
    return this.name;
  }

  public EnoaValue value() {
    return EnoaValue.with(this.value);
  }

  @Override
  public String toString() {
    return "StoveArg{" +
      "name='" + name + '\'' +
      ", code='" + value + '\'' +
      '}';
  }
}
