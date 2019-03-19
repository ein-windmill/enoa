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

import io.enoa.docker.thr.RegistryException;
import io.enoa.http.EoHttp;
import io.enoa.json.EnoaJson;
import io.enoa.json.EoJsonFactory;
import io.enoa.promise.Promise;
import io.enoa.toolkit.eo.tip.EnoaTipKit;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;

public class RegistryConfig implements Serializable {

  private final String host;
  private final Integer port;
  private final Boolean ssl;
  private final Boolean debug;
  private final Boolean dockerhub;
  private final EoHttp http;
  private final String version;
  private final EnoaJson json;
  private final ExecutorService executor;

  public RegistryConfig(Builder builder) {
    this.host = builder.host;
    this.port = builder.port;
    this.ssl = builder.ssl;
    this.debug = builder.debug;
    this.http = builder.http;
    this.dockerhub = builder.dockerhub;
    this.version = builder.version;
    this.json = builder.json.json();
    this.executor = builder.executor;
  }

  public ExecutorService executor() {
    return executor;
  }

  public String version() {
    return version;
  }

  public EnoaJson json() {
    return json;
  }

  public Boolean dockerhub() {
    return dockerhub;
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
    private Boolean dockerhub;
    private EoHttp http;
    private String version;
    private EoJsonFactory json;
    private ExecutorService executor;

    public Builder() {
      this.ssl = Boolean.FALSE;
      this.debug = Boolean.FALSE;
      this.dockerhub = Boolean.FALSE;
      this.http = EoHttp.def();
      this.version = "v2";
      this.executor = Promise.builder().executor().enqueue("Registry Dispatcher");
    }

    public RegistryConfig build() {
      if (this.json == null)
        throw new RegistryException(EnoaTipKit.message("eo.tip.docker.no_json"));
      return new RegistryConfig(this);
    }

    public Builder executor(ExecutorService executor) {
      this.executor = executor;
      return this;
    }

    public Builder dockerhub(Boolean dockerhub) {
      this.dockerhub = dockerhub;
      return this;
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

    public Builder json(EoJsonFactory json) {
      this.json = json;
      return this;
    }
  }

}
