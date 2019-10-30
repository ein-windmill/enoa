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
package io.enoa.docker.async;

import io.enoa.docker.AbstractDockerTest;
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.dockerinfo.EDockerInfo;
import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.docker.container.DQPContainerCreate;
import io.enoa.docker.parser.docker.DIParser;
import io.enoa.json.Json;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Ignore
public class AsyncDockerTest extends AbstractDockerTest {

  private void sleep() {
    try {
      TimeUnit.SECONDS.sleep(4L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void info() {
    super.async()
      .origin()
      .info()
      .enqueue()
      .done(ret -> System.out.println(ret.string()))
      .capture(System.err::println)
      .always(() -> System.out.println("Always"));

    super.async()
      .generic()
      .info(DIParser.dockerinfo())
      .enqueue()
      .asset(ret -> ret.ok())
      .failthrow(ret -> System.err.println(ret.message()))
      .<DRet<EDockerInfo>>then(DRet::data)
      .<EDockerInfo>execute(info -> System.out.println(Json.toJson(info)))
      .capture(System.err::println)
      .always(() -> System.out.println("Always"));

    super.async()
      .info()
      .enqueue()
      .asset(ret -> ret.ok())
      .failthrow(ret -> System.err.println(ret.message()))
      .<DRet<EDockerInfo>>then(DRet::data)
      .<EDockerInfo>execute(info -> System.out.println(Json.toJson(info)))
      .capture(System.err::println)
      .always(() -> System.out.println("Always"));

    this.sleep();
  }

  @Test
  public void testRun() {
    DQPContainerCreate dqp = DQP.docker().container().create()
//          .detach()
      .interactive()
      .tty()
      .rm()
      .name("test")
      .publish("999:800")
      .publish("127.0.0.1:998:800")
      .publish("942:942")
      .env("ENV", "fa")
      .env("NAME=kin")
      .labels("label 0")
      .image("alpine:3.8")
      .cmd("ls");

    super.async()
      .run("test", dqp)
      .enqueue()
      .asset(ret -> ret.ok())
      .failthrow(ret -> System.err.println(ret.message()))
      .<DRet<String>>then(DRet::data)
      .<String>execute(System.out::println)
      .capture(System.err::println)
      .always(() -> System.out.println("always"));

    this.sleep();
  }

}
