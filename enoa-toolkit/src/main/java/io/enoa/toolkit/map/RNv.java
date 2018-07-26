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
package io.enoa.toolkit.map;

import io.enoa.toolkit.value.EnoaValue;

import java.util.HashMap;

public class RNv extends HashMap<String, Object> {

  private RNv(String name, Object value) {
    super.put("name", name);
    super.put("value", value);
  }

  public static RNv create(String name, Object value) {
    return new RNv(name, value);
  }

  private EnoaValue get(String key) {
    return EnoaValue.with(super.get(key));
  }

  public String name() {
    return this.get("name").as();
  }

  public EnoaValue value() {
    return this.get("value");
  }

}
