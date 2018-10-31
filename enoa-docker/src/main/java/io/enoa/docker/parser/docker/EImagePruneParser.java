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
import io.enoa.docker.dket.docker.image.EImagePrune;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.*;

class EImagePruneParser extends AbstractParser<EImagePrune> {

  private static class Holder {
    private static final EImagePruneParser INSTANCE = new EImagePruneParser();
  }

  static EImagePruneParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EImagePrune ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    EImagePrune.Builder builder = new EImagePrune.Builder()
      .spacereclaimed(kv.integer("SpaceReclaimed"))
      .imagesdeleted(this.removes(kv));
    CollectionKit.clear(kv);
    return builder.build();
  }

  private List<EIRemove> removes(Kv kv) {
    Object idt = kv.get("ImagesDeleted");
    if (!(idt instanceof Collection))
      return Collections.emptyList();
    Collection rms = (Collection) idt;
    List<EIRemove> rets = new ArrayList<>(rms.size());
    rms.forEach(rm -> {
      if (!(rm instanceof Map))
        return;
      Kv remove = Kv.by((Map) rm);
      EIRemove.Builder builder = new EIRemove.Builder()
        .deleted(remove.string("Deleted"))
        .untagged(remove.string("Untagged"));
      rets.add(builder.build());
    });
    CollectionKit.clear(rms);
    return rets;
  }
}
