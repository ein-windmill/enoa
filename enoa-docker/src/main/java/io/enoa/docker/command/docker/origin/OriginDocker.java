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

import io.enoa.docker.command.docker._DockerConfigSupport;
import io.enoa.docker.dket.docker.DResp;

public interface OriginDocker extends _DockerConfigSupport {

  DResp info();

  EOriginDockerContainer container();

  EOriginDockerImage image();

  EOriginDockerNetwork network();

  EOriginDockerVolume volume();

  EOriginDockerExec exec();

  EOriginDockerSwarm swarm();

  EOriginDockerNode node();

  EOriginDockerService service();

  EOriginDockerTask task();

  EOriginDockerSecret secret();

  EOriginDockerConfig config();

  EOriginDockerPlugin plugin();

  EOriginDockerSystem system();

  EOriginDockerDistribution distribution();

}
