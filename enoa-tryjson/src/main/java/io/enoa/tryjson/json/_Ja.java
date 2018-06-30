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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

abstract class _Ja<E> implements Collection<E>, _Json {

  Collection<E> coll;

  _Ja() {
    this(new ArrayList<>());
  }

  _Ja(Collection<E> collection) {
    this.coll = collection;
  }

  @Override
  public int size() {
    return this.coll.size();
  }

  @Override
  public boolean isEmpty() {
    return this.coll.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return this.coll.contains(o);
  }

  @Override
  public Iterator<E> iterator() {
    return this.coll.iterator();
  }

  @Override
  public Object[] toArray() {
    return this.coll.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return this.coll.toArray(a);
  }

  @Override
  public boolean add(E e) {
    return this.coll.add(e);
  }

  @Override
  public boolean remove(Object o) {
    return this.coll.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return this.coll.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    return this.coll.addAll(c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return this.coll.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return this.coll.retainAll(c);
  }

  @Override
  public void clear() {
    this.coll.clear();
  }
}