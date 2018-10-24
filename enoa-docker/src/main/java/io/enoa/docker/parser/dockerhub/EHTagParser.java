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
package io.enoa.docker.parser.dockerhub;


import io.enoa.docker.DockerhubConfig;
import io.enoa.docker.ret.dockerhub.tag.EHImage;
import io.enoa.docker.ret.dockerhub.tag.EHTResult;
import io.enoa.docker.ret.dockerhub.tag.EHTag;
import io.enoa.docker.ret.registry.RResp;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class EHTagParser extends AbstractDockerhubParser<EHTag> {

  private static class Holder {
    private static final EHTagParser INSTANCE = new EHTagParser();
  }

  static EHTagParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EHTag ok(DockerhubConfig config, RResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    EHTag.Builder builder = new EHTag.Builder();
    HEExtra.hubpage(kv, builder);
    builder.results(this.results(kv));
    return builder.build();
  }

  public List<EHTResult> results(Kv kv) {
    Collection<Kv> kvs = HEExtra.kvs(kv, "results");
    List<EHTResult> rets = new ArrayList<>(kvs.size());
    kvs.forEach(ks -> {
      EHTResult.Builder builder = new EHTResult.Builder()
        .name(ks.string("name"))
        .fullsize(ks.longer("full_size"))
        .id(ks.integer("id"))
        .repository(ks.integer("repository"))
        .creator(ks.integer("creator"))
        .lastupdater(ks.integer("last_updater"))
        .lastupdated(HEExtra.date(ks, "last_updated"))
        .imageid(ks.string("image_id"))
        .v2(ks.bool("v2"))
        .images(this.images(ks));
      rets.add(builder.build());
    });
    CollectionKit.clear(kvs);
    return rets;
  }

  private List<EHImage> images(Kv kv) {
    Collection<Kv> kvs = HEExtra.kvs(kv, "images");
    List<EHImage> rets = new ArrayList<>(kvs.size());
    kvs.forEach(ks -> {
      EHImage.Builder builder = new EHImage.Builder()
        .size(ks.longer("size"))
        .architecture(ks.string("architecture"))
        .variant(ks.string("variant"))
        .features(ks.string("features"))
        .os(ks.string("os"))
        .osversion(ks.string("os_version"))
        .osfeatures(ks.string("os_features"));
      rets.add(builder.build());
    });
    CollectionKit.clear(kvs);
    return rets;
  }
}
