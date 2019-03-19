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
package io.enoa.docker.dket.docker.dockerinfo;

import io.enoa.docker.dket.AbstractDRRet;

public class EIndex extends AbstractDRRet {

  private final String name;
  private final String[] mirrors;
  private final Boolean secure;
  private final Boolean official;

  public EIndex(Builder builder) {
    this.name = builder.name;
    this.mirrors = builder.mirrors;
    this.secure = builder.secure;
    this.official = builder.official;
  }

  public String name() {
    return this.name;
  }

  public String[] mirrors() {
    return this.mirrors;
  }

  public Boolean secure() {
    return this.secure;
  }

  public Boolean official() {
    return this.official;
  }

  public static class Builder {

    private String name;
    private String[] mirrors;
    private Boolean secure;
    private Boolean official;


    public Builder() {
    }


    public EIndex build() {
      return new EIndex(this);
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder mirrors(String[] mirrors) {
      this.mirrors = mirrors;
      return this;
    }

    public Builder secure(Boolean secure) {
      this.secure = secure;
      return this;
    }

    public Builder official(Boolean official) {
      this.official = official;
      return this;
    }
  }

}
