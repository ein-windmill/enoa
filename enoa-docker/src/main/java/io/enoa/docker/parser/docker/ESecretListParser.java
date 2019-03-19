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
import io.enoa.docker.dket.docker.secret.ESecret;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ESecretListParser extends AbstractParser<List<ESecret>> {

  private static class Holder {
    private static final ESecretListParser INSTANCE = new ESecretListParser();
  }

  static ESecretListParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public List<ESecret> ok(DockerConfig config, DResp resp) {
    List<Kv> kvs = config.json().parseArray(resp.string(), Kv.class);
    if (CollectionKit.isEmpty(kvs))
      return Collections.emptyList();
    List<ESecret> rets = new ArrayList<>(kvs.size());
    kvs.forEach(kv -> rets.add(ESecretParser.instance().secret(kv)));
    CollectionKit.clear(kvs);
    return rets;
  }
}
