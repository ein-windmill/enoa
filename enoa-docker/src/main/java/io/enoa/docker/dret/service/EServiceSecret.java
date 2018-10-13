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
package io.enoa.docker.dret.service;

import io.enoa.docker.dret.AbstractDRet;

public class EServiceSecret extends AbstractDRet {

  private ESrvFile file;
  private String secretid;
  private String secretname;

  public EServiceSecret(Builder builder) {
    this.file = builder.file;
    this.secretid = builder.secretid;
    this.secretname = builder.secretname;
  }

  public ESrvFile file() {
    return file;
  }

  public String secretid() {
    return secretid;
  }

  public String secretname() {
    return secretname;
  }

  public static class Builder {

    private ESrvFile file;
    private String secretid;
    private String secretname;

    public EServiceSecret build() {
      return new EServiceSecret(this);
    }

    public Builder file(ESrvFile file) {
      this.file = file;
      return this;
    }

    public Builder secretid(String secretid) {
      this.secretid = secretid;
      return this;
    }

    public Builder secretname(String secretname) {
      this.secretname = secretname;
      return this;
    }
  }
}
