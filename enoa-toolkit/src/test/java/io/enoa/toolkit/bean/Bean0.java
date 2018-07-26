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
package io.enoa.toolkit.bean;

import java.util.List;
import java.util.Map;

public class Bean0<T, K> {

  private String name;
  private List<T> things;
  private Map<String, Bean2<K>> rpn;

  public String name() {
    return name;
  }

  public Bean0<T, K> name(String name) {
    this.name = name;
    return this;
  }

  public List<T> things() {
    return things;
  }

  public Bean0<T, K> things(List<T> things) {
    this.things = things;
    return this;
  }

  public Map<String, Bean2<K>> rpn() {
    return rpn;
  }

  public Bean0<T, K> rpn(Map<String, Bean2<K>> rpn) {
    this.rpn = rpn;
    return this;
  }
}
