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
package io.enoa.docker.parser.docker;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.ret.docker.system.*;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class EInfoParser extends AbstractParser<EInfo> {

  private static class Holder {
    private static final EInfoParser INSTANCE = new EInfoParser();
  }

  static EInfoParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EInfo ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    if (CollectionKit.isEmpty(kv))
      return null;
    EInfo.Builder builder = new EInfo.Builder()
      .id(kv.string("ID"))
      .containers(kv.integer("Containers"))
      .containersrunning(kv.integer("ContainersRunning"))
      .containerspaused(kv.integer("ContainersPaused"))
      .containersstopped(kv.integer("ContainersStopped"))
      .images(kv.integer("Images"))
      .driver(kv.string("Driver"))
      .driverstatus(AEExtra.listarray(kv, "DriverStatus"))
      .dockerrootdir(kv.string("DockerRootDir"))
      .systemstatus(AEExtra.listarray(kv, "SystemStatus"))
      .plugin(this.plugin(kv))
      .memorylimit(kv.bool("MemoryLimit"))
      .swaplimit(kv.bool("SwapLimit"))
      .kernelmemory(kv.bool("KernelMemory"))
      .cpucfsperiod(kv.bool("CpuCfsPeriod"))
      .cpucfsquota(kv.bool("CpuCfsQuota"))
      .cpushares(kv.bool("CPUShares"))
      .cpuset(kv.bool("CPUSet"))
      .oomkilldisable(kv.bool("OomKillDisable"))
      .ipv4forwarding(kv.bool("IPv4Forwarding"))
      .bridgenfiptables(kv.bool("BridgeNfIptables"))
      .bridgenfip6tables(kv.bool("BridgeNfIp6tables"))
      .debug(kv.bool("Debug"))
      .nfd(kv.integer("NFd"))
      .ngoroutines(kv.integer("NGoroutines"))
      .systemtime(DateKit.parse(kv.string("SystemTime"), "yyyy-MM-dd'T'HH:mm:ss.SSS")) //
      .loggingdriver(kv.string("LoggingDriver"))
      .cgroupdriver(kv.string("CgroupDriver"))
      .neventslistener(kv.integer("NEventsListener"))
      .kernelversion(kv.string("KernelVersion"))
      .operatingsystem(kv.string("OperatingSystem"))
      .ostype(kv.string("OSType"))
      .architecture(kv.string("Architecture"))
      .ncpu(kv.integer("NCPU"))
      .memtotal(kv.longer("MemTotal"))
      .indexserveraddress(kv.string("IndexServerAddress"))
      .registryconfig(EDockerInfoParser.instance().registryconfig(kv, "RegistryConfig"))
      .genericresources(ENodeParser.instance().genericresources(kv, "GenericResources"))
      .httpproxy(kv.string("HttpProxy"))
      .httpsproxy(kv.string("HttpsProxy"))
      .noproxy(kv.string("NoProxy"))
      .name(kv.string("Name"))
      .labels(AEExtra.array(kv, "Labels"))
      .experimentalbuild(kv.bool("ExperimentalBuild"))
      .serverversion(kv.string("ServerVersion"))
      .clusteradvertise(kv.string("ClusterAdvertise"))
      .clusterstore(kv.string("ClusterStore"))
      .runtime(AEExtra.runtimes(kv, "Runtimes"))
      .defaultruntime(kv.string("DefaultRuntime"))
      .swarm(this.swarm(kv))
      .liverestoreenabled(kv.bool("LiveRestoreEnabled"))
      .isolation(kv.string("Isolation"))
      .initbinary(kv.string("InitBinary"))
      .containerdcommit(EDockerInfoParser.instance().comment(kv, "ContainerdCommit"))
      .runccommit(EDockerInfoParser.instance().comment(kv, "RuncCommit"))
      .initcommit(EDockerInfoParser.instance().comment(kv, "InitCommit"))
      .securityoptions(AEExtra.array(kv, "SecurityOptions"));

    CollectionKit.clear(kv);
    return builder.build();
  }

  private EYPlugin plugin(Kv kv) {
    Object pt = kv.get("Plugins");
    if (!(pt instanceof Map))
      return null;
    Kv pk = Kv.by((Map) pt);
    EYPlugin.Builder builder = new EYPlugin.Builder()
      .authorization(AEExtra.array(pk, "Authorization"))
      .volume(AEExtra.array(pk, "Volume"))
      .network(AEExtra.array(pk, "Network"))
      .log(AEExtra.array(pk, "Log"));
    return builder.build();
  }

  private EYSwarm swarm(Kv kv) {
    Object swt = kv.get("Swarm");
    if (!(swt instanceof Map))
      return null;
    Kv sk = Kv.by((Map) swt);
    EYSwarm.Builder builder = new EYSwarm.Builder()
      .nodeid(sk.string("NodeID"))
      .nodeaddr(sk.string("NodeAddr"))
      .localnodestate(sk.string("LocalNodeState"))
      .controlavailable(sk.bool("ControlAvailable"))
      .error(sk.string("Error"))
      .remotemanages(this.remotemanage(sk))
      .nodes(sk.integer("Nodes"))
      .managers(sk.integer("Managers"))
      .cluster(this.cluster(kv));
    return builder.build();
  }


  private EYCluster cluster(Kv kv) {
    Object clu = kv.get("Cluster");
    if (!(clu instanceof Map))
      return null;
    Kv ck = Kv.by((Map) clu);
    EYCluster.Builder builder = new EYCluster.Builder()
      .id(ck.string("ID"))
      .version(AEExtra.version(ck, "Version"))
      .createdat(DateKit.parse(ck.string("CreatedAt"), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .updatedat(DateKit.parse(ck.string("UpdatedAt"), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .spec(ESwarmInspectParser.instance().spec(ck, "Spec"))
      .tlsinfo(AEExtra.tls(ck, "TLSInfo"))
      .rootrotationinprogress(ck.bool("RootRotationInProgress"));
    CollectionKit.clear(ck);
    return builder.build();
  }


  private List<EYRemoteManage> remotemanage(Kv kv) {
    Object rmo = kv.get("RemoteManagers");
    if (!(rmo instanceof Collection))
      return null;
    Collection rns = (Collection) rmo;
    List<EYRemoteManage> rets = new ArrayList<>(rns.size());
    rns.forEach(r -> {
      if (!(r instanceof Map))
        return;
      Kv rk = Kv.by((Map) r);
      EYRemoteManage.Builder builder = new EYRemoteManage.Builder()
        .nodeid(rk.string("NodeID"))
        .addr(rk.string("Addr"));
      CollectionKit.clear(rk);
      rets.add(builder.build());
    });
    return rets;
  }

}
