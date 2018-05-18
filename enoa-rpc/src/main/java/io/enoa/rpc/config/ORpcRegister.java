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
package io.enoa.rpc.config;

import io.enoa.http.EoUrl;
import io.enoa.http.protocol.HttpHeader;
import io.enoa.rpc.http._HttpRpcRegister;

import java.util.Collections;
import java.util.Set;

public class ORpcRegister {

  private static class Holder {
    private static final ORpcRegister INSTANCE = new ORpcRegister();
  }

  private ORpcRegister() {

  }

  static ORpcRegister instance() {
    return Holder.INSTANCE;
  }

  public ORpcRegister http(String name, String host) {
    return this.http(name, EoUrl.with(host));
  }

  public ORpcRegister http(String name, EoUrl host) {
    return this.http(name, host, Collections.emptySet());
  }

  public ORpcRegister http(String name, String host, Set<HttpHeader> headers) {
    return this.http(name, EoUrl.with(host), headers);
  }

  public ORpcRegister http(String name, EoUrl host, Set<HttpHeader> headers) {
    _HttpRpcRegister.instance().reg(name, host, headers);
    return this;
  }

}
