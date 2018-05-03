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
package io.enoa.http.provider.httphelper.async;

import io.enoa.http.protocol.HttpPromise;
import io.enoa.promise.*;

import java.util.ArrayList;
import java.util.List;

class HttpHelperPromise implements HttpPromise {

  List<PromiseResult> oks;
  List<PromiseResult> errors;
  List<PromiseThen> thens;
  List<PromiseCapture> captures;
  List<PromiseValid> valids;
  List<PromiseResult> execs;
  List<PromiseResult> fails;
  PromiseVoid always;

  @Override
  public HttpPromise then(PromiseThen then) {
    if (this.thens == null)
      this.thens = new ArrayList<>();
    this.thens.add(then);
    return this;
  }

  @Override
  public HttpPromise ok(PromiseResult ok) {
    if (this.oks == null)
      this.oks = new ArrayList<>();
    this.oks.add(ok);
    return this;
  }

  @Override
  public HttpPromise error(PromiseResult error) {
    if (this.errors == null)
      this.errors = new ArrayList<>();
    this.errors.add(error);
    return this;
  }

  @Override
  public <T> HttpPromise valid(PromiseValid<T> valid) {
    if (this.valids == null)
      this.valids = new ArrayList<>();
    this.valids.add(valid);
    return this;
  }

  @Override
  public <T> HttpPromise execute(PromiseResult<T> exec) {
    if (this.execs == null)
      this.execs = new ArrayList<>();
    this.execs.add(exec);
    return this;
  }

  @Override
  public <T> HttpPromise fail(PromiseResult<T> fail) {
    if (this.fails == null)
      this.fails = new ArrayList<>();
    this.fails.add(fail);
    return this;
  }

  @Override
  public HttpPromise capture(PromiseCapture capture) {
    if (this.captures == null)
      this.captures = new ArrayList<>();
    this.captures.add(capture);
    return this;
  }

  @Override
  public void always(PromiseVoid always) {
    this.always = always;
  }

}
