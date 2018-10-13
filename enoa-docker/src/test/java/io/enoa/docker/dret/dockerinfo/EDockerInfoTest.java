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
package io.enoa.docker.dret.dockerinfo;

import io.enoa.json.Json;
import io.enoa.json.provider.gson.GsonProvider;
import io.enoa.toolkit.map.Kv;
import org.junit.Before;
import org.junit.Test;

public class EDockerInfoTest {


  @Before
  public void ibe() {
    Json.epm().install(new GsonProvider());
  }


  @Test
  public void testParse() {
    String json = "{\"ID\":\"RCNO:LZ7T:WRSW:CPYY:VATO:G2AH:RWLK:PQAE:CQTT:OYKY:Z2Q7:ZSUQ\",\"Containers\":7,\"ContainersRunning\":1,\"ContainersPaused\":0,\"ContainersStopped\":6,\"Images\":15,\"Driver\":\"overlay2\",\"DriverStatus\":[[\"Backing Filesystem\",\"extfs\"],[\"Supports d_type\",\"true\"],[\"Native Overlay Diff\",\"true\"]],\"SystemStatus\":null,\"Plugins\":{\"Volume\":[\"local\"],\"Network\":[\"bridge\",\"host\",\"ipvlan\",\"macvlan\",\"null\",\"overlay\"],\"Authorization\":null,\"Log\":[\"awslogs\",\"fluentd\",\"gcplogs\",\"gelf\",\"journald\",\"json-file\",\"logentries\",\"splunk\",\"syslog\"]},\"MemoryLimit\":true,\"SwapLimit\":true,\"KernelMemory\":true,\"CpuCfsPeriod\":true,\"CpuCfsQuota\":true,\"CPUShares\":true,\"CPUSet\":true,\"IPv4Forwarding\":true,\"BridgeNfIptables\":true,\"BridgeNfIp6tables\":true,\"Debug\":true,\"NFd\":26,\"OomKillDisable\":true,\"NGoroutines\":44,\"SystemTime\":\"2018-10-02T02:02:28.4795761Z\",\"LoggingDriver\":\"json-file\",\"CgroupDriver\":\"cgroupfs\",\"NEventsListener\":1,\"KernelVersion\":\"4.9.60-linuxkit-aufs\",\"OperatingSystem\":\"Docker for Windows\",\"OSType\":\"linux\",\"Architecture\":\"x86_64\",\"IndexServerAddress\":\"https://index.docker.io/v1/\",\"RegistryConfig\":{\"AllowNondistributableArtifactsCIDRs\":[],\"AllowNondistributableArtifactsHostnames\":[],\"InsecureRegistryCIDRs\":[\"127.0.0.0/8\"],\"IndexConfigs\":{\"docker.io\":{\"Name\":\"docker.io\",\"Mirrors\":[],\"Secure\":true,\"Official\":true}},\"Mirrors\":[]},\"NCPU\":2,\"MemTotal\":2076987392,\"GenericResources\":null,\"DockerRootDir\":\"/var/lib/docker\",\"HttpProxy\":\"\",\"HttpsProxy\":\"\",\"NoProxy\":\"\",\"Name\":\"linuxkit-00155d016505\",\"Labels\":[],\"ExperimentalBuild\":true,\"ServerVersion\":\"17.12.0-ce\",\"ClusterStore\":\"\",\"ClusterAdvertise\":\"\",\"Runtimes\":{\"runc\":{\"path\":\"docker-runc\"}},\"DefaultRuntime\":\"runc\",\"Swarm\":{\"NodeID\":\"\",\"NodeAddr\":\"\",\"LocalNodeState\":\"inactive\",\"ControlAvailable\":false,\"Error\":\"\",\"RemoteManagers\":null},\"LiveRestoreEnabled\":false,\"Isolation\":\"\",\"InitBinary\":\"docker-init\",\"ContainerdCommit\":{\"ID\":\"89623f28b87a6004d4b785663257362d1658a729\",\"Expected\":\"89623f28b87a6004d4b785663257362d1658a729\"},\"RuncCommit\":{\"ID\":\"b2567b37d7b75eb4cf325b77297b140ea686ce8f\",\"Expected\":\"b2567b37d7b75eb4cf325b77297b140ea686ce8f\"},\"InitCommit\":{\"ID\":\"949e6fa\",\"Expected\":\"949e6fa\"},\"SecurityOptions\":[\"name=seccomp,profile=default\"]}";
    Kv kv = Json.parse(json, Kv.class);
    EDockerInfo di = Json.parse(json, EDockerInfo.class);
  }
}
