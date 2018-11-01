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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * OKv (Ordered Key Value)
 * <p>
 * OKv 与 Kv 的唯一区别在于 OKv 继承自 LinkedHashMap，而 Kv 继承自 HashMap
 * 所以对 OKv 中的数据进行迭代输出的次序与数据插入的先后次序一致
 * <p>
 * Example：
 * OKv para = OKv.by("id", 123);
 * User user = user.findFirst(getSqlPara("find", para));
 */
@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class OKv extends LinkedHashMap<String, Object> implements FastKv<OKv> {

  private boolean skipcase;

  public OKv() {
    super();
  }

  public OKv(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public OKv(int initialCapacity) {
    super(initialCapacity);
  }

  public OKv(int initialCapacity, float loadFactor, boolean accessOrder) {
    super(initialCapacity, loadFactor, accessOrder);
  }

  public static OKv by(String key, Object value) {
    return new OKv().set(key, value);
  }

  public static OKv by(Map<String, ?> map) {
    return new OKv().set(map);
  }

  public static OKv create() {
    return new OKv();
  }

  public static OKv create(int initialCapacity, float loadFactor) {
    return new OKv(initialCapacity, loadFactor);
  }

  public static OKv create(int initialCapacity) {
    return new OKv(initialCapacity);
  }

  public static OKv create(int initialCapacity, float loadFactor, boolean accessOrder) {
    return new OKv(initialCapacity, loadFactor, accessOrder);
  }

  public Kv kv() {
    return Kv.by(this);
  }

  public OKv skipcase() {
    return this.skipcase(Boolean.TRUE);
  }

  public OKv skipcase(boolean skip) {
    this.skipcase = skip;
    return this;
  }

  @Override
  public Object get(Object key) {
    if (!this.skipcase)
      return super.get(key);

    Optional<String> first = this.keySet().stream()
      .filter(k -> k.equalsIgnoreCase(key.toString()))
      .findFirst();
    return first.map(super::get).orElse(null);
  }
}
