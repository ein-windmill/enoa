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
package io.enoa.docker.command.origin;

import io.enoa.docker.dqp.container.DQPListContainer;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;

public class ETCPDockerContainer implements EOriginDockerContainer {

  private EnoaTCPDocker docker;

  ETCPDockerContainer(EnoaTCPDocker docker) {
    this.docker = docker;
  }


  @Override
  public String ps(DQPListContainer dqp) {
    HttpResponse response = this.docker.http("/containers/json")
      .para(dqp.dqr().httpparas())
      .emit();
    return response.body().string();
  }

  @Override
  public String create(String name, String body) {
    HttpResponse response = this.docker.http("/containers/create")
      .method(HttpMethod.POST)
      .para("name", name)
      .raw(body, "application/json")
      .emit();
    return response.body().string();
  }

  @Override
  public String inspect(String id, Boolean size) {
    return null;
  }

}
