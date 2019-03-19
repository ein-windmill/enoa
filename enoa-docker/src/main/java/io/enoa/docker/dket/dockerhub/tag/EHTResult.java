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
package io.enoa.docker.dket.dockerhub.tag;

import io.enoa.docker.dket.AbstractDRRet;

import java.util.Date;
import java.util.List;

public class EHTResult extends AbstractDRRet {

  private String name;
  private Long fullsize;
  private Integer id;
  private Integer repository;
  private Integer creator;
  private Integer lastupdater;
  private Date lastupdated;
  private String imageid;
  private Boolean v2;
  private List<EHImage> images;

  public EHTResult(Builder builder) {
    this.name = builder.name;
    this.fullsize = builder.fullsize;
    this.id = builder.id;
    this.repository = builder.repository;
    this.creator = builder.creator;
    this.lastupdater = builder.lastupdater;
    this.lastupdated = builder.lastupdated;
    this.imageid = builder.imageid;
    this.v2 = builder.v2;
    this.images = builder.images;
  }

  public String name() {
    return this.name;
  }

  public Long fullsize() {
    return this.fullsize;
  }

  public Integer id() {
    return this.id;
  }

  public Integer repository() {
    return this.repository;
  }

  public Integer creator() {
    return this.creator;
  }

  public Integer lastupdater() {
    return this.lastupdater;
  }

  public Date lastupdated() {
    return this.lastupdated;
  }

  public String imageid() {
    return this.imageid;
  }

  public Boolean v2() {
    return this.v2;
  }

  public List<EHImage> images() {
    return this.images;
  }

  public static class Builder {
    private String name;
    private Long fullsize;
    private Integer id;
    private Integer repository;
    private Integer creator;
    private Integer lastupdater;
    private Date lastupdated;
    private String imageid;
    private Boolean v2;
    private List<EHImage> images;

    public EHTResult build() {
      return new EHTResult(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder fullsize(Long fullsize) {
      this.fullsize = fullsize;
      return this;
    }

    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Builder repository(Integer repository) {
      this.repository = repository;
      return this;
    }

    public Builder creator(Integer creator) {
      this.creator = creator;
      return this;
    }

    public Builder lastupdater(Integer lastupdater) {
      this.lastupdater = lastupdater;
      return this;
    }

    public Builder lastupdated(Date lastupdated) {
      this.lastupdated = lastupdated;
      return this;
    }

    public Builder imageid(String imageid) {
      this.imageid = imageid;
      return this;
    }

    public Builder v2(Boolean v2) {
      this.v2 = v2;
      return this;
    }

    public Builder images(List<EHImage> images) {
      this.images = images;
      return this;
    }
  }

}
