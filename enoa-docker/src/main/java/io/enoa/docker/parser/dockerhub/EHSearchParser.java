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
import io.enoa.docker.dket.dockerhub.search.EHSResult;
import io.enoa.docker.dket.dockerhub.search.EHSearch;
import io.enoa.docker.dket.registry.RResp;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class EHSearchParser extends AbstractDockerhubParser<EHSearch> {
  private static class Holder {
    private static final EHSearchParser INSTANCE = new EHSearchParser();
  }

  static EHSearchParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EHSearch ok(DockerhubConfig config, RResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    EHSearch.Builder builder = new EHSearch.Builder();
    HEExtra.hubpage(kv, builder);
    builder.results(this.results(kv));
    CollectionKit.clear(kv);
    return builder.build();
  }

  private List<EHSResult> results(Kv kv) {
    Collection<Kv> kvs = HEExtra.kvs(kv, "results");
    List<EHSResult> rets = new ArrayList<>(kvs.size());
    kvs.forEach(ks -> {
      EHSResult.Builder builder = new EHSResult.Builder()
        .reponame(ks.string("repo_name"))
        .shortdescription(ks.string("short_description"))
        .starcount(ks.integer("star_count"))
        .pullcount(ks.longer("pull_count"))
        .repoowner(ks.string("repo_owner"))
        .isautomated(ks.bool("is_automated"))
        .isofficial(ks.bool("is_official"));
      rets.add(builder.build());
    });
    CollectionKit.clear(kvs);
    return rets;
  }
}
