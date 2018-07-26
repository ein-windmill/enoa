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
package io.enoa.toolkit.reflect;

import io.enoa.toolkit.thr.EoReflectException;

public class NewClassInstanceBuilder<T> {

  private Class<T> clazz;
  private Class<?>[] paras;
  private Object[] args;

  NewClassInstanceBuilder(Class<T> clazz) {
    this.clazz = clazz;
  }

  public NewClassInstanceBuilder<T> constructor(Class<?>... paras) {
    this.paras = paras;
    return this;
  }

  public NewClassInstanceBuilder<T> instance(Object... args) {
    this.args = args;
    return this;
  }

  public T build() {
    try {
      return this.clazz.getConstructor(this.paras).newInstance(this.args);
    } catch (Exception e) {
      throw new EoReflectException(e.getMessage(), e);
    }
  }

}
