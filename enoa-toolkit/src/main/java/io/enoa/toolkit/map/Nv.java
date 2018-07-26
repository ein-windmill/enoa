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

public class Nv extends HashMap<String, Object> {

//  private String name;
//  private T value;

  private Nv() {
  }

  private Nv(String name, Object value) {
    this.name(name)
      .value(value);
  }

  public static Nv create() {
    return new Nv();
  }

  public static Nv create(String name, Object value) {
    return new Nv(name, value);
  }

  private EnoaValue get(String key) {
    return EnoaValue.with(super.get(key));
  }

  public String name() {
    return this.get("name").as();
  }

  public Nv name(String name) {
    super.put("name", name);
    return this;
  }

  public EnoaValue value() {
    return this.get("value");
  }

  public Nv value(Object value) {
    super.put("value", value);
    return this;
  }

//  @Override
//  public int hashCode() {
//    int result = 17;
//    result = this.name == null ? 0 : 31 * result + this.name.hashCode();
//    result = this.name == null ? 0 : 31 * result + this.value.hashCode();
//    return result;
//  }
//
//  @Override
//  public boolean equals(Object obj) {
//    if (!(obj instanceof Nv))
//      return false;
//    if (this == obj)
//      return true;
//    Nv that = (Nv) obj;
//    return this.name == null ? that.name == null : this.name.equals(that.name)
//      && this.value == null ? that.value == null : this.value.equals(that.value);
//  }
}
