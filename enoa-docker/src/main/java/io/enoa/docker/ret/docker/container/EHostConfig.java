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
package io.enoa.docker.ret.docker.container;

import io.enoa.docker.ret.AbstractDockerRet;
import io.enoa.toolkit.map.Kv;

import java.util.List;

public class EHostConfig extends AbstractDockerRet {

  //  private Object BlkioWeightDevice;
//  private Object BlkioDeviceReadBps;
//  private Object BlkioDeviceWriteBps;
//  private Object BlkioDeviceReadIOps;
//  private Object BlkioDeviceWriteIOps;
//  private Object ulimits;
  private final String containeridfile;
  private final Integer maximumiops;
  private final Integer maximumiobps;
  private final Integer blkioweight;
  private final String cpusetcpus;
  private final String cpusetmems;
  private final Integer cpupercent;
  private final Integer cpushares;
  private final Integer cpuperiod;
  private final Integer cpurealtimeperiod;
  private final Integer cpurealtimeruntime;
  private final Object devices;
  private final String ipcmode;
  private final Object lxcconf;
  private final Integer memory;
  private final Integer memoryswap;
  private final Integer memoryreservation;
  private final Integer kernelmemory;
  private final Boolean oomkilldisable;
  private final Integer oomscoreadj;
  private final String networkmode;
  private final String pidmode;
  private final Object portbindings;
  private final Boolean privileged;
  private final Boolean readonlyrootfs;
  private final Boolean publishallports;
  private final String volumedriver;
  private final Integer shmsize;
  private final ERestartPolicy restartpolicy;
  private final ELogConfig logconfig;
  private final Kv sysctl;
  private final List<String> binds;

  public EHostConfig(Builder builder) {
    this.containeridfile = builder.containeridfile;
    this.maximumiops = builder.maximumiops;
    this.maximumiobps = builder.maximumiobps;
    this.blkioweight = builder.blkioweight;
    this.cpusetcpus = builder.cpusetcpus;
    this.cpusetmems = builder.cpusetmems;
    this.cpupercent = builder.cpupercent;
    this.cpushares = builder.cpushares;
    this.cpuperiod = builder.cpuperiod;
    this.cpurealtimeperiod = builder.cpurealtimeperiod;
    this.cpurealtimeruntime = builder.cpurealtimeruntime;
    this.devices = builder.devices;
    this.ipcmode = builder.ipcmode;
    this.lxcconf = builder.lxcconf;
    this.memory = builder.memory;
    this.memoryswap = builder.memoryswap;
    this.memoryreservation = builder.memoryreservation;
    this.kernelmemory = builder.kernelmemory;
    this.oomkilldisable = builder.oomkilldisable;
    this.oomscoreadj = builder.oomscoreadj;
    this.networkmode = builder.networkmode;
    this.pidmode = builder.pidmode;
    this.portbindings = builder.portbindings;
    this.privileged = builder.privileged;
    this.readonlyrootfs = builder.readonlyrootfs;
    this.publishallports = builder.publishallports;
    this.volumedriver = builder.volumedriver;
    this.shmsize = builder.shmsize;
    this.restartpolicy = builder.restartpolicy;
    this.logconfig = builder.logconfig;
    this.sysctl = builder.sysctl;
    this.binds = builder.binds;
  }

  public List<String> binds() {
    return binds;
  }

  public String containeridfile() {
    return containeridfile;
  }

  public Integer maximumiops() {
    return maximumiops;
  }

  public Integer maximumiobps() {
    return maximumiobps;
  }

  public Integer blkioweight() {
    return blkioweight;
  }

  public String cpusetcpus() {
    return cpusetcpus;
  }

  public String cpusetmems() {
    return cpusetmems;
  }

  public Integer cpupercent() {
    return cpupercent;
  }

  public Integer cpushares() {
    return cpushares;
  }

  public Integer cpuperiod() {
    return cpuperiod;
  }

  public Integer cpurealtimeperiod() {
    return cpurealtimeperiod;
  }

  public Integer cpurealtimeruntime() {
    return cpurealtimeruntime;
  }

  public Object devices() {
    return devices;
  }

  public String ipcmode() {
    return ipcmode;
  }

  public Object lxcconf() {
    return lxcconf;
  }

  public Integer memory() {
    return memory;
  }

  public Integer memoryswap() {
    return memoryswap;
  }

  public Integer memoryreservation() {
    return memoryreservation;
  }

  public Integer kernelmemory() {
    return kernelmemory;
  }

  public Boolean oomkilldisable() {
    return oomkilldisable;
  }

  public Integer oomscoreadj() {
    return oomscoreadj;
  }

  public String networkmode() {
    return networkmode;
  }

  public String pidmode() {
    return pidmode;
  }

  public Object portbindings() {
    return portbindings;
  }

  public Boolean privileged() {
    return privileged;
  }

  public Boolean readonlyrootfs() {
    return readonlyrootfs;
  }

  public Boolean publishallports() {
    return publishallports;
  }

  public String volumedriver() {
    return volumedriver;
  }

