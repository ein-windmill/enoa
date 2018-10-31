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

import io.enoa.docker.command.hub.eo.EnoaDockerHub;
import io.enoa.docker.command.hub.generic.GenericDockerhub;
import io.enoa.docker.command.hub.origin.OriginDockerhub;
import io.enoa.docker.dqp.common.DQPPage;
import io.enoa.docker.dqp.dockerhub.DQPSearch;
import io.enoa.docker.dket.dockerhub.HRet;
import io.enoa.docker.dket.dockerhub.build.EHAutobuild;
import io.enoa.docker.dket.dockerhub.build.EHBuildHistory;
import io.enoa.docker.dket.dockerhub.explore.EHExplore;
import io.enoa.docker.dket.dockerhub.inspece.EHRepository;
import io.enoa.docker.dket.dockerhub.search.EHSearch;
import io.enoa.docker.dket.dockerhub.tag.EHTag;

public class Dockerhub {

  public static EPMDockerhub epm() {
    return EPMDockerhub.instance();
  }

  public static OriginDockerhub origin() {
    return epm().origin();
  }

  public static GenericDockerhub generic() {
    return epm().generic();
  }

  public static EnoaDockerHub use() {
    return epm().dockerhub();
  }


  public static HRet<EHExplore> explore() {
    return use().explore();
  }

  public static HRet<EHExplore> explore(DQPPage dqp) {
    return use().explore(dqp);
  }

  public static HRet<EHSearch> search(String q) {
    return use().search(q);
  }

  public static HRet<EHSearch> search(DQPSearch dqp) {
    return use().search(dqp);
  }

  public static HRet<EHRepository> inspect(String repository) {
    return use().inspect(repository);
  }

  public static HRet<EHTag> tags(String repository) {
    return use().tags(repository);
  }

  public static HRet<EHTag> tags(String repository, DQPPage dqp) {
    return use().tags(repository, dqp);
  }

  public static HRet<String> dockerfile(String repository) {
    return use().dockerfile(repository);
  }

  public static HRet<EHAutobuild> autobuild(String repository) {
    return use().autobuild(repository);
  }

  public static HRet<EHBuildHistory> history(String repository) {
    return use().history(repository);
  }

  public static HRet<EHBuildHistory> history(String repository, DQPPage dqp) {
    return use().history(repository, dqp);
  }

}
