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
package io.enoa.docker.dqp.exec;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

import java.util.ArrayList;
import java.util.List;

public class DQPExecCreate implements DQP {

  private Boolean attachstdin;
  private Boolean attachstdout;
  private Boolean attachstderr;
  private String detachkeys;
  private Boolean tty;
  private List<String> env;
  private List<String> cmd;
  private Boolean privileged;
  private String user;
  private String workingdir;

  public static DQPExecCreate create() {
    return new DQPExecCreate();
  }

  public DQPExecCreate() {
    this.privileged = Boolean.FALSE;
  }

  public DQPExecCreate attachstdin() {
    return this.attachstdin(Boolean.TRUE);
  }

  public DQPExecCreate attachstdin(Boolean attachstdin) {
    this.attachstdin = attachstdin;
    return this;
  }

  public DQPExecCreate attachstdout() {
    return this.attachstdout(Boolean.TRUE);
  }

  public DQPExecCreate attachstdout(Boolean attachstdout) {
    this.attachstdout = attachstdout;
    return this;
  }

  public DQPExecCreate attachstderr() {
    return this.attachstderr(Boolean.TRUE);
  }

  public DQPExecCreate attachstderr(Boolean attachstderr) {
    this.attachstderr = attachstderr;
    return this;
  }

  public DQPExecCreate detachkeys(String detachkeys) {
    this.detachkeys = detachkeys;
    return this;
  }

  public DQPExecCreate tty(Boolean tty) {
    this.tty = tty;
    return this;
  }

  public DQPExecCreate env(String env) {
    if (this.env == null)
      this.env = new ArrayList<>();
    this.env.add(env);
    return this;
  }

  public DQPExecCreate env(List<String> env) {
    this.env = env;
    return this;
  }

  public DQPExecCreate cmd(String cmd) {
    if (this.cmd == null)
      this.cmd = new ArrayList<>();
    this.cmd.add(cmd);
    return this;
  }

  public DQPExecCreate cmd(List<String> cmd) {
    this.cmd = cmd;
    return this;
  }

  public DQPExecCreate privileged() {
    return this.privileged(Boolean.TRUE);
  }

  public DQPExecCreate privileged(Boolean privileged) {
    this.privileged = privileged;
    return this;
  }

  public DQPExecCreate user(String user) {
    this.user = user;
    return this;
  }

  public DQPExecCreate workingdir(String workingdir) {
    this.workingdir = workingdir;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create()
      .putIf("AttachStdin", this.attachstdin)
      .putIf("AttachStdout", this.attachstdout)
      .putIf("AttachStderr", this.attachstderr)
      .putIf("DetachKeys", this.detachkeys)
      .putIf("Tty", this.tty)
      .putIf("Env", this.env)
      .putIf("Cmd", this.cmd)
      .putIf("Privileged", this.privileged)
      .putIf("User", this.user)
      .putIf("WorkingDir", this.workingdir);
    return dqr;
  }
}
