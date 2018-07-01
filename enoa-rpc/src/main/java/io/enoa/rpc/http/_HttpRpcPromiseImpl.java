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
package io.enoa.rpc.http;

import io.enoa.http.protocol.HttpPromise;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.promise.arg.PromiseArg;
import io.enoa.promise.arg.PromiseCapture;
import io.enoa.promise.arg.PromiseVoid;
import io.enoa.rpc.parser.IRpcParser;

import java.lang.reflect.Type;

class _HttpRpcPromiseImpl<P> implements HttpRpcPromise<P> {

  private HttpPromise promise;
  private Type type;
  private IRpcParser<P> handler;

  _HttpRpcPromiseImpl(HttpPromise promise, Type type) {
    this.promise = promise;
    this.type = type;
  }

  _HttpRpcPromiseImpl(HttpPromise promise, Type type, IRpcParser<P> handler) {
    this.promise = promise;
    this.type = type;
    this.handler = handler;
  }

  @Override
  public HttpRpcPromise<P> capture(PromiseCapture capture) {
    this.promise.capture(capture);
    return this;
  }

  @Override
  public void always(PromiseVoid always) {
    this.promise.always(always);
  }

  @Override
  public HttpRpcPromise<P> done(PromiseArg<HttpRpcResult<P>> done) {
    this.promise.<HttpResponse>execute(ret0 -> {
      if (this.handler != null) {
        done.execute(new _HttpRpcResultImpl<>(ret0, this.type, this.handler));
        return;
      }
      done.execute(new _HttpRpcResultImpl<>(ret0, this.type));
    });
    return null;
  }
}
