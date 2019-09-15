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
package io.enoa.docker.command.docker.generic;

import io.enoa.docker.command.docker._DockerConfigSupport;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.parser.docker.DIParser;

public interface GenericDocker extends _DockerConfigSupport {

  <T> DRet<T> info(DIParser<T> parser);

  EGenericDockerContainer container();

  EGenericDockerImage image();

  EGenericDockerNetwork network();

  EGenericDockerVolume volume();

  EGenericDockerExec exec();

  EGenericDockerSwarm swarm();

  EGenericDockerNode node();

  EGenericDockerService service();

  EGenericDockerTask task();

  EGenericDockerSecret secret();

  EGenericDockerConfig config();

  EGenericDockerPlugin plugin();

  EGenericDockerSystem system();

  EGenericDockerDistribution distribution();

}
