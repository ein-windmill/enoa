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
package io.enoa.docker;

import io.enoa.docker.command.eo.EnoaDockerImpl;
import io.enoa.docker.command.eo.EoDocker;
import io.enoa.docker.command.geneic.EnoaGenericDocker;
import io.enoa.docker.command.geneic.GeneicDocker;
import io.enoa.docker.command.origin.EnoaTCPDocker;
import io.enoa.docker.command.origin.EnoaUNIXSOCKETDocker;
import io.enoa.docker.command.origin.OriginDocker;
import io.enoa.docker.thr.DockerException;
import io.enoa.toolkit.eo.tip.EnoaTipKit;
import io.enoa.toolkit.text.TextKit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EPMDocker {

  private static class Holder {
    private static final EPMDocker INSTANCE = new EPMDocker();
  }

  static EPMDocker instance() {
    return Holder.INSTANCE;
  }

  private Map<String, OriginDocker> originmap = new ConcurrentHashMap<>();
  private Map<String, EoDocker> dockermap = new ConcurrentHashMap<>();
  private Map<String, GeneicDocker> geneicmap = new ConcurrentHashMap<>();

  private boolean exists(String name) {
    return this.dockermap.containsKey(name);
  }

  public void install(String name, DockerConfig config) {
    if (this.exists(name))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.docker.name_exists"));
    String host = config.host();
    if (TextKit.blanky(host))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.docker.host_null"));

    host = host.toLowerCase();
    if (host.startsWith("tcp://") ||
      host.startsWith("http://") ||
      host.startsWith("https://")) {
      EnoaTCPDocker tcpd = new EnoaTCPDocker(config);
      this.originmap.put(name, tcpd);
      return;
    }
    if (host.startsWith("unix://")) {
      EnoaUNIXSOCKETDocker uxins = new EnoaUNIXSOCKETDocker(config);
      this.originmap.put(name, uxins);
      return;
    }
    throw new DockerException(EnoaTipKit.message("eo.tip.docker.illegal_host"));
  }

  public void install(DockerConfig config) {
    this.install("main", config);
  }

  public EoDocker docker(String name) {
    if (this.dockermap.containsKey(name))
      return this.dockermap.get(name);

    GeneicDocker generic = this.generic(name);
    if (generic == null)
      throw new DockerException(EnoaTipKit.message("eo.tip.docker.docker_404", name));
    EoDocker docker = new EnoaDockerImpl(generic);
    this.dockermap.put(name, docker);
    return docker;
  }

  public EoDocker docker() {
    return this.docker("main");
  }

  public GeneicDocker generic(String name) {
    if (this.geneicmap.containsKey(name))
      return this.geneicmap.get(name);

    OriginDocker docker = this.origin(name);
    if (docker == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.docker.docker_404", name));
    GeneicDocker gdker = new EnoaGenericDocker(docker);
    this.geneicmap.put(name, gdker);
    return gdker;
  }

  public GeneicDocker generic() {
    return this.generic("main");
  }

  public OriginDocker origin(String name) {
    return this.originmap.get(name);
  }

  public OriginDocker origin() {
    return this.origin("main");
  }


}
