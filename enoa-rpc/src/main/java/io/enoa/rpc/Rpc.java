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
package io.enoa.rpc;

import io.enoa.http.EoHttp;
import io.enoa.rpc.epm.EPMRpc;
import io.enoa.rpc.http.EnoaHttpRpc;

public interface Rpc {

  static EPMRpc epm() {
    return EPMRpc.instance();
  }

//  static RpcConfig config() {
//    return RpcConfig.instance();
//  }

  static TcpRpc http(String name, String api) {
    return new EnoaHttpRpc(name, api);
  }

  static TcpRpc http(String name, String api, EoHttp http) {
    return new EnoaHttpRpc(name, api, http);
  }

}
