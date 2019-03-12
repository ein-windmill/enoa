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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class Kv implements FastKv<Kv> {

  private Map<String, Object> map;

  private boolean skipcase;

  public Kv(int initialCapacity, float loadFactor) {
    this.map = new HashMap<>(initialCapacity, loadFactor);
  }

  public Kv(int initialCapacity) {
    this.map = new HashMap<>(initialCapacity);
  }

  public Kv(Map<? extends String, ?> map) {
    this.map = (Map<String, Object>) map;
  }

  public Kv() {
    this(new HashMap<>());
  }

  public static Kv by(Map<String, ?> map) {
    return map == null ? null : new Kv().set(map);
  }

  public static Kv by(String key, Object value) {
    return new Kv().set(key, value);
  }

  public static Kv create() {
    return new Kv();
  }

  public static Kv create(int initialCapacity) {
    return new Kv(initialCapacity);
  }

  public static Kv create(int initialCapacity, float loadFactor) {
    return new Kv(initialCapacity, loadFactor);
  }

  public static Kv create(Map<? extends String, ?> map) {
    return new Kv(map);
  }

//  public OKv okv() {
//    return OKv.by(this);
//  }

  public Kv skipcase() {
    return this.skipcase(Boolean.TRUE);
  }

  public Kv skipcase(boolean skip) {
    this.skipcase = skip;
    return this;
  }

  @Override
  public Map<String, Object> map() {
    return this.map;
  }

  @Override
  public Object get(Object key) {
    if (!this.skipcase)
      return this.map.get(key);

    Optional<String> first = this.keySet().stream()
      .filter(k -> k.equalsIgnoreCase(key.toString()))
      .findFirst();
    return first.map(k -> this.map.get(k)).orElse(null);
  }

}
