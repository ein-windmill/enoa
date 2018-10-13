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
import io.enoa.docker.dret.system.EYVersion;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.map.Kv;

class EYVersionParser extends AbstractParser<EYVersion> {

  private static class Holder {
    private static final EYVersionParser INSTANCE = new EYVersionParser();
  }

  static EYVersionParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EYVersion ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    EYVersion.Builder builder = new EYVersion.Builder()
      .version(kv.string("Version"))
      .os(kv.string("Os"))
      .kernelversion(kv.string("KernelVersion"))
      .goversion(kv.string("GoVersion"))
      .gitcommit(kv.string("GitCommit"))
      .arch(kv.string("Arch"))
      .apiversion(kv.string("ApiVersion"))
      .minapiversion(kv.string("MinAPIVersion"))
      .buildtime(DateKit.parse(kv.string("BuildTime"), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .experimental(kv.bool("Experimental"));
    CollectionKit.clear(kv);
    return builder.build();
  }
}
