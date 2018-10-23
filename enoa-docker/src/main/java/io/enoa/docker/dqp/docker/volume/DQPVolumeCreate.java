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
package io.enoa.docker.dqp.docker.volume;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;
import io.enoa.toolkit.map.Kv;

public class DQPVolumeCreate implements DQP {

  private String name;
  private String driver;
  private Kv driveropts;
  private Kv labels;


  public DQPVolumeCreate() {
    this.driver = "local";
  }

  public DQPVolumeCreate name(String name) {
    this.name = name;
    return this;
  }

  public DQPVolumeCreate driver(String driver) {
    this.driver = driver;
    return this;
  }

  public DQPVolumeCreate driveropts(String key, String value) {
    if (this.driveropts == null)
      this.driveropts = Kv.create();
    this.driveropts.set(key, value);
    return this;
  }

  public DQPVolumeCreate driveropts(Kv driveropts) {
    this.driveropts = driveropts;
    return this;
  }

  public DQPVolumeCreate labels(String key, String value) {
    if (this.labels == null)
      this.labels = Kv.create();
    this.labels.set(key, value);
    return this;
  }

  public DQPVolumeCreate labels(Kv labels) {
    this.labels = labels;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create()
      .putIf("Name", this.name)
      .putIf("Driver", this.driver)
      .putIf("Labels", this.labels)
      .putIf("DriverOpts", this.driveropts);
    return dqr;
  }
}
