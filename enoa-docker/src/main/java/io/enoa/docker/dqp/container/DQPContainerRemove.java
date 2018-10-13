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

public class DQPContainerRemove implements DQP {


  /**
   * boolean
   * false
   * <p>
   * Remove the volumes associated with the container.
   */
  private boolean volumes;
  private boolean force;
  private boolean link;


  public static DQPContainerRemove create() {
    return new DQPContainerRemove();
  }

  public DQPContainerRemove() {
  }

  public DQPContainerRemove volumes() {
    return this.volumes(Boolean.TRUE);
  }

  public DQPContainerRemove volumes(boolean volumes) {
    this.volumes = volumes;
    return this;
  }

  public DQPContainerRemove force() {
    return this.force(Boolean.TRUE);
  }

  public DQPContainerRemove force(boolean force) {
    this.force = force;
    return this;
  }

  public DQPContainerRemove link() {
    return this.link(Boolean.TRUE);
  }

  public DQPContainerRemove link(boolean link) {
    this.link = link;
    return this;
  }

  @Override
  public DQR dqr() {
    DQR dqr = DQR.create();
    if (this.volumes)
      dqr.put("v", this.volumes);
    if (this.force)
      dqr.put("force", this.force);
    if (this.link)
      dqr.put("link", this.link);
    return dqr;
  }
}
