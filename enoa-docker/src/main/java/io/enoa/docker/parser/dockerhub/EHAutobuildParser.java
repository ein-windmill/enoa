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
import io.enoa.docker.dket.dockerhub.build.EHAutobuild;
import io.enoa.docker.dket.dockerhub.build.EHBuildTag;
import io.enoa.docker.dket.registry.RResp;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class EHAutobuildParser extends AbstractDockerhubParser<EHAutobuild> {

  private static class Holder {
    private static final EHAutobuildParser INSTANCE = new EHAutobuildParser();
  }

  static EHAutobuildParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EHAutobuild ok(DockerhubConfig config, RResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    EHAutobuild.Builder builder = new EHAutobuild.Builder()
      .repository(kv.integer("repository"))
      .buildname(kv.string("build_name"))
      .provider(kv.string("provider"))
      .sourceurl(kv.string("source_url"))
      .dockerurl(kv.string("docker_url"))
      .repoweburl(kv.string("repo_web_url"))
      .repotype(kv.string("repo_type"))
      .active(kv.bool("active"))
      .repoid(kv.string("repo_id"))
      .deploykey(kv.string("deploykey"))
      .webhookid(kv.string("hook_id"))
      .hookid(kv.integer("webhook_id"))
      .buildtags(this.tags(kv));
    CollectionKit.clear(kv);
    return builder.build();
  }


  private List<EHBuildTag> tags(Kv kv) {
    Collection<Kv> kvs = HEExtra.kvs(kv, "build_tags");
    List<EHBuildTag> rets = new ArrayList<>(kvs.size());
    kvs.forEach(ks -> {
      EHBuildTag.Builder builder = new EHBuildTag.Builder()
        .id(ks.integer("id"))
        .name(ks.string("name"))
        .dockerfilelocation(ks.string("dockerfile_location"))
        .sourcename(ks.string("source_name"))
        .sourcetype(ks.string("source_type"));
      rets.add(builder.build());
    });
    CollectionKit.clear(kvs);
    return rets;
  }

}
