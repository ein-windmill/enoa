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
package io.enoa.docker.dket.docker.system;

import io.enoa.docker.dket.AbstractDRRet;
import io.enoa.docker.dket.docker.common.EGenericResource;
import io.enoa.docker.dket.docker.dockerinfo.ECommit;
import io.enoa.docker.dket.docker.dockerinfo.ERegistryConfig;
import io.enoa.docker.dket.docker.dockerinfo.ERuntimes;

import java.util.Date;
import java.util.List;

public class EInfo extends AbstractDRRet {

  private final String id;
  private final Integer containers;
  private final Integer containersrunning;
  private final Integer containerspaused;
  private final Integer containersstopped;
  private final Integer images;
  private final String driver;
  private final List<String[]> driverstatus;
  private final String dockerrootdir;
  private final List<String[]> systemstatus;
  private final EYPlugin plugin;

  private final Boolean memorylimit;
  private final Boolean swaplimit;
  private final Boolean kernelmemory;
  private final Boolean cpucfsperiod;
  private final Boolean cpucfsquota;
  private final Boolean cpushares;
  private final Boolean cpuset;
  private final Boolean oomkilldisable;
  private final Boolean ipv4forwarding;
  private final Boolean bridgenfiptables;
  private final Boolean bridgenfip6tables;
  private final Boolean debug;

  private final Integer nfd;
  private final Integer ngoroutines;
  private final Date systemtime;
  private final String loggingdriver;
  private final String cgroupdriver;
  private final Integer neventslistener;
  private final String kernelversion;
  private final String operatingsystem;
  private final String ostype;
  private final String architecture;
  private final Integer ncpu;
  private final Long memtotal;
  private final String indexserveraddress;
  private final ERegistryConfig registryconfig;
  private final List<EGenericResource> genericresources;
  private final String httpproxy;
  private final String httpsproxy;
  private final String noproxy;
  private final String name;
  private final String[] labels;
  private final Boolean experimentalbuild;
  private final String serverversion;
  private final String clusterstore;
  private final String clusteradvertise;
  private final ERuntimes runtime;
  private final String defaultruntime;
  private final EYSwarm swarm;

  private final Boolean liverestoreenabled;
  private final String isolation;
  private final String initbinary;

  private final ECommit containerdcommit;
  private final ECommit runccommit;
  private final ECommit initcommit;
  private final String[] securityoptions;

  public EInfo(Builder builder) {
    this.id = builder.id;
    this.containers = builder.containers;
    this.containersrunning = builder.containersrunning;
    this.containerspaused = builder.containerspaused;
    this.containersstopped = builder.containersstopped;
    this.images = builder.images;
    this.driver = builder.driver;
    this.driverstatus = builder.driverstatus;
    this.dockerrootdir = builder.dockerrootdir;
    this.systemstatus = builder.systemstatus;
    this.plugin = builder.plugin;
    this.memorylimit = builder.memorylimit;
    this.swaplimit = builder.swaplimit;
    this.kernelmemory = builder.kernelmemory;
    this.cpucfsperiod = builder.cpucfsperiod;
    this.cpucfsquota = builder.cpucfsquota;
    this.cpushares = builder.cpushares;
    this.cpuset = builder.cpuset;
    this.oomkilldisable = builder.oomkilldisable;
    this.ipv4forwarding = builder.ipv4forwarding;
    this.bridgenfiptables = builder.bridgenfiptables;
    this.bridgenfip6tables = builder.bridgenfip6tables;
    this.debug = builder.debug;
    this.nfd = builder.nfd;
    this.ngoroutines = builder.ngoroutines;
    this.systemtime = builder.systemtime;
    this.loggingdriver = builder.loggingdriver;
    this.cgroupdriver = builder.cgroupdriver;
    this.neventslistener = builder.neventslistener;
    this.kernelversion = builder.kernelversion;
    this.operatingsystem = builder.operatingsystem;
    this.ostype = builder.ostype;
    this.architecture = builder.architecture;
    this.ncpu = builder.ncpu;
    this.memtotal = builder.memtotal;
    this.indexserveraddress = builder.indexserveraddress;
    this.registryconfig = builder.registryconfig;
    this.genericresources = builder.genericresources;
    this.httpproxy = builder.httpproxy;
    this.httpsproxy = builder.httpsproxy;
    this.noproxy = builder.noproxy;
    this.name = builder.name;
    this.labels = builder.labels;
    this.experimentalbuild = builder.experimentalbuild;
    this.serverversion = builder.serverversion;
    this.clusterstore = builder.clusterstore;
    this.clusteradvertise = builder.clusteradvertise;
    this.runtime = builder.runtime;
    this.defaultruntime = builder.defaultruntime;
    this.swarm = builder.swarm;
    this.liverestoreenabled = builder.liverestoreenabled;
    this.isolation = builder.isolation;
    this.initbinary = builder.initbinary;
    this.containerdcommit = builder.containerdcommit;
    this.runccommit = builder.runccommit;
    this.initcommit = builder.initcommit;
    this.securityoptions = builder.securityoptions;
  }

