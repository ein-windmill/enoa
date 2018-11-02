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
package io.enoa.docker.dqp.docker.container;

import io.enoa.docker.dket.common.DEnv;
import io.enoa.docker.dket.mark.DRestart;
import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.map.Kv;
import io.enoa.toolkit.number.NumberKit;
import io.enoa.toolkit.text.TextKit;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DQPContainerCreate implements DQP {

  public static DQPContainerCreate create() {
    return new DQPContainerCreate();
  }

  private Boolean detach;
  private Boolean interactive;
  private Boolean tty;
  private String user;
  private List<String> attach;
  private String workdir;
  private Integer cpushares;
  private Set<String> env;
  private Integer memory;
  private Set<String> publish;
  private String hostname;
  private Set<String> volume;
  private Set<String> capadd;
  private Set<String> capdrop;
  private String cidfile;
  private String cpuset;
  private Set<String> device;
  private Set<String> dns;
  private Set<String> dnssearch;
  private String entrypoint;
  private Set<String> envfile;
  private Set<String> expose;
  private Set<String> link;
  private Set<String> lxcconf;
  private String name;
  private String net;
  private Boolean privileged;
  private DRestart restart;
  private Boolean rm;
  private Boolean sigproxy;
  private Set<String> cmd;
  private String image;
  private Set<String> groupadd;
  private Set<String> dnsopt;
  private Boolean disablecontenttrust;
  private String cgroupparent;
  private Integer cpuperiod;
  private Integer cpuquota;
  private String cpusetcpus;
  private String cpusetmems;
  private String ipc;
  private Integer kernelmemory;
  private Set<String> labels;
  private String logdriver;
  private String logopt;
  private String macaddress;
  private Integer memoryreservation;
  private Integer memoryswap;
  private Integer memoryswappiness;
  private Boolean oomkilldisable;
  private Boolean readonly;
  private Set<String> securityopt;
  private Integer shmsize;
  private String stopsignal;
  private Integer blkioweight;
  private String pid;
  private Boolean publishallports;


  public DQPContainerCreate() {
    this.publishallports = Boolean.FALSE;
    this.blkioweight = 0;
    this.stopsignal = "SIGTERM";
    this.shmsize = 0;
    this.readonly = Boolean.FALSE;
    this.disablecontenttrust = Boolean.TRUE;
    this.oomkilldisable = Boolean.FALSE;
    this.memoryswappiness = -1;
    this.kernelmemory = 0;
    this.memoryreservation = 0;
    this.memoryswap = 0;
    this.detach = Boolean.FALSE;
    this.interactive = Boolean.FALSE;
    this.tty = Boolean.FALSE;
    this.cpushares = 0;
    this.net = "bridge";
    this.privileged = Boolean.FALSE;
    this.restart = DRestart.NO;
    this.rm = Boolean.FALSE;
    this.sigproxy = Boolean.FALSE;
    this.memory = 0;
    this.cpuperiod = 0;
    this.cpuquota = 0;
  }

  public DQPContainerCreate publishallports() {
    return this.publishallports(Boolean.TRUE);
  }

  public DQPContainerCreate publishallports(Boolean publishallports) {
    this.publishallports = publishallports;
    return this;
  }

  public DQPContainerCreate pid(String pid) {
    this.pid = pid;
    return this;
  }

  public DQPContainerCreate detach() {
    return this.detach(Boolean.TRUE);
  }

  public DQPContainerCreate detach(Boolean detach) {
    this.detach = detach;
    return this;
  }

  public DQPContainerCreate interactive() {
    return this.interactive(Boolean.TRUE);
  }

  public DQPContainerCreate interactive(Boolean interactive) {
    this.interactive = interactive;
    return this;
  }

  public DQPContainerCreate tty() {
    return this.tty(Boolean.TRUE);
  }

  public DQPContainerCreate tty(Boolean tty) {
    this.tty = tty;
    return this;
  }

  public DQPContainerCreate user(String user) {
    this.user = user;
    return this;
  }

  public DQPContainerCreate attach(String attach) {
    if (this.attach == null)
      this.attach = new ArrayList<>();
    this.attach.add(attach);
    return this;
  }

  public DQPContainerCreate attach(List<String> attach) {
    this.attach = attach;
    return this;
  }

  public DQPContainerCreate workdir(String workdir) {
    this.workdir = workdir;
    return this;
  }

  public DQPContainerCreate cpushares(Integer cpushares) {
    this.cpushares = cpushares;
    return this;
  }

  public DQPContainerCreate env(DEnv env) {
    return this.env(env.name(), env.value());
  }

  public DQPContainerCreate env(String env) {
    if (this.env == null)
      this.env = new HashSet<>();
    this.env.add(env);
    return this;
  }

  public DQPContainerCreate env(String name, String value) {
    if (this.env == null)
      this.env = new HashSet<>();
    this.env.add(TextKit.union(name, "=", value));
    return this;
  }

  public DQPContainerCreate env(Set<String> env) {
    this.env = env;
    return this;
  }

  public DQPContainerCreate memory(Integer memory) {
    this.memory = memory;
    return this;
  }

  public DQPContainerCreate publish(String publish) {
    if (this.publish == null)
      this.publish = new HashSet<>();
    this.publish.add(publish);
    return this;
  }

  public DQPContainerCreate publish(Set<String> publish) {
    this.publish = publish;
    return this;
  }

  public DQPContainerCreate hostname(String hostname) {
    this.hostname = hostname;
    return this;
  }

  public DQPContainerCreate volume(String volume) {
    if (this.volume == null)
      this.volume = new HashSet<>();
    this.volume.add(volume);
    return this;
  }

  public DQPContainerCreate volume(Set<String> volume) {
    this.volume = volume;
    return this;
  }

  public DQPContainerCreate capadd(String capadd) {
    if (this.capadd == null)
      this.capadd = new HashSet<>();
    this.capadd.add(capadd);
    return this;
  }

  public DQPContainerCreate capadd(Set<String> capadd) {
    this.capadd = capadd;
    return this;
  }

  public DQPContainerCreate capdrop(String capdrop) {
    if (this.capdrop == null)
      this.capdrop = new HashSet<>();
    this.capdrop.add(capdrop);
    return this;
  }

  public DQPContainerCreate capdrop(Set<String> capdrop) {
    this.capdrop = capdrop;
    return this;
  }

  public DQPContainerCreate cidfile(String cidfile) {
    this.cidfile = cidfile;
    return this;
  }

  public DQPContainerCreate cpuset(String cpuset) {
    this.cpuset = cpuset;
    return this;
  }

  public DQPContainerCreate device(String device) {
    if (this.device == null)
      this.device = new HashSet<>();
    this.device.add(device);
    return this;
  }

  public DQPContainerCreate device(Set<String> device) {
    this.device = device;
    return this;
  }

  public DQPContainerCreate dns(String dns) {
    if (this.dns == null)
      this.dns = new HashSet<>();
    this.dns.add(dns);
    return this;
  }

  public DQPContainerCreate dns(Set<String> dns) {
    this.dns = dns;
    return this;
  }

  public DQPContainerCreate dnssearch(String dnssearch) {
    if (this.dnssearch == null)
      this.dnssearch = new HashSet<>();
    this.dnssearch.add(dnssearch);
    return this;
  }

  public DQPContainerCreate dnssearch(Set<String> dnssearch) {
    this.dnssearch = dnssearch;
    return this;
  }

  public DQPContainerCreate entrypoint(String entrypoint) {
    this.entrypoint = entrypoint;
    return this;
  }

  public DQPContainerCreate envfile(String envfile) {
    if (this.envfile == null)
      this.envfile = new HashSet<>();
    this.envfile.add(envfile);
    return this;
  }

  public DQPContainerCreate envfile(Set<String> envfile) {
    this.envfile = envfile;
    return this;
  }

  public DQPContainerCreate expose(String expose) {
    if (this.expose == null)
      this.expose = new HashSet<>();
    this.expose.add(expose);
    return this;
  }

  public DQPContainerCreate expose(Set<String> expose) {
    this.expose = expose;
    return this;
  }

  public DQPContainerCreate link(String link) {
    if (this.link == null)
      this.link = new HashSet<>();
    this.link.add(link);
    return this;
  }

  public DQPContainerCreate link(Set<String> link) {
    this.link = link;
    return this;
  }

  public DQPContainerCreate lxcconf(String lxcconf) {
    if (this.lxcconf == null)
      this.lxcconf = new HashSet<>();
    this.lxcconf.add(lxcconf);
    return this;
  }

  public DQPContainerCreate lxcconf(Set<String> lxcconf) {
    this.lxcconf = lxcconf;
    return this;
  }

  public DQPContainerCreate name(String name) {
    this.name = name;
    return this;
  }

  public DQPContainerCreate net(String net) {
    this.net = net;
    return this;
  }

  public DQPContainerCreate privileged() {
    return this.privileged(Boolean.TRUE);
  }

  public DQPContainerCreate privileged(Boolean privileged) {
    this.privileged = privileged;
    return this;
  }

  public DQPContainerCreate restart(DRestart restart) {
    this.restart = restart;
    return this;
  }

  public DQPContainerCreate rm() {
    return this.rm(Boolean.TRUE);
  }

  public DQPContainerCreate rm(Boolean rm) {
    this.rm = rm;
    return this;
  }

  public DQPContainerCreate sigproxy() {
    return this.sigproxy(Boolean.TRUE);
  }

  public DQPContainerCreate sigproxy(Boolean sigproxy) {
    this.sigproxy = sigproxy;
    return this;
  }

  public DQPContainerCreate cmd(String cmd) {
    if (this.cmd == null)
      this.cmd = new HashSet<>();
    this.cmd.add(cmd);
    return this;
  }

  public DQPContainerCreate cmd(Set<String> cmd) {
    this.cmd = cmd;
    return this;
  }

  public DQPContainerCreate image(String image) {
    this.image = image;
    return this;
  }

  public DQPContainerCreate groupadd(String groupadd) {
    if (this.groupadd == null)
      this.groupadd = new HashSet<>();
    this.groupadd.add(groupadd);
    return this;
  }

  public DQPContainerCreate groupadd(Set<String> groupadd) {
    this.groupadd = groupadd;
    return this;
  }

  public DQPContainerCreate dnsopt(String dnsopt) {
    if (this.dnsopt == null)
      this.dnsopt = new HashSet<>();
    this.dnsopt.add(dnsopt);
    return this;
  }

  public DQPContainerCreate dnsopt(Set<String> dnsopt) {
    this.dnsopt = dnsopt;
    return this;
  }

  public DQPContainerCreate disablecontenttrust() {
    return this.disablecontenttrust(Boolean.TRUE);
  }

  public DQPContainerCreate disablecontenttrust(Boolean disablecontenttrust) {
    this.disablecontenttrust = disablecontenttrust;
    return this;
  }

  public DQPContainerCreate cgroupparent(String cgroupparent) {
    this.cgroupparent = cgroupparent;
    return this;
  }

  public DQPContainerCreate cpuperiod(Integer cpuperiod) {
    this.cpuperiod = cpuperiod;
    return this;
  }

  public DQPContainerCreate cpuquota(Integer cpuquota) {
    this.cpuquota = cpuquota;
    return this;
  }

  public DQPContainerCreate cpusetcpus(String cpusetcpus) {
    this.cpusetcpus = cpusetcpus;
    return this;
  }

  public DQPContainerCreate cpusetmems(String cpusetmems) {
    this.cpusetmems = cpusetmems;
    return this;
  }

  public DQPContainerCreate ipc(String ipc) {
    this.ipc = ipc;
    return this;
  }

  public DQPContainerCreate kernelmemory(Integer kernelmemory) {
    this.kernelmemory = kernelmemory;
    return this;
  }

  public DQPContainerCreate labels(String label) {
    if (this.labels == null)
      this.labels = new HashSet<>();
    this.labels.add(label);
    return this;
  }

  public DQPContainerCreate labels(Set<String> labels) {
    this.labels = labels;
    return this;
  }

  public DQPContainerCreate logdriver(String logdriver) {
    this.logdriver = logdriver;
    return this;
  }

  public DQPContainerCreate logopt(String logopt) {
    this.logopt = logopt;
    return this;
  }

  public DQPContainerCreate macaddress(String macaddress) {
    this.macaddress = macaddress;
    return this;
  }

  public DQPContainerCreate memoryreservation(Integer memoryreservation) {
    this.memoryreservation = memoryreservation;
    return this;
  }

  public DQPContainerCreate memoryswap(Integer memoryswap) {
    this.memoryswap = memoryswap;
    return this;
  }

  public DQPContainerCreate memoryswappiness(Integer memoryswappiness) {
    this.memoryswappiness = memoryswappiness;
    return this;
  }

  public DQPContainerCreate oomkilldisable() {
    return this.oomkilldisable(Boolean.TRUE);
  }

  public DQPContainerCreate oomkilldisable(Boolean oomkilldisable) {
    this.oomkilldisable = oomkilldisable;
    return this;
  }

  public DQPContainerCreate readonly() {
    return this.readonly(Boolean.TRUE);
  }

  public DQPContainerCreate readonly(Boolean readonly) {
    this.readonly = readonly;
    return this;
  }

  public DQPContainerCreate securityopt(String securityopt) {
    if (this.securityopt == null)
      this.securityopt = new HashSet<>();
    this.securityopt.add(securityopt);
    return this;
  }


  public DQPContainerCreate securityopt(Set<String> securityopt) {
    this.securityopt = securityopt;
    return this;
  }

  public DQPContainerCreate shmsize(Integer shmsize) {
    this.shmsize = shmsize;
    return this;
  }

  public DQPContainerCreate stopsignal(String stopsignal) {
    this.stopsignal = stopsignal;
    return this;
  }

  public DQPContainerCreate blkioweight(Integer blkioweight) {
    this.blkioweight = blkioweight;
    return this;
  }

  public boolean autoremove() {
    return this.rm;
  }

  public boolean isinteractive() {
    return this.interactive;
  }

  public boolean showtty() {
    return this.tty;
  }

  public boolean isdetach() {
    return this.detach;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create()
      .putIf("Hostname", this.hostname)
      .put("Domainname", "")
      .putIf("User", this.user)
      .put("AttachStdin", this.detach ? Boolean.FALSE : this.interactive)
      .put("AttachStdout", this.detach ? Boolean.FALSE : this.interactive)
      .put("AttachStderr", this.detach ? Boolean.FALSE : this.interactive)
      .putIf("ExposedPorts", this.exposed())
      .putIf("Tty", this.tty)
      .put("OpenStdin", this.interactive)
      .put("StdinOnce", this.detach ? Boolean.FALSE : this.interactive)
      .putIf("Env", this.env)
      .putIf("Cmd", this.cmd)
      .putIf("Image", this.image)
      .put("Volumes", Collections.emptyMap())
      .putIf("WorkingDir", this.workdir)
      .putIf("Entrypoint", this.entrypoint)
      .putIf("OnBuild", null)
      .putIf("Labels", this.labels())
      .putIf("HostConfig", this.hostconfig())
      .putIf("NetworkingConfig", this.networkingconfig());
    return dqr;
  }

  private Kv exposed() {
    Set<String> exposes = this.expose;
    if (exposes == null)
      return null;
    Kv kv = Kv.create();
    exposes.forEach(expo -> {
      String key = expo.contains("/") ? expo : TextKit.union(expo, "/tcp");
      kv.set(key, Collections.emptyMap());
    });
    return kv;
  }

  private Kv hostconfig() {
    Kv kv = Kv.create();
    kv.set("Binds", this.volume)
      .set("ContainerIDFile", this.cidfile)
      .set("LogConfig", Kv.by("Type", "").set("Config", Collections.emptyMap()))
      .set("NetworkMode", this.net)
      .set("PortBindings", this.publish())
      .set("RestartPolicy", this.restart())
      .set("AutoRemove", this.rm)
      .set("VolumeDriver", "")
      .set("VolumesFrom", null)
      .set("CapAdd", this.capadd)
      .set("CapDrop", this.capdrop)
      .set("Dns", this.dns == null ? Collections.emptySet() : this.dns)
      .set("DnsOptions", this.dnsopt == null ? Collections.emptySet() : this.dnsopt)
      .set("DnsSearch", this.dnssearch == null ? Collections.emptySet() : this.dnssearch)
      .set("ExtraHosts", null)
      .set("GroupAdd", this.groupadd)
      .set("IpcMode", this.ipc)
      .set("Cgroup", "")
      .set("Links", this.link)
      .set("OomScoreAdj", 0)
      .set("PidMode", this.pid)
      .set("Privileged", this.privileged)
      .set("PublishAllPorts", this.publishallports)
      .set("ReadonlyRootfs", this.readonly)
      .set("SecurityOpt", this.securityopt)
      .set("UTSMode", "")
      .set("UsernsMode", "")
      .set("ShmSize", this.shmsize)
      .set("ConsoleSize", new Integer[]{0, 0})
      .set("Isolation", "")
      .set("CpuShares", this.cpushares)
      .set("Memory", this.memory)
      .set("NanoCpus", 0)
      .set("CgroupParent", this.cgroupparent)
      .set("BlkioWeight", this.blkioweight)
      .set("BlkioWeightDevice", Collections.emptySet())
      .set("BlkioDeviceReadBps", null)
      .set("BlkioDeviceWriteBps", null)
      .set("BlkioDeviceReadIOps", null)
      .set("CpuPeriod", this.cpuperiod)
      .set("CpuQuota", this.cpuquota)
      .set("CpuRealtimePeriod", 0)
      .set("CpuRealtimeRuntime", 0)
      .set("CpusetCpus", this.cpusetcpus)
      .set("CpusetMems", this.cpusetmems)
      .set("Devices", Collections.emptySet())
      .set("DeviceCgroupRules", null)
      .set("DiskQuota", 0)
      .set("KernelMemory", this.kernelmemory)
      .set("MemoryReservation", this.memoryreservation)
      .set("MemorySwap", this.memoryswap)
      .set("MemorySwappiness", this.memoryswappiness)
      .set("OomKillDisable", this.oomkilldisable)
      .set("PidsLimit", 0)
      .set("Ulimits", null)
      .set("CpuCount", 0)
      .set("CpuPercent", 0)
      .set("IOMaximumIOps", 0)
      .set("IOMaximumBandwidth", 0)
      .set("MaskedPaths", null)
      .set("ReadonlyPaths", null);
    return kv;
  }

  private Kv labels() {
    if (this.labels == null)
      return null;
    Kv kv = Kv.create(this.labels.size());
    this.labels.forEach(label -> kv.set(label, ""));
    return kv;
  }

  private Kv restart() {
    Kv kv = Kv.create(2)
      .set("Name", this.restart.val())
      .set("MaximumRetryCount", 0);
    return kv;
  }

  private Kv publish() {
    if (this.publish == null)
      return null;
    Kv kv = Kv.create();
    this.publish.forEach(pub -> {
      // 127.0.0.1:8080:80/tcp
      int ix = pub.lastIndexOf(":");
      if (ix == -1) {
        throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.docker.container_port_fail", pub));
      }
      String left = pub.substring(0, ix),
        right = pub.substring(ix + 1);
      String key, host = null, port;
      int six = right.indexOf("/");
      if (six != -1) {
        if (!NumberKit.isNumber(right.substring(0, six)))
          throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.docker.container_ill_port", right));
      } else {
        if (!NumberKit.isNumber(right))
          throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.docker.container_ill_port", right));
      }
      key = six != -1 ? right : TextKit.union(right, "/tcp");

      int qix = left.indexOf(":");
      if (qix == -1) {
        port = left;
      } else {
        host = left.substring(0, qix);
        port = left.substring(qix + 1);
      }
      if (!NumberKit.isNumber(port))
        throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.docker.container_ill_port", port));

      Kv rel = Kv.create()
        .set("HostIp", host)
        .set("HostPort", port);

      List<Kv> psm = kv.as(key);
      if (psm == null) {
        psm = new ArrayList<>();
        psm.add(rel);
        kv.set(key, psm);
        return;
      }

      AtomicBoolean exists = new AtomicBoolean(false);
      psm.forEach(m -> {
        String _mhost = m.string("HostIp"),
          _mport = m.string("HostPort");
        boolean eqport = _mport.equals(rel.string("HostPort"));

        if (_mhost == null && rel.get("HostIp") == null && eqport) {
          exists.set(true);
          return;
        }
        if (_mhost != null && rel.get("HostIp") != null) {
          if (_mhost.equals(rel.string("HostIp")) && eqport) {
            exists.set(true);
            return;
          }
        }
      });
      if (exists.get())
        return;
      psm.add(rel);
    });
    return kv;
  }

  private Kv networkingconfig() {
    Kv kv = Kv.create();
    kv.set("EndpointsConfig", Collections.emptyMap());
    return kv;
  }


}
