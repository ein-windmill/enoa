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
import io.enoa.docker.dket.docker.container.EChange;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class EChangesParser extends AbstractParser<List<EChange>> {


  private static class Holder {
    private static final EChangesParser INSTANCE = new EChangesParser();
  }

  static EChangesParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public List<EChange> ok(DockerConfig config, DResp resp) {
    List<Kv> kvs = config.json().parseArray(resp.string(), Kv.class);
    if (CollectionKit.isEmpty(kvs))
      return Collections.emptyList();
    List<EChange> changes = new ArrayList<>(kvs.size());
    kvs.forEach(kv -> {
      EChange.Builder builder = new EChange.Builder();
      builder.path(kv.string("Path"))
        .kind(kv.integer("Kind"));
      changes.add(builder.build());
    });
    return changes;
  }
}
