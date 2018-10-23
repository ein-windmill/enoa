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
import io.enoa.docker.ret.docker.DResp;

public interface EOriginNode {


  /**
   * List nodes
   *
   * @param dqp dqp
   * @return DResp
   */
  DResp nodes(DQPFilter dqp);


  /**
   * Inspect a node
   *
   * @param id string Required
   *           <p>
   *           The ID or name of the node
   * @return DResp
   */
  DResp inspect(String id);

  default DResp remove(String id) {
    return this.remove(id, Boolean.FALSE);
  }

  /**
   * Delete a node
   *
   * @param id    string Required
   *              <p>
   *              The ID or name of the node
   * @param force boolean
   *              default false
   *              <p>
   *              Force remove a node from the swarm
   * @return DResp
   */
  DResp remove(String id, boolean force);


  /**
   * Update a node
   *
   * @param id      string Required
   *                <p>
   *                The ID of the node
   * @param version integer <int64> Required
   *                <p>
   *                The version number of the node object being updated. This is required to avoid conflicting writes.
   * @param body    body
   *                {
   *                "Availability": "active",
   *                "Name": "node-name",
   *                "Role": "manager",
   *                "Labels": {
   *                "foo": "bar"
   *                }
   *                }
   * @return DResp
   */
  DResp update(String id, long version, String body);

}
