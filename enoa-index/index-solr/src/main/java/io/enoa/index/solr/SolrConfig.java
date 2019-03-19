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
package io.enoa.index.solr;

import io.enoa.http.EoHttp;
import io.enoa.http.Http;
import io.enoa.http.HttpAuth;

public class SolrConfig {

  private final String host;
  private final EoHttp http;
  private final HttpAuth auth;
  private final boolean debug;

  private SolrConfig(Builder builder) {
    this.host = builder.host;
    this.auth = builder.auth;
    this.http = builder.http;
    this.debug = builder.debug;
  }

  public String host() {
    return host;
  }

  public HttpAuth auth() {
    return auth;
  }

  public boolean debug() {
    return debug;
  }

  public Http http() {
    Http http = this.http.http();
    if (this.auth != null)
      http.auth(this.auth);
    return http;
  }

  public static class Builder {
    private String host;
    private EoHttp http;
    private HttpAuth auth;
    private boolean debug;

    public Builder() {
      this.debug = Boolean.FALSE;
      this.http = EoHttp.def();
    }

    public SolrConfig build() {
      return new SolrConfig(this);
    }

    public Builder http(EoHttp http) {
      this.http = http;
      return this;
    }

    public Builder host(String host) {
      this.host = host;
      return this;
    }

    public Builder auth(HttpAuth auth) {
      this.auth = auth;
      return this;
    }

    public Builder debug(boolean debug) {
      this.debug = debug;
      return this;
    }
  }

}
