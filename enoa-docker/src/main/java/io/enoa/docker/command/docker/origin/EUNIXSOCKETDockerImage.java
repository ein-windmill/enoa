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

import io.enoa.docker.dqp.docker.common.DQPFilter;
import io.enoa.docker.dqp.docker.image.*;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.stream.DStream;

public class EUNIXSOCKETDockerImage implements EOriginDockerImage {

  private EnoaUNIXSOCKETDocker docker;

  EUNIXSOCKETDockerImage(EnoaUNIXSOCKETDocker docker) {
    this.docker = docker;
  }

  @Override
  public DResp list(DQPImageList dqp) {
    return null;
  }

  @Override
  public DResp build(DQPImageBuild dqp, String dockerfile, DStream<String> dstream) {
    return null;
  }

  @Override
  public DResp prunebuild() {
    return null;
  }

  @Override
  public DResp create(DQPImageCreate dqp, String body) {
    return null;
  }

  @Override
  public DResp inspect(String id) {
    return null;
  }

  @Override
  public DResp history(String id) {
    return null;
  }

  @Override
  public DResp push(String id, DQPImagePush dqp, DStream<String> dstream) {
    return null;
  }

  @Override
  public DResp tag(String id, DQPImageTag dqp) {
    return null;
  }

  @Override
  public DResp remove(String id, DQPImageRmi dqp) {
    return null;
  }

  @Override
  public DResp search(DQPImageSearch dqp) {
    return null;
  }

  @Override
  public DResp pruneimage(DQPFilter dqp) {
    return null;
  }

  @Override
  public DResp commit(String body, DQPImageCommit dqp) {
    return null;
  }

  @Override
  public DResp export(String id) {
    return null;
  }

  @Override
  public DResp export(DQPImageExportSeveral dqp) {
    return null;
  }

  @Override
  public DResp load(byte[] binary, DQPImageLoad dqp) {
    return null;
  }
}
