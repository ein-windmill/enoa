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
package io.enoa.docker.ret.docker.image;

import io.enoa.docker.ret.AbstractDRRet;
import io.enoa.toolkit.map.Kv;

import java.util.List;

public class EImage extends AbstractDRRet {

  private String id;
  private String parentid;
  private List<String> repotags;
  private List<String> repodigests;
  private Long created;
  private Long size;
  private Long virtualsize;
  private Integer sharedsize;
  private Kv labels;
  private Integer containers;

  public EImage(Builder builder) {
    this.id = builder.id;
    this.parentid = builder.parentid;
    this.repotags = builder.repotags;
    this.repodigests = builder.repodigests;
    this.created = builder.created;
    this.size = builder.size;
    this.virtualsize = builder.virtualsize;
    this.sharedsize = builder.sharedsize;
    this.labels = builder.labels;
    this.containers = builder.containers;
  }

  public String id() {
    return this.id;
  }

  public String parentid() {
    return this.parentid;
  }

  public List<String> repotags() {
    return this.repotags;
  }

  public List<String> repodigests() {
    return this.repodigests;
  }

  public Long created() {
    return this.created;
  }

  public Long size() {
    return this.size;
  }

  public Long virtualsize() {
    return this.virtualsize;
  }

  public Integer sharedsize() {
    return this.sharedsize;
  }

  public Kv labels() {
    return this.labels;
  }

  public Integer containers() {
    return this.containers;
  }

  public static class Builder {
    private String id;
    private String parentid;
    private List<String> repotags;
    private List<String> repodigests;
    private Long created;
    private Long size;
    private Long virtualsize;
    private Integer sharedsize;
    private Kv labels;
    private Integer containers;

    public EImage build() {
      return new EImage(this);
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder parentid(String parentid) {
      this.parentid = parentid;
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

    public Builder created(Long created) {
      this.created = created;
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

    public Builder sharedsize(Integer sharedsize) {
      this.sharedsize = sharedsize;
      return this;
    }

    public Builder labels(Kv labels) {
      this.labels = labels;
      return this;
    }

    public Builder containers(Integer containers) {
      this.containers = containers;
      return this;
    }
  }

}
