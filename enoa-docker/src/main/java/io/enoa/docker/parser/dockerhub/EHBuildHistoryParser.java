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
import io.enoa.docker.ret.dockerhub.build.EHBuildHistory;
import io.enoa.docker.ret.dockerhub.build.EHHistory;
import io.enoa.docker.ret.registry.RResp;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class EHBuildHistoryParser extends AbstractDockerhubParser<EHBuildHistory> {
  private static class Holder {
    private static final EHBuildHistoryParser INSTANCE = new EHBuildHistoryParser();
  }

  static EHBuildHistoryParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EHBuildHistory ok(DockerhubConfig config, RResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    EHBuildHistory.Builder builder = new EHBuildHistory.Builder();
    HEExtra.hubpage(kv, builder);
    builder.results(this.results(kv));
    CollectionKit.clear(kv);
    return builder.build();
  }

  private List<EHHistory> results(Kv kv) {
    Collection<Kv> kvs = HEExtra.kvs(kv, "results");
    List<EHHistory> rets = new ArrayList<>(kvs.size());
    kvs.forEach(ks -> {
      EHHistory.Builder builder = new EHHistory.Builder()
        .id(ks.integer("id"))
        .status(ks.integer("status"))
        .createddate(HEExtra.date(ks, "created_date"))
        .lastupdated(HEExtra.date(ks, "last_updated"))
        .buildcode(ks.string("build_code"))
        .dockertagname(ks.string("dockertag_name"))
        .cause(ks.string("cause"));
      rets.add(builder.build());
    });
    CollectionKit.clear(kvs);
    return rets;
  }

}
