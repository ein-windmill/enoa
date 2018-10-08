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

import io.enoa.docker.dqp.container.DQPLogs;
import io.enoa.docker.dret.DRet;
import io.enoa.docker.dret.container.*;
import io.enoa.docker.dret.dockerinfo.EDockerInfo;
import io.enoa.json.Json;
import io.enoa.json.provider.gson.GsonProvider;
import io.enoa.toolkit.value.Void;
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
  public void testList() {
    DRet<List<EContainer>> ret = Docker.container().list();
    assert ret.ok();
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testInspect() {
    DRet<EInspect> ret = Docker.container().inspect("gitbook");
    assert ret.ok();
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testTop() {
    DRet<EProcesses> ret = Docker.container().top("gitbook");
    assert ret.ok();
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testLogs() {
    DRet<String> ret = Docker.container().logs("nginx", DQPLogs.create().stdout());
    assert ret.ok();
    String logs = ret.data();
    System.out.println(logs);
  }

  @Test
  public void testChanges() {
    DRet<List<EChange>> ret = Docker.container().changes("redis");
    assert ret.ok();
    String json = Json.toJson(ret.data());
    System.out.println(json);
  }

  @Test
  public void testExport() {
    DRet<Void> ret = Docker.container().export("nginx");
    assert ret.ok();
    Void data = ret.data();
    System.out.println(data);
  }

  @Test
  public void testCreate() {
    String json = "{\n" +
      "  \"Hostname\": \"\",\n" +
      "  \"Domainname\": \"\",\n" +
      "  \"User\": \"\",\n" +
      "  \"AttachStdin\": false,\n" +
      "  \"AttachStdout\": true,\n" +
      "  \"AttachStderr\": true,\n" +
      "  \"Tty\": false,\n" +
      "  \"OpenStdin\": false,\n" +
      "  \"StdinOnce\": false,\n" +
      "  \"Env\": [\n" +
      "    \"FOO=bar\",\n" +
      "    \"BAZ=quux\"\n" +
      "  ],\n" +
      "  \"Cmd\": [\n" +
      "    \"date\"\n" +
      "  ],\n" +
      "  \"Entrypoint\": \"\",\n" +
      "  \"Image\": \"ubuntu\",\n" +
      "  \"Labels\": {\n" +
      "    \"com.example.vendor\": \"Acme\",\n" +
      "    \"com.example.license\": \"GPL\",\n" +
      "    \"com.example.version\": \"1.0\"\n" +
      "  },\n" +
      "  \"Volumes\": {\n" +
      "    \"/volumes/data\": {}\n" +
      "  },\n" +
      "  \"WorkingDir\": \"\",\n" +
      "  \"NetworkDisabled\": false,\n" +
      "  \"MacAddress\": \"12:34:56:78:9a:bc\",\n" +
      "  \"ExposedPorts\": {\n" +
      "    \"22/tcp\": {}\n" +
      "  },\n" +
      "  \"StopSignal\": \"SIGTERM\",\n" +
      "  \"StopTimeout\": 10,\n" +
      "  \"HostConfig\": {\n" +
      "    \"Binds\": [\n" +
      "      \"/tmp:/tmp\"\n" +
      "    ],\n" +
      "    \"Links\": [\n" +
      "      \"redis3:redis\"\n" +
      "    ],\n" +
      "    \"Memory\": 0,\n" +
      "    \"MemorySwap\": 0,\n" +
      "    \"MemoryReservation\": 0,\n" +
      "    \"KernelMemory\": 0,\n" +
      "    \"NanoCPUs\": 500000,\n" +
      "    \"CpuPercent\": 80,\n" +
      "    \"CpuShares\": 512,\n" +
      "    \"CpuPeriod\": 100000,\n" +
      "    \"CpuRealtimePeriod\": 1000000,\n" +
      "    \"CpuRealtimeRuntime\": 10000,\n" +
      "    \"CpuQuota\": 50000,\n" +
      "    \"CpusetCpus\": \"0,1\",\n" +
      "    \"CpusetMems\": \"0,1\",\n" +
      "    \"MaximumIOps\": 0,\n" +
      "    \"MaximumIOBps\": 0,\n" +
      "    \"BlkioWeight\": 300,\n" +
      "    \"BlkioWeightDevice\": [\n" +
      "      {}\n" +
      "    ],\n" +
      "    \"BlkioDeviceReadBps\": [\n" +
      "      {}\n" +
      "    ],\n" +
      "    \"BlkioDeviceReadIOps\": [\n" +
      "      {}\n" +
      "    ],\n" +
      "    \"BlkioDeviceWriteBps\": [\n" +
      "      {}\n" +
      "    ],\n" +
      "    \"BlkioDeviceWriteIOps\": [\n" +
      "      {}\n" +
      "    ],\n" +
      "    \"MemorySwappiness\": 60,\n" +
      "    \"OomKillDisable\": false,\n" +
      "    \"OomScoreAdj\": 500,\n" +
      "    \"PidMode\": \"\",\n" +
      "    \"PidsLimit\": -1,\n" +
      "    \"PortBindings\": {\n" +
      "      \"22/tcp\": [\n" +
      "        {\n" +
      "          \"HostPort\": \"11022\"\n" +
      "        }\n" +
      "      ]\n" +
      "    },\n" +
      "    \"PublishAllPorts\": false,\n" +
      "    \"Privileged\": false,\n" +
      "    \"ReadonlyRootfs\": false,\n" +
      "    \"Dns\": [\n" +
      "      \"8.8.8.8\"\n" +
      "    ],\n" +
      "    \"DnsOptions\": [\n" +
      "      \"\"\n" +
      "    ],\n" +
      "    \"DnsSearch\": [\n" +
      "      \"\"\n" +
      "    ],\n" +
      "    \"VolumesFrom\": [\n" +
      "      \"parent\",\n" +
      "      \"other:ro\"\n" +
      "    ],\n" +
      "    \"CapAdd\": [\n" +
      "      \"NET_ADMIN\"\n" +
      "    ],\n" +
      "    \"CapDrop\": [\n" +
      "      \"MKNOD\"\n" +
      "    ],\n" +
      "    \"GroupAdd\": [\n" +
      "      \"newgroup\"\n" +
      "    ],\n" +
      "    \"RestartPolicy\": {\n" +
      "      \"Name\": \"\",\n" +
      "      \"MaximumRetryCount\": 0\n" +
      "    },\n" +
      "    \"AutoRemove\": true,\n" +
      "    \"NetworkMode\": \"bridge\",\n" +
      "    \"Devices\": [],\n" +
      "    \"Ulimits\": [\n" +
      "      {}\n" +
      "    ],\n" +
      "    \"LogConfig\": {\n" +
      "      \"Type\": \"json-file\",\n" +
      "      \"Config\": {}\n" +
      "    },\n" +
      "    \"SecurityOpt\": [],\n" +
      "    \"StorageOpt\": {},\n" +
      "    \"CgroupParent\": \"\",\n" +
      "    \"VolumeDriver\": \"\",\n" +
      "    \"ShmSize\": 67108864\n" +
      "  },\n" +
      "  \"NetworkingConfig\": {\n" +
      "    \"EndpointsConfig\": {\n" +
      "      \"isolated_nw\": {\n" +
      "        \"IPAMConfig\": {\n" +
      "          \"IPv4Address\": \"172.20.30.33\",\n" +
      "          \"IPv6Address\": \"2001:db8:abcd::3033\",\n" +
      "          \"LinkLocalIPs\": [\n" +
      "            \"169.254.34.68\",\n" +
      "            \"fe80::3468\"\n" +
      "          ]\n" +
      "        },\n" +
      "        \"Links\": [\n" +
      "          \"container_1\",\n" +
      "          \"container_2\"\n" +
      "        ],\n" +
      "        \"Aliases\": [\n" +
      "          \"server_x\",\n" +
      "          \"server_y\"\n" +
      "        ]\n" +
      "      }\n" +
      "    }\n" +
      "  }\n" +
      "}";
    DRet<EContainerCreated> ret = Docker.container().create("test", json);
    assert ret.ok();
    System.out.println(ret);
  }


}
