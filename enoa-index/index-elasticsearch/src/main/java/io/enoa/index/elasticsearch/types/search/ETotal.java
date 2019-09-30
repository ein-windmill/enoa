/*
 * Copyright 2016 ikidou
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
package io.enoa.index.elasticsearch.types.search;

public class ETotal {

  private Long value;
  private String relation;

  public Long getValue() {
    return value;
  }

  public ETotal setValue(Long value) {
    this.value = value;
    return this;
  }

  public String getRelation() {
    return relation;
  }

  public ETotal setRelation(String relation) {
    this.relation = relation;
    return this;
  }

  @Override
  public String toString() {
    return "ETotal{" +
      "value=" + value +
      ", relation='" + relation + '\'' +
      '}';
  }
}
