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
package io.enoa.docker.dqp.docker.container;

import io.enoa.docker.dqp.DQP;
import io.enoa.docker.dqp.DQR;
import io.enoa.toolkit.map.Kv;

import java.util.List;

public class DQPContainerCreate implements DQP {

  public static DQPContainerCreate create() {
    return new DQPContainerCreate();
  }

  public DQPContainerCreate() {
  }

  private String Hostname;
  private String Domainname;
  private String User;
  private Boolean AttachStdin;
  private Boolean AttachStdout;
  private Boolean AttachStderr;
  private Boolean Tty;
  private Boolean OpenStdin;
  private Boolean StdinOnce;
  private List<String> Env;
  private List<String> Cmd;
  private String Entrypoint;
  private String Image;
  private Kv Labels;
  private Kv Volumes;
  private String WorkingDir;
  private Boolean NetworkDisabled;
  private String MacAddress;





  @Override
  public DQR dqr() {
    return null;
  }
}
