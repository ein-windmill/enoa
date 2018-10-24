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

import io.enoa.docker.dqp.docker.system.DQPMonitor;
import io.enoa.docker.dqp.docker.system.DQPSystemAuth;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;

public class ETCPDockerDockerSystem implements EOriginDockerSystem {

  private EnoaTCPDocker docker;

  ETCPDockerDockerSystem(EnoaTCPDocker docker) {
    this.docker = docker;
  }

  @Override
  public DResp auth(DQPSystemAuth dqp) {
    Http http = this.docker.http("auth")
      .method(HttpMethod.POST);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp info() {
    HttpResponse response = this.docker.http("info").emit();
    return DResp.create(response);
  }

  @Override
  public DResp version() {
    HttpResponse response = this.docker.http("version").emit();
    return DResp.create(response);
  }

  @Override
  public DResp ping() {
    HttpResponse response = this.docker.http("_ping").emit();
    return DResp.create(response);
  }

  @Override
  public DResp monitor(DQPMonitor dqp) {
    Http http = this.docker.http("events");
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp df() {
    HttpResponse response = this.docker.http("system/df").emit();
    return DResp.create(response);
  }
}
