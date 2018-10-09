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

import java.io.Serializable;

public class Nv implements Serializable {

  private String name;
  private Object value;

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

  public String name() {
    return this.name;
  }

  public Nv name(String name) {
    this.name = name;
    return this;
  }

  public EnoaValue value() {
    return EnoaValue.with(this.value);
  }

  public Nv value(Object value) {
    this.value = value;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Nv nv = (Nv) o;

    if (!name.equals(nv.name)) return false;
    return value != null ? value.equals(nv.value) : nv.value == null;
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }
}
