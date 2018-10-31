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
package io.enoa.docker.dket.dockerhub.build;

import io.enoa.docker.dket.AbstractDRRet;

import java.util.List;

public class EHAutobuild extends AbstractDRRet {

  private final Integer repository;
  private final String buildname;
  private final String provider;
  private final String sourceurl;
  private final String dockerurl;
  private final String repoweburl;
  private final String repotype;
  private final Boolean active;
  private final String repoid;
  private final List<EHBuildTag> buildtags;
  private final String deploykey;
  private final Integer hookid;
  private final String webhookid;

  public EHAutobuild(Builder builder) {
    this.repository = builder.repository;
    this.buildname = builder.buildname;
    this.provider = builder.provider;
    this.sourceurl = builder.sourceurl;
    this.dockerurl = builder.dockerurl;
    this.repoweburl = builder.repoweburl;
    this.repotype = builder.repotype;
    this.active = builder.active;
    this.repoid = builder.repoid;
    this.buildtags = builder.buildtags;
    this.deploykey = builder.deploykey;
    this.hookid = builder.hookid;
    this.webhookid = builder.webhookid;
  }

  public Integer repository() {
    return this.repository;
  }

  public String buildname() {
    return this.buildname;
  }

  public String provider() {
    return this.provider;
  }

  public String sourceurl() {
    return this.sourceurl;
  }

  public String dockerurl() {
    return this.dockerurl;
  }

  public String repoweburl() {
    return this.repoweburl;
  }

  public String repotype() {
    return this.repotype;
  }

  public Boolean active() {
    return this.active;
  }

  public String repoid() {
    return this.repoid;
  }

  public List<EHBuildTag> buildtags() {
    return this.buildtags;
  }

  public String deploykey() {
    return this.deploykey;
  }

  public Integer hookid() {
    return this.hookid;
  }

  public String webhookid() {
    return this.webhookid;
  }

  public static class Builder {
    private Integer repository;
    private String buildname;
    private String provider;
    private String sourceurl;
    private String dockerurl;
    private String repoweburl;
    private String repotype;
    private Boolean active;
    private String repoid;
    private List<EHBuildTag> buildtags;
    private String deploykey;
    private Integer hookid;
    private String webhookid;

    public EHAutobuild build() {
      return new EHAutobuild(this);
    }

    public Builder repository(Integer repository) {
      this.repository = repository;
      return this;
    }

    public Builder buildname(String buildname) {
      this.buildname = buildname;
      return this;
    }

    public Builder provider(String provider) {
      this.provider = provider;
      return this;
    }

    public Builder sourceurl(String sourceurl) {
      this.sourceurl = sourceurl;
      return this;
    }

    public Builder dockerurl(String dockerurl) {
      this.dockerurl = dockerurl;
      return this;
    }

    public Builder repoweburl(String repoweburl) {
      this.repoweburl = repoweburl;
      return this;
    }

    public Builder repotype(String repotype) {
      this.repotype = repotype;
      return this;
    }

    public Builder active(Boolean active) {
      this.active = active;
      return this;
    }

    public Builder repoid(String repoid) {
      this.repoid = repoid;
      return this;
    }

    public Builder buildtags(List<EHBuildTag> buildtags) {
      this.buildtags = buildtags;
      return this;
    }

    public Builder deploykey(String deploykey) {
      this.deploykey = deploykey;
      return this;
    }

    public Builder hookid(Integer hookid) {
      this.hookid = hookid;
      return this;
    }

    public Builder webhookid(String webhookid) {
      this.webhookid = webhookid;
      return this;
    }
  }
}