  public String id() {
    return this.id;
  }

  public Integer containers() {
    return this.containers;
  }

  public Integer containersrunning() {
    return this.containersrunning;
  }

  public Integer containerspaused() {
    return this.containerspaused;
  }

  public Integer containersstopped() {
    return this.containersstopped;
  }

  public Integer images() {
    return this.images;
  }

  public String driver() {
    return this.driver;
  }

  public List<String[]> driverstatus() {
    return this.driverstatus;
  }

  public String dockerrootdir() {
    return this.dockerrootdir;
  }

  public List<String[]> systemstatus() {
    return this.systemstatus;
  }

  public EYPlugin plugin() {
    return this.plugin;
  }

  public Boolean memorylimit() {
    return this.memorylimit;
  }

  public Boolean swaplimit() {
    return this.swaplimit;
  }

  public Boolean kernelmemory() {
    return this.kernelmemory;
  }

  public Boolean cpucfsperiod() {
    return this.cpucfsperiod;
  }

  public Boolean cpucfsquota() {
    return this.cpucfsquota;
  }

  public Boolean cpushares() {
    return this.cpushares;
  }

  public Boolean cpuset() {
    return this.cpuset;
  }

  public Boolean oomkilldisable() {
    return this.oomkilldisable;
  }

  public Boolean ipv4forwarding() {
    return this.ipv4forwarding;
  }

  public Boolean bridgenfiptables() {
    return this.bridgenfiptables;
  }

  public Boolean bridgenfip6tables() {
    return this.bridgenfip6tables;
  }

  public Boolean debug() {
    return this.debug;
  }

  public Integer nfd() {
    return this.nfd;
  }

  public Integer ngoroutines() {
    return this.ngoroutines;
  }

  public Date systemtime() {
    return this.systemtime;
  }

  public String loggingdriver() {
    return this.loggingdriver;
  }

  public String cgroupdriver() {
    return this.cgroupdriver;
  }

  public Integer neventslistener() {
    return this.neventslistener;
  }

  public String kernelversion() {
    return this.kernelversion;
  }

  public String operatingsystem() {
    return this.operatingsystem;
  }

  public String ostype() {
    return this.ostype;
  }

  public String architecture() {
    return this.architecture;
  }

  public Integer ncpu() {
    return this.ncpu;
  }

  public Long memtotal() {
    return this.memtotal;
  }

  public String indexserveraddress() {
    return this.indexserveraddress;
  }

  public ERegistryConfig registryconfig() {
    return this.registryconfig;
  }

  public List<EGenericResource> genericresources() {
    return this.genericresources;
  }

  public String httpproxy() {
    return this.httpproxy;
  }

  public String httpsproxy() {
    return this.httpsproxy;
  }

  public String noproxy() {
    return this.noproxy;
  }

  public String name() {
    return this.name;
  }

  public String[] labels() {
    return this.labels;
  }

  public Boolean experimentalbuild() {
    return this.experimentalbuild;
  }

  public String serverversion() {
    return this.serverversion;
  }

  public String clusterstore() {
    return this.clusterstore;
  }

  public String clusteradvertise() {
    return this.clusteradvertise;
  }

  public ERuntimes runtime() {
    return this.runtime;
  }

  public String defaultruntime() {
    return this.defaultruntime;
  }

  public EYSwarm swarm() {
    return this.swarm;
  }

  public Boolean liverestoreenabled() {
    return this.liverestoreenabled;
  }

