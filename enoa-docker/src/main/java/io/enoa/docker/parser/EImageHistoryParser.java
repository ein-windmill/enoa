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
import io.enoa.docker.dret.DResp;
import io.enoa.docker.dret.image.EHistory;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class EImageHistoryParser extends AbstractParser<List<EHistory>> {

  private static class Holder {
    private static final EImageHistoryParser INSTANCE = new EImageHistoryParser();
  }

  static EImageHistoryParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public List<EHistory> ok(DockerConfig config, DResp resp) {
    List<Kv> kvs = config.json().parseArray(resp.string(), Kv.class);
    if (CollectionKit.isEmpty(kvs))
      return Collections.emptyList();
    List<EHistory> rets = new ArrayList<>(kvs.size());
    kvs.forEach(kv -> {
      EHistory.Builder builder = new EHistory.Builder()
        .id(kv.string("Id"))
        .created(kv.longer("Created"))
        .createdby(kv.string("CreatedBy"))
        .tags(AEExtra.stringarray(kv, "Tags"))
        .size(kv.longer("Size"))
        .comment(kv.string("Comment"));
      rets.add(builder.build());
    });
    return rets;
  }

}
