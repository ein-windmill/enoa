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

import io.enoa.docker.dret.DRet;
import io.enoa.docker.dret.container.EContainer;
import io.enoa.docker.dret.dockerinfo.EDockerInfo;
import io.enoa.json.Json;
import io.enoa.json.provider.gson.GsonProvider;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

@Ignore
public class DockerTest {

  @Before
  public void ibe() {
    Json.epm().install(new GsonProvider());
    DockerConfig config = new DockerConfig.Builder()
      .host("tcp://localhost:2375")
      .tls(Boolean.FALSE)
      .version("v1.35")
      .debug()
      .json(new GsonProvider())
      .build();
    Docker.epm().install(config);
  }

  @Test
  public void testInfo() {
    DRet<EDockerInfo> ret = Docker.info();
    assert ret.ok();
    String json = Json.toJson(ret);
    System.out.println(json);
  }

  @Test
  public void testPs() {
    DRet<List<EContainer>> ret = Docker.use().container().ps();
    assert ret.ok();
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

}