  public String isolation() {
    return this.isolation;
  }

  public String initbinary() {
    return this.initbinary;
  }

  public ECommit containerdcommit() {
    return this.containerdcommit;
  }

  public ECommit runccommit() {
    return this.runccommit;
  }

  public ECommit initcommit() {
    return this.initcommit;
  }

  public String[] securityoptions() {
    return this.securityoptions;
  }

  public static class Builder {

    private String id;
    private Integer containers;
    private Integer containersrunning;
    private Integer containerspaused;
    private Integer containersstopped;
    private Integer images;
    private String driver;
    private List<String[]> driverstatus;
    private String dockerrootdir;
    private List<String[]> systemstatus;
    private EYPlugin plugin;

    private Boolean memorylimit;
    private Boolean swaplimit;
    private Boolean kernelmemory;
    private Boolean cpucfsperiod;
    private Boolean cpucfsquota;
    private Boolean cpushares;
    private Boolean cpuset;
    private Boolean oomkilldisable;
    private Boolean ipv4forwarding;
    private Boolean bridgenfiptables;
    private Boolean bridgenfip6tables;
    private Boolean debug;

    private Integer nfd;
    private Integer ngoroutines;
    private Date systemtime;
    private String loggingdriver;
    private String cgroupdriver;
    private Integer neventslistener;
    private String kernelversion;
    private String operatingsystem;
    private String ostype;
    private String architecture;
    private Integer ncpu;
    private Long memtotal;
    private String indexserveraddress;
    private ERegistryConfig registryconfig;
    private List<EGenericResource> genericresources;
    private String httpproxy;
    private String httpsproxy;
    private String noproxy;
    private String name;
    private String[] labels;
    private Boolean experimentalbuild;
    private String serverversion;
    private String clusterstore;
    private String clusteradvertise;
    private ERuntimes runtime;
    private String defaultruntime;
    private EYSwarm swarm;

    private Boolean liverestoreenabled;
    private String isolation;
    private String initbinary;

    private ECommit containerdcommit;
    private ECommit runccommit;
    private ECommit initcommit;
    private String[] securityoptions;

