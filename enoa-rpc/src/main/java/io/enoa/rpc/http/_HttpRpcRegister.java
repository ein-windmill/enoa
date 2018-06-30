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

import io.enoa.http.EoUrl;
import io.enoa.http.protocol.HttpHeader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class _HttpRpcRegister {

  private static class Holder {
    private static final _HttpRpcRegister INSTANCE = new _HttpRpcRegister();
  }

  public static _HttpRpcRegister instance() {
    return Holder.INSTANCE;
  }

  private Map<String, EoUrl> target0 = new HashMap<>();
  private Map<String, Set<HttpHeader>> target1 = new HashMap<>();

  EoUrl url(String name) {
    return this.target0.get(name);
  }

  Set<HttpHeader> headers(String name) {
    return this.target1.get(name);
  }

  public _HttpRpcRegister reg(String name, EoUrl url, Set<HttpHeader> headers) {
    this.target0.put(name, url);
    this.target1.put(name, headers == null ? Collections.emptySet() : headers);
    return this;
  }


}
