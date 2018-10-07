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
package io.enoa.docker;

import io.enoa.docker.command.eo.EoDocker;
import io.enoa.docker.command.geneic.GeneicDocker;
import io.enoa.docker.command.origin.OriginDocker;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.dret.dockerinfo.EDockerInfo;
import io.enoa.docker.parser.DIParser;

public class Docker {

  public static EPMDocker epm() {
    return EPMDocker.instance();
  }

  public static EoDocker use(String name) {
    return epm().docker(name);
  }

  public static EoDocker use() {
    return epm().docker();
  }

  public static GeneicDocker generic(String name) {
    return epm().generic(name);
  }

  public static GeneicDocker generic() {
    return epm().generic();
  }

  public static OriginDocker origin(String name) {
    return epm().origin(name);
  }

  public static OriginDocker origin() {
    return epm().origin();
  }

  public static DRet<EDockerInfo> info() {
    return use().info();
  }

  public static DRet<EDockerInfo> info(DIParser<EDockerInfo> parser) {
    return use().info(parser);
  }

}
