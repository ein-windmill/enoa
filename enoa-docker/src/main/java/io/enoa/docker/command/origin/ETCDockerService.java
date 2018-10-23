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

import io.enoa.docker.dqp.DQH;
import io.enoa.docker.dqp.DQR;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.service.DQPServiceCreate;
import io.enoa.docker.dqp.service.DQPServiceUpdate;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.ret.docker.service.DQPServiceLogs;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;

public class ETCDockerService implements EOriginService {

  private EnoaTCPDocker docker;

  ETCDockerService(EnoaTCPDocker docker) {
    this.docker = docker;
  }

  @Override
  public DResp list(DQPFilter dqp) {
    Http http = this.docker.http("services");
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp create(String body, DQPServiceCreate dqp) {
    Http http = this.docker.http("services/create")
      .method(HttpMethod.POST)
      .raw(body, "application/json");
    if (dqp != null)
      http.header(dqp.dqh().headers());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp inspect(String id, boolean insertDefaults) {
    HttpResponse response = this.docker.http("services/", id)
      .para("insertDefaults", insertDefaults)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp remove(String id) {
    HttpResponse response = this.docker.http("services", id)
      .method(HttpMethod.DELETE)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp update(String id, String body, DQPServiceUpdate dqp) {
    Http http = this.docker.http("services", id, "update")
      .method(HttpMethod.POST)
      .raw(body, "application/json");
    if (dqp != null) {
      DQR dqr = dqp.dqr();
      if (dqr != null)
        http.para(dqr.http());
      DQH dqh = dqp.dqh();
      if (dqh != null)
        http.header(dqh.headers());
    }
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp logs(String id, DQPServiceLogs dqp) {
    Http http = this.docker.http("services", id);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }
}
