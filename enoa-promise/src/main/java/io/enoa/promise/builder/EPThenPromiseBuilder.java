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
package io.enoa.promise.builder;

import io.enoa.promise.EoPromise;
import io.enoa.promise.ThenPromise;
import io.enoa.promise.arg.PromiseArg;
import io.enoa.promise.arg.PromiseCapture;
import io.enoa.promise.arg.PromiseThen;
import io.enoa.promise.arg.PromiseVoid;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EPThenPromiseBuilder extends EPEoPromiseBuilder {

  private EPEoPromiseBuilder oe;

  private List<PromiseThen> thens;
  private List<PromiseArg> executes;

  EPThenPromiseBuilder() {
    this.oe = new EPEoPromiseBuilder();
  }

  public List<PromiseThen> thens() {
    return this.thens == null ? Collections.emptyList() : this.thens;
  }

  public List<PromiseArg> executes() {
    return this.executes == null ? Collections.emptyList() : this.executes;
  }

  @Override
  public List<PromiseCapture> captures() {
    return this.oe.captures();
  }

  @Override
  public PromiseVoid always() {
    return this.oe.always();
  }

  @Override
  public ThenPromise build() {
    EoPromise promise = this.oe.build();
    return new ThenPromise<ThenPromise>() {
      @Override
      public <P> ThenPromise then(PromiseThen<Object, P> then) {
        if (thens == null)
          thens = new LinkedList<>();
        thens.add(then);
        return this;
      }

      @Override
      public <T> ThenPromise execute(PromiseArg<T> arg) {
        if (executes == null)
          executes = new LinkedList<>();
        executes.add(arg);
        return this;
      }

      @Override
      public ThenPromise capture(PromiseCapture capture) {
        promise.capture(capture);
        return this;
      }

      @Override
      public void always(PromiseVoid always) {
        promise.always(always);
      }
    };
  }

}
