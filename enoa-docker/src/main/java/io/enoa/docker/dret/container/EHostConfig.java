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
package io.enoa.docker.dret.container;

import io.enoa.docker.dret.AbstractDRet;

public class EHostConfig extends AbstractDRet {

  //  private Object BlkioWeightDevice;
//  private Object BlkioDeviceReadBps;
//  private Object BlkioDeviceWriteBps;
//  private Object BlkioDeviceReadIOps;
//  private Object BlkioDeviceWriteIOps;
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
  /*
    "RestartPolicy": {
      "MaximumRetryCount": 2,
      "Name": "on-failure"
    },
    "LogConfig": {
      "Type": "json-file"
    },
    "Sysctls": {
      "net.ipv4.ip_forward": "1"
    },
    "Ulimits": [
      {}
    ],
   */

  public EHostConfig(Builder builder) {
    this.networkmode = builder.networkmode;
  }

  public String networkmode() {
    return this.networkmode;
  }

  public static class Builder {
    private String networkmode;

    public EHostConfig build() {
      return new EHostConfig(this);
    }

    public Builder networkmode(String networkmode) {
      this.networkmode = networkmode;
      return this;
    }
  }
}
