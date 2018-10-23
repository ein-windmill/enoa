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
import io.enoa.docker.ret.docker.volume.EUseageData;
import io.enoa.docker.ret.docker.volume.EVolume;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.Map;

class EVolumeParser extends AbstractParser<EVolume> {


  private static class Holder {
    private static final EVolumeParser INSTANCE = new EVolumeParser();
  }

  static EVolumeParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EVolume ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    EVolume volume = this.volume(kv);
    CollectionKit.clear(kv);
    return volume;
  }


  EVolume volume(Kv kv) {
    EVolume.Builder builder = new EVolume.Builder()
      .name(kv.string("Name"))
      .driver(kv.string("Driver"))
      .mountpoint(kv.string("Mountpoint"))
      .createdat(AEExtra.date(kv, "CreatedAt"))
      .status(AEExtra.kv(kv, "Status"))
      .labels(AEExtra.kv(kv, "Labels"))
      .scope(kv.string("Scope", "local"))
      .options(AEExtra.kv(kv, "Options"));
    Object udo = kv.get("UsageData");
    if (udo instanceof Map) {
      Kv udk = (Kv) udo;
      EUseageData.Builder udb = new EUseageData.Builder()
        .refcount(udk.integer("RefCount"))
        .size(udk.integer("Size"));
      builder.useagedata(udb.build());
      CollectionKit.clear(udk);
    }
    return builder.build();
  }

}
