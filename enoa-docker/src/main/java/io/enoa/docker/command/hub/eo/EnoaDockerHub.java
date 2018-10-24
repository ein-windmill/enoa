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
package io.enoa.docker.command.hub.eo;

import io.enoa.docker.DockerhubConfig;
import io.enoa.docker.command.hub._DockerhubConfigSupport;
import io.enoa.docker.command.hub.generic.GenericDockerhub;
import io.enoa.docker.dqp.common.DQPPage;
import io.enoa.docker.dqp.dockerhub.DQPSearch;
import io.enoa.docker.parser.dockerhub.HIParser;
import io.enoa.docker.ret.dockerhub.HRet;
import io.enoa.docker.ret.dockerhub.build.EHAutobuild;
import io.enoa.docker.ret.dockerhub.build.EHBuildHistory;
import io.enoa.docker.ret.dockerhub.explore.EHExplore;
import io.enoa.docker.ret.dockerhub.inspece.EHRepository;
import io.enoa.docker.ret.dockerhub.search.EHSearch;
import io.enoa.docker.ret.dockerhub.tag.EHTag;

public class EnoaDockerHub implements _DockerhubConfigSupport {

  private GenericDockerhub hub;

  public EnoaDockerHub(GenericDockerhub hub) {
    this.hub = hub;
  }

  @Override
  public DockerhubConfig _dockerhubconfig() {
    return this.hub._dockerhubconfig();
  }

  public HRet<EHExplore> explore() {
    return this.hub.explore(HIParser.explore());
  }

  public HRet<EHExplore> explore(DQPPage dqp) {
    return this.hub.explore(HIParser.explore(), dqp);
  }

  public HRet<EHSearch> search(String q) {
    return this.hub.search(HIParser.search(), q);
  }

  public HRet<EHSearch> search(DQPSearch dqp) {
    return this.hub.search(HIParser.search(), dqp);
  }

  public HRet<EHRepository> inspect(String repository) {
    return this.hub.inspect(HIParser.inspect(), repository);
  }

  public HRet<EHTag> tags(String repository) {
    return this.hub.tags(HIParser.tag(), repository);
  }

  public HRet<EHTag> tags(String repository, DQPPage dqp) {
    return this.hub.tags(HIParser.tag(), repository, dqp);
  }

  public HRet<String> dockerfile(String repository) {
    return this.hub.dockerfile(repository);
  }

  public HRet<EHAutobuild> autobuild(String repository) {
    return this.hub.autobuild(HIParser.autobuild(), repository);
  }

  public HRet<EHBuildHistory> history(String repository) {
    return this.hub.history(HIParser.buildhistory(), repository);
  }

  public HRet<EHBuildHistory> history(String repository, DQPPage dqp) {
    return this.hub.history(HIParser.buildhistory(), repository, dqp);
  }

}
