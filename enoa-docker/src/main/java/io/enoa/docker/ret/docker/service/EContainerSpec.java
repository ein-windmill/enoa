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
package io.enoa.docker.ret.docker.service;

import io.enoa.docker.ret.AbstractDRRet;
import io.enoa.docker.ret.docker.image.EHealthcheck;
import io.enoa.toolkit.map.Kv;

import java.util.List;

public class EContainerSpec extends AbstractDRRet {

  private String Image;
  private Kv Labels;
  private List<String> Command;
  private List<String> Args;
  private String Hostname;
  private List<String> Env;
  private String Dir;
  private String User;
  private List<String> Groups;
  private EPrivileges privileges;
  private Boolean tty;
  private Boolean OpenStdin;
  private Boolean ReadOnly;
  private EServiceMount mount;
  private String StopSignal;
  private Long StopGracePeriod;
  private EHealthcheck healthcheck;
  private List<String> Hosts;
  private EDNSConfig DNSConfig;
  private List<EServiceSecret> secrets;
  private List<ESrvConfig> configs;
  private EServiceResources Resources;
  private ESrvRestartPolicy RestartPolicy;

// todo fill it


}
