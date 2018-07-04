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
package io.enoa.tryjson.json;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

abstract class _Jo implements Map<String, Object>, Toa {

  Map<String, Object> map;

  _Jo() {
    this(new HashMap<>());
  }

  _Jo(Map<String, Object> map) {
    this.map = map;
  }

  @Override
  public int size() {
    return this.map.size();
  }

  @Override
  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return this.map.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return this.map.containsValue(value);
  }

  @Override
  public Object get(Object key) {
    return this.map.get(key);
  }

  @Override
  public Object put(String key, Object value) {
    return this.map.put(key, value);
  }

  @Override
  public Object remove(Object key) {
    return this.map.remove(key);
  }

  @Override
  public void putAll(Map<? extends String, ?> m) {
    this.map.putAll(m);
  }

  @Override
  public void clear() {
    this.map.clear();
  }

  @Override
  public Set<String> keySet() {
    return this.map.keySet();
  }

  @Override
  public Collection<Object> values() {
    return this.map.values();
  }

  @Override
  public Set<Entry<String, Object>> entrySet() {
    return this.map.entrySet();
  }
}
