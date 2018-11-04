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

import io.enoa.docker.AbstractDockerTest;
import io.enoa.docker.Docker;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.common.ECreatedWithWarning;
import io.enoa.docker.dket.docker.container.*;
import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.docker.container.DQPContainerUpdate;
import io.enoa.docker.stream.DStream;
import io.enoa.json.Json;
import io.enoa.toolkit.EoConst;
import io.enoa.toolkit.file.FileKit;
import io.enoa.toolkit.path.PathKit;
import io.enoa.toolkit.text.TextKit;
import io.enoa.toolkit.value.Void;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Ignore
public class DockerContainerTest extends AbstractDockerTest {


  @Test
  public void testList() {
    DRet<List<EContainer>> ret = Docker.container().list();
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testInspect() {
    DRet<ECInspect> ret = Docker.container().inspect("gitbook");
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testTop() {
    DRet<EProcesses> ret = Docker.container().top("gitbook");
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testLogs() {
//    DRet<String> ret = Docker.container().logs("nginx", DQPContainerLogs.create().stdout());
    DRet<String> ret = Docker.container().logs("nginx", DQP.docker().container().logs().stdout());
    Assert.assertTrue(ret.ok());
    String logs = ret.data();
    System.out.println(logs);
  }

  @Test
  public void testChanges() {
    DRet<List<EChange>> ret = Docker.container().changes("redis");
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testExport() {
    DRet<Void> ret = Docker.container().export("nginx");
    Assert.assertTrue(ret.ok());
    Void data = ret.data();
    System.out.println(data);
  }

  @Test
  public void testStatistics() {
    DRet<EStatistics> ret = Docker.container().statistics("nginx", DStream.<DRet<EStatistics>>builder(stats -> {
      Assert.assertTrue(stats.ok());
      String json = Json.toJson(stats.data());
      System.out.println(json);
    }).build());
//    Assert.assertTrue(ret.ok());
//    String json = Json.toJson(ret.data());
//    System.out.println(json);
  }

  @Test
  public void testResize() {
    DRet<Void> ret = Docker.container().resize("nginx");
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }

  @Test
  public void testRestart() {
    DRet<Void> ret = Docker.container().restart("nginx");
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }

  @Test
  public void testKill() {
    DRet<Void> ret = Docker.container().kill("nginx");
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }

  @Test
  public void testUpdate() {
    DRet<EUpdate> ret = Docker.container().update("nginx", DQPContainerUpdate.create());
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testRename() {
    DRet<Void> ret = Docker.container().rename("nginx", "nginxx");
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }

  @Test
  public void testPause() {
    DRet<Void> ret = Docker.container().pause("nginx");
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }

  @Test
  public void testUnpause() {
    DRet<Void> ret = Docker.container().unpause("nginx");
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }

  @Test
  public void testArchive() {
//    DRet<Void> ret = Docker.container().archive("nginx", "/home");
//    Assert.assertTrue(ret.ok());
////    String json = Json.toJson(ret.data());
//    System.out.println(ret);
    DResp resp = Docker.origin().container().archive("nginx", "/homne");
  }

  @Test
  public void testPrune() {
    DRet<ECPrune> ret = Docker.container().prune();
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }


  @Test
  public void testCreate() {
    DRet<ECreatedWithWarning> ret = Docker.container().create("test",
      DQP.docker().container().create()
        .interactive()
        .detach()
        .tty()
        .rm()
        .name("test")
        .volume("/data:/data")
        .volume("/opt:/opt")
        .volume("test:/testx")
        .publish("999:800")
        .publish("127.0.0.1:998:800")
        .publish("942:942")
        .env("ENV", "fa")
        .env("NAME=kin")
        .labels("label 0")
        .link("registry:f")
        .image("alpine")
        .cmd("ls")
    );
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testAttach() {
    DRet<String> ret = Docker.container().attach("test",
      DQP.docker().container().attach()
        .stderr()
        .stdin()
        .stdout()
        .stream(),
      DStream.<String>builder(System.out::println).build()
    );
    Assert.assertTrue(ret.ok());
    System.out.println(ret.data());
  }

  @Test
  public void testWait() {
    DRet<ECWait> ret = Docker.container().wait("test", "removed");
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testStart() {
    DRet<Void> ret = Docker.container().start("test");
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }

  @Test
  public void testStop() {
    DRet<Void> ret = Docker.container().stop("nginx");
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }


  @Test
  public void testRemove() {
    DRet<Void> ret = Docker.container().remove("test");
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }

  @Test
  public void testRun() {
    Path tmp = PathKit.debugPath().resolve("_tmp");
    String result = this.dockerrun(tmp);
    FileKit.write(Paths.get("D:/tmp/run.txt"), ByteBuffer.wrap(result.getBytes(EoConst.CHARSET)));
    System.out.println(result);
    FileKit.delete(tmp);
  }

  @Test
  public void testMu() {
    Path tmp = PathKit.debugPath().resolve("_tmp");
    while (true) {
      String result = this.dockerrun(tmp);
      System.out.println(result);
      FileKit.delete(tmp);
      try {
        TimeUnit.SECONDS.sleep(1L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private String dockerrun(Path path) {
    DRet<String> ret = Docker.run("dockerrun",
      DQP.docker().container().create()
        .interactive()
        .tty()
        .rm()
        .name("dockerrun")
        .volume(TextKit.union(path.toString(), ":/git"))
        .image("alpine/git")
        .cmd("clone")
        .cmd("https://gitee.com/panqingyun/E3D-Engine.git"),
//        .cmd("https://github.com/fewensa/enoa.git"),
      DStream.<String>builder(data -> {
        System.out.println(data);
      }).build());
    Assert.assertTrue(ret.ok());
    String tty = ret.data();
    if (TextKit.blanky(tty))
      throw new RuntimeException("No result");
    return tty;
  }


}
