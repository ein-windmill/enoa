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

public class EHPermission extends AbstractDRRet {

  private Boolean read;
  private Boolean write;
  private Boolean admin;

  public EHPermission(Builder builder) {
    this.read = builder.read;
    this.write = builder.write;
    this.admin = builder.admin;
  }

  public Boolean read() {
    return this.read;
  }

  public Boolean write() {
    return this.write;
  }

  public Boolean admin() {
    return this.admin;
  }

  public static class Builder {

    private Boolean read;
    private Boolean write;
    private Boolean admin;

    public EHPermission build() {
      return new EHPermission(this);
    }

    public Builder read(Boolean read) {
      this.read = read;
      return this;
    }

    public Builder write(Boolean write) {
      this.write = write;
      return this;
    }

    public Builder admin(Boolean admin) {
      this.admin = admin;
      return this;
    }
  }

}
