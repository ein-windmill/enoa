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
package io.enoa.gateway;

import io.enoa.gateway.auth.GatewayAuth;
import io.enoa.gateway.data.GMapping;
import io.enoa.log.EoLogFactory;
import io.enoa.repeater.EoxConfig;
import io.enoa.repeater.factory.server.RepeaterServerFactory;
import io.enoa.repeater.http.Header;

import java.util.Collection;

public interface Gateway {

  static Gateway createServer() {
    return new _EnoaGateway();
  }

  Gateway provider(RepeaterServerFactory provider);

  Gateway log(EoLogFactory logFactory);

  Gateway capture(GErrorRenderFactory capture);

  default Gateway ssl() {
    return this.ssl(Boolean.TRUE);
  }

  Gateway ssl(boolean ssl);

  default Gateway interceptoption() {
    return this.interceptoption(Boolean.TRUE);
  }

  Gateway interceptoption(boolean intercept);

  Gateway cros();

  Gateway cros(Header header);

  Gateway cros(Header... headers);

  Gateway cros(Collection<Header> headers);

  Gateway config(EoxConfig config);

  Gateway auth(GatewayAuth auth);

  default Gateway auth(GatewayAuth auth, String uri) {
    return this.auth(auth, new String[]{uri});
  }

  Gateway auth(GatewayAuth auth, String... uris);

  Gateway noauth(String uri);

  Gateway noauth(String... uris);

  Gateway noauth(Collection<String> uris);

  Gateway mapping(String name, String source, String dest, GatewayAuth auth);

  Gateway mapping(String source, String dest, GatewayAuth auth);

  Gateway mapping(String name, String source, String dest);

  Gateway mapping(String source, String dest);

  Gateway mapping(GMapping mapping);

  Gateway mapping(GMapping[] mappings);

  Gateway mapping(Collection<GMapping> mappings);

  void listen(String hostname, int port);

  void listen(int port);

  void listen();

}
