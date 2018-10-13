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
package io.enoa.docker.dret.image;

import io.enoa.docker.dret.AbstractDRet;
import io.enoa.docker.dret.container.EGraphDriver;

import java.util.Date;
import java.util.List;

public class EIInspect extends AbstractDRet {

  private String id;
  private List<String> repotags;
  private List<String> repodigests;
  private String parent;
  private String comment;
  private Date created;
  private String container;
  private EContainerConfig containerconfig;
  private String dockerversion;
  private String author;
  private EContainerConfig config;
  private String architecture;
  private String os;
  private String osversion;
  private Long size;
  private Long virtualsize;
  private EGraphDriver graphdriver;
  private ERootFS rootfs;
  private EIMetadata metadata;

  public EIInspect(Builder builder) {
    this.id = builder.id;
    this.repotags = builder.repotags;
    this.repodigests = builder.repodigests;
    this.parent = builder.parent;
    this.comment = builder.comment;
    this.created = builder.created;
    this.container = builder.container;
    this.containerconfig = builder.containerconfig;
    this.dockerversion = builder.dockerversion;
    this.author = builder.author;
    this.config = builder.config;
    this.architecture = builder.architecture;
    this.os = builder.os;
    this.osversion = builder.osversion;
    this.size = builder.size;
    this.virtualsize = builder.virtualsize;
    this.graphdriver = builder.graphdriver;
    this.rootfs = builder.rootfs;
    this.metadata = builder.metadata;
  }

  public String id() {
    return this.id;
  }

  public List<String> repotags() {
    return this.repotags;
  }

  public List<String> repodigests() {
    return this.repodigests;
  }

  public String parent() {
    return this.parent;
  }

  public String comment() {
    return this.comment;
  }

  public Date created() {
    return this.created;
  }

  public String container() {
    return this.container;
  }

  public EContainerConfig containerconfig() {
    return this.containerconfig;
  }

  public String dockerversion() {
    return this.dockerversion;
  }

  public String author() {
    return this.author;
  }

  public EContainerConfig config() {
    return this.config;
  }

  public String architecture() {
    return this.architecture;
  }

  public String os() {
    return this.os;
  }

  public String osversion() {
    return this.osversion;
  }

  public Long size() {
    return this.size;
  }

  public Long virtualsize() {
    return this.virtualsize;
  }

  public EGraphDriver graphdriver() {
    return this.graphdriver;
  }

  public ERootFS rootfs() {
    return this.rootfs;
  }

  public EIMetadata metadata() {
    return this.metadata;
  }

  public static class Builder {

    private String id;
    private List<String> repotags;
    private List<String> repodigests;
    private String parent;
    private String comment;
    private Date created;
    private String container;
    private EContainerConfig containerconfig;
    private String dockerversion;
    private String author;
    private EContainerConfig config;
    private String architecture;
    private String os;
    private String osversion;
    private Long size;
    private Long virtualsize;
    private EGraphDriver graphdriver;
    private ERootFS rootfs;
    private EIMetadata metadata;


    public EIInspect build() {
      return new EIInspect(this);
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder repotags(List<String> repotags) {
      this.repotags = repotags;
      return this;
    }

    public Builder repodigests(List<String> repodigests) {
      this.repodigests = repodigests;
      return this;
    }

    public Builder parent(String parent) {
      this.parent = parent;
      return this;
    }

    public Builder comment(String comment) {
      this.comment = comment;
      return this;
    }

    public Builder created(Date created) {
      this.created = created;
      return this;
    }

    public Builder container(String container) {
      this.container = container;
      return this;
    }

    public Builder containerconfig(EContainerConfig containerconfig) {
      this.containerconfig = containerconfig;
      return this;
    }

    public Builder dockerversion(String dockerversion) {
      this.dockerversion = dockerversion;
      return this;
    }

    public Builder author(String author) {
      this.author = author;
      return this;
    }

    public Builder config(EContainerConfig config) {
      this.config = config;
      return this;
    }

    public Builder architecture(String architecture) {
      this.architecture = architecture;
      return this;
    }

    public Builder os(String os) {
      this.os = os;
      return this;
    }

    public Builder osversion(String osversion) {
      this.osversion = osversion;
      return this;
    }

    public Builder size(Long size) {
      this.size = size;
      return this;
    }

    public Builder virtualsize(Long virtualsize) {
      this.virtualsize = virtualsize;
      return this;
    }

    public Builder graphdriver(EGraphDriver graphdriver) {
      this.graphdriver = graphdriver;
      return this;
    }

    public Builder rootfs(ERootFS rootfs) {
      this.rootfs = rootfs;
      return this;
    }

    public Builder metadata(EIMetadata metadata) {
      this.metadata = metadata;
      return this;
    }
  }


}
