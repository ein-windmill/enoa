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
import io.enoa.docker.ret.dockerhub.explore.EHEResult;
import io.enoa.docker.ret.dockerhub.explore.EHExplore;
import io.enoa.docker.ret.registry.RResp;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class EHExploreParser extends AbstractDockerhubParser<EHExplore> {

  private static class Holder {
    private static final EHExploreParser INSTANCE = new EHExploreParser();
  }

  static EHExploreParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EHExplore ok(DockerhubConfig config, RResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    EHExplore.Builder builder = new EHExplore.Builder();
    HEExtra.hubpage(kv, builder);
    builder.results(this.results(kv));
    CollectionKit.clear(kv);
    return builder.build();
  }


  private List<EHEResult> results(Kv kv) {
    Collection<Kv> clks = HEExtra.kvs(kv, "results");
    List<EHEResult> rets = new ArrayList<>(clks.size());
    clks.forEach(ks -> {
      EHEResult.Builder builder = new EHEResult.Builder()
        .user(ks.string("user"))
        .name(ks.string("name"))
        .namespace(ks.string("namespace"))
        .type(ks.string("repository_type"))
        .starcount(ks.integer("status"))
        .description(ks.string("description"))
        .isprivate(ks.bool("is_private"))
        .isautomated(ks.bool("is_automated"))
        .canedit(ks.bool("can_edit"))
        .starcount(ks.integer("star_count"))
        .pullcount(ks.longer("pull_count"))
        .lastupdated(HEExtra.date(kv, "last_updated"));
      rets.add(builder.build());
    });
    CollectionKit.clear(clks);
    return rets;
  }

}
