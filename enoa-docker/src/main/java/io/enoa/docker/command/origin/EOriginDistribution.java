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
package io.enoa.docker.command.origin;

import io.enoa.docker.dret.DResp;

public interface EOriginDistribution {

  /**
   * Get image information from the registry
   * Return image digest and platform information by contacting the registry.
   *
   * @param id string Required
   *             <p>
   *             Image name or id
   * @return DResp
   */
  DResp distribution(String id);

}
