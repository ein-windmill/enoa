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
package io.enoa.docker.docker;

import io.enoa.chunk.Chunk;
import io.enoa.docker.AbstractDockerTest;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.image.*;
import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.docker.image.*;
import io.enoa.json.Json;
import io.enoa.toolkit.binary.EnoaBinary;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.path.PathKit;
import io.enoa.toolkit.value.Void;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

@Ignore
public class DockerImageTest extends AbstractDockerTest {


  @Test
  public void testList() {
    DRet<List<EImage>> ret = super.docker().image().list(DQPImageList.create().all());
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testBuild() {
    DQPImageBuild dqp = DQPImageBuild.create()
      .t("enoa-alpine:0.1")
      .forcerm();
    String dockerfile = "FROM ubuntu:16.04\n" +
      "RUN mkdir demo";
//    String ret = Docker.origin().image().build(dqp, dockerfile, DStream.<String>builder(System.out::print).build());
//    System.out.println();
//    System.out.println("done===");

//    DRet<List<Kv>> ret = Docker.image().build(dockerfile, dqp, DStream.<Kv>builder(kv -> System.out.println(Json.toJson(kv))).build());

    DRet<List<Kv>> ret = super.docker().image().build(dockerfile, dqp, Chunk.builder((bytes, linebreak) -> {
      Kv kv = Json.parse(EnoaBinary.create(bytes).string(), Kv.class);
      System.out.println(kv);
    }).build());
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testPrunebuild() {
    DRet<EIPrune> ret = super.docker().image().prunebuild();
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testInspect() {
    DRet<EIInspect> ret = super.docker().image().inspect("ubuntu:16.04");
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testHistory() {
    DRet<List<EHistory>> ret = super.docker().image().history("ubuntu:16:04");
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testPush() {
    DRet<Void> ret = super.docker().image().push("ubuntu:16:04");
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }

  @Test
  public void testTag() {
    DRet<Void> ret = super.docker().image().tag("ubuntu:16.04", DQPImageTag.create()
      .repo("registry.host.com/ubuntu")
      .tag("16:04"));
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }

  @Test
  public void testRemove() {
    DRet<List<EIRemove>> ret = super.docker().image().remove("ubuntu:16.04");
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testSearch() {
    DRet<List<EISearch>> ret = super.docker().image().search(DQP.docker().image().search().term("nginx"));
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testPruneimage() {
    DRet<EImagePrune> ret = super.docker().image().pruneimage();
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testCommit() {
    String body = "{}";
    DRet<EICommit> ret = super.docker().image().commit(body);
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testExport() {
    DRet<EnoaBinary> ret = super.docker().image().export("nginx");
    Assert.assertTrue(ret.ok());
    EnoaBinary binary = ret.data();
    FileKit.write(PathKit.debugPath().resolve("_tmp/nginx.tgz"), binary.bytebuffer());
    System.out.println();
  }

  @Test
  public void testExportSeveral() {
    DRet<EnoaBinary> ret = super.docker().image().export(DQPImageExport.create().names("nginx"));
    Assert.assertTrue(ret.ok());
    EnoaBinary binary = ret.data();
    FileKit.write(PathKit.debugPath().resolve("_tmp/nginx.tgz"), binary.bytebuffer());
    System.out.println();
  }

  @Test
  public void testLoad() {
    EnoaBinary binary = FileKit.read(PathKit.debugPath().resolve("_tmp/nginx.tgz"));
    DRet<Void> ret = super.docker().image().load(binary.bytes());
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }


  @Test
  public void testCreate() {
    super.docker().image().create(DQPImageCreate.create().fromimage("docker.io/alpine").tag("3.8"),
      Chunk.generic(bytes -> EnoaBinary.create(bytes).string(), (text, linebreak) -> System.out.println(text)));
  }

}
