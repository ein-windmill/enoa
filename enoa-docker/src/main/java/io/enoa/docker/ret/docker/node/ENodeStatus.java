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
package io.enoa.docker.ret.docker.node;

import io.enoa.docker.ret.AbstractDRRet;

public class ENodeStatus extends AbstractDRRet {

  private String state;
  private String message;
  private String addr;

  public ENodeStatus(Builder builder) {
    this.state = builder.state;
    this.message = builder.message;
    this.addr = builder.addr;
  }

  public String state() {
    return this.state;
  }

  public String message() {
    return this.message;
  }

  public String addr() {
    return this.addr;
  }

  public static class Builder {

    private String state;
    private String message;
    private String addr;

    public ENodeStatus build() {
      return new ENodeStatus(this);
    }

    public Builder state(String state) {
      this.state = state;
      return this;
    }

    public Builder message(String message) {
      this.message = message;
      return this;
    }

    public Builder addr(String addr) {
      this.addr = addr;
      return this;
    }
  }
}
