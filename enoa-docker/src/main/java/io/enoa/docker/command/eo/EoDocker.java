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
package io.enoa.docker.command.eo;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.dret.dockerinfo.EDockerInfo;
import io.enoa.docker.parser.DIParser;

public interface EoDocker {


  DockerConfig _config();

  default DRet<EDockerInfo> info() {
    return this.info(DIParser.dockerinfo());
  }

  DRet<EDockerInfo> info(DIParser<EDockerInfo> parser);

  EnoaDockerContainer container();

}
