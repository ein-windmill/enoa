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
import io.enoa.docker.dret.image.*;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.map.Kv;

import java.util.Map;

class EImageInspectParser extends AbstractParser<EIInspect> {

  private static class Holder {
    private static final EImageInspectParser INSTANCE = new EImageInspectParser();
  }

  static EImageInspectParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EIInspect ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    EContainerConfig containerconfig = this.containerconfig(kv);
    EIInspect.Builder builder = new EIInspect.Builder()
      .id(kv.string("Id"))
      .repotags(AEExtra.stringarray(kv, "RepoTags"))
      .repodigests(AEExtra.stringarray(kv, "RepoDigests"))
      .parent(kv.string("Parent"))
      .comment(kv.string("Comment"))
      .created(DateKit.parse(kv.string("Created"), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .container(kv.string("Container"))
      .containerconfig(containerconfig)
      .config(containerconfig)
      .dockerversion(kv.string("DockerVersion"))
      .author(kv.string("Author"))
      .architecture(kv.string("Architecture"))
      .os(kv.string("Os"))
      .osversion(kv.string("OsVersion"))
      .size(kv.longer("Size"))
      .virtualsize(kv.longer("VirtualSize"))
      .graphdriver(AEExtra.graphdriver(kv, "GraphDriver"))
      .rootfs(this.rootfs(kv))
      .metadata(this.metadata(kv));
    CollectionKit.clear(kv);
    return builder.build();
  }


  private EIMetadata metadata(Kv kv) {
    Object mot = kv.get("Metadata");
    if (!(mot instanceof Map))
      return null;
    Kv metadata = Kv.by((Map) mot);
    EIMetadata.Builder builder = new EIMetadata.Builder()
      .lasttagtime(DateKit.parse(metadata.string("LastTagTime"), "yyyy-MM-dd'T'HH:mm:ss.SSS"));
    CollectionKit.clear(metadata);
    return builder.build();
  }

  private ERootFS rootfs(Kv kv) {
    Object rotf = kv.get("RootFS");
    if (!(rotf instanceof Map))
      return null;
    Kv rootfs = Kv.by((Map) rotf);
    ERootFS.Builder builder = new ERootFS.Builder()
      .type(rootfs.string("Type"))
      .layers(AEExtra.stringarray(rootfs, "Layers"))
      .baselayer(rootfs.string("BaseLayer"));
    return builder.build();
  }

  private EContainerConfig containerconfig(Kv kv) {
    Object cog = kv.get("Config");
    if (!(cog instanceof Map))
      return null;
    Kv config = Kv.by((Map) cog);
    EContainerConfig.Builder builder = new EContainerConfig.Builder()
      .hostname(config.string("Hostname"))
      .domainname(config.string("Domainname"))
      .user(config.string(" User"))
      .attachstdin(config.bool("AttachStdin"))
      .attachstdout(config.bool("AttachStdout"))
      .attachstderr(config.bool("AttachStderr"))
      .exposedports(Kv.by(config.as("ExposedPorts")))
      .tty(config.bool("Tty"))
      .openstdin(config.bool("OpenStdin"))
      .stdinonce(config.bool("StdinOnce"))
      .env(AEExtra.stringarray(config, "Env"))
      .cmd(AEExtra.stringarray(config, "Cmd"))
      .healthcheck(this.healthcheck(config))
      .argsescaped(config.bool("ArgsEscaped"))
      .image(config.string("Image"))
      .volumes(Kv.by(config.as("Volumes")))
      .workingdir(config.string("WorkingDir"))
      .entrypoint(AEExtra.stringarray(config, "Entrypoint"))
      .networkdisabled(kv.bool("NetworkDisabled"))
      .macaddress(kv.string("MacAddress"))
      .onbuild(AEExtra.stringarray(config, "OnBuild"))
      .labels(Kv.by(config.as("Labels")))
      .stopsignal(config.string("StopSignal", "SIGTERM"))
      .stoptimeout(config.integer("StopTimeout", 10))
      .shell(AEExtra.stringarray(config, "Shell"));
    CollectionKit.clear(config);
    return builder.build();
  }

  private EHealthcheck healthcheck(Kv kv) {
    Object hto = kv.get("Healthcheck");
    if (!(hto instanceof Map))
      return null;
    Kv healthcheck = Kv.by((Map) hto);
    EHealthcheck.Builder builder = new EHealthcheck.Builder()
      .test(AEExtra.stringarray(healthcheck, "Test"))
      .interval(healthcheck.integer("Interval"))
      .timeout(healthcheck.integer("Timeout"))
      .retries(healthcheck.integer("Retries"))
      .startperiod(healthcheck.integer("StartPeriod"));
    CollectionKit.clear(healthcheck);
    return builder.build();
  }

}
