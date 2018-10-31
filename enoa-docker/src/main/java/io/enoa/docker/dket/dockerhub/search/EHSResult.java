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
package io.enoa.docker.dket.dockerhub.search;

import io.enoa.docker.dket.AbstractDRRet;

public class EHSResult extends AbstractDRRet {

  private final String reponame;
  private final String shortdescription;
  private final Integer starcount;
  private final Long pullcount;
  private final String repoowner;
  private final Boolean isautomated;
  private final Boolean isofficial;

  public EHSResult(Builder builder) {
    this.reponame = builder.reponame;
    this.shortdescription = builder.shortdescription;
    this.starcount = builder.starcount;
    this.pullcount = builder.pullcount;
    this.repoowner = builder.repoowner;
    this.isautomated = builder.isautomated;
    this.isofficial = builder.isofficial;
  }

  public String reponame() {
    return this.reponame;
  }

  public String shortdescription() {
    return this.shortdescription;
  }

  public Integer starcount() {
    return this.starcount;
  }

  public Long pullcount() {
    return this.pullcount;
  }

  public String repoowner() {
    return this.repoowner;
  }

  public Boolean isautomated() {
    return this.isautomated;
  }

  public Boolean isofficial() {
    return this.isofficial;
  }

  public static class Builder {

    private String reponame;
    private String shortdescription;
    private Integer starcount;
    private Long pullcount;
    private String repoowner;
    private Boolean isautomated;
    private Boolean isofficial;

    public EHSResult build() {
      return new EHSResult(this);
    }

    public Builder reponame(String reponame) {
      this.reponame = reponame;
      return this;
    }

    public Builder shortdescription(String shortdescription) {
      this.shortdescription = shortdescription;
      return this;
    }

    public Builder starcount(Integer starcount) {
      this.starcount = starcount;
      return this;
    }

    public Builder pullcount(Long pullcount) {
      this.pullcount = pullcount;
      return this;
    }

    public Builder repoowner(String repoowner) {
      this.repoowner = repoowner;
      return this;
    }

    public Builder isautomated(Boolean isautomated) {
      this.isautomated = isautomated;
      return this;
    }

    public Builder isofficial(Boolean isofficial) {
      this.isofficial = isofficial;
      return this;
    }
  }
}
