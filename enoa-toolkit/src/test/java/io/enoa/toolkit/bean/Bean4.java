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
package io.enoa.toolkit.bean;

public class Bean4<T> {

  private String id;
  private T day;

  public String id() {
    return id;
  }

  public Bean4<T> id(String id) {
    this.id = id;
    return this;
  }

  public T day() {
    return day;
  }

  public Bean4<T> day(T day) {
    this.day = day;
    return this;
  }
}
