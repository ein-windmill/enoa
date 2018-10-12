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
package io.enoa.docker.dqp.plugin;

import io.enoa.docker.dqp.DQH;
import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

public class DQPPluginInstall implements DQP {

  private String remote;
  private String name;

  private String registryauth;

  public static DQPPluginInstall create() {
    return new DQPPluginInstall();
  }

  public DQPPluginInstall remote(String remote) {
    this.remote = remote;
    return this;
  }

  public DQPPluginInstall name(String name) {
    this.name = name;
    return this;
  }

  public DQPPluginInstall registryauth(String registryauth) {
    this.registryauth = registryauth;
    return this;
  }

  @Override
  public DQR dqr() {
    return DQR.create()
      .putIf("remote", this.remote)
      .putIf("name", this.name);
  }

  @Override
  public DQH dqh() {
    return DQH.create()
      .addIf("X-Registry-Auth", this.registryauth);
  }
}
