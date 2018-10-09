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

public class DQPRemove implements DQP {


  /**
   * boolean
   * false
   * <p>
   * Remove the volumes associated with the container.
   */
  private boolean volumes;
  private boolean force;
  private boolean link;


  public static DQPRemove create() {
    return new DQPRemove();
  }

  public DQPRemove() {
  }

  public DQPRemove volumes() {
    return this.volumes(Boolean.TRUE);
  }

  public DQPRemove volumes(boolean volumes) {
    this.volumes = volumes;
    return this;
  }

  public DQPRemove force() {
    return this.force(Boolean.TRUE);
  }

  public DQPRemove force(boolean force) {
    this.force = force;
    return this;
  }

  public DQPRemove link() {
    return this.link(Boolean.TRUE);
  }

  public DQPRemove link(boolean link) {
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
