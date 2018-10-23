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
package io.enoa.docker.ret.docker.exec;

import io.enoa.docker.ret.AbstractDockerRet;

import java.util.List;

public class EProcessConfig extends AbstractDockerRet {

  private Boolean privileged;
  private String user;
  private Boolean tty;
  private String entrypoint;
  private List<String> arguments;

  public EProcessConfig(Builder builder) {
    this.privileged = builder.privileged;
    this.user = builder.user;
    this.tty = builder.tty;
    this.entrypoint = builder.entrypoint;
    this.arguments = builder.arguments;
  }


  public Boolean privileged() {
    return privileged;
  }

  public String user() {
    return user;
  }

  public Boolean tty() {
    return tty;
  }

  public String entrypoint() {
    return entrypoint;
  }

  public List<String> arguments() {
    return arguments;
  }

  public static class Builder {

    private Boolean privileged;
    private String user;
    private Boolean tty;
    private String entrypoint;
    private List<String> arguments;

    public EProcessConfig build() {
      return new EProcessConfig(this);
    }

    public Builder privileged(Boolean privileged) {
      this.privileged = privileged;
      return this;
    }

    public Builder user(String user) {
      this.user = user;
      return this;
    }

    public Builder tty(Boolean tty) {
      this.tty = tty;
      return this;
    }

    public Builder entrypoint(String entrypoint) {
      this.entrypoint = entrypoint;
      return this;
    }

    public Builder arguments(List<String> arguments) {
      this.arguments = arguments;
      return this;
    }
  }

}
