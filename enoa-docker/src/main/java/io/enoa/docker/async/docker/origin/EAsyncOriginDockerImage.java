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
package io.enoa.docker.async.docker.origin;

import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.enqueue.EnqueueDoneargDocker;
import io.enoa.docker.command.docker.origin.EOriginDockerImage;
import io.enoa.docker.command.docker.origin.OriginDocker;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.image.*;
import io.enoa.docker.stream.DStream;

public class EAsyncOriginDockerImage {

  private OriginDocker docker;
  private EOriginDockerImage image;

  EAsyncOriginDockerImage(OriginDocker docker) {
    this.docker = docker;
    this.image = docker.image();
  }

  public EnqueueDoneargDocker<DResp> list() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.list());
  }

  public EnqueueDoneargDocker<DResp> list(DQPImageList dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.list(dqp));
  }

  public EnqueueDoneargDocker<DResp> build(String dockerfile, DQPImageBuild dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.build(dockerfile, dqp));
  }

  public EnqueueDoneargDocker<DResp> build(String dockerfile, DQPImageBuild dqp, DStream<String> dstream) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.build(dockerfile, dqp, dstream));
  }

  public EnqueueDoneargDocker<DResp> prunebuild() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.prunebuild());
  }

  public EnqueueDoneargDocker<DResp> create(DQPImageCreate dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.create(dqp));
  }

  public EnqueueDoneargDocker<DResp> create(DQPImageCreate dqp, String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.create(dqp, body));
  }

  public EnqueueDoneargDocker<DResp> create(DQPImageCreate dqp, DStream<String> dstream) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.create(dqp, dstream));
  }

  public EnqueueDoneargDocker<DResp> create(DQPImageCreate dqp, String body, DStream<String> dstream) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.create(dqp, body, dstream));
  }

  public EnqueueDoneargDocker<DResp> inspect(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.inspect(id));
  }

  public EnqueueDoneargDocker<DResp> history(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.history(id));
  }

  public EnqueueDoneargDocker<DResp> push(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.push(id));
  }

  public EnqueueDoneargDocker<DResp> push(String id, DStream<String> dstream) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.push(id, dstream));
  }

  public EnqueueDoneargDocker<DResp> push(String id, DQPImagePush dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.push(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> push(String id, DQPImagePush dqp, DStream<String> dstream) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.push(id, dqp, dstream));
  }

  public EnqueueDoneargDocker<DResp> tag(String id, DQPImageTag dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.tag(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> remove(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.remove(id));
  }

  public EnqueueDoneargDocker<DResp> remove(String id, DQPImageRmi dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.remove(id, dqp));
  }

  public EnqueueDoneargDocker<DResp> search(DQPImageSearch dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.search(dqp));
  }

  public EnqueueDoneargDocker<DResp> pruneimage() {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.pruneimage());
  }

  public EnqueueDoneargDocker<DResp> pruneimage(DQPFilter dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.pruneimage(dqp));
  }

  public EnqueueDoneargDocker<DResp> commit(String body) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.commit(body));
  }

  public EnqueueDoneargDocker<DResp> commit(String body, DQPImageCommit dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.commit(body, dqp));
  }

  public EnqueueDoneargDocker<DResp> export(String id) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.export(id));
  }

  public EnqueueDoneargDocker<DResp> export(DQPImageExport dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.export(dqp));
  }

  public EnqueueDoneargDocker<DResp> load(byte[] binary) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.load(binary));
  }

  public EnqueueDoneargDocker<DResp> load(byte[] binary, DQPImageLoad dqp) {
    return EnqueueDocker.donearg(this.docker._dockerconfig().executor(), () -> this.image.load(binary, dqp));
  }
}
