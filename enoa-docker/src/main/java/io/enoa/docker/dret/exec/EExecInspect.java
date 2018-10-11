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
package io.enoa.docker.dret.exec;

import io.enoa.docker.dret.AbstractDRet;

public class EExecInspect extends AbstractDRet {

  private Boolean canremove;
  private String containerid;
  private String detachkeys;
  private Integer exitcode;
  private String id;
  private Boolean openstderr;
  private Boolean openstdin;
  private Boolean openstdout;
  private Boolean running;
  private Integer pid;
  private EProcessConfig processconfig;

  public EExecInspect(Builder builder) {
    this.canremove = builder.canremove;
    this.containerid = builder.containerid;
    this.detachkeys = builder.detachkeys;
    this.exitcode = builder.exitcode;
    this.id = builder.id;
    this.openstderr = builder.openstderr;
    this.openstdin = builder.openstdin;
    this.openstdout = builder.openstdout;
    this.running = builder.running;
    this.pid = builder.pid;
    this.processconfig = builder.processconfig;
  }

  public Boolean canremove() {
    return canremove;
  }

  public String containerid() {
    return containerid;
  }

  public String detachkeys() {
    return detachkeys;
  }

  public Integer exitcode() {
    return exitcode;
  }

  public String id() {
    return id;
  }

  public Boolean openstderr() {
    return openstderr;
  }

  public Boolean openstdin() {
    return openstdin;
  }

  public Boolean openstdout() {
    return openstdout;
  }

  public Boolean running() {
    return running;
  }

  public Integer pid() {
    return pid;
  }

  public EProcessConfig processconfig() {
    return processconfig;
  }

  public static class Builder {

    private Boolean canremove;
    private String containerid;
    private String detachkeys;
    private Integer exitcode;
    private String id;
    private Boolean openstderr;
    private Boolean openstdin;
    private Boolean openstdout;
    private Boolean running;
    private Integer pid;
    private EProcessConfig processconfig;


    public EExecInspect build() {
      return new EExecInspect(this);
    }

    public Builder canremove(Boolean canremove) {
      this.canremove = canremove;
      return this;
    }

    public Builder containerid(String containerid) {
      this.containerid = containerid;
      return this;
    }

    public Builder detachkeys(String detachkeys) {
      this.detachkeys = detachkeys;
      return this;
    }

    public Builder exitcode(Integer exitcode) {
      this.exitcode = exitcode;
      return this;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder openstderr(Boolean openstderr) {
      this.openstderr = openstderr;
      return this;
    }

    public Builder openstdin(Boolean openstdin) {
      this.openstdin = openstdin;
      return this;
    }

    public Builder openstdout(Boolean openstdout) {
      this.openstdout = openstdout;
      return this;
    }

    public Builder running(Boolean running) {
      this.running = running;
      return this;
    }

    public Builder pid(Integer pid) {
      this.pid = pid;
      return this;
    }

    public Builder processconfig(EProcessConfig processconfig) {
      this.processconfig = processconfig;
      return this;
    }
  }
}
