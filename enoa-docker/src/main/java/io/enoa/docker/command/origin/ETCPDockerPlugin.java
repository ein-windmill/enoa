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
import io.enoa.docker.dqp.plugin.DQPPluginInstall;
import io.enoa.docker.dqp.plugin.DQPPluginUpgrade;
import io.enoa.docker.dret.DResp;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.json.Json;
import io.enoa.toolkit.text.TextKit;

import java.util.Collection;

public class ETCPDockerPlugin implements EOriginPlugin {


  private EnoaTCPDocker docker;

  ETCPDockerPlugin(EnoaTCPDocker docker) {
    this.docker = docker;
  }

  @Override
  public DResp list(DQPFilter dqp) {
    Http http = this.docker.http("plugins");
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp privileges(String remote) {
    Http http = this.docker.http("plugins/privileges");
    if (TextKit.blankn())
      http.para("remote", remote);
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp install(DQPPluginInstall dqp) {
    Http http = this.docker.http("plugins/pull")
      .method(HttpMethod.POST);
    if (dqp != null) {
      DQH dqh = dqp.dqh();
      if (dqh != null)
        http.header(dqh.headers());
      DQR dqr = dqp.dqr();
      if (dqr != null)
        http.para(dqr.http());
    }
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp inspect(String id) {
    HttpResponse response = this.docker.http("plugins", id, "json").emit();
    return DResp.create(response);
  }

  @Override
  public DResp remove(String id, boolean force) {
    HttpResponse response = this.docker.http("plugins", id)
      .method(HttpMethod.DELETE)
      .para("force", force)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp enable(String id, int timeout) {
    HttpResponse response = this.docker.http("plugins", id, "enable")
      .method(HttpMethod.POST)
      .para("timeout", timeout)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp disable(String id) {
    HttpResponse response = this.docker.http("plugins", id, "disable")
      .method(HttpMethod.POST)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp upgrade(String id, DQPPluginUpgrade dqp) {
    Http http = this.docker.http("plugins", id, "upgrade")
      .method(HttpMethod.POST);
    if (dqp != null) {
      DQH dqh = dqp.dqh();
      if (dqh != null)
        http.header(dqh.headers());
      DQR dqr = dqp.dqr();
      if (dqr != null)
        http.para(dqr.http());
    }
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp create(String id, String raw) {
    Http http = this.docker.http("plugins/create")
      .method(HttpMethod.POST);
    if (TextKit.blankn())
      http.raw(raw);
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp push(String id) {
    HttpResponse response = this.docker.http("plugins", id, "push")
      .method(HttpMethod.POST)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp set(String id, Collection<String> args) {
    HttpResponse response = this.docker.http("plugins", id, "set")
      .method(HttpMethod.POST)
      .raw(Json.toJson(args), "application/json")
      .emit();
    return DResp.create(response);
  }
}
