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
import io.enoa.docker.dket.docker.container.ECError;
import io.enoa.docker.dket.docker.container.ECWait;
import io.enoa.toolkit.collection.CollectionKit;
import io.enoa.toolkit.map.Kv;

import java.util.Map;

class EWaitParser extends AbstractParser<ECWait> {

  private static class Holder {
    private static final EWaitParser INSTANCE = new EWaitParser();
  }

  static EWaitParser instance() {
    return Holder.INSTANCE;
  }

  @Override
  protected OkCheck isok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    try {
      if (kv.integer("StatusCode") != 0)
        return OkCheck.fail(kv.string("Error"));
      return OkCheck.ok();
    } finally {
      CollectionKit.clear(kv);
    }
  }

  @Override
  public ECWait ok(DockerConfig config, DResp resp) {
    Kv kv = config.json().parse(resp.string(), Kv.class);
    ECWait.Builder builder = new ECWait.Builder()
      .statuscode(kv.integer("StatusCode"));
    Object error = kv.get("Error");
    if (error instanceof Map) {
      Kv erm = Kv.by((Map) error);
      ECError.Builder eb = new ECError.Builder()
        .message(erm.string("Message"));
      builder.error(eb.build());
    }
    CollectionKit.clear(kv);
    return builder.build();
  }
}
