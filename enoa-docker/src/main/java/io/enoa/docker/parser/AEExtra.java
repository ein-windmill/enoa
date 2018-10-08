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
package io.enoa.docker.parser;

import io.enoa.docker.dret.container.*;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.value.EnoaValue;

import java.util.*;
import java.util.stream.Collectors;

class AEExtra {

  static List<String> stringarray(Map map, String key) {
    Object sarr = map.get(key);
    if (!(sarr instanceof Collection)) {
      return Collections.emptyList();
    }
    return (List<String>) ((Collection) sarr).stream().collect(Collectors.toList());
  }

  static ECState inspectstate(Map map) {
    Object state = map.get("State");
    if (!(state instanceof Map))
      return null;
    Map stm = (Map) state;
    ECState.Builder builder = new ECState.Builder()
      .status(EnoaValue.with(stm.get("Status")).string())
      .running(EnoaValue.with(stm.get("Running")).bool())
      .paused(EnoaValue.with(stm.get("Paused")).bool())
      .restarting(EnoaValue.with(stm.get("Restarting")).bool())
      .oomkilled(EnoaValue.with(stm.get("OOMKilled")).bool())
      .dead(EnoaValue.with(stm.get("Dead")).bool())
      .pid(EnoaValue.with(stm.get("Pid")).integer())
      .exitcode(EnoaValue.with(stm.get("ExitCode")).integer())
      .error(EnoaValue.with(stm.get("Error")).string())
      .startedat(DateKit.parse(EnoaValue.with(stm.get("StartedAt")).string(), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .finishedat(DateKit.parse(EnoaValue.with(stm.get("FinishedAt")).string(), "yyyy-MM-dd'T'HH:mm:ss"));
    return builder.build();
  }


  static EHostConfig hostconfig(Map map) {
    return hostconfig(map, "HostConfig");
  }

  static EHostConfig hostconfig(Map map, String key) {
    Object hostconfig = map.get(key);
    if (!(hostconfig instanceof Map))
      return null;
    Map hgm = (Map) hostconfig;
    if (CollectionKit.isEmpty(hgm))
      return null;
    EHostConfig.Builder hcgb = new EHostConfig.Builder()
      .maximumiops(EnoaValue.with(hgm.get("MaximumIOps")).integer())
      .maximumiobps(EnoaValue.with(hgm.get("MaximumIOBps")).integer())
      .blkioweight(EnoaValue.with(hgm.get("BlkioWeight")).integer())
      .containeridfile(EnoaValue.with(hgm.get("ContainerIDFile")).string())
      .cpusetcpus(EnoaValue.with(hgm.get("CpusetCpus")).string())
      .cpusetmems(EnoaValue.with(hgm.get("CpusetMems")).string())
      .cpupercent(EnoaValue.with(hgm.get("CpuPercent")).integer())
      .cpushares(EnoaValue.with(hgm.get("CpuShares")).integer())
      .cpuperiod(EnoaValue.with(hgm.get("CpuPeriod")).integer())
      .cpurealtimeperiod(EnoaValue.with(hgm.get("CpuRealtimePeriod")).integer())
      .cpurealtimeruntime(EnoaValue.with(hgm.get("CpuRealtimeRuntime")).integer())
      .devices(hgm.get("Devices"))
      .ipcmode(EnoaValue.with(hgm.get("IpcMode")).string())
      .lxcconf(hgm.get("LxcConf"))
      .memory(EnoaValue.with(hgm.get("Memory")).integer())
      .memoryswap(EnoaValue.with(hgm.get("MemorySwap")).integer())
      .memoryreservation(EnoaValue.with(hgm.get("MemoryReservation")).integer())
      .kernelmemory(EnoaValue.with(hgm.get("KernelMemory")).integer())
      .oomkilldisable(EnoaValue.with(hgm.get("OomKillDisable")).bool())
      .oomscoreadj(EnoaValue.with(hgm.get("OomScoreAdj")).integer())
      .networkmode(EnoaValue.with(hgm.get("NetworkMode")).string())
      .pidmode(EnoaValue.with(hgm.get("PidMode")).string())
      .portbindings(hgm.get("PortBindings"))
      .privileged(EnoaValue.with(hgm.get("Privileged")).bool())
      .readonlyrootfs(EnoaValue.with(hgm.get("ReadonlyRootfs")).bool())
      .publishallports(EnoaValue.with(hgm.get("PublishAllPorts")).bool())
      .volumedriver(EnoaValue.with(hgm.get("VolumeDriver")).string())
      .shmsize(EnoaValue.with(hgm.get("ShmSize")).integer());

    Object policy = hgm.get("RestartPolicy");
    if (policy instanceof Map) {
      ERestartPolicy.Builder erpb = new ERestartPolicy.Builder();
      Map pym = (Map) policy;
      erpb.maximumretrycount(EnoaValue.with(pym.get("MaximumRetryCount")).integer())
        .name(EnoaValue.with(pym.get("Name")).string());
      hcgb.restartpolicy(erpb.build());
    }

    Object logconfig = hgm.get("LogConfig");
    if (logconfig instanceof Map) {
      ELogConfig.Builder lcb = new ELogConfig.Builder();
      Map lcm = (Map) logconfig;
      lcb.type(EnoaValue.with(lcm.get("Type")).string());
      lcb.config(lcm.get("Config"));
      hcgb.logconfig(lcb.build());
    }

    Object sysctls = hgm.get("Sysctls");
    if (sysctls instanceof Map) {
      Kv syskv = Kv.by((Map<String, ?>) sysctls);
      hcgb.sysctl(syskv);
    }

    Object binds = hgm.get("Binds");
    if (binds instanceof Collection) {
      List<String> bnds = (List<String>) ((Collection) binds).stream().collect(Collectors.toList());
      hcgb.binds(bnds);
    }

    return hcgb.build();
  }

  static EGraphDriver graphdriver(Map map) {
    return graphdriver(map, "GraphDriver");
  }

  static EGraphDriver graphdriver(Map map, String key) {
    Object gdr = map.get(key);
    if (!(gdr instanceof Map))
      return null;
    Map graphdriver = (Map) gdr;
    EGraphDriver.Builder builder = new EGraphDriver.Builder();
    builder.name(EnoaValue.with(graphdriver.get("Name")).string());
    Object data = graphdriver.get("Data");
    if (data instanceof Map) {
      Map mda = (Map) data;
      EGDData.Builder gdb = new EGDData.Builder();
      gdb.lowerdir(EnoaValue.with(mda.get("LowerDir")).string())
        .mergeddir(EnoaValue.with(mda.get("MergedDir")).string())
        .upperdir(EnoaValue.with(mda.get("UpperDir")).string())
        .workdir(EnoaValue.with(mda.get("WorkDir")).string());
      builder.data(gdb.build());
    }
    return builder.build();
  }


  static List<EMount> mounts(Map map) {
    return mounts(map, "Mounts");
  }

  static List<EMount> mounts(Map map, String key) {
    Object mounts = map.get(key);
    if (!(mounts instanceof Collection))
      return Collections.emptyList();
    Collection mts = (Collection) mounts;
    List<EMount> rets = new ArrayList<>(mts.size());
    mts.forEach(mt -> {
      if (!(mt instanceof Map))
        return;
      Map mtm = (Map) mt;
      EMount.Builder builder = new EMount.Builder()
        .type(EnoaValue.with(mtm.get("Type")).string())
        .name(EnoaValue.with(mtm.get("Name")).string())
        .source(EnoaValue.with(mtm.get("Source")).string())
        .destination(EnoaValue.with(mtm.get("Destination")).string())
        .driver(EnoaValue.with(mtm.get("Driver")).string())
        .mode(EnoaValue.with(mtm.get("Mode")).string())
        .rw(EnoaValue.with(mtm.get("RW")).bool())
        .propagation(EnoaValue.with(mtm.get("Propagation")).string());
      rets.add(builder.build());
    });
    return rets;
  }


  static ENetworkSetting networksetting(Map map) {
    return networksetting(map, "NetworkSettings");
  }

  static ENetworkSetting networksetting(Map map, String key) {
    Object networksettings = map.get(key);
    if (!(networksettings instanceof Map))
      return null;
    ENetworkSetting.Builder nsb = new ENetworkSetting.Builder();
    Map mssm = (Map) networksettings;
    nsb.bridge(EnoaValue.with(mssm.get("Bridge")).string())
      .sandboxid(EnoaValue.with(mssm.get("SandboxID")).string())
      .hairpinmode(EnoaValue.with(mssm.get("HairpinMode")).bool())
      .linklocalipv6address(EnoaValue.with(mssm.get("LinkLocalIPv6Address")).string())
      .linklocalipv6prefixlen(EnoaValue.with(mssm.get("LinkLocalIPv6PrefixLen")).integer())
      .sandboxkey(EnoaValue.with(mssm.get("SandboxKey")).string())
      .endpointid(EnoaValue.with(mssm.get("EndpointID")).string())
      .gateway(EnoaValue.with(mssm.get("Gateway")).string())
      .globalipv6address(EnoaValue.with(mssm.get("GlobalIPv6Address")).string())
      .globalipv6prefixlen(EnoaValue.with(mssm.get("GlobalIPv6PrefixLen")).integer())
      .ipaddress(EnoaValue.with(mssm.get("IPAddress")).string())
      .ipprefixlen(EnoaValue.with(mssm.get("IPPrefixLen")).integer())
      .ipv6gateway(EnoaValue.with(mssm.get("IPv6Gateway")).string())
      .macaddress(EnoaValue.with(mssm.get("MacAddress")).string());

    Object networks = mssm.get("Networks");
    if (networks instanceof Map) {
      ENetwork.Builder nkb = new ENetwork.Builder();
      Map nksm = (Map) networks;
      Object bridge = nksm.get("bridge");
      if (bridge instanceof Map) {
        Map bgm = (Map) bridge;
        EBridge.Builder ebb = new EBridge.Builder();
        // fixme IPAMConfig Links Aliases DriverOpts
        ebb.ipamconfig(bgm.get("IPAMConfig"))
          .links(bgm.get("Links"))
          .aliases(bgm.get("Aliases"))
          .networkid(EnoaValue.with(bgm.get("NetworkID")).string())
          .endpointid(EnoaValue.with(bgm.get("EndpointID")).string())
          .gateway(EnoaValue.with(bgm.get("Gateway")).string())
          .ipaddress(EnoaValue.with(bgm.get("IPAddress")).string())
          .ipprefixlen(EnoaValue.with(bgm.get("IPPrefixLen")).integer())
          .ipv6gateway(EnoaValue.with(bgm.get("IPv6Gateway")).string())
          .globalipv6address(EnoaValue.with(bgm.get("GlobalIPv6Address")).string())
          .globalipv6prefixlen(EnoaValue.with(bgm.get("GlobalIPv6PrefixLen")).integer())
          .macaddress(EnoaValue.with(bgm.get("MacAddress")).string())
          .driveropts(bgm.get("DriverOpts"));
        nkb.bridge(ebb.build());
      }
      nsb.network(nkb.build());
    }
    return nsb.build();
  }

}
