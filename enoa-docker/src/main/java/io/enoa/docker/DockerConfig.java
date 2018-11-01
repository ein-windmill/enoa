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

import io.enoa.docker.thr.DockerException;
import io.enoa.json.EnoaJson;
import io.enoa.json.EoJsonFactory;
import io.enoa.promise.builder.PromiseBuilder;
import io.enoa.toolkit.eo.tip.EnoaTipKit;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;

public class DockerConfig implements Serializable {

  private final String host;
  private final boolean tls;
  private final Path certpath;
  private final Path dockercfg;
  private final String version;
  private final String registryurl;
  private final String registryuser;
  private final String registryemail;
  private final boolean debug;
  private final EnoaJson json;
  private final boolean failthrow;
  private final ExecutorService executor;

  private DockerConfig(Builder builder) {
    this.host = builder.host;
    this.tls = builder.tls;
    this.certpath = builder.certpath;
    this.dockercfg = builder.dockercfg;
    this.version = builder.version;
    this.registryurl = builder.registryurl;
    this.registryuser = builder.registryuser;
    this.registryemail = builder.registryemail;
    this.debug = builder.debug;
    this.json = builder.json.json();
    this.failthrow = builder.failthrow;
    this.executor = builder.executor;
  }

  public ExecutorService executor() {
    return this.executor;
  }

  public boolean failthrow() {
    return this.failthrow;
  }

  public String host() {
    return this.host;
  }

  public boolean tls() {
    return this.tls;
  }

  public Path certpath() {
    return this.certpath;
  }

  public Path dockercfg() {
    return this.dockercfg;
  }

  public String version() {
    return this.version;
  }

  public String registryurl() {
    return this.registryurl;
  }

  public String registryuser() {
    return this.registryuser;
  }

  public String registryemail() {
    return this.registryemail;
  }

  public boolean debug() {
    return this.debug;
  }

  public EnoaJson json() {
    return this.json;
  }

  public static class Builder {

    private String host;
    private boolean tls;
    private Path certpath;
    private Path dockercfg;
    private String version;
    private String registryurl;
    private String registryuser;
    private String registryemail;
    private boolean debug;
    private EoJsonFactory json;
    private boolean failthrow;
    private ExecutorService executor;

    public Builder() {
      this.version = "v1.35";
      this.executor = PromiseBuilder.executor().enqueue("Docker Dispatcher");
    }

    public DockerConfig build() {
      if (this.json == null)
        throw new DockerException(EnoaTipKit.message("eo.tip.docker.no_json"));
      return new DockerConfig(this);
    }

    public Builder executor(ExecutorService executor) {
      this.executor = executor;
      return this;
    }

    public Builder failthrow(boolean failthrow) {
      this.failthrow = failthrow;
      return this;
    }

    public Builder json(EoJsonFactory json) {
      this.json = json;
      return this;
    }

    public Builder debug(boolean debug) {
      this.debug = debug;
      return this;
    }

    public Builder debug() {
      return this.debug(Boolean.TRUE);
    }

    public Builder host(String host) {
      this.host = host;
      return this;
    }

    public Builder tls(boolean tls) {
      this.tls = tls;
      return this;
    }

    public Builder tls() {
      return this.tls(Boolean.TRUE);
    }

    public Builder certpath(Path certpath) {
      this.certpath = certpath;
      return this;
    }

    public Builder dockercfg(Path dockercfg) {
      this.dockercfg = dockercfg;
      return this;
    }

    public Builder version(String version) {
      this.version = version;
      return this;
    }

    public Builder registryurl(String registryurl) {
      this.registryurl = registryurl;
      return this;
    }

    public Builder registryuser(String registryuser) {
      this.registryuser = registryuser;
      return this;
    }

    public Builder registryemail(String registryemail) {
      this.registryemail = registryemail;
      return this;
    }
  }


}
