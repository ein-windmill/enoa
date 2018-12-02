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
package io.enoa.docker.async.docker.eo;

import io.enoa.chunk.Chunk;
import io.enoa.docker.command.docker.eo.EnoaDockerImage;
import io.enoa.docker.command.docker.eo.EoDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.image.*;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.image.*;
import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EAsyncEnoaDockerImage {

  private EoDocker docker;
  private EnoaDockerImage image;

  EAsyncEnoaDockerImage(EoDocker docker) {
    this.image = docker.image();
    this.docker = docker;
  }


  public EnqueueAssetDocker<DRet<List<EImage>>> list() {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.list());
  }

  public EnqueueAssetDocker<DRet<List<EImage>>> list(DQPImageList dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.list(dqp));
  }

  public EnqueueAssetDocker<DRet<List<Kv>>> build(String dockerfile, DQPImageBuild dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.build(dockerfile, dqp));
  }

  public EnqueueAssetDocker<DRet<List<Kv>>> build(String dockerfile, DQPImageBuild dqp, Chunk chunk) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.build(dockerfile, dqp, chunk));
  }

  public EnqueueAssetDocker<DRet<EIPrune>> prunebuild() {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.prunebuild());
  }

  public EnqueueAssetDocker<DRet<List<Kv>>> create(DQPImageCreate dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.create(dqp));
  }

  public EnqueueAssetDocker<DRet<List<Kv>>> create(DQPImageCreate dqp, String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.create(dqp, body));
  }

  public EnqueueAssetDocker<DRet<List<Kv>>> create(DQPImageCreate dqp, Chunk chunk) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.create(dqp, chunk));
  }

  public EnqueueAssetDocker<DRet<List<Kv>>> create(DQPImageCreate dqp, String body, Chunk chunk) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.create(dqp, body, chunk));
  }

  public EnqueueAssetDocker<DRet<EIInspect>> inspect(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.inspect(id));
  }

  public EnqueueAssetDocker<DRet<List<EHistory>>> history(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.history(id));
  }

  public EnqueueAssetDocker<DRet<Void>> push(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.push(id));
  }

  public EnqueueAssetDocker<DRet<Void>> push(String id, DQPImagePush dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.push(id, dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> push(String id, Chunk chunk) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.push(id, chunk));
  }

  public EnqueueAssetDocker<DRet<Void>> push(String id, DQPImagePush dqp, Chunk chunk) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.push(id, dqp, chunk));
  }

  public EnqueueAssetDocker<DRet<Void>> tag(String id, DQPImageTag dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.tag(id, dqp));
  }

  public EnqueueAssetDocker<DRet<List<EIRemove>>> remove(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.remove(id));
  }

  public EnqueueAssetDocker<DRet<List<EIRemove>>> remove(String id, DQPImageRmi dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.remove(id, dqp));
  }

  public EnqueueAssetDocker<DRet<List<EISearch>>> search(DQPImageSearch dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.search(dqp));
  }

  public EnqueueAssetDocker<DRet<EImagePrune>> pruneimage() {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.pruneimage());
  }

  public EnqueueAssetDocker<DRet<EImagePrune>> pruneimage(DQPFilter dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.pruneimage(dqp));
  }

  public EnqueueAssetDocker<DRet<EICommit>> commit(String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.commit(body));
  }

  public EnqueueAssetDocker<DRet<EICommit>> commit(String body, DQPImageCommit dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.commit(body, dqp));
  }

  public EnqueueAssetDocker<DRet<EnoaBinary>> export(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.export(id));
  }

  public EnqueueAssetDocker<DRet<EnoaBinary>> export(DQPImageExport dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.export(dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> load(byte[] binary) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.load(binary));
  }

  public EnqueueAssetDocker<DRet<Void>> load(byte[] binary, DQPImageLoad dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.load(binary, dqp));
  }
}
