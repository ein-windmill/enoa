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
package io.enoa.docker.dket.docker.system;

import io.enoa.docker.dket.AbstractDRRet;

public class EYRemoteManage extends AbstractDRRet {

  private String nodeid;
  private String addr;

  public EYRemoteManage(Builder builder) {
    this.nodeid = builder.nodeid;
    this.addr = builder.addr;
  }

  public String nodeid() {
    return this.nodeid;
  }

  public String addr() {
    return this.addr;
  }

  public static class Builder {

    private String nodeid;
    private String addr;

    public EYRemoteManage build() {
      return new EYRemoteManage(this);
    }

    public Builder nodeid(String nodeid) {
      this.nodeid = nodeid;
      return this;
    }

    public Builder addr(String addr) {
      this.addr = addr;
      return this;
    }
  }

}
