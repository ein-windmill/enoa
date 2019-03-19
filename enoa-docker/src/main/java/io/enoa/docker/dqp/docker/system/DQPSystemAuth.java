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
package io.enoa.docker.dqp.docker.system;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;

public class DQPSystemAuth implements DQP {

  private String username;
  private String password;
  private String email;
  private String serveraddress;

  public static DQPSystemAuth create() {
    return new DQPSystemAuth();
  }

  public DQPSystemAuth username(String username) {
    this.username = username;
    return this;
  }

  public DQPSystemAuth password(String password) {
    this.password = password;
    return this;
  }

  public DQPSystemAuth email(String email) {
    this.email = email;
    return this;
  }

  public DQPSystemAuth serveraddress(String serveraddress) {
    this.serveraddress = serveraddress;
    return this;
  }

  @Override
  public DQR dqr() {
    return DQR.create()
      .putIf("username", this.username)
      .putIf("password", this.password)
      .putIf("email", this.email)
      .putIf("serveraddress", this.serveraddress);
  }
}
