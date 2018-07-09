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

import io.enoa.toolkit.collection.FastIt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

abstract class _Ja<E> implements Iterable<E>, Toa, FastIt<E, Ja> {

  Collection<E> collection;

  _Ja() {
    this(new ArrayList<>());
  }

  _Ja(Collection<E> collection) {
    this.collection = collection;
  }

  public Collection<E> origin() {
    return this.collection;
  }

  @Override
  public Iterator<E> iterator() {
    return this.collection.iterator();
  }

  @Override
  public Ja add(E item) {
    this.collection.add(item);
    return (Ja) this;
  }

  @Override
  public Ja addAll(Collection<? extends E> other) {
    this.collection.addAll(other);
    return (Ja) this;
  }

  @Override
  public E get(int ix) {
    if (this.collection instanceof List)
      return (E) ((List) this.collection).get(ix);
    if (ix < 0)
      throw new IndexOutOfBoundsException("Index cannot be less than 0. => " + ix);
    int i = 0;
    for (E next : this.collection) {
      if (i == ix)
        return next;
    }
    throw new IndexOutOfBoundsException("Index " + ix + " out-of-bounds for length " + this.collection.size());
  }

  @Override
  public Ja remove(int ix) {
    if (ix < 0)
      throw new IndexOutOfBoundsException("Index cannot be less than 0. => " + ix);

    Iterator<E> iterator = this.collection.iterator();
    int i = 0;
    while (iterator.hasNext()) {
      if (i != ix)
        continue;
      iterator.remove();
      return (Ja) this;
    }
    throw new IndexOutOfBoundsException("Index " + ix + " out-of-bounds for length " + this.collection.size());
  }

  @Override
  public Ja remove(E object) {
    this.collection.removeIf(next -> next == object);
    return (Ja) this;
  }

  @Override
  public int size() {
    return this.collection.size();
  }


}
