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
package io.enoa.docker.parser.docker;

import io.enoa.docker.DockerConfig;
import io.enoa.docker.dket.docker.DResp;
import io.enoa.docker.dket.docker.exec.EExecInspect;
import io.enoa.docker.dket.docker.exec.EProcessConfig;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.map.Kv;

import java.util.Map;

class EExecInspectParser extends AbstractParser<EExecInspect> {

  private static class Holder {
    private static final EExecInspectParser INSTANCE = new EExecInspectParser();
  }


  static EExecInspectParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EExecInspect ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    if (Is.empty(kv))
      return null;
    EExecInspect.Builder builder = new EExecInspect.Builder()
      .canremove(kv.bool("CanRemove"))
      .containerid(kv.string("ContainerID"))
      .detachkeys(kv.string("DetachKeys"))
      .exitcode(kv.integer("ExitCode"))
      .id(kv.string("ID"))
      .openstderr(kv.bool("OpenStderr"))
      .openstdin(kv.bool("OpenStdin"))
      .openstdout(kv.bool("OpenStdout"))
      .running(kv.bool("Running"))
      .pid(kv.integer("Pid"))
      .processconfig(this.processconfig(kv));
    CollectionKit.clear(kv);
    return builder.build();
  }

  private EProcessConfig processconfig(Kv kv) {
    Object pct = kv.get("ProcessConfig");
    if (!(pct instanceof Map))
      return null;
    Kv pcm = Kv.by((Map) pct);
    EProcessConfig.Builder builder = new EProcessConfig.Builder()
      .arguments(AEExtra.stringarray(pcm, "arguments"))
      .entrypoint(pcm.string("entrypoint"))
      .privileged(pcm.bool("privileged"))
      .tty(pcm.bool("tty"))
      .user(pcm.string("user"));
    CollectionKit.clear(pcm);
    return builder.build();
  }

}
