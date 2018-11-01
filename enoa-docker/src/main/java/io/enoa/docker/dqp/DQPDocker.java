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
package io.enoa.docker.dqp;

import io.enoa.docker.dket.docker.service.DQPServiceLogs;
import io.enoa.docker.dqp.common.DQPFilter;
import io.enoa.docker.dqp.common.DQPResize;
import io.enoa.docker.dqp.docker.container.*;
import io.enoa.docker.dqp.docker.exec.DQPExecCreate;
import io.enoa.docker.dqp.docker.exec.DQPExecStart;
import io.enoa.docker.dqp.docker.image.*;
import io.enoa.docker.dqp.docker.network.DQPNetworkInspect;
import io.enoa.docker.dqp.docker.network.DQPNetworkList;
import io.enoa.docker.dqp.docker.plugin.DQPPluginInstall;
import io.enoa.docker.dqp.docker.plugin.DQPPluginUpgrade;
import io.enoa.docker.dqp.docker.service.DQPServiceCreate;
import io.enoa.docker.dqp.docker.service.DQPServiceUpdate;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmJoin;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmUnlock;
import io.enoa.docker.dqp.docker.swarm.DQPSwarmUpdate;
import io.enoa.docker.dqp.docker.system.DQPSystemAuth;
import io.enoa.docker.dqp.docker.volume.DQPVolumeCreate;
import io.enoa.docker.dqp.docker.volume.DQPVolumeList;

public final class DQPDocker {

  private static class Holder {
    private static final DQPDocker INSTANCE = new DQPDocker();
  }

  static DQPDocker instance() {
    return Holder.INSTANCE;
  }

  private final Container container;
  private final Exec exec;
  private final Image image;
  private final Network network;
  private final Plugin plugin;
  private final Service service;
  private final Swarm swarm;
  private final System system;
  private final Volume volume;

  private DQPDocker() {
    this.container = new Container();
    this.exec = new Exec();
    this.image = new Image();
    this.network = new Network();
    this.plugin = new Plugin();
    this.service = new Service();
    this.swarm = new Swarm();
    this.system = new System();
    this.volume = new Volume();
  }

  public DQPFilter filter() {
    return DQPFilter.create();
  }

  public DQPResize resize() {
    return DQPResize.create();
  }


  public Container container() {
    return this.container;
  }

  public Exec exec() {
    return this.exec;
  }

  public Image image() {
    return image;
  }

  public Network network() {
    return network;
  }

  public Plugin plugin() {
    return plugin;
  }

  public Service service() {
    return service;
  }

  public Swarm swarm() {
    return swarm;
  }

  public System system() {
    return system;
  }

  public Volume volume() {
    return volume;
  }

  public static final class Container {


//    public DQPContainerCreate run() {
//      return DQPContainerCreate.create();
//    }

    public DQPContainerCreate create() {
      return DQPContainerCreate.create();
    }

    public DQPContainerList list() {
      return DQPContainerList.create();
    }

    public DQPContainerLogs logs() {
      return DQPContainerLogs.create();
    }

    public DQPContainerStart start() {
      return DQPContainerStart.create();
    }

    public DQPContainerTime time() {
      return DQPContainerTime.create();
    }

    public DQPContainerKill kill() {
      return DQPContainerKill.create();
    }

    public DQPContainerUpdate update() {
      return DQPContainerUpdate.create();
    }

    public DQPContainerAttch attch() {
      return DQPContainerAttch.create();
    }

    public DQPContainerRemove remove() {
      return DQPContainerRemove.create();
    }

  }

  public static final class Exec {

    public DQPExecCreate create() {
      return DQPExecCreate.create();
    }

    public DQPExecStart start() {
      return DQPExecStart.create();
    }

  }

  public static final class Image {

    public DQPImageList list() {
      return DQPImageList.create();
    }

    public DQPImageBuild build() {
      return DQPImageBuild.create();
    }

    public DQPImageCreate create() {
      return DQPImageCreate.create();
    }

    public DQPImagePush push() {
      return DQPImagePush.create();
    }

    public DQPImageTag tag() {
      return DQPImageTag.create();
    }

    public DQPImageRmi rmi() {
      return DQPImageRmi.create();
    }

    public DQPImageSearch search() {
      return DQPImageSearch.create();
    }

    public DQPImageCommit commit() {
      return DQPImageCommit.create();
    }

    public DQPImageExport export() {
      return DQPImageExport.create();
    }

    public DQPImageLoad load() {
      return DQPImageLoad.create();
    }

  }

  public static final class Network {
    public DQPNetworkList list() {
      return DQPNetworkList.create();
    }

    public DQPNetworkInspect inspect() {
      return DQPNetworkInspect.create();
    }
  }

  public static final class Plugin {
    public DQPPluginInstall install() {
      return DQPPluginInstall.create();
    }

    public DQPPluginUpgrade upgrade() {
      return DQPPluginUpgrade.create();
    }
  }

  public static final class Service {
    public DQPServiceCreate create() {
      return DQPServiceCreate.create();
    }

    public DQPServiceUpdate update() {
      return DQPServiceUpdate.create();
    }

    public DQPServiceLogs logs() {
      return DQPServiceLogs.create();
    }
  }

  public static final class Swarm {
    public DQPSwarmJoin join() {
      return DQPSwarmJoin.create();
    }

    public DQPSwarmUnlock unlock() {
      return DQPSwarmUnlock.create();
    }

    public DQPSwarmUpdate update() {
      return DQPSwarmUpdate.create();
    }
  }

  public static final class System {
    public DQPSystemAuth auth() {
      return DQPSystemAuth.create();
    }
  }

  public static final class Volume {
    public DQPVolumeCreate create() {
      return DQPVolumeCreate.create();
    }

    public DQPVolumeList list() {
      return DQPVolumeList.create();
    }
  }
}
