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

import java.util.Date;

public class EHHistory extends AbstractDRRet {

  private final Integer id;
  private final Integer status;
  private final Date createddate;
  private final Date lastupdated;
  private final String buildcode;
  private final String dockertagname;
  private final String cause;

  public EHHistory(Builder builder) {
    this.id = builder.id;
    this.status = builder.status;
    this.createddate = builder.createddate;
    this.lastupdated = builder.lastupdated;
    this.buildcode = builder.buildcode;
    this.dockertagname = builder.dockertagname;
    this.cause = builder.cause;
  }

  public Integer id() {
    return this.id;
  }

  public Integer status() {
    return this.status;
  }

  public Date createddate() {
    return this.createddate;
  }

  public Date lastupdated() {
    return this.lastupdated;
  }

  public String buildcode() {
    return this.buildcode;
  }

  public String dockertagname() {
    return this.dockertagname;
  }

  public String cause() {
    return this.cause;
  }

  public static class Builder {

    private Integer id;
    private Integer status;
    private Date createddate;
    private Date lastupdated;
    private String buildcode;
    private String dockertagname;
    private String cause;

    public EHHistory build() {
      return new EHHistory(this);
    }

    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Builder status(Integer status) {
      this.status = status;
      return this;
    }

    public Builder createddate(Date createddate) {
      this.createddate = createddate;
      return this;
    }

    public Builder lastupdated(Date lastupdated) {
      this.lastupdated = lastupdated;
      return this;
    }

    public Builder buildcode(String buildcode) {
      this.buildcode = buildcode;
      return this;
    }

    public Builder dockertagname(String dockertagname) {
      this.dockertagname = dockertagname;
      return this;
    }

    public Builder cause(String cause) {
      this.cause = cause;
      return this;
    }
  }
}
