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
package io.enoa.docker.parser;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.dret.container.EContainer;
import io.enoa.docker.dret.container.EContainerCreated;
import io.enoa.docker.dret.dockerinfo.EDockerInfo;

import java.util.List;

@FunctionalInterface
public interface DIParser<T> {

  static DIParser<EDockerInfo> dockerinfo() {
    return EDockerInfoParser.instance();
  }

  static DIParser<List<EContainer>> ps() {
    return EDockerPsParser.instance();
  }

  static DIParser<EContainerCreated> created() {
    return EContainerCreatedParser.instance();
  }

  DRet<T> parse(DockerConfig config, String origin);

}
