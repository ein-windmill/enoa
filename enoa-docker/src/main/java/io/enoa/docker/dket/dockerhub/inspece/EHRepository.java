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
package io.enoa.docker.dket.dockerhub.inspece;

import io.enoa.docker.dket.AbstractDRRet;

import java.util.Date;

public class EHRepository extends AbstractDRRet {

  private final String user;
  private final String name;
  private final String namespace;
  private final String type;
  private final Integer status;
  private final String description;
  private final Boolean isprivate;
  private final Boolean isautomated;
  private final Boolean canedit;
  private final Integer starcount;
  private final Long pullcount;
  private final Date lastupdated;
  private final Boolean hasstarred;
  private final String fulldescription;
  private final String affiliation;
  private final EHPermission permission;

  public EHRepository(Builder builder) {
    this.user = builder.user;
    this.name = builder.name;
    this.namespace = builder.namespace;
    this.type = builder.type;
    this.status = builder.status;
    this.description = builder.description;
    this.isprivate = builder.isprivate;
    this.isautomated = builder.isautomated;
    this.canedit = builder.canedit;
    this.starcount = builder.starcount;
    this.pullcount = builder.pullcount;
    this.lastupdated = builder.lastupdated;
    this.hasstarred = builder.hasstarred;
    this.fulldescription = builder.fulldescription;
    this.affiliation = builder.affiliation;
    this.permission = builder.permission;
  }

  public String user() {
    return this.user;
  }

  public String name() {
    return this.name;
  }

  public String namespace() {
    return this.namespace;
  }

  public String type() {
    return this.type;
  }

  public Integer status() {
    return this.status;
  }

  public String description() {
    return this.description;
  }

  public Boolean isprivate() {
    return this.isprivate;
  }

  public Boolean isautomated() {
    return this.isautomated;
  }

  public Boolean canedit() {
    return this.canedit;
  }

  public Integer starcount() {
    return this.starcount;
  }

  public Long pullcount() {
    return this.pullcount;
  }

  public Date lastupdated() {
    return this.lastupdated;
  }

  public Boolean hasstarred() {
    return this.hasstarred;
  }

  public String fulldescription() {
    return this.fulldescription;
  }

  public String affiliation() {
    return this.affiliation;
  }

  public EHPermission permission() {
    return this.permission;
  }

  public static class Builder {

    private String user;
    private String name;
    private String namespace;
    private String type;
    private Integer status;
    private String description;
    private Boolean isprivate;
    private Boolean isautomated;
    private Boolean canedit;
    private Integer starcount;
    private Long pullcount;
    private Date lastupdated;
    private Boolean hasstarred;
    private String fulldescription;
    private String affiliation;
    private EHPermission permission;

    public EHRepository build() {
      return new EHRepository(this);
    }

    public Builder user(String user) {
      this.user = user;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder namespace(String namespace) {
      this.namespace = namespace;
      return this;
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder status(Integer status) {
      this.status = status;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder isprivate(Boolean isprivate) {
      this.isprivate = isprivate;
      return this;
    }

    public Builder isautomated(Boolean isautomated) {
      this.isautomated = isautomated;
      return this;
    }

    public Builder canedit(Boolean canedit) {
      this.canedit = canedit;
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

    public Builder lastupdated(Date lastupdated) {
      this.lastupdated = lastupdated;
      return this;
    }

    public Builder hasstarred(Boolean hasstarred) {
      this.hasstarred = hasstarred;
      return this;
    }

    public Builder fulldescription(String fulldescription) {
      this.fulldescription = fulldescription;
      return this;
    }

    public Builder affiliation(String affiliation) {
      this.affiliation = affiliation;
      return this;
    }

    public Builder permission(EHPermission permission) {
      this.permission = permission;
      return this;
    }
  }
}
