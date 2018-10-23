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
package io.enoa.docker.dqp.common;

import io.enoa.docker.dqp.DQH;
import io.enoa.docker.dqp.DQP;

public abstract class DQPRegistryAuth implements DQP {


  private String registryauth;


  public DQPRegistryAuth registryauth(String registryauth) {
    this.registryauth = registryauth;
    return this;
  }

  @Override
  public DQH dqh() {
    return DQH.create()
      .addIf("X-Registry-Auth", this.registryauth);
  }
}
