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

import io.enoa.docker.async.docker.eo.EAsyncEnoaDocker;
import io.enoa.docker.async.docker.generic.EAsyncGenericDocker;
import io.enoa.docker.async.docker.origin.EAsyncOriginDocker;
import io.enoa.docker.command.docker.eo.EnoaDockerImpl;
import io.enoa.docker.command.docker.eo.EoDocker;
import io.enoa.docker.command.docker.generic.EnoaGenericDocker;
import io.enoa.docker.command.docker.generic.GenericDocker;
import io.enoa.docker.command.docker.origin.EnoaTCPDocker;
import io.enoa.docker.command.docker.origin.EnoaUNIXSOCKETDocker;
import io.enoa.docker.command.docker.origin.OriginDocker;
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
  private Map<String, GenericDocker> geneicmap = new ConcurrentHashMap<>();
  private Map<String, AsyncDocker> asyncmap = new ConcurrentHashMap<>();

  private Map<String, EAsyncOriginDocker> asyncorigin = new ConcurrentHashMap<>();
  private Map<String, EAsyncGenericDocker> asyncgeneric = new ConcurrentHashMap<>();
  private Map<String, EAsyncEnoaDocker> asynceo = new ConcurrentHashMap<>();

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

  public void uninstall(String name) {
    this.originmap.remove(name);
    this.dockermap.remove(name);
    this.geneicmap.remove(name);
    this.asyncmap.remove(name);
    this.asyncgeneric.remove(name);
    this.asyncorigin.remove(name);
    this.asynceo.remove(name);
  }

  public void uninstall() {
    this.uninstall("main");
  }

  public EoDocker docker(String name) {
    if (this.dockermap.containsKey(name))
      return this.dockermap.get(name);

    GenericDocker generic = this.generic(name);
    if (generic == null)
      throw new DockerException(EnoaTipKit.message("eo.tip.docker.docker_404", name));
    EoDocker docker = new EnoaDockerImpl(generic);
    this.dockermap.put(name, docker);
    return docker;
  }

  public EoDocker docker() {
    return this.docker("main");
  }

  public GenericDocker generic(String name) {
    if (this.geneicmap.containsKey(name))
      return this.geneicmap.get(name);

    OriginDocker docker = this.origin(name);
    if (docker == null)
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.docker.docker_404", name));
    GenericDocker gdker = new EnoaGenericDocker(docker);
    this.geneicmap.put(name, gdker);
    return gdker;
  }

  public GenericDocker generic() {
    return this.generic("main");
  }

  public OriginDocker origin(String name) {
    return this.originmap.get(name);
  }

  public OriginDocker origin() {
    return this.origin("main");
  }

  public AsyncDocker async(String name) {
    AsyncDocker async = this.asyncmap.get(name);
    if (async != null)
      return async;
    if (!this.originmap.containsKey(name))
      throw new IllegalArgumentException(EnoaTipKit.message("eo.tip.docker.docker_404", name));
    async = new AsyncDocker(name);
    this.asyncmap.put(name, async);
    return async;
  }

  public AsyncDocker async() {
    return this.async("main");
  }

  EAsyncOriginDocker asyncorigin(String name) {
    EAsyncOriginDocker docker = this.asyncorigin.get(name);
    if (docker != null)
      return docker;
    docker = new EAsyncOriginDocker(this.origin(name));
    this.asyncorigin.put(name, docker);
    return docker;
  }

  EAsyncGenericDocker asyncgeneric(String name) {
    EAsyncGenericDocker docker = this.asyncgeneric.get(name);
    if (docker != null)
      return docker;
    docker = new EAsyncGenericDocker(this.generic(name));
    this.asyncgeneric.put(name, docker);
    return docker;
  }

  EAsyncEnoaDocker asynceo(String name) {
    EAsyncEnoaDocker docker = this.asynceo.get(name);
    if (docker != null)
      return docker;
    docker = new EAsyncEnoaDocker(this.docker(name));
    this.asynceo.put(name, docker);
    return docker;
  }


}
