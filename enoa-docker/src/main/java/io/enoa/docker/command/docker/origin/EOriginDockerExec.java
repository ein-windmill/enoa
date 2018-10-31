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
package io.enoa.docker.command.docker.origin;

import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.exec.DQPExecCreate;
import io.enoa.docker.dqp.docker.exec.DQPExecStart;
import io.enoa.docker.dket.docker.DResp;

public interface EOriginDockerExec {


  /**
   * Create an exec instance
   * Run a command inside a running container.
   *
   * @param body body
   * @return DResp
   */
  DResp exec(String id, String body);

  default DResp exec(String id, DQPExecCreate dqp) {
    return this.exec(id, dqp.dqr().json());
  }

  /**
   * Start an exec instance
   * Starts a previously set up exec instance. If detach is true, this endpoint returns immediately after starting the command. Otherwise, it sets up an interactive session with the command.
   *
   * @param body body
   * @return DResp
   */
  DResp start(String id, String body);

  default DResp start(String id, DQPExecStart dqp) {
    return this.start(id, dqp.dqr().json());
  }

  default DResp resize(String id) {
    return this.resize(id, null);
  }

  /**
   * Resize an exec instance
   * Resize the TTY session used by an exec instance. This endpoint only works if tty was specified as part of creating and starting the exec instance.
   *
   * @param id  id
   * @param dqp dqp
   * @return DResp
   */
  DResp resize(String id, DQPResize dqp);

  /**
   * Inspect an exec instance
   * Return low-level information about an exec instance.
   *
   * @param id id
   * @return Dresp
   */
  DResp inspect(String id);

}
