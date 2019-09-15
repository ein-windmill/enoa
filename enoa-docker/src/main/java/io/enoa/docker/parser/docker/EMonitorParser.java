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
import io.enoa.docker.dket.docker.system.EActor;
import io.enoa.docker.dket.docker.system.EMonitor;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.map.Kv;

import java.util.Map;

class EMonitorParser extends AbstractParser<EMonitor> {

  private static class Holder {
    private static final EMonitorParser INSTANCE = new EMonitorParser();
  }

  static EMonitorParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EMonitor ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    if (Is.empty(kv))
      return null;
    EMonitor.Builder builder = new EMonitor.Builder()
      .action(kv.string("Action"))
      .type(kv.string("Type"))
      .time(kv.longer("time"))
      .actor(this.actor(kv));
    CollectionKit.clear(kv);
    return builder.build();
  }

  private EActor actor(Kv kv) {
    Object ao = kv.get("Actor");
    if (!(ao instanceof Map))
      return null;
    Kv ak = Kv.by((Map) ao);
    EActor.Builder builder = new EActor.Builder()
      .id(ak.string("ID"))
      .attributes(AEExtra.kv(ak, "Attributes"));
    CollectionKit.clear(ak);
    return builder.build();
  }

}
