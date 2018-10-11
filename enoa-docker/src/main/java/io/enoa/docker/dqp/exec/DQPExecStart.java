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

public class DQPExecStart implements DQP {

  private Boolean detach;
  private Boolean tty;

  public static DQPExecStart create() {
    return new DQPExecStart();
  }

  public DQPExecStart() {

  }

  public DQPExecStart detach(Boolean detach) {
    this.detach = detach;
    return this;
  }

  public DQPExecStart tty(Boolean tty) {
    this.tty = tty;
    return this;
  }

  @Override
  public DQR dqr() {
    return DQR.create()
      .putIf("Tty", this.tty)
      .putIf("Detach", this.detach);
  }
}
