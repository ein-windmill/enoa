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

import io.enoa.docker.command.docker.eo.*;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.ret.docker.DRet;
import io.enoa.docker.ret.docker.dockerinfo.EDockerInfo;

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

  public static GenericDocker generic(String name) {
    return epm().generic(name);
  }

  public static GenericDocker generic() {
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

  public static EnoaDockerContainer container() {
    return use().container();
  }

  public static EnoaDockerImage image() {
    return use().image();
  }

  public static EnoaDockerNetwork network() {
    return use().network();
  }

  public static EnoaDockerVolume volume() {
    return use().volume();
  }

  public static EnoaDockerExec exec() {
    return use().exec();
  }

  public static EnoaDockerSwarm swarm() {
    return use().swarm();
  }

  public static EnoaDockerNode node() {
    return use().node();
  }

  public static EnoaDockerService service() {
    return use().service();
  }

  public static EnoaDockerTask task() {
    return use().task();
  }

  public static EnoaDockerSecret secret() {
    return use().secret();
  }

  public static EnoaDockerConfig config() {
    return use().config();
  }

  public static EnoaDockerPlugin plugin() {
    return use().plugin();
  }

  public static EnoaDockerSystem system() {
    return use().system();
  }

  public static EnoaDockerDistribution distribution() {
    return use().distribution();
  }

}
