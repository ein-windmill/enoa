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

import java.util.Date;

public class EIMetadata extends AbstractDRRet {

  private final Date lasttagtime;

  public EIMetadata(Builder builder) {
    this.lasttagtime = builder.lasttagtime;
  }

  public Date lasttagtime() {
    return this.lasttagtime;
  }

  public static class Builder {
    private Date lasttagtime;

    public EIMetadata build() {
      return new EIMetadata(this);
    }

    public Builder lasttagtime(Date lasttagtime) {
      this.lasttagtime = lasttagtime;
      return this;
    }
  }

}