  public Integer shmsize() {
    return shmsize;
  }

  public ERestartPolicy restartpolicy() {
    return restartpolicy;
  }

  public ELogConfig logconfig() {
    return logconfig;
  }

  public Kv sysctl() {
    return sysctl;
  }

  public static class Builder {
    private String containeridfile;
    private Integer maximumiops;
    private Integer maximumiobps;
    private Integer blkioweight;
    private String cpusetcpus;
    private String cpusetmems;
    private Integer cpupercent;
    private Integer cpushares;
    private Integer cpuperiod;
    private Integer cpurealtimeperiod;
    private Integer cpurealtimeruntime;
    private Object devices;
    private String ipcmode;
    private Object lxcconf;
    private Integer memory;
    private Integer memoryswap;
    private Integer memoryreservation;
    private Integer kernelmemory;
    private Boolean oomkilldisable;
    private Integer oomscoreadj;
    private String networkmode;
    private String pidmode;
    private Object portbindings;
    private Boolean privileged;
    private Boolean readonlyrootfs;
    private Boolean publishallports;
    private String volumedriver;
    private Integer shmsize;
    private ERestartPolicy restartpolicy;
    private ELogConfig logconfig;
    private Kv sysctl;
    private List<String> binds;


    public EHostConfig build() {
      return new EHostConfig(this);
    }

    public Builder binds(List<String> binds) {
      this.binds = binds;
      return this;
    }

    public Builder containeridfile(String containeridfile) {
      this.containeridfile = containeridfile;
      return this;
    }

    public Builder maximumiops(Integer maximumiops) {
      this.maximumiops = maximumiops;
      return this;
    }

    public Builder maximumiobps(Integer maximumiobps) {
      this.maximumiobps = maximumiobps;
      return this;
    }

    public Builder blkioweight(Integer blkioweight) {
      this.blkioweight = blkioweight;
      return this;
    }

    public Builder cpusetcpus(String cpusetcpus) {
      this.cpusetcpus = cpusetcpus;
      return this;
    }

    public Builder cpusetmems(String cpusetmems) {
      this.cpusetmems = cpusetmems;
      return this;
    }

    public Builder cpupercent(Integer cpupercent) {
      this.cpupercent = cpupercent;
      return this;
    }

    public Builder cpushares(Integer cpushares) {
      this.cpushares = cpushares;
      return this;
    }

    public Builder cpuperiod(Integer cpuperiod) {
      this.cpuperiod = cpuperiod;
      return this;
    }

    public Builder cpurealtimeperiod(Integer cpurealtimeperiod) {
      this.cpurealtimeperiod = cpurealtimeperiod;
      return this;
    }

    public Builder cpurealtimeruntime(Integer cpurealtimeruntime) {
      this.cpurealtimeruntime = cpurealtimeruntime;
      return this;
    }

    public Builder devices(Object devices) {
      this.devices = devices;
      return this;
    }

    public Builder ipcmode(String ipcmode) {
      this.ipcmode = ipcmode;
      return this;
    }

    public Builder lxcconf(Object lxcconf) {
      this.lxcconf = lxcconf;
      return this;
    }

    public Builder memory(Integer memory) {
      this.memory = memory;
      return this;
    }

    public Builder memoryswap(Integer memoryswap) {
      this.memoryswap = memoryswap;
      return this;
    }

    public Builder memoryreservation(Integer memoryreservation) {
      this.memoryreservation = memoryreservation;
      return this;
    }

    public Builder kernelmemory(Integer kernelmemory) {
      this.kernelmemory = kernelmemory;
      return this;
    }

    public Builder oomkilldisable(Boolean oomkilldisable) {
      this.oomkilldisable = oomkilldisable;
      return this;
    }

    public Builder oomscoreadj(Integer oomscoreadj) {
      this.oomscoreadj = oomscoreadj;
      return this;
    }

    public Builder networkmode(String networkmode) {
      this.networkmode = networkmode;
      return this;
    }

    public Builder pidmode(String pidmode) {
      this.pidmode = pidmode;
      return this;
    }

    public Builder portbindings(Object portbindings) {
      this.portbindings = portbindings;
      return this;
    }

    public Builder privileged(Boolean privileged) {
      this.privileged = privileged;
      return this;
    }

    public Builder readonlyrootfs(Boolean readonlyrootfs) {
      this.readonlyrootfs = readonlyrootfs;
      return this;
    }

    public Builder publishallports(Boolean publishallports) {
      this.publishallports = publishallports;
      return this;
    }

    public Builder volumedriver(String volumedriver) {
      this.volumedriver = volumedriver;
      return this;
    }

    public Builder shmsize(Integer shmsize) {
      this.shmsize = shmsize;
      return this;
    }

    public Builder restartpolicy(ERestartPolicy restartpolicy) {
      this.restartpolicy = restartpolicy;
      return this;
    }

    public Builder logconfig(ELogConfig logconfig) {
      this.logconfig = logconfig;
      return this;
    }

    public Builder sysctl(Kv sysctl) {
      this.sysctl = sysctl;
      return this;
    }
  }
}
