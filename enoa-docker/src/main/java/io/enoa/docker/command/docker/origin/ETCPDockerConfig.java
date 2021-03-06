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
package io.enoa.docker.command.docker.origin;

import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;

public class ETCPDockerConfig implements EOriginDockerConfig {

  private EnoaTCPDocker docker;

  ETCPDockerConfig(EnoaTCPDocker docker) {
    this.docker = docker;
  }

  @Override
  public DResp list(DQPFilter dqp) {
    Http http = this.docker.http("configs");
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp create(String body) {
    HttpResponse response = this.docker.http("configs/create")
      .method(HttpMethod.POST)
      .raw(body, "application/json")
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp inspect(String id) {
    HttpResponse response = this.docker.http("configs", id)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp remove(String id) {
    HttpResponse response = this.docker.http("configs", id)
      .method(HttpMethod.DELETE)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp update(String id, long version, String body) {
    HttpResponse response = this.docker.http("configs", id, "update")
      .method(HttpMethod.POST)
      .para("version", version)
      .raw(body, "application/json")
      .emit();
    return DResp.create(response);
  }
}
