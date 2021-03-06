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
package io.enoa.docker.command.registry.origin;

import io.enoa.docker.dket.registry.RResp;

public interface OriginUpload {


  /**
   * Create Upload
   *
   * @param repository Repository name
   * @return RResp
   */
  RResp create(String repository);


  /**
   * Create Upload
   *
   * @param repository Repository name
   * @param uuid       uuid
   * @return RResp
   */
  RResp find(String repository, String uuid);


  /**
   * Update Upload
   *
   * @param repository Repository name
   * @param uuid       uuid
   * @return RResp
   */
  RResp update(String repository, String uuid);

  /**
   * Delete Upload
   *
   * @param repository Repository name
   * @param uuid       uuid
   * @return RResp
   */
  RResp delete(String repository, String uuid);

}
