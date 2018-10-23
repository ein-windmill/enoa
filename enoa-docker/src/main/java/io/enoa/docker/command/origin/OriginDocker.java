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

import io.enoa.docker.command._DockerConfigSupport;
import io.enoa.docker.ret.docker.DResp;

public interface OriginDocker extends _DockerConfigSupport {

  DResp info();

  EOriginDockerContainer container();

  EOriginDockerImage image();

  EOriginDockerNetwork network();

  EOriginDockerVolume volume();

  EOriginDockerExec exec();

  EOriginSwarm swarm();

  EOriginNode node();

  EOriginService service();

  EOriginTask task();

  EOriginSecret secret();

  EOriginConfig config();

  EOriginPlugin plugin();

  EOriginSystem system();

  EOriginDistribution distribution();

}
