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
package io.enoa.docker.command.registry.origin;

import io.enoa.docker.RegistryConfig;
import io.enoa.http.EoUrl;
import io.enoa.http.Http;
import io.enoa.toolkit.text.TextKit;

abstract class AbstractOriginRegistry implements OriginRegistry {

  private RegistryConfig config;

  protected AbstractOriginRegistry(RegistryConfig config) {
    this.config = config;
  }

  protected Http http(String api, String... subpath) {
    String _u = TextKit.union(this.config.ssl() ? "https://" : "http://",
      this.config.host(),
      this.config.port() == null ? "" : ":",
      this.config.port() == null ? "" : this.config.port(),
      "/");
    return this.config.http().http()
      .url(EoUrl.with(_u)
        .subpath(this.config.version())
        .subpath(api)
        .subpath(subpath));
  }

  protected RegistryConfig config() {
    return this.config;
  }

}
