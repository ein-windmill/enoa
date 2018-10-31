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
package io.enoa.docker.command.hub.origin;

import io.enoa.docker.command.hub._DockerhubConfigSupport;
import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.common.DQPPage;
import io.enoa.docker.dqp.dockerhub.DQPSearch;
import io.enoa.docker.dket.registry.RResp;

public interface OriginDockerhub extends _DockerhubConfigSupport {

  default RResp explore() {
    return this.explore(DQP.hub().page());
  }

  RResp explore(DQPPage dqp);

  default RResp search(String q) {
    return this.search(DQP.hub().search().q(q));
  }

  RResp search(DQPSearch dqp);

  RResp inspect(String repository);

  default RResp tags(String repository) {
    return this.tags(repository, DQP.hub().page(50));
  }

  RResp tags(String repository, DQPPage dqp);

  RResp dockerfile(String repository);

  RResp autobuild(String repository);

  default RResp history(String repository) {
    return this.history(repository, DQP.hub().page(50));
  }

  RResp history(String repository, DQPPage dqp);

}
