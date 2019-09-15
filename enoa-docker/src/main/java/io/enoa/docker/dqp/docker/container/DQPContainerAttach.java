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
package io.enoa.docker.dqp.docker.container;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;
import io.enoa.toolkit.is.Is;

public class DQPContainerAttach implements DQP {

  private String detachkeys;
  private boolean logs;
  private boolean stream;
  private boolean stdin;
  private boolean stdout;
  private boolean stderr;


  public static DQPContainerAttach create() {
    return new DQPContainerAttach();
  }

  public DQPContainerAttach() {
  }


  public DQPContainerAttach detachkeys(String detachkeys) {
    this.detachkeys = detachkeys;
    return this;
  }

  public DQPContainerAttach logs() {
    return this.logs(Boolean.TRUE);
  }

  public DQPContainerAttach logs(boolean logs) {
    this.logs = logs;
    return this;
  }

  public DQPContainerAttach stream() {
    return this.stream(Boolean.TRUE);
  }

  public DQPContainerAttach stream(boolean stream) {
    this.stream = stream;
    return this;
  }

  public DQPContainerAttach stdin() {
    return this.stdin(Boolean.TRUE);
  }

  public DQPContainerAttach stdin(boolean stdin) {
    this.stdin = stdin;
    return this;
  }

  public DQPContainerAttach stdout() {
    return this.stdout(Boolean.TRUE);
  }

  public DQPContainerAttach stdout(boolean stdout) {
    this.stdout = stdout;
    return this;
  }

  public DQPContainerAttach stderr() {
    return this.stderr(Boolean.TRUE);
  }

  public DQPContainerAttach stderr(boolean stderr) {
    this.stderr = stderr;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create();
    if (Is.truthy(detachkeys))
      dqr.put("detachKeys", this.detachkeys);
    if (this.logs)
      dqr.put("logs", 1);
    if (this.stream)
      dqr.put("stream", 1);
    if (this.stdin)
      dqr.put("stdin", 1);
    if (this.stdout)
      dqr.put("stdout", 1);
    if (this.stderr)
      dqr.put("stderr", 1);
    return dqr;
  }

}
