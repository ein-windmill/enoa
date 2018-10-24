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
package io.enoa.docker.command.hub.origin;

import io.enoa.docker.DockerhubConfig;
import io.enoa.http.EoUrl;
import io.enoa.http.Http;

abstract class AbstractDockerhub implements OriginDockerhub {

  private DockerhubConfig config;

  AbstractDockerhub(DockerhubConfig config) {
    this.config = config;
  }

  @Override
  public DockerhubConfig _dockerhubconfig() {
    return this.config;
  }

  protected Http http(String url, String... subpaths) {
    return this.config.http().http()
      .url(EoUrl.with(this.config.context()).subpath(url).subpath(subpaths));
  }

}
