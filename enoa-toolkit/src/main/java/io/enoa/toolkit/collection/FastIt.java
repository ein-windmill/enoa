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
package io.enoa.toolkit.collection;

import io.enoa.toolkit.value.EnoaValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

public interface FastIt<E, S> extends Iterable<E> {

//  default T get(int ix) {
//    Iterator<T> iterator = this.iterator();
//    int i = 0;
//    while (iterator.hasNext()) {
//      T next = iterator.next();
//      if (i == ix)
//        return next;
//    }
////    throw new IndexOutOfBoundsException("Index " + ix + " out-of-bounds length ");
//    return null;
//  }

  E get(int ix);

  S add(E item);

  S addAll(Collection<? extends E> collection);

  S remove(int ix);

  S remove(E value);

  int size();

  default EnoaValue value(int ix) {
    return EnoaValue.with(this.get(ix));
  }

//  default <T> T as(int ix, T def) {
//    return this.value(ix).as(def);
//  }
//
//  default <T> T as(int ix) {
//    return this.value(ix).as();
//  }

  default String string(int ix, String def) {
    return this.value(ix).string(def);
  }

  default String string(int ix) {
    return this.value(ix).string();
  }

  default Integer integer(int ix, Integer def) {
    return this.value(ix).integer(def);
  }

  default Integer integer(int ix) {
    return this.value(ix).integer();
  }

  default Long longer(int ix, Long def) {
    return this.value(ix).longer(def);
  }

  default Long longer(int ix) {
    return this.value(ix).longer();
  }

  default Number number(int ix, Number def) {
    return this.value(ix).number(def);
  }

  default Number number(int ix) {
    return this.value(ix).number();
  }

  default Short shorter(int ix) {
    return this.value(ix).shorter();
  }

  default Short shorter(int ix, Short def) {
    return this.value(ix).shorter(def);
  }

  default BigInteger bigint(int ix) {
    return this.value(ix).bigint();
  }

  default BigInteger bigint(int ix, BigInteger def) {
    return this.value(ix).bigint(def);
  }

  default BigDecimal bigdecimal(int ix) {
    return this.value(ix).bigdecimal();
  }

  default BigDecimal bigdecimal(int ix, BigDecimal def) {
    return this.value(ix).bigdecimal(def);
  }

  default Double doubler(int ix) {
    return this.value(ix).doubler();
  }

  default Double doubler(int ix, Double def) {
    return this.value(ix).doubler(def);
  }

  default Float floater(int ix) {
    return this.value(ix).floater();
  }

  default Float floater(int ix, Float def) {
    return this.value(ix).floater(def);
  }

  default Boolean bool(int ix, Boolean def) {
    return this.value(ix).bool(def);
  }

  default Boolean bool(int ix) {
    return this.value(ix).bool();
  }

  default Date date(int ix, String format, Date def) {
    return this.value(ix).date(format, def);
  }

  default Date date(int ix, String format) {
    return this.value(ix).date(format);
  }

  default Date date(int ix) {
    return this.value(ix).date();
  }

  default Timestamp timestamp(int ix, String format, Timestamp def) {
    return this.value(ix).timestamp(format, def);
  }

  default Timestamp timestamp(int ix, String format) {
    return this.value(ix).timestamp(format);
  }

  default Timestamp timestamp(int ix) {
    return this.value(ix).timestamp();
  }

  default boolean exists(int ix) {
    return ix >= 0 && ix <= this.size() - 1;
  }

}
