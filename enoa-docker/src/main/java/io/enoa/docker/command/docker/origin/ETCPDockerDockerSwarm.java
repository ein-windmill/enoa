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

import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmUpdate;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;

public class ETCPDockerDockerSwarm implements EOriginDockerSwarm {

  private EnoaTCPDocker docker;

  ETCPDockerDockerSwarm(EnoaTCPDocker docker) {
    this.docker = docker;
  }

  @Override
  public DResp inspect() {
    HttpResponse response = this.docker.http("swarm").emit();
    return DResp.create(response);
  }

  @Override
  public DResp init(String body) {
    HttpResponse response = this.docker.http("swarm/init")
      .method(HttpMethod.POST)
      .raw(body, "application/json")
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp join(String body) {
    HttpResponse response = this.docker.http("swarm/join")
      .method(HttpMethod.POST)
      .raw(body, "application/json")
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp leave(boolean force) {
    HttpResponse response = this.docker.http("swarm/leave")
      .method(HttpMethod.POST)
      .para("force", force)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp update(DQPSwarmUpdate dqp, String body) {
    Http http = this.docker.http("swarm/update")
      .method(HttpMethod.POST)
      .raw(body, "application/json");
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp unlockkey() {
    HttpResponse response = this.docker.http("swarm/unlockkey").emit();
    return DResp.create(response);
  }

  @Override
  public DResp unlock(String body) {
    HttpResponse response = this.docker.http("swarm/unlock")
      .raw(body, "application/json")
      .emit();
    return DResp.create(response);
  }
}
