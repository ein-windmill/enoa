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
package io.enoa.docker.dqp.service;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

public class DQPServiceUpdate extends DQPServiceCreate implements DQP {


  private Long version;
  private String registryauthfrom;
  private String rollback;

  public DQPServiceUpdate version(Long version) {
    this.version = version;
    return this;
  }

  public DQPServiceUpdate registryauthfrom(String registryauthfrom) {
    this.registryauthfrom = registryauthfrom;
    return this;
  }

  public DQPServiceUpdate rollback(String rollback) {
    this.rollback = rollback;
    return this;
  }

  @Override
  public DQR dqr() {
    return DQR.create()
      .putIf("version", this.version)
      .putIf("registryAuthFrom", this.registryauthfrom)
      .putIf("rollback", this.rollback);
  }
}
