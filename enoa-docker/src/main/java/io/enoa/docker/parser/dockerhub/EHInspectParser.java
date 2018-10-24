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
import io.enoa.docker.ret.dockerhub.inspece.EHPermission;
import io.enoa.docker.ret.dockerhub.inspece.EHRepository;
import io.enoa.docker.ret.registry.RResp;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.Map;

class EHInspectParser extends AbstractDockerhubParser<EHRepository> {

  private static class Holder {
    private static final EHInspectParser INSTANCE = new EHInspectParser();
  }

  static EHInspectParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public EHRepository ok(DockerhubConfig config, RResp resp) {

    Kv kv = config.json().parse(resp.string(), Kv.class);
    EHRepository.Builder builder = new EHRepository.Builder()
      .user(kv.string("user"))
      .name(kv.string("name"))
      .namespace(kv.string("namespace"))
      .type(kv.string("repository_type"))
      .status(kv.integer("status"))
      .description(kv.string("description"))
      .isprivate(kv.bool("is_private"))
      .isautomated(kv.bool("is_automated"))
      .canedit(kv.bool("can_edit"))
      .starcount(kv.integer("star_count"))
      .pullcount(kv.longer("pull_count"))
      .lastupdated(HEExtra.date(kv, "last_updated"))
      .hasstarred(kv.bool("has_starred"))
      .fulldescription(kv.string("full_description"))
      .affiliation(kv.string("affiliation"))
      .permission(this.permission(kv));
    CollectionKit.clear(kv);
    return builder.build();
  }

  private EHPermission permission(Kv kv) {
    Object pero = kv.get("permissions");
    if (!(pero instanceof Map)) {
      return null;
    }
    Kv pk = Kv.by((Map) pero);
    EHPermission.Builder builder = new EHPermission.Builder()
      .read(pk.bool("read"))
      .write(pk.bool("write"))
      .admin(pk.bool("admin"));
    CollectionKit.clear(pk);
    return builder.build();
  }
}
