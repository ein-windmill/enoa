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
package io.enoa.docker.command.docker.eo;

import io.enoa.docker.command.docker.generic.EGenericDockerImage;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.docker.image.*;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.image.*;
import io.enoa.docker.stream.DStream;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.value.Void;

import java.util.List;

public class EnoaDockerImage {


  private GenericDocker docker;
  private EGenericDockerImage image;

  EnoaDockerImage(GenericDocker docker) {
    this.docker = docker;
    this.image = docker.image();
  }

  public DRet<List<EImage>> list() {
    return this.image.list(DIParser.imagelist());
  }

  public DRet<List<EImage>> list(DQPImageList dqp) {
    return this.image.list(DIParser.imagelist(), dqp);
  }

  public DRet<List<Kv>> build(String dockerfile, DQPImageBuild dqp) {
    return this.image.build(dockerfile, dqp);
  }

  public DRet<List<Kv>> build(String dockerfile, DQPImageBuild dqp, DStream<Kv> dstream) {
    return this.image.build(dockerfile, dqp, dstream);
  }

  public DRet<EIPrune> prunebuild() {
    return this.image.prunebuild(DIParser.buildprune());
  }

  public DRet<List<Kv>> create(DQPImageCreate dqp) {
    return this.image.create(dqp);
  }

  public DRet<List<Kv>> create(DQPImageCreate dqp, String body) {
    return this.image.create(dqp, body);
  }

  public DRet<List<Kv>> create(DQPImageCreate dqp, DStream<Kv> dstream) {
    return this.image.create(dqp, dstream);
  }

  public DRet<List<Kv>> create(DQPImageCreate dqp, String body, DStream<Kv> dstream) {
    return this.image.create(dqp, body, dstream);
  }

  public DRet<EIInspect> inspect(String id) {
    return this.image.inspect(DIParser.imageinspect(), id);
  }

  public DRet<List<EHistory>> history(String id) {
    return this.image.history(DIParser.imagehistory(), id);
  }

  public DRet<Void> push(String id) {
    return this.image.push(id);
  }

  public DRet<Void> push(String id, DQPImagePush dqp) {
    return this.image.push(id, dqp);
  }

  public DRet<Void> push(String id, DStream<Kv> dstream) {
    return this.image.push(id, dstream);
  }

  public DRet<Void> push(String id, DQPImagePush dqp, DStream<Kv> dstream) {
    return this.image.push(id, dqp, dstream);
  }

  public DRet<Void> tag(String id, DQPImageTag dqp) {
    return this.image.tag(id, dqp);
  }

  public DRet<List<EIRemove>> remove(String id) {
    return this.remove(id, null);
  }

  public DRet<List<EIRemove>> remove(String id, DQPImageRmi dqp) {
    return this.image.remove(DIParser.imageremove(), id, dqp);
  }

  public DRet<List<EISearch>> search(DQPImageSearch dqp) {
    return this.image.search(DIParser.imagesearch(), dqp);
  }

  public DRet<EImagePrune> pruneimage() {
    return this.pruneimage(null);
  }

  public DRet<EImagePrune> pruneimage(DQPFilter dqp) {
    return this.image.pruneimage(DIParser.imageprune(), dqp);
  }

  public DRet<EICommit> commit(String body) {
    return this.image.commit(DIParser.imagecommit(), body);
  }

  public DRet<EICommit> commit(String body, DQPImageCommit dqp) {
    return this.image.commit(DIParser.imagecommit(), body, dqp);
  }

  public DRet<EnoaBinary> export(String id) {
    return this.image.export(DIParser.binary(), id);
  }

  public DRet<EnoaBinary> export(DQPImageExport dqp) {
    return this.image.export(DIParser.binary(), dqp);
  }

  public DRet<Void> load(byte[] binary) {
    return this.image.load(binary);
  }

  public DRet<Void> load(byte[] binary, DQPImageLoad dqp) {
    return this.image.load(binary, dqp);
  }

}