    public EInfo build() {
      return new EInfo(this);
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder containers(Integer containers) {
      this.containers = containers;
      return this;
    }

    public Builder containersrunning(Integer containersrunning) {
      this.containersrunning = containersrunning;
      return this;
    }

    public Builder containerspaused(Integer containerspaused) {
      this.containerspaused = containerspaused;
      return this;
    }

    public Builder containersstopped(Integer containersstopped) {
      this.containersstopped = containersstopped;
      return this;
    }

    public Builder images(Integer images) {
      this.images = images;
      return this;
    }

    public Builder driver(String driver) {
      this.driver = driver;
      return this;
    }

    public Builder driverstatus(List<String[]> driverstatus) {
      this.driverstatus = driverstatus;
      return this;
    }

    public Builder dockerrootdir(String dockerrootdir) {
      this.dockerrootdir = dockerrootdir;
      return this;
    }

    public Builder systemstatus(List<String[]> systemstatus) {
      this.systemstatus = systemstatus;
      return this;
    }

    public Builder plugin(EYPlugin plugin) {
      this.plugin = plugin;
      return this;
    }

    public Builder memorylimit(Boolean memorylimit) {
      this.memorylimit = memorylimit;
      return this;
    }

    public Builder swaplimit(Boolean swaplimit) {
      this.swaplimit = swaplimit;
      return this;
    }

    public Builder kernelmemory(Boolean kernelmemory) {
      this.kernelmemory = kernelmemory;
      return this;
    }

    public Builder cpucfsperiod(Boolean cpucfsperiod) {
      this.cpucfsperiod = cpucfsperiod;
      return this;
    }

    public Builder cpucfsquota(Boolean cpucfsquota) {
      this.cpucfsquota = cpucfsquota;
      return this;
    }

    public Builder cpushares(Boolean cpushares) {
      this.cpushares = cpushares;
      return this;
    }

    public Builder cpuset(Boolean cpuset) {
      this.cpuset = cpuset;
      return this;
    }

    public Builder oomkilldisable(Boolean oomkilldisable) {
      this.oomkilldisable = oomkilldisable;
      return this;
    }

    public Builder ipv4forwarding(Boolean ipv4forwarding) {
      this.ipv4forwarding = ipv4forwarding;
      return this;
    }

    public Builder bridgenfiptables(Boolean bridgenfiptables) {
      this.bridgenfiptables = bridgenfiptables;
      return this;
    }

    public Builder bridgenfip6tables(Boolean bridgenfip6tables) {
      this.bridgenfip6tables = bridgenfip6tables;
      return this;
    }

    public Builder debug(Boolean debug) {
      this.debug = debug;
      return this;
    }

    public Builder nfd(Integer nfd) {
      this.nfd = nfd;
      return this;
    }

    public Builder ngoroutines(Integer ngoroutines) {
      this.ngoroutines = ngoroutines;
      return this;
    }

    public Builder systemtime(Date systemtime) {
      this.systemtime = systemtime;
      return this;
    }

    public Builder loggingdriver(String loggingdriver) {
      this.loggingdriver = loggingdriver;
      return this;
    }

    public Builder cgroupdriver(String cgroupdriver) {
      this.cgroupdriver = cgroupdriver;
      return this;
    }

    public Builder neventslistener(Integer neventslistener) {
      this.neventslistener = neventslistener;
      return this;
    }

    public Builder kernelversion(String kernelversion) {
      this.kernelversion = kernelversion;
      return this;
    }

    public Builder operatingsystem(String operatingsystem) {
      this.operatingsystem = operatingsystem;
      return this;
    }

    public Builder ostype(String ostype) {
      this.ostype = ostype;
      return this;
    }

    public Builder architecture(String architecture) {
      this.architecture = architecture;
      return this;
    }

    public Builder ncpu(Integer ncpu) {
      this.ncpu = ncpu;
      return this;
    }

    public Builder memtotal(Long memtotal) {
      this.memtotal = memtotal;
      return this;
    }

    public Builder indexserveraddress(String indexserveraddress) {
      this.indexserveraddress = indexserveraddress;
      return this;
    }

    public Builder registryconfig(ERegistryConfig registryconfig) {
      this.registryconfig = registryconfig;
      return this;
    }

    public Builder genericresources(List<EGenericResource> genericresources) {
      this.genericresources = genericresources;
      return this;
    }

    public Builder httpproxy(String httpproxy) {
      this.httpproxy = httpproxy;
      return this;
    }

    public Builder httpsproxy(String httpsproxy) {
      this.httpsproxy = httpsproxy;
      return this;
    }

    public Builder noproxy(String noproxy) {
      this.noproxy = noproxy;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder labels(String[] labels) {
      this.labels = labels;
      return this;
    }

    public Builder experimentalbuild(Boolean experimentalbuild) {
      this.experimentalbuild = experimentalbuild;
      return this;
    }

    public Builder serverversion(String serverversion) {
      this.serverversion = serverversion;
      return this;
    }

    public Builder clusterstore(String clusterstore) {
      this.clusterstore = clusterstore;
      return this;
    }

    public Builder clusteradvertise(String clusteradvertise) {
      this.clusteradvertise = clusteradvertise;
      return this;
    }

    public Builder runtime(ERuntimes runtime) {
      this.runtime = runtime;
      return this;
    }

    public Builder defaultruntime(String defaultruntime) {
      this.defaultruntime = defaultruntime;
      return this;
    }

    public Builder swarm(EYSwarm swarm) {
      this.swarm = swarm;
      return this;
    }

    public Builder liverestoreenabled(Boolean liverestoreenabled) {
      this.liverestoreenabled = liverestoreenabled;
      return this;
    }

    public Builder isolation(String isolation) {
      this.isolation = isolation;
      return this;
    }

    public Builder initbinary(String initbinary) {
      this.initbinary = initbinary;
      return this;
    }

    public Builder containerdcommit(ECommit containerdcommit) {
      this.containerdcommit = containerdcommit;
      return this;
    }

    public Builder runccommit(ECommit runccommit) {
      this.runccommit = runccommit;
      return this;
    }

    public Builder initcommit(ECommit initcommit) {
      this.initcommit = initcommit;
      return this;
    }

    public Builder securityoptions(String[] securityoptions) {
      this.securityoptions = securityoptions;
      return this;
    }
  }

}
