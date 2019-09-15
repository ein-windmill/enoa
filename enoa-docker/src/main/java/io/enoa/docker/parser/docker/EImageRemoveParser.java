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
import io.enoa.docker.dket.docker.image.EIRemove;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class EImageRemoveParser extends AbstractParser<List<EIRemove>> {

  private static class Holder {
    private static final EImageRemoveParser INSTANCE = new EImageRemoveParser();
  }

  static EImageRemoveParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public List<EIRemove> ok(DockerConfig config, DResp resp) {
    List<Kv> kvs = config.json().parseArray(resp.string(), Kv.class);
    if (Is.empty(kvs))
      return Collections.emptyList();
    List<EIRemove> rets = new ArrayList<>(kvs.size());
    kvs.forEach(kv -> {
      EIRemove.Builder builder = new EIRemove.Builder()
        .untagged(kv.string("Untagged"))
        .deleted(kv.string("Deleted"));
      rets.add(builder.build());
    });
    CollectionKit.clear(kvs);
    return rets;
  }
}
