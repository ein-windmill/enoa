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
package io.enoa.docker.dket.docker.image;

import io.enoa.docker.dket.AbstractDRRet;

import java.util.List;

public class EHistory extends AbstractDRRet {

  private String id;
  private Long created;
  private String createdby;
  private List<String> tags;
  private Long size;
  private String comment;

  public EHistory(Builder builder) {
    this.id = builder.id;
    this.created = builder.created;
    this.createdby = builder.createdby;
    this.tags = builder.tags;
    this.size = builder.size;
    this.comment = builder.comment;
  }

  public String id() {
    return this.id;
  }

  public Long created() {
    return this.created;
  }

  public String createdby() {
    return this.createdby;
  }

  public List<String> tags() {
    return this.tags;
  }

  public Long size() {
    return this.size;
  }

  public String comment() {
    return this.comment;
  }

  public static class Builder {

    private String id;
    private Long created;
    private String createdby;
    private List<String> tags;
    private Long size;
    private String comment;


    public EHistory build() {
      return new EHistory(this);
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder created(Long created) {
      this.created = created;
      return this;
    }

    public Builder createdby(String createdby) {
      this.createdby = createdby;
      return this;
    }

    public Builder tags(List<String> tags) {
      this.tags = tags;
      return this;
    }

    public Builder size(Long size) {
      this.size = size;
      return this;
    }

    public Builder comment(String comment) {
      this.comment = comment;
      return this;
    }
  }

}
