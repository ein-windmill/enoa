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

import io.enoa.docker.dqp.docker.common.DQPFilter;
import io.enoa.docker.dqp.docker.volume.DQPVolumeCreate;
import io.enoa.docker.dqp.docker.volume.DQPVolumeList;
import io.enoa.docker.ret.docker.DResp;

public interface EOriginDockerVolume {


  default DResp list() {
    return this.list(null);
  }

  /**
   * List volumes
   *
   * @param dqp dqp
   * @return DResp
   */
  DResp list(DQPVolumeList dqp);


  /**
   * Create a volume
   *
   * @param body request body
   *             {
   *             "Name": "tardis",
   *             "Labels": {
   *             "com.example.some-label": "some-value",
   *             "com.example.some-other-label": "some-other-value"
   *             },
   *             "Driver": "custom"
   *             }
   * @return DResp
   */
  DResp create(String body);

  default DResp create(DQPVolumeCreate dqp) {
    return this.create(dqp.dqr().json());
  }

  /**
   * Inspect a volume
   *
   * @param id string Required
   *           <p>
   *           Volume name or ID
   * @return DResp
   */
  DResp inspect(String id);

  default DResp remove(String id) {
    return this.remove(id, Boolean.FALSE);
  }

  /**
   * Remove a volume
   * Instruct the driver to remove the volume.
   *
   * @param id string Required
   *           <p>
   *           Volume name or ID
   * @return DResp
   */
  DResp remove(String id, Boolean force);


  default DResp prune() {
    return this.prune(null);
  }

  /**
   * Delete unused volumes
   *
   * @param dqp dqp
   * @return DResp
   */
  DResp prune(DQPFilter dqp);

}
