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

import io.enoa.docker.ret.docker.common.ETLSInfo;
import io.enoa.docker.ret.docker.container.*;
import io.enoa.docker.ret.docker.dockerinfo.ERuntimes;
import io.enoa.docker.ret.docker.swarm.EVersion;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.value.EnoaValue;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class AEExtra {

  static List<String> stringarray(Map map, String key) {
    Object sarr = map.get(key);
    if (!(sarr instanceof Collection)) {
      return Collections.emptyList();
    }
    return (List<String>) ((Collection) sarr).stream()
      .collect(Collectors.toCollection((Supplier<Collection<Object>>) () -> new LinkedList<>()));
  }

  static List<String[]> listarray(Map map, String key) {
    Object pa = map.get(key);
    if (!(pa instanceof Collection))
      return Collections.emptyList();
    Collection cas = (Collection) pa;
    List<String[]> rets = new ArrayList<>(cas.size());
    cas.forEach(ca -> {
      if (!(ca instanceof Collection))
        return;
      Collection cs = (Collection) ca;
      rets.add((String[]) cs.toArray(new String[cs.size()]));
    });
    return rets;
  }

  static String[] array(Map map, String key) {
    List<String> stringarray = stringarray(map, key);
    return stringarray.toArray(new String[stringarray.size()]);
  }

  static ECState inspectstate(Map map) {
    Object state = map.get("State");
    if (!(state instanceof Map))
      return null;
    Kv stm = Kv.by((Map) state);
    ECState.Builder builder = new ECState.Builder()
      .status(stm.string("Status"))
      .running(stm.bool("Running"))
      .paused(stm.bool("Paused"))
      .restarting(stm.bool("Restarting"))
      .oomkilled(stm.bool("OOMKilled"))
      .dead(stm.bool("Dead"))
      .pid(stm.integer("Pid"))
      .exitcode(stm.integer("ExitCode"))
      .error(stm.string("Error"))
      .startedat(DateKit.parse(stm.string("StartedAt"), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .finishedat(DateKit.parse(stm.string("FinishedAt"), "yyyy-MM-dd'T'HH:mm:ss"));
    return builder.build();
  }

  static EHostConfig hostconfig(Map map) {
    return hostconfig(map, "HostConfig");
  }

  static EHostConfig hostconfig(Map map, String key) {
    Object hostconfig = map.get(key);
    if (!(hostconfig instanceof Map))
      return null;
    Kv hgm = Kv.by((Map) hostconfig);
    if (CollectionKit.isEmpty(hgm))
      return null;
    EHostConfig.Builder hcgb = new EHostConfig.Builder()
      .maximumiops(hgm.integer("MaximumIOps"))
      .maximumiobps(hgm.integer("MaximumIOBps"))
      .blkioweight(hgm.integer("BlkioWeight"))
      .containeridfile(hgm.string("ContainerIDFile"))
      .cpusetcpus(hgm.string("CpusetCpus"))
      .cpusetmems(hgm.string("CpusetMems"))
      .cpupercent(hgm.integer("CpuPercent"))
      .cpushares(hgm.integer("CpuShares"))
      .cpuperiod(hgm.integer("CpuPeriod"))
      .cpurealtimeperiod(hgm.integer("CpuRealtimePeriod"))
      .cpurealtimeruntime(hgm.integer("CpuRealtimeRuntime"))
      .devices(hgm.get("Devices"))
      .ipcmode(hgm.string("IpcMode"))
      .lxcconf(hgm.get("LxcConf"))
      .memory(hgm.integer("Memory"))
      .memoryswap(hgm.integer("MemorySwap"))
      .memoryreservation(hgm.integer("MemoryReservation"))
      .kernelmemory(hgm.integer("KernelMemory"))
      .oomkilldisable(hgm.bool("OomKillDisable"))
      .oomscoreadj(hgm.integer("OomScoreAdj"))
      .networkmode(hgm.string("NetworkMode"))
      .pidmode(hgm.string("PidMode"))
      .portbindings(hgm.get("PortBindings"))
      .privileged(hgm.bool("Privileged"))
      .readonlyrootfs(hgm.bool("ReadonlyRootfs"))
      .publishallports(hgm.bool("PublishAllPorts"))
      .volumedriver(hgm.string("VolumeDriver"))
      .shmsize(hgm.integer("ShmSize"));

    Object policy = hgm.get("RestartPolicy");
    if (policy instanceof Map) {
      ERestartPolicy.Builder erpb = new ERestartPolicy.Builder();
      Kv pym = Kv.by((Map) policy);
      erpb.maximumretrycount(pym.integer("MaximumRetryCount"))
        .name(pym.string("Name"));
      hcgb.restartpolicy(erpb.build());
    }

    Object logconfig = hgm.get("LogConfig");
    if (logconfig instanceof Map) {
      ELogConfig.Builder lcb = new ELogConfig.Builder();
      Kv lcm = Kv.by((Map) logconfig);
      lcb.type(lcm.string("Type"));
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
    Kv graphdriver = Kv.by((Map) gdr);
    EGraphDriver.Builder builder = new EGraphDriver.Builder();
    builder.name(graphdriver.string("Name"));
    Object data = graphdriver.get("Data");
    if (data instanceof Map) {
      Kv mda = Kv.by((Map) data);
      EGDData.Builder gdb = new EGDData.Builder();
      gdb.lowerdir(mda.string("LowerDir"))
        .mergeddir(mda.string("MergedDir"))
        .upperdir(mda.string("UpperDir"))
        .workdir(mda.string("WorkDir"));
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
      Kv mtm = Kv.by((Map) mt);
      EMount.Builder builder = new EMount.Builder()
        .type(mtm.string("Type"))
        .name(mtm.string("Name"))
        .source(mtm.string("Source"))
        .destination(mtm.string("Destination"))
        .driver(mtm.string("Driver"))
        .mode(mtm.string("Mode"))
        .rw(mtm.bool("RW"))
        .propagation(mtm.string("Propagation"));
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
    Kv mssm = Kv.by((Map) networksettings);
    nsb.bridge(mssm.string("Bridge"))
      .sandboxid(mssm.string("SandboxID"))
      .hairpinmode(mssm.bool("HairpinMode"))
      .linklocalipv6address(mssm.string("LinkLocalIPv6Address"))
      .linklocalipv6prefixlen(mssm.integer("LinkLocalIPv6PrefixLen"))
      .sandboxkey(mssm.string("SandboxKey"))
      .endpointid(mssm.string("EndpointID"))
      .gateway(mssm.string("Gateway"))
      .globalipv6address(mssm.string("GlobalIPv6Address"))
      .globalipv6prefixlen(mssm.integer("GlobalIPv6PrefixLen"))
      .ipaddress(mssm.string("IPAddress"))
      .ipprefixlen(mssm.integer("IPPrefixLen"))
      .ipv6gateway(mssm.string("IPv6Gateway"))
      .macaddress(mssm.string("MacAddress"));

    Object networks = mssm.get("Networks");
    if (networks instanceof Map) {
      ECNetwork.Builder nkb = new ECNetwork.Builder();
      Map nksm = (Map) networks;
      Object bridge = nksm.get("bridge");
      if (bridge instanceof Map) {
        Kv bgm = Kv.by((Map) bridge);
        EBridge.Builder ebb = new EBridge.Builder();
        // fixme IPAMConfig Links Aliases DriverOpts
        ebb.ipamconfig(bgm.get("IPAMConfig"))
          .links(bgm.get("Links"))
          .aliases(bgm.get("Aliases"))
          .networkid(bgm.string("NetworkID"))
          .endpointid(bgm.string("EndpointID"))
          .gateway(bgm.string("Gateway"))
          .ipaddress(bgm.string("IPAddress"))
          .ipprefixlen(bgm.integer("IPPrefixLen"))
          .ipv6gateway(bgm.string("IPv6Gateway"))
          .globalipv6address(bgm.string("GlobalIPv6Address"))
          .globalipv6prefixlen(bgm.integer("GlobalIPv6PrefixLen"))
          .macaddress(bgm.string("MacAddress"))
          .driveropts(bgm.get("DriverOpts"));
        nkb.bridge(ebb.build());
      }
      nsb.network(nkb.build());
    }
    return nsb.build();
  }


  static Kv kv(Map map, String key) {
    Object o = map.get(key);
    if (!(o instanceof Map))
      return null;
    return Kv.by((Map) o);
  }

  static ETLSInfo tls(Map kv, String key) {
    Object tsc = kv.get(key);
    if (!(tsc instanceof Map)) {
      return null;
    }
    Kv tls = Kv.by((Map) tsc);
    ETLSInfo.Builder tlsb = new ETLSInfo.Builder()
      .trustroot(tls.string("TrustRoot"))
      .certissuersubject(tls.string("CertIssuerSubject"))
      .certissuerpublickey(tls.string("CertIssuerPublicKey"));
    CollectionKit.clear(tls);
    return tlsb.build();
  }


  static EVersion version(Map map) {
    return version(map, "Version");
  }

  static EVersion version(Map map, String key) {
    Object vot = map.get(key);
    if (!(vot instanceof Map))
      return null;
    Kv vom = Kv.by((Map) vot);
    EVersion.Builder builder = new EVersion.Builder()
      .index(vom.integer("Index"));
    CollectionKit.clear(vom);
    return builder.build();
  }

  static ERuntimes runtimes(Map map, String key) {
    Object runtimes = map.get(key);
    if (!(runtimes instanceof Map))
      return null;
    Map rum = (Map) runtimes;
    ERuntimes.Builder builder = new ERuntimes.Builder();

    Object runc = rum.get("runc");
    if (runc instanceof Map) {
      builder.runc(Kv.by((Map) runc));
    }
    Object runcm = rum.get("runc-master");
    if (runcm instanceof Map) {
      builder.runc(Kv.by((Map) runcm));
    }
    Object ctm = rum.get("custom");
    if (ctm instanceof Map) {
      Kv ck = Kv.by((Map) ctm);
      Object args = ck.get("runtimeArgs");
      if (args instanceof Collection) {
        Collection ans = (Collection) args;
        ck.set("runtimeArgs", ans.toArray(new String[ans.size()]));
      }
      builder.custom(ck);
    }
    return builder.build();
  }

  static Date date(Map map, String key) {
    return date(map, key, "yyyy-MM-dd'T'HH:mm:ss");
  }

  static Date date(Map map, String key, String format) {
    Object o = map.get(key);
    if (o == null)
      return null;
    return DateKit.parse(EnoaValue.with(o).string(), format);
  }

}
