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
package io.enoa.docker.dket.docker.service;

import io.enoa.docker.dket.AbstractDRRet;

public class ESrvFile extends AbstractDRRet {

  private String name;
  private String uid;
  private String gid;
  private Integer mode;

  public ESrvFile(Builder builder) {
    this.name = builder.name;
    this.uid = builder.uid;
    this.gid = builder.gid;
    this.mode = builder.mode;
  }

  public String name() {
    return name;
  }

  public String uid() {
    return uid;
  }

  public String gid() {
    return gid;
  }

  public Integer mode() {
    return mode;
  }

  public static class Builder {

    private String name;
    private String uid;
    private String gid;
    private Integer mode;

    public ESrvFile build() {
      return new ESrvFile(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder uid(String uid) {
      this.uid = uid;
      return this;
    }

    public Builder gid(String gid) {
      this.gid = gid;
      return this;
    }

    public Builder mode(Integer mode) {
      this.mode = mode;
      return this;
    }
  }


}
