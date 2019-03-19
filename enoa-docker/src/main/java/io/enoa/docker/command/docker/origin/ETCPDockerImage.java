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
import io.enoa.docker.dqp.DQH;
import io.enoa.docker.dqp.DQR;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.image.*;
import io.enoa.docker.tar.DTar;
import io.enoa.docker.thr.DockerException;
import io.enoa.http.Http;
import io.enoa.http.protocol.HttpMethod;
import io.enoa.http.protocol.HttpResponse;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.text.TextKit;

public class ETCPDockerImage implements EOriginDockerImage {

  private EnoaTCPDocker docker;

  ETCPDockerImage(EnoaTCPDocker docker) {
    this.docker = docker;
  }

  @Override
  public DResp list(DQPImageList dqp) {
    Http http = this.docker.http("images/json")
      .method(HttpMethod.GET);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp build(String dockerfile, DQPImageBuild dqp, Chunk chunk) {
    if (dqp == null)
      throw new DockerException(EnoaTipKit.message("eo.tip.docker.lost_dqp"));
    DQR dqr = dqp.dqr();
    Http http = this.docker.http("build")
      .method(HttpMethod.POST)
      .para(dqr.http())
      .header(dqp.dqh().headers())
      .binary(DTar.cvf(dqr.value("dockerfile").string(), dockerfile).bytebuffer());
    HttpResponse response = chunk == null ?
      http.emit() :
      http.chunk(chunk);
    return DResp.create(response);
  }

  @Override
  public DResp prunebuild() {
    HttpResponse response = this.docker.http("build/prune")
      .method(HttpMethod.POST)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp create(DQPImageCreate dqp, String body, Chunk chunk) {
    Http http = this.docker.http("images/create")
      .method(HttpMethod.POST);
    if (dqp != null) {
      DQH dqh = dqp.dqh();
      if (dqh != null)
        http.header(dqh.headers());
      DQR dqr = dqp.dqr();
      if (dqr != null)
        http.para(dqr.http());
    }
    if (TextKit.blankn(body))
      http.raw(body);

    HttpResponse response = chunk == null ?
      http.emit() :
      http.chunk(chunk);
    return DResp.create(response);
  }

//  @Override
//  public DResp create(String body, DQPImageCreate dqp) {
//    Http http = this.docker.http("images/create")
//      .method(HttpMethod.POST);
//    if (dqp != null) {
//      DQH dqh = dqp.dqh();
//      if (dqh != null)
//        http.header(dqh.headers());
//      DQR dqr = dqp.dqr();
//      if (dqr != null)
//        http.para(dqr.http());
//    }
//    HttpResponse response = http.emit();
//    return DResp.create(response);
//  }

  @Override
  public DResp inspect(String id) {
    HttpResponse response = this.docker.http("images", id, "json")
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp history(String id) {
    HttpResponse response = this.docker.http("images", id, "history")
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp push(String id, DQPImagePush dqp, Chunk chunk) {
    Http http = this.docker.http("images", id, "push")
      .method(HttpMethod.POST);
    if (dqp != null) {
      DQH dqh = dqp.dqh();
      if (dqh != null)
        http.header(dqh.headers());
      DQR dqr = dqp.dqr();
      if (dqr != null)
        http.para(dqr.http());
    }
    HttpResponse response = chunk == null ?
      http.emit() :
      http.chunk(chunk);
    return DResp.create(response);
  }

  @Override
  public DResp tag(String id, DQPImageTag dqp) {
    HttpResponse response = this.docker.http("images", id, "tag")
      .method(HttpMethod.POST)
      .para(dqp.dqr().http())
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp remove(String id, DQPImageRmi dqp) {
    Http http = this.docker.http("images", id)
      .method(HttpMethod.DELETE);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp search(DQPImageSearch dqp) {
    HttpResponse response = this.docker.http("images/search")
      .method(HttpMethod.GET)
      .para(dqp.dqr().http())
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp pruneimage(DQPFilter dqp) {
    Http http = this.docker.http("images/prune")
      .method(HttpMethod.POST);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp commit(String body, DQPImageCommit dqp) {
    Http http = this.docker.http("commit")
      .method(HttpMethod.POST)
      .raw(body, "application/json");
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp export(String id) {
    HttpResponse response = this.docker.http("images", id, "get")
      .method(HttpMethod.GET)
      .emit();
    return DResp.create(response);
  }

  @Override
  public DResp export(DQPImageExport dqp) {
    Http http = this.docker.http("images/get");
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }

  @Override
  public DResp load(byte[] binary, DQPImageLoad dqp) {
    Http http = this.docker.http("images/load")
      .method(HttpMethod.POST)
      .binary(binary);
    if (dqp != null)
      http.para(dqp.dqr().http());
    HttpResponse response = http.emit();
    return DResp.create(response);
  }
}
