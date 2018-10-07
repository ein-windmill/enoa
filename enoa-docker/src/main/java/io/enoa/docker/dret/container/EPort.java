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
package io.enoa.docker.dret.container;

import io.enoa.docker.dret.AbstractDRet;

public class EPort extends AbstractDRet {

  private final String ip;
  private final Integer privateport;
  private final Integer publicport;
  private final String type;

  public EPort(Builder builder) {
    this.ip = builder.ip;
    this.privateport = builder.privateport;
    this.publicport = builder.publicport;
    this.type = builder.type;
  }


  public String ip() {
    return this.ip;
  }

  public Integer privateport() {
    return this.privateport;
  }

  public Integer publicport() {
    return this.publicport;
  }

  public String type() {
    return this.type;
  }

  public static class Builder {

    private String ip;
    private Integer privateport;
    private Integer publicport;
    private String type;


    public EPort build() {
      return new EPort(this);
    }

    public Builder ip(String ip) {
      this.ip = ip;
      return this;
    }

    public Builder privateport(Integer privateport) {
      this.privateport = privateport;
      return this;
    }

    public Builder publicport(Integer publicport) {
      this.publicport = publicport;
      return this;
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }
  }


}
