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
import io.enoa.docker.ret.docker.DResp;
import io.enoa.docker.ret.docker.container.ECPrune;
import io.enoa.toolkit.map.Kv;

class ECPruneParser extends AbstractParser<ECPrune> {

  private static class Holder {
    private static final ECPruneParser INSTANCE = new ECPruneParser();
  }

  static ECPruneParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public ECPrune ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    ECPrune.Builder builder = new ECPrune.Builder()
      .containersdeleted(AEExtra.stringarray(kv, "ContainersDeleted"))
      .spacereclaimed(kv.integer("SpaceReclaimed"));
    return builder.build();
  }
}
