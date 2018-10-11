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
package io.enoa.docker.dqp.swarm;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

public class DQPSwarmJoin implements DQP {

  private String listenaddr;
  private String advertiseaddr;
  private String datapathaddr;
  private String remoteaddrs;
  private String jointoken;

  public static DQPSwarmJoin create() {
    return new DQPSwarmJoin();
  }

  public DQPSwarmJoin listenaddr(String listenaddr) {
    this.listenaddr = listenaddr;
    return this;
  }

  public DQPSwarmJoin advertiseaddr(String advertiseaddr) {
    this.advertiseaddr = advertiseaddr;
    return this;
  }

  public DQPSwarmJoin datapathaddr(String datapathaddr) {
    this.datapathaddr = datapathaddr;
    return this;
  }

  public DQPSwarmJoin remoteaddrs(String remoteaddrs) {
    this.remoteaddrs = remoteaddrs;
    return this;
  }

  public DQPSwarmJoin jointoken(String jointoken) {
    this.jointoken = jointoken;
    return this;
  }

  @Override
  public DQR dqr() {
    return DQR.create()
      .putIf("ListenAddr", this.listenaddr)
      .putIf("AdvertiseAddr", this.advertiseaddr)
      .putIf("DataPathAddr", this.datapathaddr)
      .putIf("RemoteAddrs", this.remoteaddrs)
      .putIf("JoinToken", this.jointoken);
  }
}
