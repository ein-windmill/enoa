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
package io.enoa.docker.ret.docker.system;

import io.enoa.docker.ret.AbstractDRRet;

import java.util.Date;

public class EYVersion extends AbstractDRRet {

  private final String version;
  private final String os;
  private final String kernelversion;
  private final String goversion;
  private final String gitcommit;
  private final String arch;
  private final String apiversion;
  private final String minapiversion;
  private final Date buildtime;
  private final Boolean experimental;

  public EYVersion(Builder builder) {
    this.version = builder.version;
    this.os = builder.os;
    this.kernelversion = builder.kernelversion;
    this.goversion = builder.goversion;
    this.gitcommit = builder.gitcommit;
    this.arch = builder.arch;
    this.apiversion = builder.apiversion;
    this.minapiversion = builder.minapiversion;
    this.buildtime = builder.buildtime;
    this.experimental = builder.experimental;
  }

  public String version() {
    return this.version;
  }

  public String os() {
    return this.os;
  }

  public String kernelversion() {
    return this.kernelversion;
  }

  public String goversion() {
    return this.goversion;
  }

  public String gitcommit() {
    return this.gitcommit;
  }

  public String arch() {
    return this.arch;
  }

  public String apiversion() {
    return this.apiversion;
  }

  public String minapiversion() {
    return this.minapiversion;
  }

  public Date buildtime() {
    return this.buildtime;
  }

  public Boolean experimental() {
    return this.experimental;
  }

  public static class Builder {

    private String version;
    private String os;
    private String kernelversion;
    private String goversion;
    private String gitcommit;
    private String arch;
    private String apiversion;
    private String minapiversion;
    private Date buildtime;
    private Boolean experimental;

    public EYVersion build() {
      return new EYVersion(this);
    }

    public Builder version(String version) {
      this.version = version;
      return this;
    }

    public Builder os(String os) {
      this.os = os;
      return this;
    }

    public Builder kernelversion(String kernelversion) {
      this.kernelversion = kernelversion;
      return this;
    }

    public Builder goversion(String goversion) {
      this.goversion = goversion;
      return this;
    }

    public Builder gitcommit(String gitcommit) {
      this.gitcommit = gitcommit;
      return this;
    }

    public Builder arch(String arch) {
      this.arch = arch;
      return this;
    }

    public Builder apiversion(String apiversion) {
      this.apiversion = apiversion;
      return this;
    }

    public Builder minapiversion(String minapiversion) {
      this.minapiversion = minapiversion;
      return this;
    }

    public Builder buildtime(Date buildtime) {
      this.buildtime = buildtime;
      return this;
    }

    public Builder experimental(Boolean experimental) {
      this.experimental = experimental;
      return this;
    }
  }

}
