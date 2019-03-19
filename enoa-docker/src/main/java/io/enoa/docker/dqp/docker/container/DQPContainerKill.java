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
import io.enoa.toolkit.text.TextKit;

public class DQPContainerKill implements DQP {

  /**
   * string
   * "SIGKILL"
   * <p>
   * Signal to send to the container as an integer or string (e.g. SIGINT)
   */
  private String signal;


  public static DQPContainerKill create() {
    return new DQPContainerKill();
  }

  public DQPContainerKill() {
  }

  public DQPContainerKill signal(String signal) {
    this.signal = signal;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create();
    if (TextKit.blankn(this.signal))
      dqr.put("signal", this.signal);
    return dqr;
  }
}
