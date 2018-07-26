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
package io.enoa.http.provider.httphelper.async;

import io.enoa.http.protocol.HttpPromise;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.promise.EoPromise;
import io.enoa.promise.arg.*;
import io.enoa.promise.builder.EOePromiseBuilder;

import java.util.ArrayList;
import java.util.List;

class HttpHelperPromiseBuilder extends EOePromiseBuilder {

  private EOePromiseBuilder oe;


  private List<PromiseArg> oks;
  private List<PromiseArg> errors;
  private List<PromiseThen> thens;
  private List<PromiseBool> valids;
  private List<PromiseArg> execs;
  private List<PromiseArg> fails;

  public HttpHelperPromiseBuilder() {
    this.oe = new EOePromiseBuilder();
  }

  @Override
  public List<PromiseCapture> captures() {
    return this.oe.captures();
  }

  @Override
  public PromiseVoid always() {
    return this.oe.always();
  }

  public List<PromiseArg> oks() {
    return this.oks;
  }

  public List<PromiseArg> errors() {
    return this.errors;
  }

  public List<PromiseThen> thens() {
    return this.thens;
  }

  public List<PromiseBool> valids() {
    return this.valids;
  }

  public List<PromiseArg> execs() {
    return this.execs;
  }

  public List<PromiseArg> fails() {
    return this.fails;
  }

  @Override
  public HttpPromise build() {
    EoPromise promise = oe.build();
    return new HttpPromise() {
      @Override
      public HttpPromise capture(PromiseCapture capture0) {
        promise.capture(capture0);
        return this;
      }

      @Override
      public void always(PromiseVoid always0) {
        promise.always(always0);
      }

      @Override
      public HttpPromise ok(PromiseArg<HttpResponse> ok0) {
        if (oks == null)
          oks = new ArrayList<>();
        oks.add(ok0);
        return this;
      }

      @Override
      public HttpPromise error(PromiseArg<HttpResponse> error0) {
        if (errors == null)
          errors = new ArrayList<>();
        errors.add(error0);
        return this;
      }

      @Override
      public <T> HttpPromise then(PromiseThen<Object, T> then0) {
        if (thens == null)
          thens = new ArrayList<>();
        thens.add(then0);
        return this;
      }

      @Override
      public <T> HttpPromise valid(PromiseBool<T> valid0) {
        if (valids == null)
          valids = new ArrayList<>();
        valids.add(valid0);
        return this;
      }

      @Override
      public <T> HttpPromise execute(PromiseArg<T> execute0) {
        if (execs == null)
          execs = new ArrayList<>();
        execs.add(execute0);
        return this;
      }

      @Override
      public <T> HttpPromise fail(PromiseArg<T> fail0) {
        if (fails == null)
          fails = new ArrayList<>();
        fails.add(fail0);
        return this;
      }
    };
  }
}
