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
package io.enoa.docker.command.geneic;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.command.origin.EOriginPlugins;
import io.enoa.docker.command.origin.OriginDocker;

public class EGeneicDockerPlugins {

  private OriginDocker docker;
  private DockerConfig config;
  private EOriginPlugins plugins;

  EGeneicDockerPlugins(OriginDocker docker) {
    this.docker = docker;
    this.config = docker._config();
    this.plugins = docker.plugins();
  }
}
