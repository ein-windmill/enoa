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

import io.enoa.docker.DockerhubConfig;
import io.enoa.docker.dqp.common.DQPPage;
import io.enoa.docker.dqp.dockerhub.DQPSearch;
import io.enoa.docker.dket.registry.RResp;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.toolkit.text.TextKit;

public class EOriginDockerhubImpl extends AbstractDockerhub {

  public EOriginDockerhubImpl(DockerhubConfig config) {
    super(config);
  }

  private String repository(String repository) {
    return repository == null ? "" :
      (repository.contains("/") ? repository :
        repository.contains("library") ? repository :
          TextKit.union("library/", repository));
  }

  @Override
  public RResp explore(DQPPage dqp) {
    HttpResponse response = super.http("repositories/library")
      .para(dqp.dqr().http())
      .emit();
    return RResp.create(response);
  }

  @Override
  public RResp search(DQPSearch dqp) {
    HttpResponse response = super.http("search/repositories")
      .para(dqp.dqr().http())
      .emit();
    return RResp.create(response);
  }

  @Override
  public RResp inspect(String repository) {
    HttpResponse response = super.http("repositories", this.repository(repository)).emit();
    return RResp.create(response);
  }

  @Override
  public RResp tags(String repository, DQPPage dqp) {
    HttpResponse response = super.http("repositories", this.repository(repository), "tags")
      .para(dqp.dqr().http())
      .emit();
    return RResp.create(response);
  }

  @Override
  public RResp dockerfile(String repository) {
    HttpResponse response = super.http("repositories", this.repository(repository), "dockerfile")
      .emit();
    return RResp.create(response);
  }

  @Override
  public RResp autobuild(String repository) {
    HttpResponse response = super.http("repositories", this.repository(repository), "autobuild")
      .emit();
    return RResp.create(response);
  }

  @Override
  public RResp history(String repository, DQPPage dqp) {
    HttpResponse response = super.http("repositories", this.repository(repository), "buildhistory")
      .para(dqp.dqr().http())
      .emit();
    return RResp.create(response);
  }
}
