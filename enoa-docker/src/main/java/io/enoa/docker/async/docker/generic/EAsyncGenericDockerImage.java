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
package io.enoa.docker.async.docker.generic;

import io.enoa.docker.enqueue.EnqueueAssetDocker;
import io.enoa.docker.enqueue.EnqueueDocker;
import io.enoa.docker.command.docker.generic.EGenericDockerImage;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.image.*;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.docker.stream.DStream;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EAsyncGenericDockerImage {


  private GenericDocker docker;
  private EGenericDockerImage image;

  EAsyncGenericDockerImage(GenericDocker docker) {
    this.docker = docker;
    this.image = docker.image();
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> list(DIParser<List<T>> parser) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.list(parser));
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> list(DIParser<List<T>> parser, DQPImageList dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.list(parser, dqp));
  }

  public EnqueueAssetDocker<DRet<List<Kv>>> build(String dockerfile, DQPImageBuild dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.build(dockerfile, dqp));
  }

  public EnqueueAssetDocker<DRet<List<Kv>>> build(String dockerfile, DQPImageBuild dqp, DStream<Kv> dstream) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.build(dockerfile, dqp, dstream));
  }

  public <T> EnqueueAssetDocker<DRet<T>> prunebuild(DIParser<T> parser) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.prunebuild(parser));
  }

  public EnqueueAssetDocker<DRet<List<Kv>>> create(DQPImageCreate dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.create(dqp));
  }

  public EnqueueAssetDocker<DRet<List<Kv>>> create(DQPImageCreate dqp, String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.create(dqp, body));
  }

  public EnqueueAssetDocker<DRet<List<Kv>>> create(DQPImageCreate dqp, DStream<Kv> dstream) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.create(dqp, dstream));
  }

  public EnqueueAssetDocker<DRet<List<Kv>>> create(DQPImageCreate dqp, String body, DStream<Kv> dstream) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.create(dqp, body, dstream));
  }

  public <T> EnqueueAssetDocker<DRet<T>> inspect(DIParser<T> parser, String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.inspect(parser, id));
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> history(DIParser<List<T>> parser, String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.history(parser, id));
  }

  public EnqueueAssetDocker<DRet<Void>> push(String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.push(id));
  }

  public EnqueueAssetDocker<DRet<Void>> push(String id, DQPImagePush dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.push(id, dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> push(String id, DStream<Kv> dstream) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.push(id, dstream));
  }

  public EnqueueAssetDocker<DRet<Void>> push(String id, DQPImagePush dqp, DStream<Kv> dstream) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.push(id, dqp, dstream));
  }

  public EnqueueAssetDocker<DRet<Void>> tag(String id, DQPImageTag dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.tag(id, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> remove(DIParser<List<T>> parser, String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.remove(parser, id));
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> remove(DIParser<List<T>> parser, String id, DQPImageRmi dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.remove(parser, id, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<List<T>>> search(DIParser<List<T>> parser, DQPImageSearch dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.search(parser, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> pruneimage(DIParser<T> parser) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.pruneimage(parser));
  }

  public <T> EnqueueAssetDocker<DRet<T>> pruneimage(DIParser<T> parser, DQPFilter dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.pruneimage(parser, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> commit(DIParser<T> parser, String body) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.commit(parser, body));
  }

  public <T> EnqueueAssetDocker<DRet<T>> commit(DIParser<T> parser, String body, DQPImageCommit dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.commit(parser, body, dqp));
  }

  public <T> EnqueueAssetDocker<DRet<T>> export(DIParser<T> parser, String id) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.export(parser, id));
  }

  public <T> EnqueueAssetDocker<DRet<T>> export(DIParser<T> parser, DQPImageExport dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.export(parser, dqp));
  }

  public EnqueueAssetDocker<DRet<Void>> load(byte[] binary) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.load(binary));
  }

  public EnqueueAssetDocker<DRet<Void>> load(byte[] binary, DQPImageLoad dqp) {
    return EnqueueDocker.asseterdocker(this.docker._dockerconfig().executor(), () -> this.image.load(binary, dqp));
  }
}
