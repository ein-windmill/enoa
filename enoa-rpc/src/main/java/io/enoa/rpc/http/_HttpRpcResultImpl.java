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

import io.enoa.http.protocol.HttpResponse;
import io.enoa.rpc.handler.IHandler;
import io.enoa.toolkit.text.TextKit;

import java.lang.reflect.Type;

class _HttpRpcResultImpl<T> implements HttpRpcResult<T> {

  private HttpResponse response;
  private Type type;
  private IHandler<T> handler;

  _HttpRpcResultImpl(HttpResponse response, Type type) {
    this.response = response;
    this.type = type;
  }

  _HttpRpcResultImpl(HttpResponse response, Type type, IHandler<T> handler) {
    this.response = response;
    this.type = type;
    this.handler = handler;
  }

  @Override
  public HttpResponse response() {
    return this.response;
  }

  @Override
  public T result() {
    if (this.handler != null) {
      return this.handler.handle(this.response.body(), this.type);
    }
    return HttpRpcHandler.handle(this.response, this.type);
  }

  @Override
  public String toString() {
    T result = this.result();
    return TextKit.union("RESULT => ", result, "\n", "RESPONSE => ", this.response);
  }
}
