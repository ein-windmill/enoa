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
package io.enoa.docker.ret.docker.system;

import io.enoa.docker.ret.AbstractDRRet;

public class EAuth extends AbstractDRRet {

  private String status;
  private String identitytoken;

  public EAuth(Builder builder) {
    this.status = builder.status;
    this.identitytoken = builder.identitytoken;
  }

  public String status() {
    return this.status;
  }

  public String identitytoken() {
    return this.identitytoken;
  }

  public static class Builder {

    private String status;
    private String identitytoken;


    public EAuth build() {
      return new EAuth(this);
    }

    public Builder status(String status) {
      this.status = status;
      return this;
    }

    public Builder identitytoken(String identitytoken) {
      this.identitytoken = identitytoken;
      return this;
    }
  }


}
