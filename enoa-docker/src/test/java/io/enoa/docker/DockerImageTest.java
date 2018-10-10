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
package io.enoa.docker;

import io.enoa.docker.dqp.image.DQPBuild;
import io.enoa.docker.dqp.image.DQPListImage;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.dret.image.EImage;
import io.enoa.docker.stream.DStream;
import io.enoa.json.Json;
import io.enoa.toolkit.map.Kv;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

@Ignore
public class DockerImageTest extends AbstractDockerTest {


  @Test
  public void testList() {
    DRet<List<EImage>> ret = Docker.image().list(DQPListImage.create().all());
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testBuild() {
    DQPBuild dqp = DQPBuild.create()
      .t("enoa-alpine:0.1")
      .forcerm();
    String dockerfile = "FROM ubuntu:16.04\n" +
      "RUN mkdir demo";
//    String ret = Docker.origin().image().build(dqp, dockerfile, DStream.<String>builder(System.out::print).build());
//    System.out.println();
//    System.out.println("done===");

    DRet<List<Kv>> ret = Docker.image().build(dqp, dockerfile, DStream.<Kv>builder(kv -> System.out.println(Json.toJson(kv))).build());
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

}
