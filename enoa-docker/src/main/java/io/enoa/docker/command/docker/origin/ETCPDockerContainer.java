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

import io.enoa.chunk.Chunk;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dqp.DQR;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.container.*;
import io.enoa.http.EoUrl;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpPara;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.toolkit.text.TextKit;

import java.util.Set;

public class ETCPDockerContainer implements EOriginDockerContainer {

  private EnoaTCPDocker docker;

  ETCPDockerContainer(EnoaTCPDocker docker) {
    this.docker = docker;
  }


  @Override
  public DResp list(DQPContainerList dqp) {
    Http http = this.docker.http("/containers/json");
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp create(String name, String body) {
    HttpResponse response = this.docker.http("/containers/create")
      .method(HttpMethod.POST)
      .para("name", name)
      .raw(body, "application/json")
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp inspect(String id, Boolean size) {
    HttpResponse response = this.docker.http("containers", id, "json")
      .para("size", size)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp top(String id, String para) {
    Http http = this.docker.http("containers", id, "top");
    if (TextKit.blankn(para))
      http.para("ps_args", para);
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp logs(String id, DQPContainerLogs dqp) {
    Http http = this.docker.http("containers", id, "logs");
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp changes(String id) {
    HttpResponse response = this.docker.http("containers", id, "changes")
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp export(String id) {
    HttpResponse response = this.docker.http("containers", id, "export")
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp statistics(String id) {
    HttpResponse response = this.docker.http("containers", id, "stats")
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp statistics(String id, Chunk chunk) {
//    Chunk.Builder builder = Chunk.builder(bytes -> dstream.runner().run(EnoaBinary.create(bytes, EoConst.CHARSET).string()))
//      .stopper(() -> dstream.stopper() == null ? Boolean.FALSE : dstream.stopper().stop());
    HttpResponse response = this.docker.http("containers", id, "stats")
      .para("stream", true)
//      .chunk(builder.build());
      .chunk(chunk);
    return DResp.create(response);
  }

  @Override
  public DResp resize(String id, DQPResize dqp) {
    Http http = this.docker.http("containers", id, "resize")
      .method(HttpMethod.POST);
    if (dqp != null) {
      http.para(dqp.dqr().http());
    }
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp start(String id, DQPContainerStart dqp) {
    Http http = this.docker.http("containers", id, "start")
      .method(HttpMethod.POST);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp stop(String id, DQPContainerTime dqp) {
    Http http = this.docker.http("containers", id, "stop")
      .method(HttpMethod.POST);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp restart(String id, DQPContainerTime dqp) {
    Http http = this.docker.http("containers", id, "restart")
      .method(HttpMethod.POST);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp kill(String id, DQPContainerKill dqp) {
    Http http = this.docker.http("containers", id, "kill")
      .method(HttpMethod.POST);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp update(String id, String body) {
    HttpResponse response = this.docker.http("containers", id, "update")
      .method(HttpMethod.POST)
      .raw(body, "application/json")
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp rename(String id, String name) {
    HttpResponse response = this.docker.http("containers", id, "rename")
      .method(HttpMethod.POST)
      .para("name", name)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp pause(String id) {
    HttpResponse response = this.docker.http("containers", id, "pause")
      .method(HttpMethod.POST)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp unpause(String id) {
    HttpResponse response = this.docker.http("containers", id, "unpause")
      .method(HttpMethod.POST)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp attach(String id, DQPContainerAttach dqp, Chunk chunk) {
    // fixme sometimes attach will return value include stdin=1&stream=1&stdout=1&stderr=1 fix it.
    /*
  stdin=1&stream=1&stdout=1&stderr=1* [32mmaster[m
  [31mremotes/origin/HEAD[m -> origin/master
  [31mremotes/origin/dev[m
  [31mremotes/origin/master[m
     */
    DQR dqr = dqp == null ? DQR.empty() : dqp.dqr();
    Set<HttpPara> paras = dqr.http();
    EoUrl url = this.docker.url("containers", id, "attach");
    paras.forEach(para -> url.para(para.name(), para.value()));
    Http http = this.docker.http(url)
      .method(HttpMethod.POST)
      .header("Upgrade", "tcp")
      .header("Connection", "Upgrade")
      .header("Content-Type", "text/aplin");
    HttpResponse response = chunk == null ?
      http.emit() :
      http.chunk(chunk);
    return DResp.create(response);
  }

  @Override
  public DResp wait(String id, String condition) {
    HttpResponse response = this.docker.http(this.docker.url("containers", id, "wait").para("condition", condition))
      .method(HttpMethod.POST)
      .emit();
//      .chunk(Chunk.builder(bytes -> System.out.println(EnoaBinary.create(bytes).string())).build());
    return DResp.create(response);
  }

  @Override
  public DResp remove(String id, DQPContainerRemove dqp) {
    Http http = this.docker.http("containers", id)
      .method(HttpMethod.DELETE);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp archive(String id, String path) {
    HttpResponse response = this.docker.http("containers", id, "archive")
      .method(HttpMethod.GET)
      .para("path", path)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp prune(DQPFilter dqp) {
    Http http = this.docker.http("containers/prune")
      .method(HttpMethod.POST);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }
}
