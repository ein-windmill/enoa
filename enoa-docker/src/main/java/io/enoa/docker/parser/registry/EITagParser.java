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
package io.enoa.docker.parser.registry;

import io.enoa.docker.RegistryConfig;
import io.enoa.docker.dket.registry.RResp;
import io.enoa.docker.dket.registry.tag.EITag;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

class EITagParser extends AbstractParser<EITag> {

  private static class Holder {
    private static final EITagParser INSTANCE = new EITagParser();
  }

  static EITagParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EITag ok(RegistryConfig config, RResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    EITag.Builder builder = new EITag.Builder()
      .name(kv.string("name"))
      .tags(REExtra.array(kv, "tags"));
    CollectionKit.clear(kv);
    return builder.build();
  }
}
