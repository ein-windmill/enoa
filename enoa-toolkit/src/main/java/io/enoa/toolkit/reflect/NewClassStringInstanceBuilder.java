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
package io.enoa.toolkit.reflect;

import io.enoa.toolkit.thr.EoReflectException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class NewClassStringInstanceBuilder {

  private String czn;
  private Class<?>[] paras;
  private Object[] args;
  private Class<?> interfaceFor;

  NewClassStringInstanceBuilder(String czn) {
    this.czn = czn;
  }

  public NewClassStringInstanceBuilder constructor(Class<?>... paras) {
    this.paras = paras;
    return this;
  }

  public NewClassStringInstanceBuilder instance(Object... args) {
    this.args = args;
    return this;
  }

  public NewClassStringInstanceBuilder interfaceFor(Class<?> clazz) {
    this.interfaceFor = clazz;
    return this;
  }

  public <T> Class<T> clazz() {
    Class<T> clazz;
    try {
      clazz = (Class<T>) Class.forName(this.czn);
    } catch (ClassNotFoundException e) {
      throw new EoReflectException(e.getMessage(), e);
    }
    if (clazz.isInterface()) {
      if (this.interfaceFor == null) {
        if (Map.class.isAssignableFrom(clazz))
          this.interfaceFor = HashMap.class;
        if (Collection.class.isAssignableFrom(clazz))
          this.interfaceFor = ArrayList.class;
      }
      clazz = (Class<T>) this.interfaceFor;
    }
    return clazz;
  }

  public <T> T build() {
    return ReflectKit.newInstance(this.<T>clazz())
      .constructor(this.paras)
      .instance(this.args)
      .build();
  }

}
