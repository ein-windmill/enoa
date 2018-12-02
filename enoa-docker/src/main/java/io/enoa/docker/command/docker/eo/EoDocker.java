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
package io.enoa.docker.command.docker.eo;

import io.enoa.chunk.stream.ChunkStream;
import io.enoa.docker.command.docker._DockerConfigSupport;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.dockerinfo.EDockerInfo;
import io.enoa.docker.dket.docker.run.EDRun;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.container.DQPContainerCreate;

public interface EoDocker extends _DockerConfigSupport {

  DRet<EDockerInfo> info();

  EnoaDockerContainer container();

  EnoaDockerImage image();

  EnoaDockerNetwork network();

  EnoaDockerVolume volume();

  EnoaDockerExec exec();

  EnoaDockerSwarm swarm();

  EnoaDockerNode node();

  EnoaDockerService service();

  EnoaDockerTask task();

  EnoaDockerSecret secret();

  EnoaDockerConfig config();

  EnoaDockerPlugin plugin();

  EnoaDockerSystem system();

  EnoaDockerDistribution distribution();

  default DRet<EDRun> run(String name, DQPContainerCreate dqp) {
    return this.run(name, dqp, null, null);
  }

  default DRet<EDRun> run(String name, DQPContainerCreate dqp, DQPResize resize) {
    return this.run(name, dqp, null, resize);
  }

  default DRet<EDRun> run(String name, DQPContainerCreate dqp, ChunkStream stream) {
    return this.run(name, dqp, stream, null);
  }

  DRet<EDRun> run(String name, DQPContainerCreate dqp, ChunkStream stream, DQPResize resize);

}
