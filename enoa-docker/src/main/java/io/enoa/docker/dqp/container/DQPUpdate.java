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
package io.enoa.docker.dqp.container;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DQPUpdate implements DQP {


  private Integer cpushares;
  private Long memory;
  private String cgroupparent;
  private Integer blkioweight;
  private List<BlkioWeight> blkioweightdevice;
  private List<BlkioRate> blkiodevicereadbps;
  private List<BlkioRate> blkiodevicewritebps;
  private List<BlkioRate> blkiodevicereadiops;
  private List<BlkioRate> blkiodevicewriteiops;
  private Long cpuperiod;
  private Long cpuquota;
  private Long cpurealtimeperiod;
  private Long cpurealtimeruntime;
  private String cpusetcpus;
  private String cpusetmems;
  private List<Device> devices;
  private List<String> devicecgrouprules;
  private Long diskquota;
  private Long kernelmemory;
  private Long memoryreservation;
  private Long memoryswap;
  private Long memoryswappiness;
  private Long nanocpus;
  private Boolean oomkilldisable;
  private Boolean init;
  private Long pidslimit;
  private List<Ulimit> ulimits;
  private Long cpucount;
  private Long cpupercent;
  private Long iomaximumiops;
  private Long iomaximumbandwidth;
  private RestartPolicy restartpolicy;


  public static DQPUpdate create() {
    return new DQPUpdate();
  }

  public DQPUpdate() {
  }

  public DQPUpdate cpushares(Integer cpushares) {
    this.cpushares = cpushares;
    return this;
  }

  public DQPUpdate memory(Long memory) {
    this.memory = memory;
    return this;
  }

  public DQPUpdate cgroupparent(String cgroupparent) {
    this.cgroupparent = cgroupparent;
    return this;
  }

  public DQPUpdate blkioweight(Integer blkioweight) {
    this.blkioweight = blkioweight;
    return this;
  }

  public DQPUpdate blkioweightdevice(BlkioWeight blkioweightdevice) {
    if (this.blkioweightdevice == null)
      this.blkioweightdevice = new ArrayList<>();
    this.blkioweightdevice.add(blkioweightdevice);
    return this;
  }

  public DQPUpdate blkioweightdevice(List<BlkioWeight> blkioweightdevice) {
    this.blkioweightdevice = blkioweightdevice;
    return this;
  }

  public DQPUpdate blkiodevicereadbps(BlkioRate blkiodevicereadbps) {
    if (this.blkiodevicereadbps == null)
      this.blkiodevicereadbps = new ArrayList<>();
    this.blkiodevicereadbps.add(blkiodevicereadbps);
    return this;
  }

  public DQPUpdate blkiodevicereadbps(List<BlkioRate> blkiodevicereadbps) {
    this.blkiodevicereadbps = blkiodevicereadbps;
    return this;
  }

  public DQPUpdate blkiodevicewritebps(BlkioRate blkiodevicewritebps) {
    if (this.blkiodevicewritebps == null)
      this.blkiodevicewritebps = new ArrayList<>();
    this.blkiodevicewritebps.add(blkiodevicewritebps);
    return this;
  }

  public DQPUpdate blkiodevicewritebps(List<BlkioRate> blkiodevicewritebps) {
    this.blkiodevicewritebps = blkiodevicewritebps;
    return this;
  }

  public DQPUpdate blkiodevicereadiops(BlkioRate blkiodevicereadiops) {
    if (this.blkiodevicereadiops == null)
      this.blkiodevicereadiops = new ArrayList<>();
    this.blkiodevicereadiops.add(blkiodevicereadiops);
    return this;
  }

  public DQPUpdate blkiodevicereadiops(List<BlkioRate> blkiodevicereadiops) {
    this.blkiodevicereadiops = blkiodevicereadiops;
    return this;
  }

  public DQPUpdate blkiodevicewriteiops(BlkioRate blkiodevicewriteiops) {
    if (this.blkiodevicewriteiops == null)
      this.blkiodevicewriteiops = new ArrayList<>();
    this.blkiodevicewriteiops.add(blkiodevicewriteiops);
    return this;
  }

  public DQPUpdate blkiodevicewriteiops(List<BlkioRate> blkiodevicewriteiops) {
    this.blkiodevicewriteiops = blkiodevicewriteiops;
    return this;
  }

  public DQPUpdate cpuperiod(Long cpuperiod) {
    this.cpuperiod = cpuperiod;
    return this;
  }

  public DQPUpdate cpuquota(Long cpuquota) {
    this.cpuquota = cpuquota;
    return this;
  }

  public DQPUpdate cpurealtimeperiod(Long cpurealtimeperiod) {
    this.cpurealtimeperiod = cpurealtimeperiod;
    return this;
  }

  public DQPUpdate cpurealtimeruntime(Long cpurealtimeruntime) {
    this.cpurealtimeruntime = cpurealtimeruntime;
    return this;
  }

  public DQPUpdate cpusetcpus(String cpusetcpus) {
    this.cpusetcpus = cpusetcpus;
    return this;
  }

  public DQPUpdate cpusetmems(String cpusetmems) {
    this.cpusetmems = cpusetmems;
    return this;
  }

  public DQPUpdate devices(List<Device> devices) {
    this.devices = devices;
    return this;
  }

  public DQPUpdate devicecgrouprules(String devicecgrouprules) {
    if (this.devicecgrouprules == null)
      this.devicecgrouprules = new ArrayList<>();
    this.devicecgrouprules.add(devicecgrouprules);
    return this;
  }

  public DQPUpdate devicecgrouprules(List<String> devicecgrouprules) {
    this.devicecgrouprules = devicecgrouprules;
    return this;
  }

  public DQPUpdate diskquota(Long diskquota) {
    this.diskquota = diskquota;
    return this;
  }

  public DQPUpdate kernelmemory(Long kernelmemory) {
    this.kernelmemory = kernelmemory;
    return this;
  }

  public DQPUpdate memoryreservation(Long memoryreservation) {
    this.memoryreservation = memoryreservation;
    return this;
  }

  public DQPUpdate memoryswap(Long memoryswap) {
    this.memoryswap = memoryswap;
    return this;
  }

  public DQPUpdate memoryswappiness(Long memoryswappiness) {
    this.memoryswappiness = memoryswappiness;
    return this;
  }

  public DQPUpdate nanocpus(Long nanocpus) {
    this.nanocpus = nanocpus;
    return this;
  }

  public DQPUpdate oomkilldisable(Boolean oomkilldisable) {
    this.oomkilldisable = oomkilldisable;
    return this;
  }

  public DQPUpdate init(Boolean init) {
    this.init = init;
    return this;
  }

  public DQPUpdate pidslimit(Long pidslimit) {
    this.pidslimit = pidslimit;
    return this;
  }

  public DQPUpdate ulimits(List<Ulimit> ulimits) {
    this.ulimits = ulimits;
    return this;
  }

  public DQPUpdate cpucount(Long cpucount) {
    this.cpucount = cpucount;
    return this;
  }

  public DQPUpdate cpupercent(Long cpupercent) {
    this.cpupercent = cpupercent;
    return this;
  }

  public DQPUpdate iomaximumiops(Long iomaximumiops) {
    this.iomaximumiops = iomaximumiops;
    return this;
  }

  public DQPUpdate iomaximumbandwidth(Long iomaximumbandwidth) {
    this.iomaximumbandwidth = iomaximumbandwidth;
    return this;
  }

  public DQPUpdate restartpolicy(RestartPolicy restartpolicy) {
    this.restartpolicy = restartpolicy;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create();
    if (this.cpushares != null)
      dqr.put("CpuShares", this.cpushares);
    if (this.memory != null)
      dqr.put("Memory", this.memory);
    if (this.cgroupparent != null)
      dqr.put("CgroupParent", this.cgroupparent);
    if (this.blkioweight != null)
      dqr.put("BlkioWeight", this.blkioweight);
    if (this.blkioweightdevice != null) {
      List<Kv> kvs0 = this.blkioweightdevice.stream()
        .map(bwd -> Kv.create()
          .set("Path", bwd.path)
          .set("Weight", bwd.weight)
        ).collect(Collectors.toList());
      dqr.put("BlkioWeightDevice", kvs0);
    }
    if (this.blkiodevicereadbps != null)
      dqr.put("BlkioDeviceReadBps", this.ratelist(this.blkiodevicereadbps));
    if (this.blkiodevicewritebps != null)
      dqr.put("BlkioDeviceWriteBps", this.ratelist(this.blkiodevicewritebps));
    if (this.blkiodevicereadiops != null)
      dqr.put("BlkioDeviceReadIOps", this.ratelist(this.blkiodevicereadiops));
    if (this.blkiodevicewriteiops != null)
      dqr.put("BlkioDeviceWriteIOps", this.ratelist(this.blkiodevicewriteiops));
    if (this.cpuperiod != null)
      dqr.put("CpuPeriod", this.cpuperiod);
    if (this.cpuquota != null)
      dqr.put("CpuQuota", this.cpuquota);
    if (this.cpurealtimeperiod != null)
      dqr.put("CpuRealtimePeriod", this.cpurealtimeperiod);
    if (this.cpurealtimeruntime != null)
      dqr.put("CpuRealtimeRuntime", this.cpurealtimeruntime);
    if (this.cpusetcpus != null)
      dqr.put("CpusetCpus", this.cpusetcpus);
    if (this.cpusetmems != null)
      dqr.put("CpusetMems", this.cpusetmems);
    if (this.devices != null) {
      dqr.put("Devices", this.devices.stream().map(device ->
        Kv.create().set("PathOnHost", device.pathonhost)
          .set("PathInContainer", device.pathincontainer)
          .set("CgroupPermissions", device.cgrouppermissions)).collect(Collectors.toList()));
    }
    if (this.devicecgrouprules != null)
      dqr.put("DeviceCgroupRules", this.devicecgrouprules);
    if (this.diskquota != null)
      dqr.put("DiskQuota", this.diskquota);
    if (this.kernelmemory != null)
      dqr.put("KernelMemory", this.kernelmemory);
    if (this.memoryreservation != null)
      dqr.put("MemoryReservation", this.memoryreservation);
    if (this.memoryswap != null)
      dqr.put("MemorySwap", this.memoryswap);
    if (this.memoryswappiness != null)
      dqr.put("MemorySwappiness", this.memoryswappiness);
    if (this.nanocpus != null)
      dqr.put("NanoCPUs", this.nanocpus);
    if (this.oomkilldisable != null)
      dqr.put("OomKillDisable", this.oomkilldisable);
    if (this.init != null)
      dqr.put("Init", this.init);
    if (this.pidslimit != null)
      dqr.put("PidsLimit", this.pidslimit);
    if (this.ulimits != null) {
      dqr.put("Ulimits", this.ulimits.stream().map(ulimit -> Kv.create()
        .set("Name", ulimit.name)
        .set("Soft", ulimit.soft)
        .set("Hard", ulimit.hard))
        .collect(Collectors.toList()));
    }
    if (this.cpucount != null)
      dqr.put("CpuCount", this.cpucount);
    if (this.cpupercent != null)
      dqr.put("CpuPercent", this.cpupercent);
    if (this.iomaximumiops != null)
      dqr.put(" IOMaximumIOps", this.iomaximumiops);
    if (this.iomaximumbandwidth != null)
      dqr.put(" IOMaximumBandwidth", this.iomaximumbandwidth);
    if (this.restartpolicy != null) {
      dqr.put("RestartPolicy", Kv.create()
        .set("Name", this.restartpolicy.name)
        .set("MaximumRetryCount", this.restartpolicy.maximumretrycount));
    }
    return dqr;
  }

  private List<Kv> ratelist(List<BlkioRate> rates) {
    return rates.stream().map(rate -> Kv.create().set("Path", rate.path).set("Rate", rate.rate)).collect(Collectors.toList());
  }

  public static class BlkioRate {
    private String path;
    private Integer rate;

    public BlkioRate path(String path) {
      this.path = path;
      return this;
    }

    public BlkioRate rate(Integer rate) {
      this.rate = rate;
      return this;
    }

    @Override
    public String toString() {
      return "BlkioRate{" +
        "path='" + path + '\'' +
        ", rate=" + rate +
        '}';
    }
  }

  public static class BlkioWeight {
    private String path;
    private Integer weight;

    public BlkioWeight path(String path) {
      this.path = path;
      return this;
    }

    public BlkioWeight weight(Integer weight) {
      this.weight = weight;
      return this;
    }

    @Override
    public String toString() {
      return "BlkioWeight{" +
        "path='" + path + '\'' +
        ", weight=" + weight +
        '}';
    }
  }

  public static class Device {
    private String pathonhost;
    private String pathincontainer;
    private String cgrouppermissions;

    public Device pathonhost(String pathonhost) {
      this.pathonhost = pathonhost;
      return this;
    }

    public Device pathincontainer(String pathincontainer) {
      this.pathincontainer = pathincontainer;
      return this;
    }

    public Device cgrouppermissions(String cgrouppermissions) {
      this.cgrouppermissions = cgrouppermissions;
      return this;
    }

    @Override
    public String toString() {
      return "Devices{" +
        "pathonhost='" + pathonhost + '\'' +
        ", pathincontainer='" + pathincontainer + '\'' +
        ", cgrouppermissions='" + cgrouppermissions + '\'' +
        '}';
    }
  }


  public static class Ulimit {
    private String name;
    private String soft;
    private Integer hard;

    public Ulimit name(String name) {
      this.name = name;
      return this;
    }

    public Ulimit soft(String soft) {
      this.soft = soft;
      return this;
    }

    public Ulimit hard(Integer hard) {
      this.hard = hard;
      return this;
    }

    @Override
    public String toString() {
      return "Ulimit{" +
        "name='" + name + '\'' +
        ", soft='" + soft + '\'' +
        ", hard=" + hard +
        '}';
    }
  }


  public static class RestartPolicy {
    private String name;
    private Integer maximumretrycount;

    public RestartPolicy name(String name) {
      this.name = name;
      return this;
    }

    public RestartPolicy maximumretrycount(Integer maximumretrycount) {
      this.maximumretrycount = maximumretrycount;
      return this;
    }

    @Override
    public String toString() {
      return "RestartPolicy{" +
        "name='" + name + '\'' +
        ", maximumretrycount=" + maximumretrycount +
        '}';
    }
  }

}
