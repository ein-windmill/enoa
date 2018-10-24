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

import io.enoa.docker.dqp.docker.system.DQPMonitor;
import io.enoa.docker.dqp.docker.system.DQPSystemAuth;
import io.enoa.docker.ret.docker.DResp;

public interface EOriginDockerSystem {

  /**
   * Check auth configuration
   * Validate credentials for a registry and, if available, get an identity token for accessing the registry without password.
   *
   * @param dqp dqp
   * @return DResp
   */
  DResp auth(DQPSystemAuth dqp);

  /**
   * Get system information
   *
   * @return DResp
   */
  DResp info();

  /**
   * Get version
   * Returns the version of Docker that is running and various information about the system that Docker is running on.
   *
   * @return DResp
   */
  DResp version();

  /**
   * Ping
   * This is a dummy endpoint you can use to test if the server is accessible.
   *
   * @return DResp
   */
  DResp ping();

  default DResp monitor() {
    return this.monitor(null);
  }

  /**
   * Monitor events
   * <p>
   * Stream real-time events from the server.
   * <p>
   * Various objects within Docker report events when something happens to them.
   * <p>
   * Containers report these events: attach, commit, copy, create, destroy, detach, die, exec_create, exec_detach, exec_start, exec_die, export, health_status, kill, oom, pause, rename, resize, restart, start, stop, top, unpause, and update
   * <p>
   * Images report these events: delete, import, load, pull, push, save, tag, and untag
   * <p>
   * Volumes report these events: create, mount, unmount, and destroy
   * <p>
   * Networks report these events: create, connect, disconnect, destroy, update, and remove
   * <p>
   * The Docker daemon reports these events: reload
   * <p>
   * Services report these events: create, update, and remove
   * <p>
   * Nodes report these events: create, update, and remove
   * <p>
   * Secrets report these events: create, update, and remove
   * <p>
   * Configs report these events: create, update, and remove
   *
   * @return DResp
   */
  DResp monitor(DQPMonitor dqp);

  /**
   * Get data usage information
   *
   * @return DResp
   */
  DResp df();

}
