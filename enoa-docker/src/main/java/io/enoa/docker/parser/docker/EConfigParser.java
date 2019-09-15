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
import io.enoa.docker.dket.docker.config.EConfig;
import io.enoa.docker.dket.docker.config.EConfigSpec;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.map.Kv;

import java.util.Map;

class EConfigParser extends AbstractParser<EConfig> {

  private static class Holder {
    private static final EConfigParser INSTANCE = new EConfigParser();
  }

  static EConfigParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EConfig ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    EConfig cfg = this.config(kv);
    CollectionKit.clear(kv);
    return cfg;
  }


  EConfig config(Kv kv) {
    if (Is.empty(kv))
      return null;
    EConfig.Builder builder = new EConfig.Builder()
      .id(kv.string("ID"))
      .createdat(DateKit.parse(kv.string("CreatedAt"), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .updatedat(DateKit.parse(kv.string("UpdatedAt"), "yyyy-MM-dd'T'HH:mm:ss.SSS"));
    Object spt = kv.get("Spec");
    if (spt instanceof Map) {
      Kv spec = Kv.by((Map) spt);
      EConfigSpec.Builder scb = new EConfigSpec.Builder()
        .name(spec.string("Name"));
      CollectionKit.clear(spec);
      builder.spec(scb.build());
    }
    return builder.build();
  }

}
