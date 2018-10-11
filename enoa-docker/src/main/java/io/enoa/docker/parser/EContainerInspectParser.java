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
package io.enoa.docker.parser;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.dret.DResp;
import io.enoa.docker.dret.container.ECConfig;
import io.enoa.docker.dret.container.ECInspect;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.map.Kv;

import java.util.Map;

class EContainerInspectParser extends AbstractParser<ECInspect> {

  private static class Holder {
    private static final EContainerInspectParser INSTANCE = new EContainerInspectParser();
  }

  static EContainerInspectParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public ECInspect ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    ECInspect.Builder builder = new ECInspect.Builder();
    builder.id(kv.string("Id"))
      .created(DateKit.parse(kv.string("Created"), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .path(kv.string("Path"))
      .args(AEExtra.stringarray(kv, "Args"))
      .state(AEExtra.inspectstate(kv))
      .image(kv.string("Image"))
      .resolvconfpath(kv.string("ResolvConfPath"))
      .hostnamepath(kv.string("HostnamePath"))
      .hostspath(kv.string("HostsPath"))
      .logpath(kv.string("LogPath"))
      .name(kv.string("Name"))
      .restartcount(kv.integer("RestartCount"))
      .driver(kv.string("Driver"))
      .platform(kv.string("Platform"))
      .mountlabel(kv.string("MountLabel"))
      .processlabel(kv.string("ProcessLabel"))
      .apparmorprofile(kv.string("AppArmorProfile"))
      .execids(AEExtra.stringarray(kv, "ExecIDs"))
      .hostconfig(AEExtra.hostconfig(kv))
      .graphdriver(AEExtra.graphdriver(kv, "GraphDriver"))
      .mounts(AEExtra.mounts(kv))
      .networksetting(AEExtra.networksetting(kv))
      .config(this.config(kv));
    CollectionKit.clear(kv);
    return builder.build();
  }


  private ECConfig config(Kv kv) {
    Object config = kv.get("Config");
    if (!(config instanceof Map))
      return null;
    Kv cgm = Kv.by((Map) config);
    ECConfig.Builder builder = new ECConfig.Builder();
    builder.hostname(cgm.string("Hostname"))
      .domainname(cgm.string("Domainname"))
      .user(cgm.string("User"))
      .attachstdin(cgm.bool("AttachStdin"))
      .attachstdout(cgm.bool("AttachStdout"))
      .attachstderr(cgm.bool("AttachStderr"))
      .tty(cgm.bool("Tty"))
      .openstdin(cgm.bool("OpenStdin"))
      .stdinonce(cgm.bool("StdinOnce"))
      .image(kv.string("Image"))
      .workingdir(kv.string("WorkingDir"))
      .entrypoint(kv.get("Entrypoint"))
      .onbuild(kv.get("OnBuild"))
      .labels(kv.get("Labels"))
      .cmd(AEExtra.stringarray(cgm, "Cmd"))
      .env(AEExtra.stringarray(cgm, "Env"));

    Object volumes = kv.get("Volumes");
    if (volumes instanceof Map) {
      Map vem = (Map) volumes;
      builder.volumes(Kv.by(vem));
    }

    Object eps = kv.get("ExposedPorts");
    if (eps instanceof Map) {
      Map em = (Map) eps;
      builder.exposedports(Kv.by(em));
    }
    return builder.build();
  }
}
