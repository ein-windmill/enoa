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

import io.enoa.docker.command.registry._RegistryConfigSupport;
import io.enoa.docker.dket.registry.RResp;

public interface OriginRegistry extends _RegistryConfigSupport {


  default RResp _catelog() {
    return this._catalog(50, null);
  }

  /**
   * Find Repositories
   * Returns a list of all existing repositories. A repository is a set of images with the same name and different tags.
   * <p>
   * Query Parameters
   * Name 	Type 	Description
   * n 	integer
   * <p>
   * Maximum number of results
   * last 	string
   * <p>
   * Last result from previous response
   *
   * @return RResp
   */
  RResp _catalog(Integer n, String last);


  /**
   * Find Tags
   * Path Parameters
   * Name 	Type 	Description
   * repository 	RepositoryName
   * <p>
   * Repository name
   *
   * @param repository Repository name
   * @return RResp
   */
  RResp tags(String repository);

  OriginManifests manifests();

  OriginBlob blob();

  OriginUpload upload();

}
