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
package io.enoa.docker.dqp;

import io.enoa.http.protocol.HttpHeader;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.map.Nv;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DQH {

  private Set<Nv> nvs;

  private static DQH EMPTY = new DQH();

  public static DQH empty() {
    return EMPTY;
  }

  public static DQH create() {
    return new DQH();
  }

  public DQH() {
    this.nvs = new HashSet<>();
  }

  public DQH add(String name, Object value) {
    this.nvs.add(Nv.create(name, value));
    return this;
  }

  public DQH addIf(String name, Object value) {
    if (value == null)
      return this;
    return this.add(name, value);
  }

  public DQH add(Collection<Nv> nvs) {
    if (Is.empty(nvs))
      return this;
    this.nvs.addAll(nvs);
    return this;
  }

  public DQH addIf(Collection<Nv> nvs) {
    return this.add(nvs);
  }

  public DQH add(DQH dqh) {
    if (dqh == null)
      return this;
    if (Is.empty(dqh.nvs))
      return this;
    return this.add(dqh.nvs);
  }

  public DQH addIf(DQH dqh) {
    return this.add(dqh);
  }

  public DQH clear() {
    CollectionKit.clear(this.nvs);
    return this;
  }

  public Set<HttpHeader> headers() {
    if (Is.empty(this.nvs))
      return Collections.emptySet();
    return this.nvs.stream().map(nv -> new HttpHeader(nv.name(), nv.value().string())).collect(Collectors.toSet());
  }

}
