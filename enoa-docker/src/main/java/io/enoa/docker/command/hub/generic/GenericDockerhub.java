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
package io.enoa.docker.command.hub.generic;

import io.enoa.docker.DockerhubConfig;
import io.enoa.docker.command.hub._DockerhubConfigSupport;
import io.enoa.docker.command.hub.origin.OriginDockerhub;
import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.common.DQPPage;
import io.enoa.docker.dqp.dockerhub.DQPSearch;
import io.enoa.docker.parser.dockerhub.HIParser;
import io.enoa.docker.dket.dockerhub.HRet;
import io.enoa.docker.dket.registry.RResp;

public class GenericDockerhub implements _DockerhubConfigSupport {

  private OriginDockerhub hub;

  public GenericDockerhub(OriginDockerhub hub) {
    this.hub = hub;
  }

  @Override
  public DockerhubConfig _dockerhubconfig() {
    return this.hub._dockerhubconfig();
  }

  public <T> HRet<T> explore(HIParser<T> parser) {
    return this.explore(parser, DQP.hub().page());
  }

  public <T> HRet<T> explore(HIParser<T> parser, DQPPage dqp) {
    RResp resp = this.hub.explore(dqp);
    return parser.parse(this._dockerhubconfig(), resp);
  }

  public <T> HRet<T> search(HIParser<T> parser, String q) {
    return this.search(parser, DQP.hub().search().q(q));
  }

  public <T> HRet<T> search(HIParser<T> parser, DQPSearch dqp) {
    RResp resp = this.hub.search(dqp);
    return parser.parse(this._dockerhubconfig(), resp);
  }

  public <T> HRet<T> inspect(HIParser<T> parser, String repository) {
    RResp resp = this.hub.inspect(repository);
    return parser.parse(this._dockerhubconfig(), resp);
  }

  public <T> HRet<T> tags(HIParser<T> parser, String repository) {
    return this.tags(parser, repository, DQP.hub().page());
  }

  public <T> HRet<T> tags(HIParser<T> parser, String repository, DQPPage dqp) {
    RResp resp = this.hub.tags(repository, dqp);
    return parser.parse(this._dockerhubconfig(), resp);
  }

  public HRet<String> dockerfile(String repository) {
    RResp resp = this.hub.dockerfile(repository);
    return HIParser.dockerfile().parse(this._dockerhubconfig(), resp);
  }

  public <T> HRet<T> autobuild(HIParser<T> parser, String repository) {
    RResp resp = this.hub.autobuild(repository);
    return parser.parse(this._dockerhubconfig(), resp);
  }

  public <T> HRet<T> history(HIParser<T> parser, String repository) {
    return this.history(parser, repository, DQP.hub().page());
  }

  public <T> HRet<T> history(HIParser<T> parser, String repository, DQPPage dqp) {
    RResp resp = this.hub.history(repository, dqp);
    return parser.parse(this._dockerhubconfig(), resp);
  }
}
