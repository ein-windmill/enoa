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
package io.enoa.rpc.epm;

import io.enoa.http.protocol.HttpResponseBody;
import io.enoa.rpc.Rpc;
import io.enoa.rpc.parser.IRpcParser;

import java.lang.reflect.Type;

class _DefaultJsonRpcParser<T> implements IRpcParser<T> {
  @Override
  public T parse(HttpResponseBody body, Type type) {
    return Rpc.epm().json().parse(body.string(), type);
  }
}
