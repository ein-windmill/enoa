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

public class Bean96 {

  private String id;
  private String mark;
  private Bean90 country;

  public String id() {
    return id;
  }

  public Bean96 id(String id) {
    this.id = id;
    return this;
  }

  public String mark() {
    return mark;
  }

  public Bean96 mark(String mark) {
    this.mark = mark;
    return this;
  }

  public Bean90 country() {
    return country;
  }

  public Bean96 country(Bean90 country) {
    this.country = country;
    return this;
  }
}