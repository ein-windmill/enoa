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
import io.enoa.docker.dket.docker.image.EImage;
import io.enoa.toolkit.is.Is;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class EImageListParser extends AbstractParser<List<EImage>> {

  private static class Holder {
    private static final EImageListParser INSTANCE = new EImageListParser();
  }

  static EImageListParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public List<EImage> ok(DockerConfig config, DResp resp) {
    List<Kv> kvs = config.json().parseArray(resp.string(), Kv.class);
    if (Is.empty(kvs))
      return Collections.emptyList();
    List<EImage> rets = new ArrayList<>(kvs.size());
    kvs.forEach(kv -> rets.add(this.image(kv)));
    return rets;
  }

  EImage image(Kv kv) {
    EImage.Builder builder = new EImage.Builder()
      .id(kv.string("Id"))
      .parentid(kv.string("ParentId"))
      .repotags(AEExtra.stringarray(kv, "RepoTags"))
      .repodigests(AEExtra.stringarray(kv, "RepoDigests"))
      .created(kv.longer("Created"))
      .size(kv.longer("Size"))
      .virtualsize(kv.longer("VirtualSize"))
      .sharedsize(kv.integer("SharedSize"))
      .labels(AEExtra.kv(kv, "Labels"))
      .containers(kv.integer("Containers"));
    return builder.build();
  }

}
