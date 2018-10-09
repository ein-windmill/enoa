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

import java.util.Date;
import java.util.Map;

public class EStatistics extends AbstractDRet {


  private final String id;
  private final String name;
  private final Date read;
  private final Date preread;
  private final EPidStats pidstats;
  private final Map<String, EEth> networks;
  private final EMemoryStats memorystats;
  private final EBlkioStats blkiostats;
  private final Integer numprocs;
  private final Object storagestats;
  private final ECpuStats cpustats;
  private final ECpuStats precpustats;

  public EStatistics(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.read = builder.read;
    this.preread = builder.preread;
    this.pidstats = builder.pidstats;
    this.networks = builder.networks;
    this.memorystats = builder.memorystats;
    this.blkiostats = builder.blkiostats;
    this.numprocs = builder.numprocs;
    this.storagestats = builder.storagestats;
    this.cpustats = builder.cpustats;
    this.precpustats = builder.precpustats;
  }

  public String id() {
    return id;
  }

  public String name() {
    return name;
  }

  public Date read() {
    return read;
  }

  public Date preread() {
    return preread;
  }

  public EPidStats pidstats() {
    return pidstats;
  }

  public Map<String, EEth> networks() {
    return networks;
  }

  public EMemoryStats memorystats() {
    return memorystats;
  }

  public EBlkioStats blkiostats() {
    return blkiostats;
  }

  public Integer numprocs() {
    return numprocs;
  }

  public Object storagestats() {
    return storagestats;
  }

  public ECpuStats cpustats() {
    return cpustats;
  }

  public ECpuStats precpustats() {
    return precpustats;
  }

  public static class Builder {

    private String id;
    private String name;
    private Date read;
    private Date preread;
    private EPidStats pidstats;
    private Map<String, EEth> networks;
    private EMemoryStats memorystats;
    private EBlkioStats blkiostats;
    private Integer numprocs;
    private Object storagestats;
    private ECpuStats cpustats;
    private ECpuStats precpustats;

    public EStatistics build() {
      return new EStatistics(this);
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder read(Date read) {
      this.read = read;
      return this;
    }

    public Builder preread(Date preread) {
      this.preread = preread;
      return this;
    }

    public Builder pidstats(EPidStats pidstats) {
      this.pidstats = pidstats;
      return this;
    }

    public Builder networks(Map<String, EEth> networks) {
      this.networks = networks;
      return this;
    }

    public Builder memorystats(EMemoryStats memorystats) {
      this.memorystats = memorystats;
      return this;
    }

    public Builder blkiostats(EBlkioStats blkiostats) {
      this.blkiostats = blkiostats;
      return this;
    }

    public Builder numprocs(Integer numprocs) {
      this.numprocs = numprocs;
      return this;
    }

    public Builder storagestats(Object storagestats) {
      this.storagestats = storagestats;
      return this;
    }

    public Builder cpustats(ECpuStats cpustats) {
      this.cpustats = cpustats;
      return this;
    }

    public Builder precpustats(ECpuStats precpustats) {
      this.precpustats = precpustats;
      return this;
    }
  }

}
