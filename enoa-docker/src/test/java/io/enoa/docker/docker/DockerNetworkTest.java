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
import io.enoa.docker.dket.docker.DRet;
import io.enoa.docker.dket.docker.common.ECreatedWithWarning;
import io.enoa.docker.dket.docker.network.ENetworPrune;
import io.enoa.docker.dket.docker.network.ENetwork;
import io.enoa.json.Json;
import io.enoa.toolkit.value.Void;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

@Ignore
public class DockerNetworkTest extends AbstractDockerTest {


  @Test
  public void testList() {
    DRet<List<ENetwork>> ret = super.docker().network().list();
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testInspect() {
    DRet<ENetwork> ret = super.docker().network().inspect("bridge");
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  private void testRemove() {
    DRet<Void> ret = super.docker().network().remove("none");
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }

  @Test
  public void testCreate() {
    String body = "{}";
    DRet<ECreatedWithWarning> ret = super.docker().network().create(body);
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testConnect() {
    String body = "{}";
    DRet<Void> ret = super.docker().network().connect("bridge", body);
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }

  @Test
  public void testDisconnect() {
    String body = "{}";
    DRet<Void> ret = super.docker().network().disconnect("bridge", body);
    Assert.assertTrue(ret.ok());
    System.out.println(ret);
  }

  @Test
  public void testPrune() {
    DRet<ENetworPrune> ret = super.docker().network().prune();
    Assert.assertTrue(ret.ok());
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }


}
