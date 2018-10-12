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
import io.enoa.docker.dret.common.EDriver;
import io.enoa.docker.dret.secret.ESecret;
import io.enoa.docker.dret.secret.ESecretSpec;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.date.DateKit;
import io.enoa.toolkit.map.Kv;

import java.util.Map;

class ESecretParser extends AbstractParser<ESecret> {

  private static class Holder {
    private static final ESecretParser INSTANCE = new ESecretParser();
  }

  static ESecretParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  public ESecret ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    ESecret secret = this.secret(kv);
    CollectionKit.clear(kv);
    return secret;
  }

  ESecret secret(Kv kv) {
    if (CollectionKit.isEmpty(kv))
      return null;
    ESecret.Builder builder = new ESecret.Builder()
      .id(kv.string("ID"))
      .version(AEExtra.version(kv))
      .createdat(DateKit.parse(kv.string("CreatedAt"), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .updatedat(DateKit.parse(kv.string("UpdatedAt"), "yyyy-MM-dd'T'HH:mm:ss.SSS"))
      .spec(this.spec(kv));
    return builder.build();
  }

  private ESecretSpec spec(Kv kv) {
    Object spt = kv.get("Spec");
    if (!(spt instanceof Map)) {
      return null;
    }
    Kv spc = Kv.by((Map) spt);
    ESecretSpec.Builder builder = new ESecretSpec.Builder()
      .name(spc.string("Name"))
      .labels(AEExtra.kv(kv, "Labels"));

    Object drt = spc.get("Driver");
    if (drt instanceof Map) {
      Kv dk = Kv.by((Map) drt);
      EDriver.Builder edb = new EDriver.Builder()
        .name(dk.string("Name"))
        .options(AEExtra.kv(dk, "Options"));
      builder.driver(edb.build());
      CollectionKit.clear(dk);
    }
    CollectionKit.clear(spc);
    return builder.build();
  }

}
