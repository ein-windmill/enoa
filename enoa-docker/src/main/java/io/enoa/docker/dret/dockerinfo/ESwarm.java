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
package io.enoa.docker.dret.dockerinfo;

import io.enoa.docker.dret.AbstractDRet;

public class ESwarm extends AbstractDRet {

  private final String nodeid;
  private final String nodeaddr;
  private final String localnodestate;
  private final Boolean controlavailable;
  private final String error;
  private final Object remotemanagers;

  private ESwarm(Builder builder) {
    this.nodeaddr = builder.nodeaddr;
    this.nodeid = builder.nodeid;
    this.localnodestate = builder.localnodestate;
    this.controlavailable = builder.controlavailable;
    this.error = builder.error;
    this.remotemanagers = builder.remotemanagers;
  }

  public String nodeid() {
    return this.nodeid;
  }

  public String nodeaddr() {
    return this.nodeaddr;
  }

  public String localnodestate() {
    return this.localnodestate;
  }

  public Boolean controlavailable() {
    return this.controlavailable;
  }

  public String error() {
    return this.error;
  }

  public Object remotemanagers() {
    return this.remotemanagers;
  }

  public static class Builder {

    private String nodeid;
    private String nodeaddr;
    private String localnodestate;
    private Boolean controlavailable;
    private String error;
    private Object remotemanagers;


    public Builder() {

    }

    public ESwarm build() {
      return new ESwarm(this);
    }

    public Builder nodeid(String nodeid) {
      this.nodeid = nodeid;
      return this;
    }

    public Builder nodeaddr(String nodeaddr) {
      this.nodeaddr = nodeaddr;
      return this;
    }

    public Builder localnodestate(String localnodestate) {
      this.localnodestate = localnodestate;
      return this;
    }

    public Builder controlavailable(Boolean controlavailable) {
      this.controlavailable = controlavailable;
      return this;
    }

    public Builder error(String error) {
      this.error = error;
      return this;
    }

    public Builder remotemanagers(Object remotemanagers) {
      this.remotemanagers = remotemanagers;
      return this;
    }
  }

}
