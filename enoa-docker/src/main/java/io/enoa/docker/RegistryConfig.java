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
package io.enoa.docker;

import io.enoa.http.EoHttp;

public class RegistryConfig {

  private final String host;
  private final Integer port;
  private final Boolean ssl;
  private final Boolean debug;
  private final EoHttp http;

  public RegistryConfig(Builder builder) {
    this.host = builder.host;
    this.port = builder.port;
    this.ssl = builder.ssl;
    this.debug = builder.debug;
    this.http = builder.http;
  }


  public String host() {
    return host;
  }

  public Integer port() {
    return port;
  }

  public Boolean ssl() {
    return ssl;
  }

  public Boolean debug() {
    return debug;
  }

  public EoHttp http() {
    return http;
  }

  public static class Builder {
    private String host;
    private Integer port;
    private Boolean ssl;
    private Boolean debug;
    private EoHttp http;

    public Builder() {
      this.ssl = Boolean.FALSE;
      this.debug = Boolean.FALSE;
      this.http = EoHttp.def();
    }

    public RegistryConfig build() {
      return new RegistryConfig(this);
    }

    public Builder host(String host) {
      this.host = host;
      return this;
    }

    public Builder port(Integer port) {
      this.port = port;
      return this;
    }

    public Builder ssl(Boolean ssl) {
      this.ssl = ssl;
      return this;
    }

    public Builder debug(Boolean debug) {
      this.debug = debug;
      return this;
    }

    public Builder http(EoHttp http) {
      this.http = http;
      return this;
    }
  }

}
