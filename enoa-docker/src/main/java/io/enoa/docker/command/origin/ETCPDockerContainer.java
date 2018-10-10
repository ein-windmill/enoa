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

import io.enoa.docker.dqp.container.*;
import io.enoa.docker.stream.DStream;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.http.protocol.chunk.Chunk;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.text.TextKit;

public class ETCPDockerContainer implements EOriginDockerContainer {

  private EnoaTCPDocker docker;

  ETCPDockerContainer(EnoaTCPDocker docker) {
    this.docker = docker;
  }


  @Override
  public String list(DQPListContainer dqp) {
    Http http = this.docker.http("/containers/json");
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
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
    HttpResponse response = this.docker.http("containers", id, "json")
      .para("size", size)
      .emit();
    return response.body().string();
  }

  @Override
  public String top(String id, String para) {
    Http http = this.docker.http("containers", id, "top");
    if (TextKit.blankn(para))
      http.para("ps_args", para);
    HttpResponse response = http.emit();
    return response.body().string();
  }

  @Override
  public String logs(String id, DQPLogs dqp) {
    Http http = this.docker.http("containers", id, "logs");
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return response.body().string();
  }

  @Override
  public String changes(String id) {
    HttpResponse response = this.docker.http("containers", id, "changes")
      .emit();
    return response.body().string();
  }

  @Override
  public String export(String id) {
    HttpResponse response = this.docker.http("containers", id, "export")
      .emit();
    return response.body().string();
  }

  @Override
  public String statistics(String id) {
    HttpResponse response = this.docker.http("containers", id, "stats")
      .emit();
    return response.body().string();
  }

  @Override
  public String statistics(String id, DStream<String> dstream) {
    Chunk.Builder builder = Chunk.builder(bytes -> dstream.runner().run(EnoaBinary.create(bytes, EoConst.CHARSET).string()));
    if (dstream.stopper() != null) {
      builder.stopper(dstream.stopper()::stop);
    }
    HttpResponse response = this.docker.http("containers", id, "stats")
      .para("stream", true)
      .chunk(builder.build());
    return response.body().string();
  }

  @Override
  public String resize(String id, DQPResize dqp) {
    Http http = this.docker.http("container", id, "resize")
      .method(HttpMethod.POST);
    if (dqp != null) {
      http.para(dqp.dqr().http());
    }
    HttpResponse response = http.emit();
    return response.body().string();
  }

  @Override
  public String start(String id, DQPStart dqp) {
    Http http = this.docker.http("containers", id, "start")
      .method(HttpMethod.POST);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return response.body().string();
  }

  @Override
  public String stop(String id, DQPTime dqp) {
    Http http = this.docker.http("containers", id, "stop")
      .method(HttpMethod.POST);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return response.body().string();
  }

  @Override
  public String restart(String id, DQPTime dqp) {
    Http http = this.docker.http("containers", id, "restart")
      .method(HttpMethod.POST);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return response.body().string();
  }

  @Override
  public String kill(String id, DQPKill dqp) {
    Http http = this.docker.http("containers", id, "kill")
      .method(HttpMethod.POST);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return response.body().string();
  }

  @Override
  public String update(String id, String body) {
    HttpResponse response = this.docker.http("containers", id, "update")
      .method(HttpMethod.POST)
      .raw(body, "application/json")
      .emit();
    return response.body().string();
  }

  @Override
  public String rename(String id, String name) {
    HttpResponse response = this.docker.http("containers", id, "rename")
      .method(HttpMethod.POST)
      .para("name", name)
      .emit();
    return response.body().string();
  }

  @Override
  public String pause(String id) {
    HttpResponse response = this.docker.http("containers", id, "pause")
      .method(HttpMethod.POST)
      .emit();
    return response.body().string();
  }

  @Override
  public String unpause(String id) {
    HttpResponse response = this.docker.http("containers", id, "unpause")
      .method(HttpMethod.POST)
      .emit();
    return response.body().string();
  }

  @Override
  public String attach(String id, DQPAttch dqp) {
    Http http = this.docker.http("containers", id, "attach")
      .method(HttpMethod.POST);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return response.body().string();
  }

  @Override
  public String wait(String id, String condition) {
    HttpResponse response = this.docker.http("containers", id, "wait")
      .method(HttpMethod.POST)
      .para("condition", condition)
      .emit();
    return response.body().string();
  }

  @Override
  public String remove(String id, DQPRemove dqp) {
    Http http = this.docker.http("containers", id)
      .method(HttpMethod.DELETE);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return response.body().string();
  }

  @Override
  public EnoaBinary archive(String id, String path) {
    HttpResponse response = this.docker.http("containers", id, "archive")
      .method(HttpMethod.GET)
      .para("path", path)
      .emit();
    return EnoaBinary.create(response.body().bytes());
  }

  @Override
  public String prune(DQPPrune dqp) {
    Http http = this.docker.http("containers/prune")
      .method(HttpMethod.POST);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return response.body().string();
  }
}
